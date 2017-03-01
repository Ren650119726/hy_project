package com.mockuai.rainbowcenter.common.constant;

/**
 * Created by yeliming on 16/3/15.
 */
public enum SysFieldNameEnum {
    /**
     * Item编码
     */
    ITEM_CODE("itemCode"),

    /**
     * Item编码
     */
    ERP_ITEM_CODE("erpItemCode"),

    /**
     * 类目ID
     */
    CAT_ID("catId"),

    /**
     * 用户账户
     */
    USER("user"),

    /**
     * 店铺ID
     */
    SHOP_ID("006001"),

    /**
     * 供应商
     */
    SUPPLIER("supplier"),

    /**
     * sku条形码
     */
    BAR_CODE("barCode"),

    /**
     * 商品品牌
     */
    ITEM_BRAND("itemBrand"),

    /**
     * 仓库,Mc中的供应商id和仓库id对应
     */
    STORAGE("storage"),

    /**
     * EDB订单
     */
    EDB_ORDER("edbOrder"),

    /**
     * ERP  不需要处理的仓库
     */
    WAREHOUSE_WSCODE("1-1"),
    WAREHOUSE_MZXCODE("马振鑫微商"),
    WAREHOUSE_LLXCODE("1"),
    WAREHOUSE_WEISCODE("1-2"),
    NEIGOU_WEISCODE("内购"),
    /**
     * 拆单发出去的Barcode
     */
    EDB_DELIVERY_BARCODE("edbDeliveryBarCode")
    ;
    private String value;

    SysFieldNameEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
