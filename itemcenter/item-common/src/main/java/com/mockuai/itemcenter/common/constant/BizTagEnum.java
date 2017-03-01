package com.mockuai.itemcenter.common.constant;

/**
 * Created by yindingyu on 16/4/6.
 */
public enum BizTagEnum {

    NEW_SELLER_ITEM(1, "单店优化版本后添加或者修改的商品");

    private int bit;

    private String name;

    public int getBit() {
        return bit;
    }

    public void setBit(int bit) {
        this.bit = bit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    BizTagEnum(int bit, String name) {
        this.bit = bit;
        this.name = name;
    }
}
