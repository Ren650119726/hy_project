package com.mockuai.mainweb.common.domain.dto; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

/**
 * Created by hy on 2016/9/19.
 */
public class ImageElementDTO extends  ElementDTO {


    private String imageUrl;

    private String targetUrl ;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
