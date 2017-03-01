package com.mockuai.distributioncenter.common.constant;

/**
 * Created by duke on 16/2/19.
 */
public enum BizType {
    /**
     * 下单未支付
     * */
    TRADE_ORDER_UNPAID(0),

    /**
     * 支付成功的消息
     * */
    TRADE_PAY_SUCCESS(1),

    /**
     * 订单完成的消息
     * */
    TRADE_ORDER_FINISHED(2),

    /**
     * 退款的消息
     * */
    TRADE_REFUND(3),

    /**
     * 发货消息
     * */
    TRADE_DELIVERY(4),

    /**
     * 订单取消
     * */
    TRADE_CANCEL(5),

    /**
     * 买家关系更新消息
     * */
    UPDATE_SELLER_RELATIONSHIP(6)
    ;

    private int value;

    BizType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
