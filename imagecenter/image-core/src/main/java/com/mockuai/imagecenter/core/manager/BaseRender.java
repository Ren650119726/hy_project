package com.mockuai.imagecenter.core.manager; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.alibaba.simpleimage.render.DrawTextParameter;
import com.mockuai.imagecenter.core.domain.BizColType;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDetailDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.util.CoordDrawTextItem;
import com.mockuai.imagecenter.core.util.ImageRender;
import com.mockuai.imagecenter.core.util.PositionDrawTextItem;
import com.mockuai.imagecenter.core.util.Watermark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.*;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 冠生 on 2016/5/25.
 * 将背景和二维码合并
 */
public abstract class BaseRender {


    private static final Logger log = LoggerFactory.getLogger(BaseRender.class);


    //配置
     protected List<QrcodeConfigDetailDO> allDetailDOList;
    //二维码配置
    protected  QrcodeConfigDetailDO  qrcodeConfigDetailDO;
    //临时目录
    protected String folder=System.getProperty("java.io.tmpdir");

    protected QrcodeGenerator qrcodeGenerator;

    private FileInputStream inputStream;
    protected ImageRender imageRender;
    protected String  id ;
    protected  DrawTextParameter textParameter ;
    public BaseRender(String id, List<QrcodeConfigDetailDO> allDetailDOList,String content,String bgImage,boolean hasNeedAddLogo) throws IOException, ImageException {
        this .id =id;
        this.allDetailDOList = allDetailDOList;
        qrcodeConfigDetailDO =  getQrcodeConfigDetailDO(BizColType.QRCODE);
        this.qrcodeGenerator  =  new QrcodeGenerator(getKey(),qrcodeConfigDetailDO,content);
        inputStream = new FileInputStream(bgImage);
        imageRender = new ImageRender(inputStream,getImageType());

        qrcodeGenerator.generateQrcode();
        if(hasNeedAddLogo){
            QrcodeConfigDetailDO detailDO =  getQrcodeConfigDetailDO(BizColType.LOGO);
            String logoPath =   detailDO.getImgPath();
            qrcodeGenerator.setLogoPath(logoPath);
            qrcodeGenerator.drawLogo();
        }

        drawImage(qrcodeGenerator.getQrcodeFile(),getQrcodeConfigDetailDO(BizColType.QRCODE));

    }

    public BaseRender(String id, List<QrcodeConfigDetailDO> allDetailDOList,String content,String bgImage) throws IOException, ImageException {
       this(id, allDetailDOList, content, bgImage,false);

    }



    protected abstract  int getImageType();

    public abstract String    getKey();

    protected  void render(OutputStream os){
        imageRender.render(os);
            try {
                if (null != inputStream)
                    inputStream.close();
                inputStream = null;
            } catch (Exception e) {
                log.info("occur render fail",e);

            }
    }
    public void createParentDirctory(String tmpFileName){
        File tmpFile = new File(tmpFileName);
        if(!tmpFile.getParentFile().exists()){
            tmpFile.getParentFile().mkdirs();
        }
    }

    public   String getTmpFile(String type){
        String tmpFileName =  String.format("%1$s/%2$s/%3$s/%4$s.png",folder,type, getTime(),getKey());
        createParentDirctory(tmpFileName);
        return         tmpFileName;
    }

    public String getTime(){
        return String.format("%1$tY%1$tm%1$td",Calendar.getInstance());
    }


    public void deleteFile(String type){
        log.info("delete qrcode base tmp file:{}",getTmpFile(type));
        new File(qrcodeGenerator.getQrcodeFile()).delete();
        new File(getTmpFile(type)).delete();

    }


    /**
     * 打上二维码
     * @throws ImageException
     */
    public void drawImage(String file,QrcodeConfigDetailDO qrcodeConfigDetailDO) throws ImageException, FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(file);
        drawImage(inputStream,qrcodeConfigDetailDO);
    }
    public void drawImage(InputStream inputStream,QrcodeConfigDetailDO qrcodeConfigDetailDO) throws ImageException, FileNotFoundException {
        Watermark watermark = new Watermark();
        watermark.setY(qrcodeConfigDetailDO.getY());
        watermark.setX(qrcodeConfigDetailDO.getX());
        watermark.setQrcodeInputStream(inputStream);
        imageRender.drawImage(watermark);
        if(inputStream != null){
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public CoordDrawTextItem addCoordText(String text ,QrcodeConfigDetailDO qrcodeConfigDetailDO){
        textParameter = new DrawTextParameter();
        String  fontFamily = qrcodeConfigDetailDO.getFontFamily();
        if(fontFamily == null){
            //
            fontFamily = "Microsoft YaHei";
        }
        int fontWeight  =qrcodeConfigDetailDO.getFontWeight() == null? Font.PLAIN:qrcodeConfigDetailDO.getFontWeight();

        Font font = new Font(fontFamily,fontWeight
                ,qrcodeConfigDetailDO.getFontSize());

        String color =  qrcodeConfigDetailDO.getColor();


        CoordDrawTextItem textItem = new CoordDrawTextItem(text,new Color(Integer.parseInt(color,16)) ,null,
                font,36,0.85f,qrcodeConfigDetailDO.getX(),qrcodeConfigDetailDO.getY());
        return textItem;
    }

    public PositionDrawTextItem  addText(String text ,QrcodeConfigDetailDO qrcodeConfigDetailDO){
        textParameter = new DrawTextParameter();
        String  fontFamily = qrcodeConfigDetailDO.getFontFamily();
        if(fontFamily == null){
            //
            fontFamily = "Microsoft YaHei";
        }
        int fontWeight  =qrcodeConfigDetailDO.getFontWeight() == null? Font.PLAIN:qrcodeConfigDetailDO.getFontWeight();

        Font font = new Font(fontFamily,fontWeight
        ,qrcodeConfigDetailDO.getFontSize());

        String color =  qrcodeConfigDetailDO.getColor();



        PositionDrawTextItem textItem = new PositionDrawTextItem(text,new Color(Integer.parseInt(color,16)) ,null,
                font,36,0.85f,qrcodeConfigDetailDO.getX() /100f,qrcodeConfigDetailDO.getY()/100f );
        return textItem;
    }



    protected  void drawText(){
        imageRender.drawText(textParameter);
    }


    protected  QrcodeConfigDetailDO getQrcodeConfigDetailDO(BizColType  bizColType){
        for(QrcodeConfigDetailDO detailDO : allDetailDOList){
            if(bizColType.equals( BizColType.valueOf(  detailDO.getType()))){
                return detailDO;
            }
        }
        return null;
    }

}
