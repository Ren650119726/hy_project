package com.mockuai.shopcenter.constant;

/**
 * Created by ziqi.
 */
public enum BannerEnum {

    CAROUSEL_BANNER(1,"首页轮播图片");

    private int type;
    private String typeName;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    private BannerEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }
}
