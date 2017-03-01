package com.mockuai.usercenter.common.constant;

/**
 * Created by duke on 15/11/19.
 */
public enum BizType {
    /**
     * 迁移话机世界的用户
     * */
    ROCKET_MQ_MESSAGE_MIGRATE_USER(1),
    /**
     * 订单未支付
     */
    UN_PAY(2),
    
    /**
     * 订单已支付(区分用户这边的验证与订单支付类型无关)
     */
    USER_PAY(5),

    /**
     * 订单已支付
     */
    PAY(3)
    ;
    private int value;

    BizType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
