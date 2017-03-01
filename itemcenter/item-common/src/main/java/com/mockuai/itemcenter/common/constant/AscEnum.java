package com.mockuai.itemcenter.common.constant;

/**
 * Created by yindingyu on 16/3/24.
 */
public enum AscEnum {

    DESC(0, "desc"),

    ASC(1, "asc");

    private int code;
    private String value;

    AscEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
