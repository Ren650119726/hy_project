package com.mockuai.virtualwealthcenter.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edgar.zr on 11/11/15.
 */
public enum GrantRuleType {
    HALF_CLOSED_L_INTERVAL(1, "左开右闭区间"),
    AMOUNT_RATIO(2, "总量兑换比率"),
    /**
     * 单调降序
     */
    SINGLE_REACH(3, "达到单值");

    private static Map<Integer, GrantRuleType> map = new HashMap<Integer, GrantRuleType>();

    static {
        for (GrantRuleType grantRuleType : GrantRuleType.values()) {
            map.put(grantRuleType.getValue(), grantRuleType);
        }
    }

    private int value;
    private String name;

    GrantRuleType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static GrantRuleType getByValue(Integer value) {
        return map.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}