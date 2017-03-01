package com.mockuai.itemcenter.common.constant;

/**
 * Created by yindingyu on 16/3/1.
 */
public enum DeleteMarkEnum {

    NORMAL(0, "正常状态"),

    DELETED(1, "已删除"),

    RECYCLING(2, "回收站");

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    DeleteMarkEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
