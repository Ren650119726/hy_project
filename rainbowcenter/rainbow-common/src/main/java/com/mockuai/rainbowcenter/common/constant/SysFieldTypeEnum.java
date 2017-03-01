package com.mockuai.rainbowcenter.common.constant;

/**
 * Created by yeliming on 16/3/15.
 */
public enum SysFieldTypeEnum {
    /**
     * EDB平台
     */
    EDB("edb"),

    /**
     * 管易ERP平台
     */
    GYERP("gyerp");

    private String value;

    SysFieldTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
