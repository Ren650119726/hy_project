package com.mockuai.imagecenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by yindingyu on 16/5/20.
 */
public class ImageInfoDTO implements Serializable{

    private String pKey;
    private String imageName;
    private String imageUrl;

    public String getpKey() {
        return pKey;
    }

    public void setpKey(String pKey) {
        this.pKey = pKey;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
