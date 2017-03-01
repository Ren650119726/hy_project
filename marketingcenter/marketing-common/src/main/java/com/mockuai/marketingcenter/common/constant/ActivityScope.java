package com.mockuai.marketingcenter.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengzhangqiang on 7/19/15.
 * 营销活动范围常量
 */
public enum ActivityScope {
    /**
     * 全场活动
     */
    SCOPE_WHOLE(1),
    /**
     * 专场活动
     */
    SCOPE_SPECIAL(2),
    /**
     * 全店互动
     */
    SCOPE_SHOP(3),
    /**
     * 单品活动
     */
    SCOPE_ITEM(4),
    /**
     * 指定品类
     */
    SCOPE_CATEGORY(5),
    /**
     * 指定品牌
     */
    SCOPE_BRAND(6),;

    private static Map<Integer, ActivityScope> scopeMap = new HashMap<Integer, ActivityScope>();

    static {
        for (ActivityScope activityScope : ActivityScope.values()) {
            scopeMap.put(activityScope.getValue(), activityScope);
        }
    }

    private int value;

    ActivityScope(int value) {
        this.value = value;
    }

    public static ActivityScope getScopeByValue(int scopeValue) {
        return scopeMap.get(scopeValue);
    }

    public int getValue() {
        return value;
    }
}