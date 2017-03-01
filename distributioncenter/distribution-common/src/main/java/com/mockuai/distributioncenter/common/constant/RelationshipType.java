package com.mockuai.distributioncenter.common.constant;

/**
 * Created by duke on 15/10/28.
 */
public enum RelationshipType {
    /**
     * “卖家-卖家”关系
     * */
    SELLER_TO_SELLER(1),

    /**
     * “卖家-买家”关系
     * */
    SELLER_TO_BUYER(2);

    private int value;

    RelationshipType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
