package com.mockuai.rainbowcenter.common.constant;


public enum BizType {

    /**
     * 支付成功
     */
    PAY_SUCCESS(1),

    /**
     *  有物流的退款
     */
    REFUND_WITH_DELIVERY(2),

    /**
     *  无物流的退款
     */
    REFUND_WITHOUT_DELIVERY(3),

    /**
     * 支付成功消费接收标志
     */
    PAY_SUCCESS_MESSAGE_RECORD(4),

    /**
     * 支付成功消息未消费标志
     */
    PAY_SUCCESS_MESSAGE_RECORD_UNCONSUME(5),

    /**
     * 有物流的退款消费接收记录
     */
    REFUND_WITH_DELIVERY_MESSAGE_RECORD(5),

    /**
     * 无物流的退款消费接收记录
     */
    REFUND_WITHOUT_DELIVERY_MESSAGE_RECORD(6),

    /**
     *  海欢支付成功
     */
    HAIHUAN_PAY_SUCCESS(100)
    ;


    private int value;

    BizType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
