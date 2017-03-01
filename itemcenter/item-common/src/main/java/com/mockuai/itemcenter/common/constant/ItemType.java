package com.mockuai.itemcenter.common.constant;

/**
 * Created by yindingyu on 16/3/1.
 */
public enum ItemType {

    NORMAL(1, "普通商品"),

    SUIT(11, "套装商品"),

    RECHARGE(12, "充值商品"),

    SECKILL(13, "秒杀商品"),

    GROUP_BUY(14, "团购商品"),

    AUCTION(15, "拍卖商品"),

    DEPOSIT(16, "保证金商品"),

    /**
     * 占位符,不使用
     */
    BARTER_ITEM(17, "换购商品"),

    /**
     * 18 19已占
     */

    /**
     * 嗨云礼包商品
     */
    GIFT_PACKS(20,"礼包商品"),


    TIME_LIMIT(21,"限时购商品"),

    COMPOSITE_ITEM(22,"组合商品")

        ;

    private int type;

    private String typeName;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    private ItemType(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }
}