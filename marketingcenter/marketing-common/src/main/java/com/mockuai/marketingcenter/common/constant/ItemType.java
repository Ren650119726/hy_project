package com.mockuai.marketingcenter.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edgar.zr on 12/3/15.
 */
public enum ItemType {
    COMMON(1, "普通商品"),
    SUIT(11, "套装"),
    SECKILL(13, "秒杀"),
    GROUP_BUYING(14, "团购"),
    AUCTION(15, "竞拍"),
    DEPOSIT(16, "保证金"),
//    BARTER(17, "换购"),
    CROWD_FUNDING(18, "一元夺宝"),
    GIFT_PACKS(20, "嗨云开店礼包"),
    TIME_LIMIT(21,"限时购"),

    COMPOSITE_ITEM(22,"组合商品");


    private static Map<Integer, ItemType> map = new HashMap<Integer, ItemType>();

    static {
        for (ItemType itemType : ItemType.values()) {
            map.put(itemType.getValue(), itemType);
        }
    }

    private int value;
    private String name;

    ItemType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static ItemType getByValue(Integer value) {
        return map.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}