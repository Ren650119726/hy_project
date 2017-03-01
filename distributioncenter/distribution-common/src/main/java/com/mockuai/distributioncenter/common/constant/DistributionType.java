package com.mockuai.distributioncenter.common.constant;

/**
 * Created by duke on 16/5/16.
 */
public enum DistributionType {
    // 实际金额
    REAL_AMOUNT(0),
    // 嗨币
    HI_COIN_AMOUNT(1)
    ;
    private int type;

    DistributionType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
