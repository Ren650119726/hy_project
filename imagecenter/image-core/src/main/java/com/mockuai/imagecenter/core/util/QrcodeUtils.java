package com.mockuai.imagecenter.core.util; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.common.domain.dto.QRConfig;
import com.mockuai.imagecenter.core.exception.ImageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 冠生 on 2016/5/24.
 */
public class QrcodeUtils {

    private static final Logger log = LoggerFactory.getLogger(QrcodeUtils.class);

    // 图片宽度的一般
    private static final int FRAME_WIDTH = 2;

    // 二维码写码器
    private static MultiFormatWriter mutiWriter = new MultiFormatWriter();

    private  static BufferedImage genBarcode(String content, int width,
                                             int height) throws WriterException,
            IOException {
        return genBarcode(content,width,height,null);
    }



    private  static BufferedImage genBarcode(String content, int width,
                                      int height, BufferedImage logoImage) throws WriterException,
            IOException {
        int IMAGE_HALF_WIDTH = 0;
        int[][] srcPixels = null;

        if(logoImage != null){
            srcPixels = new int[logoImage.getWidth()][logoImage.getHeight()];
            for (int i = 0; i < logoImage.getWidth(); i++) {
                for (int j = 0; j < logoImage.getHeight(); j++) {
                    srcPixels[i][j] = logoImage.getRGB(i, j);
                }
            }
            IMAGE_HALF_WIDTH = logoImage.getWidth() / 2;
        }

        Map<EncodeHintType,Object> hint = new HashMap<EncodeHintType, Object>();
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hint.put(EncodeHintType.MARGIN, 0);//去掉白色边框
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 生成二维码
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,
                width, height, hint);

        // 二维矩阵转为一维像素数组
        int halfW = matrix.getWidth() / 2;
        int halfH = matrix.getHeight() / 2;
        int[] pixels = new int[width * height];

        // System.out.println(matrix.getHeight());
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                // 读取图片
                if (logoImage != null &&  x > halfW - IMAGE_HALF_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH
                        && y < halfH + IMAGE_HALF_WIDTH) {
                    pixels[y * width + x] = srcPixels[x - halfW
                            + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
                }
                // 在图片四周形成边框
                else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        - IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
                    pixels[y * width + x] = 0xfffffff;
                } else {
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                    pixels[y * width + x] = matrix.get(x, y) ? 0xff000000
                            : 0xfffffff;
                }
            }
        }

        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, width, height, pixels);

        return image;
    }



    public static void genQrcode(String url, QRConfig qRconfig, OutputStream outputStream) throws ImageException {
        try {
            BufferedImage image =   genBarcode(url,qRconfig.getWidth(),qRconfig.getHeight());
            ImageIO.write(image,"jpg",outputStream);
        } catch (Exception e) {
            log.error("generator qrcode occur exception",e);
            throw  new ImageException(ResponseCode.GEN_QR_CODE_ERROR,e.getMessage());
        }
    }


    public static void genQrcode(String url,String logoPath,   QRConfig qRconfig, OutputStream outputStream) throws ImageException {
        FileInputStream  logoInputStream = null;
        try {
            logoInputStream = new FileInputStream(logoPath);
            BufferedImage logoImage =  ImageIO.read(logoInputStream );
            BufferedImage image =   genBarcode(url,qRconfig.getWidth(),qRconfig.getHeight(),logoImage);
            ImageIO.write(image,"jpg",outputStream);
        } catch (Exception e) {
            log.error("generator qrcode occur exception",e);
            throw  new ImageException(ResponseCode.GEN_QR_CODE_ERROR,e.getMessage());
        } finally {
            if(logoInputStream != null){
                try {
                    logoInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


/*
    public static void genQrcode(String url, QRConfig qRconfig, OutputStream outputStream) throws ImageException {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        Map hints = Maps.newHashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);//去掉白色边框
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, qRconfig.getWidth(), qRconfig.getHeight(),hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        try {
            MatrixToImageWriter.writeToStream(bitMatrix, qRconfig.getImageFormat(), outputStream);
        } catch (IOException e) {
            log.error("生成二维码时出现问题",e);
            throw ExceptionUtil.getException(ResponseCode.GEN_QR_CODE_ERROR);
        }
    }*/

}
