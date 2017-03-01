package com.mockuai.imagecenter.core.util; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import java.io.InputStream;

/**
 * Created by 冠生 on 2016/5/23.
 */
public class Watermark {

    private InputStream qrcodeInputStream ;

    private int x;
    private int y;

    private Position gravity ;


    public enum Position {
        CENTER, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }


    public InputStream getQrcodeInputStream() {
        return qrcodeInputStream;
    }

    public void setQrcodeInputStream(InputStream qrcodeInputStream) {
        this.qrcodeInputStream = qrcodeInputStream;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position getGravity() {
        return gravity;
    }

    public void setGravity(Position gravity) {
        this.gravity = gravity;
    }
}
