package com.mockuai.imagecenter.mop.api.domain;

/**
 * Created by lizg on 2016/12/6.
 */
public class MopImageDTO {

    private String imageUrl;
    private Long userId ;
    private String content;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
