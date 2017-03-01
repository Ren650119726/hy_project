package com.mockuai.distributioncenter.common.constant;

/**
 * Created by duke on 15/10/28.
 */
public enum ActionEnum {
    GET_SHOP_QRCODE("getShopQrCode"),
    QUERY_GOODS_ITEM("queryGoodsItem"),
    GET_SHOP_GROUP("getShopGroup"),
    // 分拥方案CRUD
    ADD_ITEM_DIST_PLAN("addItemDistPlan"),
    GET_ITEM_DIST_PLAN_BY_ITEM("getItemDistPlanByItem"),

    GET_DIST_PLAN_BY_ITEM_SKU_ID("GetDistPlanByItemSkuId"),

    UPDATE_ITEM_DIST_PLAN("updateItemDistPlan"),
    ADD_ITEM_SKU_DIST_PLAN("addItemSkuDistPlan"),
    GET_ITEM_SKU_DIST_PLAN_BY_ITEM_SKU("getItemSkuDistPlanByItemSku"),
    UPDATE_ITEM_SKU_DIST_PLAN("updateItemSkuDistPlan"),
    GET_ITEM_SKU_DIST_PLAN_BY_ITEM("getItemSkuDistPlanByItem"),

    // 店铺CRUD
    GET_SHOP_BY_USER_ID("getShopByUserId"),
    UPDATE_SHOP("updateShop"),
    ADD_COLLECTION_SHOP("addCollectionShop"),
    DELETE_COLLECTION_SHOP("deleteCollectionShop"),
    QUERY_COLLECTION_SHOP_BY_USER_ID("queryCollectionShopByUserId"),
    UPDATE_SHOP_QRCODE_URI("updateShopQrcodeUri"),
    QUERY_SHOP("queryShop"),
    GET_SHOP_BY_SELLER_ID("getShopBySellerId"),

    // 卖家CRUD
    CREATE_SELLER("createSeller"),
    CREATE_SELLER_HAIKE("createSellerHaiKe"),
    GET_SELLER_BY_USER_ID("getSellerByUserId"),
    GET_SELLER_AND_INVITER_BY_USER_ID("getSellerAndInviterByUserId"),
    GET_POSTERITY_SELLER("getPosteritySeller"),
    GET_SELLER_SUMMARY("sellerSummary"),
    GET_SELLER_BY_INVITER_CODE("getSellerByInviterCode"),
    GET_POSTERITY_BY_USER_ID("getPosterityByUserId"),
    GET_PARENT_SELLER_BY_USER_ID("getParentSellerByUserId"),
    GET_TEAM_SUMMARY("getTeamSummary"),
    QUERY_SELLER("querySeller"),
    GET_SELLER_CENTER("getSellerCenter"),
    UPDATE_SELLER_COUNT("updateSellerCount"),
    UPDATE_SELLER("updateSeller"),
    UPDATE_SELLER_REAL_NAME_BY_USER_ID("updateSellerRealNameByUserId"),

    // 分拥操作
    DO_DISTRIBUTION("doDistribution"),

    // MQ 消息处理Action
    ORDER_FINISHED_LISTENER("orderFinishedListener"),
    PAY_SUCCESS_LISTENER("paySuccessListener"),
    ORDER_REFUND_SUCCESS_LISTENER("orderRefundListener"),
    ORDER_UNPAID_LISTENER("orderUnpaidListener"),
    ORDER_DELIVERY_LISTENER("orderDeliveryListener"),
    ORDER_CANCEL_LISTENER("orderCancelListener"),


    //分拥记录表
    FENYONG_ORDER_UNPAID("fenyongOrderUnpaid"),
    FENYONG_ORDER_SUCCESS("fenyongOrderSuccess"),
    
    //卖家配置
    ADD_SELLER_CONFIG("addSellerConfig"),
    UPDATE_SELLER_CONFIG("updateSellerConfig"),
    QUERY_SELLER_CONFIG("querySellerConfig"),
    GET_SELLER_CONFIG("getSellerConfig"),

    // 关系
    BUILD_RELATION_SHIP("buildRelationship"),
    CREATE_RELATION_SHIP("createRelationship"),
    //升级请求
    SELLER_UPGRADE_LIST("sellerUpgradeList"),
    AGREE_SELLER_UPGRADE("agreeSellerUpgrade"),
    REJECT_SELLER_UPGRADE("rejectSellerUpgrade"),
    ADD_SELLER_UPGRADE("addSellerUpgrade"),
    QUERY_SELLER_UPGRADE("querySellerUpdate"),

    // 订单
    LIST_ORDER("listOrder"),
    GET_ORDER_DETAIL("getOrderDetail"),

    //级别申请
    GET_SELLER_LEVEL_APPLY("getSellerLevelApply"),
    ADD_SELLER_LEVEL_APPLY("addSellerLevelApply"),
    UPDATE_SELLER_LEVEL_APPLY("updateSellerLevelApply"),
    QUERY_SELLER_LEVEL_APPLY("querySellerLevelApply"),

    //收益设置

    ADD_GAINS_SET("addGainsSet"),

    GET_GAINS_SET("getGainsSet"),

    GET_MY_FANS("getMyFans"),


    /**
     * 查询粉丝和粉丝带来的收益
     */
    QUERY_FANS_AND_DIST("queryFansAndDist"),
    
   
    
    //嗨客协议
    
    ADD_PROTOCOL("addProtocol"),

    ADD_HK_PROTOCOL("addHkProtocol"),
    
    
    GET_HK_PROTOCOL("getHkProtocol"),

    QUERY_HK_PROTOCOL("queruHkProtocol"),

    GET_PROTOCOL("getProtocol"),
    USERSELLER("userseller"),
    
    RECORDSTATISTICS("recordStatistics");
    
   
    private String actionName;

    ActionEnum(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return this.actionName;
    }
}
