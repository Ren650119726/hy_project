package com.mockuai.common.uils.staticpage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public  class StaticPage  {

   private static Logger logger = LoggerFactory.getLogger(StaticPage.class);
   private static String OS = System.getProperty("os.name").toLowerCase();
   private  int  serialType;

   private static StaticPageByOSS staticPageByOSS;

   private static StaticPageBySSD staticPageBySSD;

   private String environment ;
   private  Properties properties = new Properties();
    public  StaticPage(){

        try {
            String tmp = "/utils.properties";
            properties.load(StaticPage.class.getResourceAsStream(tmp));
        } catch (Exception e) {
            logger.error("未找到common-utils包下的utils.properties",e);
            throw  new RuntimeException("未找到common-utils包下的utils.properties");
        }


    }

    public void setEnvironment(String environment) {
        this.environment = environment;
        logger.info("初始化静态化{}环境参数",environment);
        logger.info("type:{}", properties.getProperty("common.utils.serial.type"));
        logger.info("environment:{}",properties.getProperty("common.ssd.folder"));
        serialType = Integer.parseInt(  properties.getProperty("common.utils.serial.type"));
        staticPageBySSD = new StaticPageBySSD( properties.getProperty("common.ssd.folder"),environment);
        staticPageByOSS = new StaticPageByOSS(environment);
    }




    public  void serialPage(SerialPageEnum serialPageEnum,Long id, String content){

         if(serialType == 1){
             staticPageBySSD.serial(serialPageEnum, id, content);
         }else{
             staticPageByOSS.serial(serialPageEnum, id, content);
         }

    }

    public String getFileContent(SerialPageEnum serialPageEnum,Long id){
        if(serialType == 1){
           return staticPageBySSD.getFileContent(serialPageEnum, id);
        }else{
           return staticPageByOSS.getFileContent(serialPageEnum, id);
        }
    }

    public void deleteFile(SerialPageEnum serialPageEnum,Long id){
        if(serialType == 1){
             staticPageBySSD.deleteFile(serialPageEnum, id);
        }else{
             staticPageByOSS.deleteFile(serialPageEnum, id);
        }
    }


}