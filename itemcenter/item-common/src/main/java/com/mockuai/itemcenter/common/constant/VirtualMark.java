package com.mockuai.itemcenter.common.constant;

/**
 * Created by yindingyu on 16/3/2.
 */
public enum VirtualMark {

    REAL(0, "实物商品"),

    VIRTUAL(1, "虚拟商品");

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

    VirtualMark(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
