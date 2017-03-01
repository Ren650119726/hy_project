package com.mockuai.rainbowcenter.common.constant;

/**
 * *********************************订单相关操作接口*******************************************
 */
public enum ActionEnum {
    EDB_API("edbApi"),
    /**
     * 推送订单到EDB
     */
    DELIVERY_EDB_ORDER("deliveryEdbOrder"),
    QUERY_EDB_ITEMS("queryEdbItems"),

    /**
     * 推送订单到管易ERP
     */
    DELIVERY_GYERP_ORDER("deliveryGyerpOrder"),

    /**
     * 韩束商品退回仓库
     */
    HS_RETURN_ITEM("hsReturnItem"),

    /**
     * 韩束取消订单
     */
    HS_CANCEL_ORDER("hsCancelOrder"),

    /**
     * 添加商品
     */
    ADD_ITEM("addItem"),
    /**
     * 取消订单
     */
    CANCEL_ORDER("cancelOrder"),
    /**
     * 商品退回仓库
     */
    RETURN_ITEM("returnItem"),
    /**
     * 提交订单
     */
    PUSH_ORDER_DATA_INFO("pushOrderDataInfo"),
    /**
     * 推送供应商订单
     */
    PUSH_SUPPLIER_ORDER("pushSupplierOrder"),
    /**
     * 查询商品库存
     */
    GET_STOCKS("getStocks"),
    /**
     * 供应商库存变化
     */
    UPDATE_SUPPLIER_STOCKS("updateSupplierStocks"),
    /**
     * 供应商订单状态变化
     */
    UPDATE_SUPPLIER_ORDER_STATUS("updateSupplierOrderStatus"),
    /**
     * 添加供应商物流信息
     */
    ADD_SUPPLIER_LOGISTICS_INFO("addSupplierLogisticsInfo"),
    /**
     * 插入数据到供应商订单临时表
     */
    ADD_SUPPLIER_ORDER("addSupplierOrder"),

    GET_ORDERLIST_BY_QTO_WITH_PAGE("getOrderListByQTOWithPage"),
    /**
     * 添加SysFieldMap
     */
    ADD_SYSFIELDMAP("addSysFieldMap"),
    /**
     * 批量添加SysFieldMap
     */
    BATCH_ADD_SYSFIELDMAP("batchAddSysFieldMap"),
    /**
     * 根据外部值,更新SysFieldMap
     */
    UPDATE_SYSFIELDMAP_BY_OUTVALUE("updateSysFieldMapByOutvalue"),
    /**
     * 根据内部值,更新SysFieldMap
     */
    UPDATE_SYSFIELDMAP_BY_VALUE("updateSysFieldMapByValue"),
    /**
     * 获取SysFieldMap
     */
    GET_SYSFIELDMAP("getSysFieldMap"),

    /**
     * 兑吧:兑换积分
     */

    DUIBA_DEDUCT_CREDITS("duibaDeductCredits"),


    /**
     * 兑换结果
     */
    DUIBA_EXCHANGE_RESULT("duibaExchangeResult"),

    /**
     *
     * 登陆兑吧免登录
     */
    DUIBA_CREDIT_AUTO_LOGIN("duibaCreditAutoLogin"),

    /**
     * 获取退款订单信息
     */
    GET_REFUND_ORDER_INFO("getRefundOrderInfo"),


    /**
     * 获取退货订单信息
     */
    GET_RETURN_ORDER_INFO("getReturnOrderInfo"),

    /**
     * 单个sku的库存同步
     */
    SINGLE_SKU_SN_STOCK_SYNC("singleSkuSnStockSync"),

    /**
     * 批量sku的库存同步
     */
    BATCH_SKU_SN_STOCK_SYNC("batchSkuSnStockSync"),

    /**
     * 查询版本信息
     */
    GET_VERSION_DEPLOY("getVersionDeploy"),

    /**
     * 查询SysFieldMap
     */
    QUERY_SYSFIELDMAP("querySysFieldMap");

    private String actionName;

    private ActionEnum(String actionName) {
        this.actionName = actionName;
    }

    public static ActionEnum getActionEnum(String actionName) {
        for (ActionEnum ae : ActionEnum.values()) {
            if (ae.actionName.equals(actionName)) {
                return ae;
            }
        }
        return null;
    }

    public String getActionName() {
        return actionName;
    }
}

