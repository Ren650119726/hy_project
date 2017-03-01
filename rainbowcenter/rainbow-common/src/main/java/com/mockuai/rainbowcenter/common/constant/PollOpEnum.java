package com.mockuai.rainbowcenter.common.constant;

/**
 * Created by yeliming on 16/4/22.
 */
public enum PollOpEnum {
    ITEM_POLL_ON(1),
    ITEM_POLL_OFF(0),
    ITEM_ORDER_POLL_ON(1),
    ITEM_ORDER_POLL_OFF(0),
    ITEM_STOCK_POLL_ON(1),
    ITEM_STOCK_POLL_OFF(0);
    private int code;

    public int getCode() {
        return code;
    }

    PollOpEnum(int code) {
        this.code = code;
    }
}
