package com.mockuai.virtualwealthcenter.common.constant;

/**
 * Created by edgar.zr on 5/13/2016.
 */
public enum GrantedWealthStatus {
    /**
     * 冻结
     */
    FROZEN(0),
    /**
     * 到账
     */
    TRANSFERRED(1),
    /**
     * 取消
     */
    CANCEL(2),
    /**
     * 过期
     */
    EXPIRED(3);

    private int value;

    GrantedWealthStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}