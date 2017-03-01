package com.mockuai.itemcenter.common.domain.mop;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 4/29/15.
 */
public class MopItemBrandDTO implements Serializable {
    private long id;
    private String name;
    private String enName;
    private String logoUrl;
    //updated by jiguansheng
    private String brandDesc ;

    private String bannerImg ;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }
}
