package com.mockuai.marketingcenter.common.constant;

/**
 * Created by edgar.zr on 11/5/15.
 * property 的穷举类
 */
public enum PropertyEnum {

    CONSUME("consume", "消费金额"),
    QUOTA("quota", "优惠金额"),
    EXTRA("extra", "换购价格"),
    LIMIT("limit", "每人限购"),
    SKU_ID("skuId", "换购商品sku"),
    ITEM_ID("itemId", "换购商品item"),
    FREE_POSTAGE("freePostage", "是否包邮"),
    GIFT_ITEM_LIST("giftItemList", "赠品列表"),
    ICON("couponList","优惠券列表");

    private String value;
    private String name;

    PropertyEnum(String value, String name) {

        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }
}
