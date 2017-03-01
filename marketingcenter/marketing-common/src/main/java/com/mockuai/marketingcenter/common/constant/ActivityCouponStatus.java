package com.mockuai.marketingcenter.common.constant;

/**
 * 优惠券状态
 */
public enum ActivityCouponStatus {
    /**
     * 正常状态
     */
    NORMAL(1),
    /**
     * 失效状态
     */
    INVALID(2);

    private Integer value;

    ActivityCouponStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
