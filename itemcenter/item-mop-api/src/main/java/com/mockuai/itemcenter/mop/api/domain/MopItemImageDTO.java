package com.mockuai.itemcenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 4/24/15.
 */
public class MopItemImageDTO {
    private String itemImageUid;
    private String itemUid;
    private Integer imageType;
    private String imageName;
    private String imageUrl;
    private String skuPropertyUid;

    public String getItemImageUid() {
        return itemImageUid;
    }

    public void setItemImageUid(String itemImageUid) {
        this.itemImageUid = itemImageUid;
    }

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    public Integer getImageType() {
        return imageType;
    }

    public void setImageType(Integer imageType) {
        this.imageType = imageType;
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

    public String getSkuPropertyUid() {
        return skuPropertyUid;
    }

    public void setSkuPropertyUid(String skuPropertyUid) {
        this.skuPropertyUid = skuPropertyUid;
    }
}
