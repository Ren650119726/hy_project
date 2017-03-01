package com.mockuai.distributioncenter.common.constant;

/**
 * Created by yeliming on 16/5/18.
 */
public enum LevelEnum {
    /**
     * 1级
     */
    LV1(1),
    /**
     * 2级
     */
    LV2(2),
    /**
     * 3级
     */
    LV3(3);
    private int value;

    LevelEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
