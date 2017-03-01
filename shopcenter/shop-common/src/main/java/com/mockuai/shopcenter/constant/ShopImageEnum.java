package com.mockuai.shopcenter.constant;

/**
 * Created by ziqi.
 */
public enum ShopImageEnum {

    SHOP_ICON_IMG(1,"店铺icon图"),

    SHOP_BANNER_IMG(2,"店铺banner图");

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

    private ShopImageEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }
}
