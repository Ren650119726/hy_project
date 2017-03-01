package com.mockuai.suppliercenter.common.action;

public enum ActionEnum {
    /***************************************************************************/

    /************************************ 供应商操作接口 ********************************************/
    /**
     * 添加供应商
     */
    ADD_SUPPLIER("addSupplier"),

    /**
     * 符合查询条件供应商列表
     */
    QUERY_SUPPLIER("querySupplier"),

    /**
     * 根据id获取供应商信息
     */
    GET_SUPPLIER("getSupplierInf"),

    /**
     * 修改供应商信息
     */
    UPDATE_SUPPLIER("updateSupplier"),

    /**
     * 符合查询条件供应商列表
     */
    QUERY_SUPPLIERFORORDER("querySupplierInfForOrder"),
    /************************************ 仓库操作接口 ********************************************/
    /**
     * 添加供应商
     */
    ADD_STORE("addStore"),

    /**
     * 符合查询仓库列表
     */
    QUERY_STORE("queryStore"),

    /**
     * 给订单提供使用
     */
    QUERY_STOREFORORDER("queryStoreForOrder"),

    /**
     * 修改仓库信息
     */
    UPDATE_STORE("updateStore"),

    /**
     * 删除仓库
     */
    DELETE_STORE("deleteStore"),
    /**
     * 仓库禁用
     */
    FORBIDDEN_STORE("forbiddenStore"),

    /**
     * 仓库启用
     */
    ENABLE_STORE("enableStore"),
    /************************************ 仓库和sku关联操作接口 ********************************************/

    /**
     * 根据仓库编号、itemSkuId查询skuSn， 返回给管易erp使用
     */
    GET_ITEMSKU("getItemSku"),

    /**
     * StoreItemSku
     */
    ADD_STOREITEMSKU("addStoreItemSku"),

    /**
     * StoreItemSku
     */
    CANCLE_STOREITEMSKU("cancleStoreItemSku"),

    /**
     * StoreItemSku
     */
    CANCLE_STOREITEMSKULIST("cancleStoreItemSkuList"),

    /**
     * 查询商品对应库存全部数据
     */
    QUERY_STOREITEMSKU("queryStoreItemSku"),
    
    /**
     * 查询商品对应库存全部数据
     */
    QUERY_STOREITEMSKULIST("queryStoreItemSkuList"),
    
    /**
     * 根据itemid查询商品对应库存全部数据
     */
    QUERY_STOREITEMSKUBYITEMID("queryStoreItemSkuByItemId"),

    /**
     * 符合商品库存
     */
    QUERY_ITEMSTORENUMFORORDER("queryTtemStoreNumForOrder"),
    /**
     * 符合商品库存信息
     */
    QUERY_ITEMSSTOREINFFORORDER("queryItemsStoreInfoForOrder"),
    /**
     * 修改仓库信息
     */
    UPDATE_STOREITEMSKU("updateStoreItemSku"),


    /**
     * 根据skuId和仓库id增加库存的接口
     */
    INCREASE_STORENUM("increaseStoreNumAction"),


    /**
     * 根据skuId和仓库id减少库存的接口
     */
    REDUCE_STORENUM("reduceStoreNumAction"),


    /**
     * 根据skuId和仓库id增加库存的接口
     */
    COPY_SKUSTOCK("copySkuStock"),

    /**
     * 根据skuId和仓库id增加库存的接口
     */
    COPY_SKUSTOCKRETURN("copySkuStockReturn"),


    /************************************订单sku库存锁定 ********************************************/

    /**
     * 锁定库存
     */
    LOCK_STORESKUSTOCK("lockStoreSkuStock"),


    /**
     * 解锁库存
     */
    UNLOCK_STORESKUSTOCK("unlockStoreSkuStock"),


    /**
     * 去除库存
     */
    REMOVE_STORESKUSTOCK("removeStoreSkuStock"),


    /**
     * 退单，返回库存！
     */
    RETURN_STORESKUSTOCK("returnStoreSkuStock"),


    /**
     * 订单库存
     */
    RETURN_STORESKUSTOCKBYSKU("returnStoreSkuStockBySku"),


    /**
     * 根据orderSn获取订单sku 仓库关系
     */
    GET_ORDERSTORESKU("getOrderStoreSku"),

    /**
     * 根据SKUID获取供应商id和仓库id
     */
    GET_STOREITEMSKU("getStroeItemSku"),


    /************************************ 用户操作接口 ********************************************/


    /**
     * 新增全局属性
     */
    ADD_GLOBAL_MESSAGE("addGlobalMessage"),

    /**
     * 获取消息列表
     */
    QUERY_USER_MESSAGE("queryUserMessage"),


    /**
     * 修改消息状态
     */
    UPDATE_USER_MESSAGE_STATUS("updateUserMessageStatus"),
    
    /**
     * 支付完成预扣
     */
    REREDUCE_ITEM_SKU_SUP("reReduceItemSkuSup"),
    
    /**
     * 取消支付反扣
     */
    BACKREDUCE_ITEM_SKU_SUP("backReduceItemSkuSup"),
    
    /**
     * 物流发货实扣
     */
    REALREDUCE_ITEM_SKU_SUP("realReduceItemSkuSup"),

    /**
     * 冻结库存
     */
    FREEZE_ORDER_SKU_STOCK("freezeOrderSkuStock"),

    /**
     * 解冻库存
     */
    THAW_ORDER_SKU_STOCK("thawOrderSkuStock"),


    /**
     * 同步sku库存
     */
    STOCK_TO_GYERP_BY_SKUSN("updateStockToGyerpBySkuSn"),

    /**
     * 查询全局消息
     */
    QUERY_GLOBAL_MESSAGE("queryGlobalMessage"),
    /**
     * *******************************免登陆***************************************************
     */
    PUT_SESSION("putSession"),
    GET_SESSION("getSession");


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
