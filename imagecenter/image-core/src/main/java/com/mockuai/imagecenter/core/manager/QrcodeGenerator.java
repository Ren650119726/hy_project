package com.mockuai.imagecenter.core.manager; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.common.domain.dto.QRConfig;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDetailDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.util.ImageRender;
import com.mockuai.imagecenter.core.util.QrcodeUtils;
import com.mockuai.imagecenter.core.util.Watermark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Calendar;

/**
 * Created by hy on 2016/5/25.
 */
public  class QrcodeGenerator {
    private static final Logger log = LoggerFactory.getLogger(QrcodeGenerator.class);

    //二维码配置
    private   QrcodeConfigDetailDO  qrcodeConfigDetailDO;
    //临时目录
    private String folder=System.getProperty("java.io.tmpdir");

    private String content ;

    protected String  key ;

    protected  String logoPath ;




     public QrcodeGenerator(String key, QrcodeConfigDetailDO detailDO, String content) {
        this .key =key;
        this.qrcodeConfigDetailDO = detailDO;
        this.content = content;
    }
    public QrcodeGenerator(String key,QrcodeConfigDetailDO detailDO,String content,String logoPath) {
        this(key, detailDO, content);
        this.logoPath = logoPath;
    }

     public String getQrcodeFile(){

        return  String.format("%1$s/%2$tY%2$tm%2$td/%3$s.png",folder, Calendar.getInstance(),key);
    }


    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public void drawLogo() throws IOException, ImageException {
        String outputPath =  String.format("%1$s/%2$tY%2$tm%2$td/%3$s_logo.png",folder, Calendar.getInstance(),key);
        FileInputStream qrcodeInputStream =   new FileInputStream(getQrcodeFile());
        FileInputStream logoInputStream = new FileInputStream(logoPath);
        FileOutputStream outputStream = new FileOutputStream(outputPath);
        ImageRender imageRender = new ImageRender(qrcodeInputStream, BufferedImage.TYPE_INT_ARGB);

        Watermark watermark = new Watermark();
        watermark.setGravity(Watermark.Position.CENTER);
        watermark.setQrcodeInputStream(logoInputStream);
        imageRender.drawImage(watermark);
        imageRender.render(outputStream);
        qrcodeInputStream.close();
        logoInputStream.close();
        File qrcodeFile =   new File(getQrcodeFile());
        qrcodeFile.delete();
        new File(outputPath).renameTo(qrcodeFile);
    }

     public  void generateQrcode() throws FileNotFoundException, ImageException {
        QRConfig qrConfig = new QRConfig();
        qrConfig.setImageFormat("png");
        qrConfig.setWidth(qrcodeConfigDetailDO.getWidth());
        qrConfig.setHeight(qrcodeConfigDetailDO.getHeight());
        File qrcodeFile = new File(getQrcodeFile());
        if(!qrcodeFile.getParentFile().exists()){
            qrcodeFile.getParentFile().mkdirs();
        }
        FileOutputStream outputStream = new FileOutputStream(qrcodeFile);
        QrcodeUtils.genQrcode(content,qrConfig,outputStream);
         try {
             outputStream.close();
             outputStream = null;
         } catch (IOException e) {
             log.error(ResponseCode.GEN_IMAGE_ERROR.getComment(),e);
             throw  new ImageException(ResponseCode.GEN_IMAGE_ERROR,e);
         }
     }



}
