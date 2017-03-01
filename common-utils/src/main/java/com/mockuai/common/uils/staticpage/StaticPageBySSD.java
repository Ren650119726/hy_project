package com.mockuai.common.uils.staticpage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;

public  class StaticPageBySSD implements Serializable{

   private static Logger logger = LoggerFactory.getLogger(StaticPageBySSD.class);


    //临时目录
   protected String folder=System.getProperty("java.io.tmpdir");
   public  StaticPageBySSD( ){
    }
   public  StaticPageBySSD(String folder,String environment){
         this.folder = folder+environment;
    }



    protected void createParentDirctory(String tmpFileName){
        File tmpFile = new File(tmpFileName);
        if(!tmpFile.getParentFile().exists()){
            tmpFile.getParentFile().mkdirs();
        }
    }
    protected   String getTmpFile(SerialPageEnum serialPageEnum,Long id){
        String tmpFileName =  String.format("%s/%s/%d.json",folder,serialPageEnum.getPrefix(),id);
        createParentDirctory(tmpFileName);
        return         tmpFileName;
    }
    protected String getTime(){
        return String.format("%1$tY%1$tm%1$td",Calendar.getInstance());
    }



    public void serial(SerialPageEnum serialPageEnum,Long id, String content){
        try {
            //文件存在先删除文件
            if(Files.exists(Paths.get(getTmpFile(serialPageEnum,id)))){
                Files.delete(Paths.get(getTmpFile(serialPageEnum,id)));
            }
            Files.write(Paths.get(getTmpFile(serialPageEnum,id)),content.getBytes());
        } catch (IOException e) {
            logger.error("写入序列化{}文件失败",serialPageEnum.getPrefix(),e);
            throw  new RuntimeException("写入序列化文件失败");
        }
    }


    public  String getFileContent(SerialPageEnum serialPageEnum, Long id){
        String filePath = String.format("%s/%s/%d.json",folder,serialPageEnum.getPrefix(),id);
        try {
            return  new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw  new RuntimeException(String.format("没有找到%s静态%d文件",serialPageEnum.getPrefix(),id));
        }
    }

    public void deleteFile(SerialPageEnum serialPageEnum,Long id ){
        if(Files.exists(Paths.get(getTmpFile(serialPageEnum,id)))){
            try {
                Files.delete(Paths.get(getTmpFile(serialPageEnum,id)));
            } catch (IOException e) {
               logger.error("删除文件失败！");
                throw  new RuntimeException(String.format("没有找到%s静态%d文件",serialPageEnum.getPrefix(),id));
            }
        }
    }
}