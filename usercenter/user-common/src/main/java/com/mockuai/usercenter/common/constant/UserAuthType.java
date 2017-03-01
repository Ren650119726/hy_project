package com.mockuai.usercenter.common.constant;

/**
 * Created by zengzhangqiang on 8/6/15.
 * 用户实名认证类型
 */
public enum  UserAuthType {
    /**
     * 买家个人认证
     */
    BUYER_AUTH(1),
    /**
     * 卖家个人认证
     */
    SELLER_AUTH(2),
    /**
     * 卖家企业认证
     */
    ENTERPRISE_AUTH(3);

    private int value;
    private UserAuthType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
