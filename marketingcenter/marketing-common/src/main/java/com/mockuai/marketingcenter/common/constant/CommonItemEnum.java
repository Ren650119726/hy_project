package com.mockuai.marketingcenter.common.constant;

/**
 * 适用商品范围
 * <p/>
 * Created by edgar.zr on 5/19/2016.
 */
public enum CommonItemEnum {
    /**
     * 普通商品
     */
    COMMON_ITEM(0),
    /**
     * 所有商品
     */
    ALL_ITEM(1);

    private int value;

    CommonItemEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}