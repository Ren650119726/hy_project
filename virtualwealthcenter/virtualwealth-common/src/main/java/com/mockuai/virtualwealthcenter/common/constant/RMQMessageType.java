package com.mockuai.virtualwealthcenter.common.constant;

/**
 * Created by edgar.zr on 12/16/15.
 */
public enum RMQMessageType {
    /**
     * 虚拟财富发放，余额
     */
    VIRTUAL_WEALTH_MONEY("virtualWealth", "money"),
    /**
     * 虚拟财富发放，积分
     */
    VIRTUAL_WEALTH_CREDIT("virtualWealth", "credit"),
    /**
     * 订单支付成功
     */
    TRADE_PAY_SUCCESS_NOTIFY("trade", "paySuccessNotify"),
    /**
     * 订单取消
     */
//    TRADE_ORDER_CANCEL("trade", "orderCancel"),
    /**
     * 订单最终结束
     */
    TRADE_ORDER_FINISHED_NOTIFY("trade", "orderFinishedNotify"),
    /**
     * 订单未支付
     */
    TRADE_ORDER_UNPAID("trade", "orderUnpaid"),
    /**
     * 分佣发放
     */
    DISTRIBUTOR_GRANT("distributor", "grant"),
    /**
     * 商品状态变化
     */
//    ITEM_STATUS_CHANGE_SINGLE("item-status-change", "single"),
    /**
     * 商品自动上下架
     */
//    ITEM_STATUS_CHANGE_BATCH("item-status-change", "batch"),
    /**
     * 商品删除
     */
//    ITEM_DELETE_SINGLE("item-delete", "single"),
    /**
     * 商品修改
     */
//    ITEM_UPDATE_SINGLE("item-update", "single"),
    /**
     * sku 变化
     */
//    ITEM_SKU_UPDATE_STOCK("item-sku-update", "stock"),
    ;
    private String topic;
    private String tag;

    RMQMessageType(String topic, String tag) {
        this.topic = topic;
        this.tag = tag;
    }

    public String getTopic() {
        return topic;
    }

    public String getTag() {
        return tag;
    }

    public String combine() {
        return getTopic() + "_" + getTag();
    }
}