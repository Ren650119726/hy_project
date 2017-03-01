package com.mockuai.marketingcenter.common.constant;

/**
 */
public enum UserCouponStatus {

    /**
     * 还未激活(未领取)
     */
    UN_ACTIVATE(20),
    /**
     * 未使用
     */
    UN_USE(30),
    /**
     * 冻结中（预使用）
     */
    PRE_USE(40),
    /**
     * 已使用
     */
    USED(50);

    private int value;

    UserCouponStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
