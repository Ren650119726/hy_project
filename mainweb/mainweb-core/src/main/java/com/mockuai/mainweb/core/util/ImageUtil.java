package com.mockuai.mainweb.core.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by huangsiqian  on 2016/12/19.
 */
public class ImageUtil {
    /**
     *
     * @param imgUrl 图片地址
     * @return
     */
    public static BufferedImage getBufferedImage(String imgUrl) {
        URL url = null;
        InputStream is = null;
        BufferedImage img = null;
        try {
            url = new URL(imgUrl);
            is = url.openStream();
            img = ImageIO.read(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }


    public static void main(String[] args) {

        String imageUrl="http://avatar.csdn.net/9/F/2/1_5iasp.jpg";
        BufferedImage image=getBufferedImage(imageUrl);
        if (image!=null)
        {
            System.out.println("图片高度:"+image.getHeight());
            System.out.println("图片宽度:"+image.getWidth());
        }
        else
        {
            System.out.println("图片不存在！");
        }

    }

}
