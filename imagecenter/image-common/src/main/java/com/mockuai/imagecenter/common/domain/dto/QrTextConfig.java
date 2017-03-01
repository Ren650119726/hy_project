package com.mockuai.imagecenter.common.domain.dto; /**
/

/**
 * Created by 冠生 on 2016/5/24.
 * 二维码文字配置
 */
public class QrTextConfig {

    private String fontName ;
    private int fontSize;
    private String fontColor ;

    private int x;
    private int y ;

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
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
}
