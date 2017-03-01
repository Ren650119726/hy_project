package com.mockuai.marketingcenter.common.constant;

/**
 * Created by edgar.zr on 11/4/15.
 */
public enum CouponCodeStatus {
    /**
     * 未兑换
     */
    NONE(0),
    /**
     * 已兑换
     */
    EXCHANGE(1);

    private int value;

    CouponCodeStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}