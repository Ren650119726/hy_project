package com.mockuai.tradecenter.common.enums;

/**
 * 父订单标志位
 * Created by zengzhangqiang on 6/6/16.
 */
public enum EnumOrderParentMark {
    //正常的订单或者子订单
    ORDER_NORMAL(0, "normal or sub order"),
    //父订单
    ORDER_PARENT(1, "parent order");

    private int code;
    private String comment;

    private EnumOrderParentMark(int code, String comment) {
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
