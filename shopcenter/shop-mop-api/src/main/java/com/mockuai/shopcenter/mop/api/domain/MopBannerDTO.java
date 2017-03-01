package com.mockuai.shopcenter.mop.api.domain;

/**
 * Created by yindingyu on 16/1/16.
 */
public class MopBannerDTO {

    private String bannerUid;
    private String url;
    private String imageUrl;
    private String imageDesc;
    private int bannerLocation;

    public String getBannerUid() {
        return bannerUid;
    }

    public void setBannerUid(String bannerUid) {
        this.bannerUid = bannerUid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }

    public int getBannerLocation() {
        return bannerLocation;
    }

    public void setBannerLocation(int bannerLocation) {
        this.bannerLocation = bannerLocation;
    }
}
