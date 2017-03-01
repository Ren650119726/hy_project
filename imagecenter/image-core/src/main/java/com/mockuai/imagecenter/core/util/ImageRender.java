package com.mockuai.imagecenter.core.util; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/
import com.alibaba.simpleimage.render.DrawTextItem;
import com.alibaba.simpleimage.render.DrawTextParameter;
import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.WritableRaster;
import java.io.*;

/**
 * Created by hy on 2016/5/23.
 */
public class ImageRender {
    private static final Logger log = LoggerFactory.getLogger(ImageRender.class);

     protected BufferedImage buffImg;

    protected  Graphics2D graphics2D;



    public ImageRender(File imgFile,int imageType) throws ImageException, IOException {
        if(imgFile == null){
            throw  new ImageException(ResponseCode.PARAM_E_INVALID.getComment());
        }
            FileInputStream inputStream = new FileInputStream(imgFile);
            try {
                initPNG(inputStream,imageType);
            }catch (Exception e){
                inputStream.close();
                initCMYKJPEG(imgFile,imageType);
            }



    }

    public ImageRender(InputStream inputStream,int imageType) throws ImageException {
        if(inputStream == null){
            throw  new ImageException(ResponseCode.PARAM_E_INVALID.getComment());
        }
        initPNG(inputStream, imageType);
    }



    private void initCMYKJPEG(File imgFile,int imageType) throws ImageException {
        BufferedImage srcImg = null;
        try {

            com.sun.image.codec.jpeg.JPEGImageDecoder decoder =
                    JPEGCodec.createJPEGDecoder(new FileInputStream(imgFile));
            BufferedImage src = decoder.decodeAsBufferedImage();
            WritableRaster srcRaster = src.getRaster();
            //prepare result image
            BufferedImage result = new BufferedImage(srcRaster.getWidth(), srcRaster.getHeight(), BufferedImage.TYPE_INT_RGB);
            WritableRaster resultRaster = result.getRaster();
            //prepare icc profiles

            try {
                File profile = new File("/data/resources_code/item_code/GenericCMYKProfile.icc");
                log.info("profile {}:exist {}",profile.getName(),profile.exists());
            }catch (Exception e){
               log.error("",e);
            }
            FileInputStream inputStream = new FileInputStream("/data/resources_code/item_code/GenericCMYKProfile.icc");
            //InputStream inputStream = getClass().getResourceAsStream("/Generic CMYK Profile.icc");
            ICC_Profile iccProfileCYMK = ICC_Profile.getInstance(inputStream);
            ColorSpace sRGBColorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB);

            //invert k channel
            for (int x = srcRaster.getMinX(); x < srcRaster.getWidth(); x++) {
                for (int y = srcRaster.getMinY(); y < srcRaster.getHeight(); y++) {
                    float[] pixel = srcRaster.getPixel(x, y, (float[])null);
                    pixel[3] = 255f-pixel[3];
                    srcRaster.setPixel(x, y, pixel);
                }
            }

            //convert
            ColorConvertOp cmykToRgb = new ColorConvertOp(new ICC_ColorSpace(iccProfileCYMK), sRGBColorSpace, null);
            cmykToRgb.filter(srcRaster, resultRaster);
            srcImg = result;
        }catch (Exception e){
            throw  new ImageException("",e);

        }

        initImg(srcImg,imageType);
    }


    private void initPNG(InputStream inputStream,int imageType) throws ImageException {
        BufferedImage srcImg = null;

        try {
            srcImg = ImageIO.read(inputStream);
        } catch (IOException e) {

            log.info("occur getGraphics2D fail",e);
            throw  new ImageException(ResponseCode.GEN_IMAGE_ERROR,e);
        }
        initImg(srcImg,imageType);
    }
    /**
     *
     * @param imageType #BufferedImage.TYPE_INT_RGB
     *                  #BufferedImage.TYPE_INT_ARGB
     * @throws ImageException
     */
    private void initImg(Image srcImg ,int imageType){
        buffImg = new BufferedImage(srcImg.getWidth(null),
                srcImg.getHeight(null), imageType);
        // 得到画笔对象
        // Graphics g= buffImg.getGraphics();
        graphics2D =  buffImg.createGraphics();
        // 设置对线段的锯齿状边缘处理
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        graphics2D.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg
                .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
    }







    public void drawText(DrawTextParameter drawTextParameter ){
           drawText(buffImg,drawTextParameter);
    }

    public void drawImage(Watermark watermark) throws ImageException {
        if(watermark.getQrcodeInputStream() == null){
            throw  new ImageException(ResponseCode.PARAM_E_INVALID.getComment());
        }
        BufferedImage iconImage = null;

        try {
            iconImage = ImageIO.read(watermark.getQrcodeInputStream());
        } catch (IOException e) {
            log.info("occur getGraphics2D fail",e);
            throw  new ImageException(ResponseCode.GEN_IMAGE_ERROR,e);
        }
        ImageIcon imgIcon = new ImageIcon(iconImage);
        // 表示水印图片的位置
       // graphics2D.drawImage(iconImage,166,557, null);
        setXYIFGravity(watermark,iconImage);
        graphics2D.drawImage(iconImage,watermark.getX(),watermark.getY(), null);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

    }

    private void setXYIFGravity(Watermark watermark,BufferedImage iconImage){
        int x,y;
        if(watermark.getGravity() != null && watermark.getGravity() == Watermark.Position.CENTER){
            x = (buffImg.getWidth() - iconImage.getWidth()) / 2;
            y = (buffImg.getHeight() - iconImage.getHeight()) / 2;
            watermark.setX(x);
            watermark.setY(y);
        }

    }

    /**
     * 重绘制图片
     * @param os
     */
    public void repaints(OutputStream os){
        BufferedImage newImage = new BufferedImage(buffImg.getWidth(), buffImg.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
        Graphics g = newImage.getGraphics();
        g.drawImage(buffImg, 0, 0, buffImg.getWidth(), buffImg.getHeight(), null);
        g.dispose();
        try {
            ImageIO.write(newImage,"png",os);
        } catch (IOException e) {
            log.error("",e);
        }finally {
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void compress(OutputStream os,float quality){
        try {
            JPEGImageWriter imageWriter = (JPEGImageWriter) ImageIO
                    .getImageWritersBySuffix("jpg").next();
            ImageOutputStream outputStream = ImageIO.createImageOutputStream(os);
            JPEGImageWriteParam param = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
            imageWriter.setOutput(outputStream);
            imageWriter.write(null, new IIOImage(buffImg, null, null), param);
        } catch (IOException e) {
            log.error("",e);
        }finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                log.info("occur render fail",e);

            }
        }

    }


    public void render(OutputStream os){
        try {

            ImageIO.write(buffImg, "PNG", os);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                log.info("occur render fail",e);

            }
        }

    }

    private   void drawText(BufferedImage src, DrawTextParameter dp) {
        if (dp == null || dp.getTextInfo() == null || dp.getTextInfo().size() == 0) {
            return;
        }
        int width = src.getWidth();
        int height = src.getHeight();

        Graphics2D graphics = src.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try {
            for (DrawTextItem item : dp.getTextInfo()) {
                if (item != null) {
                    item.drawText(graphics, width, height);
                }
            }
        } finally {
            if (graphics != null) {
                graphics.dispose();
            }

            log.debug("After drawText", src);
        }
    }

    public BufferedImage getBuffImg() {
        return buffImg;
    }

    public void setBuffImg(BufferedImage buffImg) {
        this.buffImg = buffImg;
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }
}
