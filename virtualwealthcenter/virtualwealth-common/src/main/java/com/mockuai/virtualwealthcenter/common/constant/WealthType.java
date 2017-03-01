package com.mockuai.virtualwealthcenter.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edgar.zr on 11/3/15.
 */
public enum WealthType {
    /**
     * 余额
     */
    VIRTUAL_WEALTH(1, "余额"),
    /**
     * 积分
     */
    CREDIT(2, "积分"),
    HI_COIN(3, "嗨币");

    private static Map<Integer, WealthType> map = new HashMap<Integer, WealthType>();

    static {
        for (WealthType wealthType : WealthType.values()) {
            map.put(wealthType.getValue(), wealthType);
        }
    }

    private int value;
    private String name;

    WealthType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static WealthType getByValue(Integer value) {
        return map.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}