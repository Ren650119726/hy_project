package com.mockuai.common.uils.staticpage;


import com.mockuai.common.uils.HttpUtil;
import com.mockuai.common.uils.oss.OSSClientAPI;
import com.mockuai.common.uils.oss.OSSFileLinkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Properties;

public  class StaticPageByOSS extends StaticPageBySSD{

   private static Logger logger = LoggerFactory.getLogger(StaticPageByOSS.class);
   private static String OS = System.getProperty("os.name").toLowerCase();
   private static String PRIFIX_PATH = "static/";
    //临时目录
   private OSSClientAPI ossClientAPI;

    public void createParentDirctory(String tmpFileName){
        File tmpFile = new File(tmpFileName);
        if(!tmpFile.getParentFile().exists()){
            tmpFile.getParentFile().mkdirs();
        }
    }

    public StaticPageByOSS(String environment){

        Properties properties = new Properties();
        try {
           // String tmp = OS.contains("windows")?"/utils.properties":"utils.properties";
            String tmp = "/utils.properties";
            properties.load(StaticPage.class.getResourceAsStream(tmp));
        } catch (Exception e) {
            throw  new RuntimeException("未找到common-utils包下的utils.properties");
        }
        String accessKey = properties.getProperty("common.oss.accesskey.id");
        String secret = properties.getProperty("common.oss.accesskey.secret");
        ossClientAPI = new OSSClientAPI(accessKey,secret);
        PRIFIX_PATH += environment;
    }
    public void serial(SerialPageEnum serialPageEnum,Long id,String content ){

        super.serial(serialPageEnum, id, content);
        String key = String.format("%s/%s/%d.json",PRIFIX_PATH,serialPageEnum.getPrefix(),id);
        ossClientAPI.uploadFile( key,getTmpFile(serialPageEnum, id));

    }

    @Override
    public String getFileContent(SerialPageEnum serialPageEnum, Long id) {
        String key = String.format("%s/%s/%d.json",PRIFIX_PATH,serialPageEnum.getPrefix(),id);

        byte[] content =  HttpUtil.downloadFile(OSSFileLinkUtil.generateFileLink(ossClientAPI.getBucketName(),key));
        return new String(content);
    }
    @Override
    public void deleteFile(SerialPageEnum serialPageEnum,Long id ){

    }

}