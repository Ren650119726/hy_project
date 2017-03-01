package com.mockuai.tradecenter.core.service.action.order.add.step;

/**
 * Created by zengzhangqiang on 5/23/16.
 */
public enum StepName {
    CHECK_BASE_PARAM_STEP("checkBaseParamStep"),
    CHECK_ORDER_DELIVERY_TYPE_STEP("checkOrderDeliveryTypeStep"),
    CHECK_ORDER_PAYMENT_STEP("checkOrderPaymentStep"),
    CHECK_PRE_ORDER_STEP("checkPreOrderStep"),
    LOAD_ORDER_ITEM_STEP("loadOrderItemStep"),
    CHECK_ORDER_ITEM_STEP("checkOrderItemStep"),
    HANDLE_GIFT_ITEM_STEP("handleGiftItemStep"),
    HANDLE_HIGO_EXTRA_INFO_STEP("handleHigoExtraInfoStep"),
    HANDLE_DISTRIBUTOR_INFO_STEP("handleDistributorInfoStep"),
    HANDLE_ITEM_FREEZING_STEP("handleItemFreezingStep"),
    HANDLE_ORDER_SPLIT_STEP("handleOrderSplitStep"),
    HANDLE_ORDER_SETTLEMENT_STEP("handleOrderSettlementStep"),
    HANDLE_ORDER_ASSEMBLING_STEP("handleOrderAssemblingStep"),
    HANDLE_GIFT_PACK_BUY_LIMIT_STEP("handleGiftPackBuyLimitStep"),
    LOAD_PAY_TIMEOUT_CONF_STEP("loadPayTimeoutConfStep"),
    HANDLE_ORDER_TRANSACTION_STEP("handleOrderTransactionStep"),
    LAST_STEP("lastStep");
    private String value;
    private StepName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
