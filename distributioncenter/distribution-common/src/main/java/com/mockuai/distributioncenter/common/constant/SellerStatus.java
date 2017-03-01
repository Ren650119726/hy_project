package com.mockuai.distributioncenter.common.constant;

/**
 * Created by duke on 16/5/16.
 */
public enum SellerStatus {
    // 失效
    INACTIVE(0),
    // 激活
    ACTIVE(1),
    ;
    private int status;

    SellerStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
