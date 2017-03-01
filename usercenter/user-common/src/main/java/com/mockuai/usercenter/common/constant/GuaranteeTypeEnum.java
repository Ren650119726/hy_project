package com.mockuai.usercenter.common.constant;

/**
 * Created by yeliming on 16/1/23.
 * 保证金类型
 */
public enum GuaranteeTypeEnum {
    /**
     *  店铺入驻保证金
     */
    SHOPGUARANTEE(1);

    private Integer value;
    GuaranteeTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
