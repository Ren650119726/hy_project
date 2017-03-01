package com.mockuai.marketingcenter.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edgar.zr on 1/11/16.
 */
public enum MarketLevel {

    SHOP_LEVEL(1, "店铺级别"),
    BIZ_LEVEL(2, "商城级别");

    public static Map<Integer, MarketLevel> map = new HashMap<Integer, MarketLevel>();

    static {
        for (MarketLevel level : MarketLevel.values())
            map.put(level.getValue(), level);
    }

    private int value;
    private String intro;

    MarketLevel(int value, String intro) {
        this.value = value;
        this.intro = intro;
    }

    public static MarketLevel getByValue(Integer value) {
        return map.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getIntro() {
        return intro;
    }
}