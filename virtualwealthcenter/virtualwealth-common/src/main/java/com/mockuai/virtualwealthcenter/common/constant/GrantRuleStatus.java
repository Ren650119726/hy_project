package com.mockuai.virtualwealthcenter.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edgar.zr on 11/11/15.
 * 财富发放规则开关状态
 */
public enum GrantRuleStatus {
    OFF(0),
    ON(1);

    private static Map<Integer, GrantRuleStatus> map = new HashMap<Integer, GrantRuleStatus>();

    static {
        for (GrantRuleStatus grantRuleStatus : GrantRuleStatus.values()) {
            map.put(grantRuleStatus.getValue(), grantRuleStatus);
        }
    }

    private int value;

    GrantRuleStatus(int value) {
        this.value = value;
    }

    public static GrantRuleStatus getByValue(Integer value) {
        return map.get(value);
    }

    public int getValue() {
        return value;
    }
}
