package com.mockuai.distributioncenter.common.constant;

/**
 * Created by yeliming on 16/5/18.
 * 卖家升级申请枚举
 */
public enum SellerUpgradeApplyStatus {
    /**
     * 待审核
     */
    Pending(0),
    /**
     * 同意升级
     */
    Agree(1),

    /**
     * 拒绝升级
     */
    Reject(2);

    private int value;

    SellerUpgradeApplyStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
