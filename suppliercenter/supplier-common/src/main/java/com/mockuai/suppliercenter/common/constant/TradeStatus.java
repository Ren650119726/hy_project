package com.mockuai.suppliercenter.common.constant;

/**
 * Created by duke on 15/9/29.
 */
public enum TradeStatus {
    NOT_PAY(1),
    PAY(2);
    private int value;

    TradeStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
