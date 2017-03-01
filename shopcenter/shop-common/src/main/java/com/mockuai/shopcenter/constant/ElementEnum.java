package com.mockuai.shopcenter.constant;

/**
 * Created by ziqi.
 */
public enum ElementEnum {

    MAIN_PAGE(1,"店铺首页");

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

    private ElementEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }
}
