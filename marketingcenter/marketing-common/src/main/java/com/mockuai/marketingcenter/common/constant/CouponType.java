package com.mockuai.marketingcenter.common.constant;

public enum CouponType {
    /**
     * 无码券
     */
    TYPE_NO_CODE(1),
    /**
     * 一卡一码
     */
    TYPE_PER_CODE(2),
    /**
     * 通用码
     */
    TYPE_COMMON_CODE(3);

    private Integer value;

    CouponType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
