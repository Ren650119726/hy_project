//package com.mokuai.test; /**
// * create by 冠生
// *
// * @date #{DATE}
// **/
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//import com.mockuai.mainweb.core.exception.ImageException;
////import com.mockuai.mainweb.core.manager.QRCodeManager;
////import com.mockuai.mainweb.core.manager.QrcodeGenerator;
////import com.mockuai.mainweb.core.manager.impl.RecommendRender;
////import com.mockuai.mainweb.core.util.QrcodeUtils;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import org.apache.commons.lang.StringUtils;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.color.ColorSpace;
//import java.awt.color.ICC_ColorSpace;
//import java.awt.color.ICC_Profile;
//import java.awt.geom.AffineTransform;
//import java.awt.image.AffineTransformOp;
//import java.awt.image.BufferedImage;
//import java.awt.image.ColorConvertOp;
//import java.awt.image.WritableRaster;
//import java.io.*;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by hy on 2016/5/23.
// */
//public class TestGenImage {
//    private static final Logger log = LoggerFactory.getLogger(TestGenImage.class);
//
//
//    private int getColor(String color){
//        if(color.matches("\\d+")){
//            return Integer.parseInt(color);
//        }else{
//            return Integer.parseInt(color,16);
//        }
//
//    }
//    @Test
//    public void testColor(){
//       String color = "192192192";
//        int r= getColor(color.substring(0,3));
//
//        int b= getColor(color.substring(3,6));
//        int g= getColor(color.substring(6,color.length()));
//        Color color1 = new Color(r,b,g);
//        System.out.print(color1.getRGB());
//    }
//
//
//
//
//
//    @Test
//    public void genQrcode() throws IOException, ImageException {
//        String url  = "http://m.haiyn.com/merchant-login.html?inviter_seller_id=420515&real_name=";
//        url =url+   URLEncoder.encode("邵长生","UTF-8");
//        log.info(url);
//    }
//    @Test
//    public void testContent(){
//        String url = "http://img.mockuai.com/images/201607/03/12/20160703124207641.jpg@0e_200w_200h_90Q.jpg";
//        log.info("is jpg:{}",url.endsWith(".jpg"));
//    }
//
//    @Test
//    public void testText(){
//        log.info("ff:{}",5>3?1:4==4);
//
//    }
//
//
//
//     @Test
//     public void testRecommendRender() throws IOException, ImageException, FontFormatException {
//         QrcodeConfigDetailDO qrcodeConfigDetailDO = new QrcodeConfigDetailDO();
//         qrcodeConfigDetailDO.setX(166);
//         qrcodeConfigDetailDO.setY(557);
//         qrcodeConfigDetailDO.setWidth(268);
//         qrcodeConfigDetailDO.setHeight(268);
//         qrcodeConfigDetailDO.setType(BizColType.QRCODE.name());
//         QrcodeConfigDetailDO realNameDetailDO = new QrcodeConfigDetailDO();
////         String pathString = FontLoader.class.getResource("e:/recommend_code/HYCHENMEIZIJ.TTF").getFile();
//       //  Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, new File(pathString));
//         realNameDetailDO.setX(20);
//         realNameDetailDO.setY(93);
//         realNameDetailDO.setFontFamily("汉仪晨妹子简");
//         realNameDetailDO.setFontSize(36);
//         realNameDetailDO.setColor("255251240");
//         realNameDetailDO.setType(BizColType.USER_NAME.name());
//         List<QrcodeConfigDetailDO> detailDOs = new ArrayList<QrcodeConfigDetailDO>();
//         detailDOs.add(qrcodeConfigDetailDO);
//         detailDOs.add(realNameDetailDO);
//         String bgImage = "e:/recommend_code/bg.png";
//         RecommendRender recommendRender = new RecommendRender("1",detailDOs,"baidu.com",bgImage);
//         recommendRender.renderRecommend("胡玉龙");
//         log.info("file:{}",recommendRender.getTmpFile(QRCodeManager.RECOMMEND));
//     }
//     @Test
//     public void test1(){
//         Long t = 2551291100L;
//         log.info(t/100+".00");
//
//
//     }
//
//     @Test
//     public void scaleTest() throws IOException, ImageException {
//        InputStream inputStream =   Files.newInputStream(Paths.get("e:","image_test","default_avatar_test.png"));
//         OutputStream outputStream =   Files.newOutputStream(Paths.get("e:","image_test","default_avatar_out.png"));
//         QrcodeConfigDetailDO detailDO = new QrcodeConfigDetailDO();
//         detailDO.setWidth(83);
//         detailDO.setHeight(83);
//         ScaleRender scaleRender = new ScaleRender(inputStream);
//         //scaleRender.resize(true,detailDO);
//         scaleRender.makeShape(20);
//         scaleRender.render(outputStream);
//
//     }
//
//    @Test
//    public void test22() throws IOException, ImageException {
//        QrcodeConfigDetailDO qrcodeConfigDetailDO = new QrcodeConfigDetailDO();
//        qrcodeConfigDetailDO.setWidth(150);
//        qrcodeConfigDetailDO.setHeight(150);
//        String logo = "e:/item_code/logo.png";
//        QrcodeGenerator     qrcodeGenerator  =  new QrcodeGenerator("1",qrcodeConfigDetailDO,"http://baidu.com",logo);
//        qrcodeGenerator.generateQrcode();
//       // qrcodeGenerator.drawLogo();
//        log.info("qrcode:{}",qrcodeGenerator.getQrcodeFile());
//
//    }
//
//    // 图片宽度的一般
//    private static final int IMAGE_WIDTH = 1;
//    private static final int IMAGE_HEIGHT = 1;
//    private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
//    private static final int FRAME_WIDTH = 2;
//
//    // 二维码写码器
//    private static MultiFormatWriter mutiWriter = new MultiFormatWriter();
//
//    /**
//     *
//     * @param content
//     *            二维码显示的文本
//     * @param width
//     *            二维码的宽度
//     * @param height
//     *            二维码的高度
//     * @param srcImagePath
//     *            中间嵌套的图片
//     * @param destImagePath
//     *            二维码生成的地址
//     */
//    public  void encode(String content, int width, int height,
//                              String srcImagePath, String destImagePath) {
//        try {
//            // ImageIO.write 参数 1、BufferedImage 2、输出的格式 3、输出的文件
//            ImageIO.write(genBarcode(content, width, height, srcImagePath),
//                    "jpg", new File(destImagePath));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 得到BufferedImage
//     *
//     * @param content
//     *            二维码显示的文本
//     * @param width
//     *            二维码的宽度
//     * @param height
//     *            二维码的高度
//     * @param srcImagePath
//     *            中间嵌套的图片
//     * @return
//     * @throws WriterException
//     * @throws IOException
//     */
//    private  BufferedImage genBarcode(String content, int width,
//                                            int height, String srcImagePath) throws WriterException,
//            IOException {
//        // 读取源图像
//        BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH,
//                IMAGE_HEIGHT, false);
//
//        int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
//        for (int i = 0; i < scaleImage.getWidth(); i++) {
//            for (int j = 0; j < scaleImage.getHeight(); j++) {
//                srcPixels[i][j] = scaleImage.getRGB(i, j);
//            }
//        }
//         int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
//
//        Map<EncodeHintType,Object> hint = new HashMap<EncodeHintType, Object>();
//        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        hint.put(EncodeHintType.MARGIN, 0);//去掉白色边框
//        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//        // 生成二维码
//        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,
//                width, height, hint);
//
//        // 二维矩阵转为一维像素数组
//        int halfW = matrix.getWidth() / 2;
//        int halfH = matrix.getHeight() / 2;
//        int[] pixels = new int[width * height];
//
//        // System.out.println(matrix.getHeight());
//        for (int y = 0; y < matrix.getHeight(); y++) {
//            for (int x = 0; x < matrix.getWidth(); x++) {
//                // 读取图片
//                if (x > halfW - IMAGE_HALF_WIDTH
//                        && x < halfW + IMAGE_HALF_WIDTH
//                        && y > halfH - IMAGE_HALF_WIDTH
//                        && y < halfH + IMAGE_HALF_WIDTH) {
//                    pixels[y * width + x] = srcPixels[x - halfW
//                            + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
//                }
//                // 在图片四周形成边框
//                else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
//                        && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
//                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
//                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
//                        || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH
//                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
//                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
//                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
//                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
//                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
//                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
//                        - IMAGE_HALF_WIDTH + FRAME_WIDTH)
//                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
//                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
//                        && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
//                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
//                    pixels[y * width + x] = 0xfffffff;
//                } else {
//                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
//                    pixels[y * width + x] = matrix.get(x, y) ? 0xff000000
//                            : 0xfffffff;
//                }
//            }
//        }
//
//        BufferedImage image = new BufferedImage(width, height,
//                BufferedImage.TYPE_INT_RGB);
//        image.getRaster().setDataElements(0, 0, width, height, pixels);
//
//        return image;
//    }
//
//    /**
//     * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
//     *
//     * @param srcImageFile
//     *            源文件地址
//     * @param height
//     *            目标高度
//     * @param width
//     *            目标宽度
//     * @param hasFiller
//     *            比例不对时是否需要补白：true为补白; false为不补白;
//     * @throws IOException
//     */
//    private  BufferedImage scale(String srcImageFile, int height,
//                                       int width, boolean hasFiller) throws IOException {
//        double ratio = 0.0; // 缩放比例
//        File file = new File(srcImageFile);
//        BufferedImage srcImage = ImageIO.read(file);
//        Image destImage = srcImage.getScaledInstance(width, height,
//                BufferedImage.SCALE_SMOOTH);
//        // 计算比例
//        if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
//            if (srcImage.getHeight() > srcImage.getWidth()) {
//                ratio = (new Integer(height)).doubleValue()
//                        / srcImage.getHeight();
//            } else {
//                ratio = (new Integer(width)).doubleValue()
//                        / srcImage.getWidth();
//            }
//            AffineTransformOp op = new AffineTransformOp(AffineTransform
//                    .getScaleInstance(ratio, ratio), null);
//            destImage = op.filter(srcImage, null);
//        }
//        if (hasFiller) {// 补白
//            BufferedImage image = new BufferedImage(width, height,
//                    BufferedImage.TYPE_INT_RGB);
//            Graphics2D graphic = image.createGraphics();
//            graphic.setColor(Color.white);
//            graphic.fillRect(0, 0, width, height);
//            if (width == destImage.getWidth(null))
//                graphic.drawImage(destImage, 0, (height - destImage
//                                .getHeight(null)) / 2, destImage.getWidth(null),
//                        destImage.getHeight(null), Color.white, null);
//            else
//                graphic.drawImage(destImage,
//                        (width - destImage.getWidth(null)) / 2, 0, destImage
//                                .getWidth(null), destImage.getHeight(null),
//                        Color.white, null);
//            graphic.dispose();
//            destImage = image;
//        }
//        return (BufferedImage) destImage;
//    }
//    @Test
//    public  void main() throws FileNotFoundException, ImageException {
//        QRConfig qrConfig = new QRConfig();
//        qrConfig.setWidth(250);
//        qrConfig.setHeight(250);
//        File file = new File("d:/qrcode.png");
//        FileOutputStream outputStream = new FileOutputStream(file);
//        QrcodeUtils.genQrcode("http://baidu.com","e:/item_code/logo.png",qrConfig,outputStream);
//    }
//
//
//     @Test
//    public void test12(){
//      /*  log.info(String.format(".jpg?d=%s",System.currentTimeMillis()));
//         List<String> names = Arrays.asList("duoduo","lili","potong","cash","momory");
//         long count = names.stream().filter(s -> s.contains("o")).count();
//         names.stream().map(s-> s.length());
//         log.info("count:{}",count);
//         Collections.sort(names, (s1,s2)->(s1.compareTo(s2)) );
//         names.forEach(log::info);*/
//       //  String orderSn = StringUtils.rightPad(DATE_FORMAT.format(new Date()), 17, "0")+"0"+StringUtils.leftPad("" + userId % 10000000, 7, "0")
//         long userId = 1454234L;
//         String result =  StringUtils.leftPad("" + userId % 10000000, 7, "0");
//         log.info("left:{},result:{}","" + userId % 10000000,result);
//     }
//
//    @Test
//    public void test222() throws IOException {
//        URL url = getClass().getResource("hehe.txt");
//        log.info(url.getPath());
//        log.info(String.valueOf(getClass().getResourceAsStream("Generic CMYK Profile.icc")));
//        ICC_Profile s = ICC_Profile.getInstance(getClass().getResourceAsStream("Generic CMYK Profile.icc"));
//
//    }
//
//
//    public void test3233() throws IOException {
//        InputStream srcImageInputStream = null;
//        com.sun.image.codec.jpeg.JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(srcImageInputStream);
//        BufferedImage src = decoder.decodeAsBufferedImage();
//        WritableRaster srcRaster = src.getRaster();
//        //prepare result image
//        BufferedImage result = new BufferedImage(srcRaster.getWidth(), srcRaster.getHeight(), BufferedImage.TYPE_INT_RGB);
//        WritableRaster resultRaster = result.getRaster();
//        //prepare icc profiles
//        ICC_Profile iccProfileCYMK = ICC_Profile.getInstance(getClass().getResourceAsStream(""));
//        ColorSpace sRGBColorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB);
//
//        //invert k channel
//        for (int x = srcRaster.getMinX(); x < srcRaster.getWidth(); x++) {
//            for (int y = srcRaster.getMinY(); y < srcRaster.getHeight(); y++) {
//                float[] pixel = srcRaster.getPixel(x, y, (float[])null);
//                pixel[3] = 255f-pixel[3];
//                srcRaster.setPixel(x, y, pixel);
//            }
//        }
//
//        //convert
//        ColorConvertOp cmykToRgb = new ColorConvertOp(new ICC_ColorSpace(iccProfileCYMK), sRGBColorSpace, null);
//        cmykToRgb.filter(srcRaster, resultRaster);
//
//    }
//}
