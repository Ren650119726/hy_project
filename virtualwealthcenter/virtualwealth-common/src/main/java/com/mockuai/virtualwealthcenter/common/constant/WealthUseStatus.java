package com.mockuai.virtualwealthcenter.common.constant;

/**
 * Created by zengzhangqiang on 6/19/15.
 */
public enum WealthUseStatus {
    /**
     * 其他
     */
    OTHER(0),
    /**
     * 预使用
     */
    PRE_USE(1),
    /**
     * 已使用
     */
    USED(2),
    /**
     * 已取消
     */
    CANCELED(3),
    /**
     * 已退还
     */
    GIVE_BACK(4);

    private int value;

    WealthUseStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}