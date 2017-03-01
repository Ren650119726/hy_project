package com.mockuai.tradecenter.common.enums;

/**
 * 购物车商品状态
 * Created by zengzhangqiang on 6/6/16.
 */
public enum EnumCartItemStatus {
    //购物车商品处于上架状态
    ON_SALE(1, "上架中"),
    //已下架
    WITHDRAW(2, "已下架");

    private int code;
    private String comment;

    private EnumCartItemStatus(int code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public int getCode() {
        return code;
    }

    public String getComment() {
        return comment;
    }
}
