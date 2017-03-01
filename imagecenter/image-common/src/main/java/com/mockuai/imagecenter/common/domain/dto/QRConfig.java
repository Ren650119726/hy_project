package com.mockuai.imagecenter.common.domain.dto;

/**
 * Created by yindingyu on 16/5/23.
 */
public class QRConfig {

    private String imageFormat;

    private int width ;
    private int height;


    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }



}
