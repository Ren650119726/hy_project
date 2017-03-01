package com.mockuai.marketingcenter.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edgar.zr on 1/14/16.
 */
public enum DeliveryType {
    EXPRESS(1, "快递"),
    PICK_UP(2, "自提"),
    DISTRIBUTION(3, "门店配送");

    private static Map<Integer, DeliveryType> map = new HashMap<Integer, DeliveryType>();

    static {
        for (DeliveryType deliveryType : DeliveryType.values())
            map.put(deliveryType.getValue(), deliveryType);
    }

    private int value;
    private String typeName;

    DeliveryType(int value, String typeName) {
        this.value = value;
        this.typeName = typeName;
    }

    public static DeliveryType getByValue(Integer value) {
        return map.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getTypeName() {
        return typeName;
    }
}