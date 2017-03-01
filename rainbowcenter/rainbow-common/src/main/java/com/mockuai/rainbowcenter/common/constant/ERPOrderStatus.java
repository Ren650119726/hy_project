package com.mockuai.rainbowcenter.common.constant;

/**
 * Created by lizg on 16/4/3.
 * 该枚举用于向ERP发送订单时的枚举信息,包含支付方式,物流等信息
 */
public enum ERPOrderStatus {

    /********************* 支付方式 *************************/
    /**
     * 支付宝
     */
    ALIPAY("zhifubao"),

    /**
     * 微信
     */
    WXPAY("weixin"),

    /**
     * 银联支付
     */
    YLPAY("yinlian"),

    /**
     * 余额支付
     */
    YEPAY("yuen"),

    /**
     * 嗨币支付
     */
    HBPAY("haibi"),

    /********************* 物流 *************************/
    /**
     * 已发货
     */
    SHIPPED("2")  //已发货
    ;
    private String value;

    ERPOrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
