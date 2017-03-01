package com.mockuai.imagecenter.core.util; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.imagecenter.core.domain.QrcodeConfigDetailDO;
import com.mockuai.imagecenter.core.exception.ImageException;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hy on 2016/6/3.
 * 缩放图片
 */
public class ScaleRender extends  ImageRender {


    public ScaleRender(File imgFile) throws ImageException, IOException {
        super(imgFile ,BufferedImage.TYPE_INT_RGB);
    }
    public ScaleRender(InputStream inputStream) throws ImageException, IOException {
        super(inputStream ,BufferedImage.TYPE_INT_RGB);
    }

    public void makeShape(int cornerRadius){
        int w = buffImg.getWidth();
        int h = buffImg.getHeight();
        BufferedImage output = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = output.createGraphics();
        Shape clipShape = new RoundRectangle2D.Double(0, 0, w, h, cornerRadius, cornerRadius);

        // create a BufferedImage with transparency
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bg = bi.createGraphics();

        // make BufferedImage fully transparent
        bg.setComposite(AlphaComposite.Clear);
        bg.fillRect(0, 0, w, h);
        bg.setComposite(AlphaComposite.SrcOver);

        // copy/paint the actual image into the BufferedImage
        bg.drawImage(buffImg, 0, 0, w, h, null);

        // set the image to be used as TexturePaint on the target Graphics
        g.setPaint(new TexturePaint(bi, new Rectangle2D.Float(0, 0, w, h)));

        // activate AntiAliasing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // translate the origin to where you want to paint the image
        //g.translate(x, y);

        // draw the Image
        g.fill(clipShape);

        // reset paint
        g.setPaint(null);
        buffImg = output;
    }



    public void resize( boolean preserveAlpha,QrcodeConfigDetailDO detailDO){
        resize(preserveAlpha,detailDO.getWidth(),detailDO.getHeight());
    }

    /**
     * 压缩图片
     * @param preserveAlpha
     * @param scaledWidth
     * @param scaledHeight
     */
    public void resize( boolean preserveAlpha, int scaledWidth, int scaledHeight){
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB
                : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight,
                imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(buffImg, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        buffImg = scaledBI;
    }
}
