
package com.mockuai.tradecenter.common.constant;



public enum ActionEnum {
	
	/**
	 * *********************************************结算相关***********************************************
	 */
	QUERY_SELLER_TRANS_LOG("querySellertranslog"),
	
	QUERY_WITHDRAW("queryWithdraw"),
	
	QUERY_SELLER_MONEY("querySellerMoney"),
	
	
	PROCESS_WITHDRAW("processWithdraw"),
	
	APPLY_WITHDRAW("applyWithdraw"),
	
	QUERY_UNSETTLEMENT_ORDERS("queryUnsettlementOrders"),
	
	NOTIFY_WITHDRAW_RESULT("notifyWithdrawResult"),
	/**
	 * *********************************************数据相关***********************************************
	 */
	/**
	 * 查询数据中心数据
	 */
	QUERY_DATA("queryData"),
	/**
	 * 获取销量top10的商品
	 */
	QUERY_TOP10_ITEM("queryTOP10Item"),
	/**
	 * 获取数据总数
	 */
	GET_DATA("getData"),
	
	/**
	 * 按天获取数据
	 */
	GET_DATA_DAILY("getDataDaily"),
	
	/**
	 * 查询销售比率、
	 */
	QUERY_SALES_RATIO("querySalesRatio"),

    /**
     * 查询订单总支付金额 huang0831
     */

    GET_USER_TOTAL_COST("getUserTotalCost"),


    /**
     * 查询用户订单数量
     */
    QUERY_USER_ORDER_COUNT("queryUserOrderCount"),
    /**
     * 查询下单用户
     */
    QUERY_USER("queryUser"),

    /**
	 * *********************************订单相关操作接口*******************************************
	 */
	REPLY_COMMENT("replyComment"),
	/**
	 *得到物流公司信息
	 */
	GET_LOGISTICS_COMPANY("getLogisticsCompany"),
	
	/**
	 * 批量发货
	 */
	BATCH_DELIVERY("batchDeliveryGoods"),
    /**
     * 添加订单
     */
    ADD_ORDER("addOrder"),
    
    ADD_PRE_ORDER("addPreOrder"),
    
    ADD_NO_DELIVERY_ORDER("addNoDeliveryOrder"),

    /**
     * 支付订单
     */
    PAY_ORDER("payOrder"),

    /**
     * 订单结算
     */
    ORDER_SETTLEMENT_GET("orderSettlementGet"),

    /**
     * 取消订单
     */
    CANCEL_ORDER("cancelOrder"),

    /**
     * 删除订单
     */
    DELETE_ORDER("deleteOrder"),

    /**
     * 复合条件查询买家订单
     */
    QUERY_USER_ORDER("queryUserOrder"),
    /**
     * 复合条件查询买家订单
     */
    QUERY_ALL_ORDER_BG("queryAllOrderBg"),
    QUERY_ACTIVITY_STATISTICS("queryActivityStatistics"),
    QUERY_SALE_RANK("querySaleRank"),



    /**
     * 复合条件查询卖家订单
     */
    QUERY_SELLER_ORDER("querySellerOrder"),
    
    /**
     * 平台间调用订单列表接口
     */
    QUERY_INNER_USER_ORDER("queryInnerUserOrder"),

	/**
	 * 批量推送订单
	 */
    UPDATE_PUSH_ORDER_STATUS("updatePushOrderStatus"),

	/**
	 * 回滚订单
	 */
    ROLLBACK_ORDER("rollBackOrder"),
    
    UPDATE_DELIVERY_MARK("updateDeliveryMark"),
    
    UPDATE_ORDER_SUPPLIER("updateOrderSupplier"),
    
    /**
     * TODO
     *  20160713晚支付无法正常回调，模拟回调处理支付成功订单
     */
    CALL_BACK_RECOVER("callbackRecover"),
    
    /**
     * 关闭订单
     */
    CLOSE_ORDER("closeOrder"),

    /**
     * 根据订单id和用户id查询订单
     */
    GET_ORDER("getOrder"),

    /**
     * 获取支付方式列表
     */
    GET_PAYMENT_CONFIG("GetPaymentConfig"),       

    /**
     * 跟新订单备注
     */
    UPDATE_ORDER_MEMO("updateOrderMemo"),

    /**
     * 修改订单支付方式
     */
    UPDATE_ORDER_PAY_TYPE("updateOrderPayType"),

    /**
     * 确认收货 整单收货
     */
    CONFIRM_RECEIVAL("confirmReceival"),

    /**
     * 评论订单
     */
    COMMENT_ORDER("commentOrder"),
    
    /**
     * 评论查询
     */
    QUERY_COMMENT("queryComment"),
    

    /**
     * 订单发货
     */
    DELIVERY_GOODS("deliverGoods"),

    /**
     * 申请退货
     */
    APPLY_RETURN("applyReturn"),
    /**
     * 审核用户客退申请
     */
    AUDIT_RETURN_APPLY("auditReturnApply"),
    /**
     * 更新收货信息
     */
    UPDATE_CONSINEE_INFO("UpdateConsigneeInfo"),

    /**
     * 代发货订单数
     */
    PENDING_DELIVERY("pendingDelivery"),
    
    
    /**
     * 修改订单支付方式
     */
    UPDATE_ORDER_PRICE("updateOrderPrice"),
    
    UPDATE_ORDER_ASTERISKS_MARK("updateOrderAsterisksMark"),


    /**
     *
     *
     */
    GET_SHARE_ID("getShareId"),


    /**************************************购物车相关接口*******************************************/
    /**
     * 购物车相关接口
     */
    CLEAN_CART("cleanCart"),
    /**
     *
     */
    CLEAN_USER_CART("cleanUserCart"),
    /**
     * 移除购物车项
     */
    DELETE_CART_ITEM("deleteCartItem"),
    /**
     * 未登入用戶添加购物车选项
     */
    ADD_CART_ITEM("addCartItem"),
    /**
     * 已登入用戶添加购物车项
     */
    ADD_USER_CART_ITEM("addUserCartItem"),
    /**
     * 已登入用户移除购物车
     */
    DELETE_USER_CART_ITEM("deleteUserCartItem"),
    /**
     * 更新购物车数量
     */
    UPDATE_CART_ITEM("updateCartItem"),
    /**
     * 更新已的登入用户购物车的商品数
     */
    UPDATE_USER_CART_ITEM("updateUserCartItem"),
    /**
     * 同步购物车
     */
    SYNC_USER_CART("syncUserCart"),
    /**
     * 已登入用户的购物车商品列表
     */
    QUERY_CART_ITEMS("queryCartItems"),
    /**
     * 已登入用户的购物车商品列表
     */
    QUERY_USER_CART_ITEMS("queryUserCartItems"),
    /**
     * 未登入购物车信息
     */
    GET_CART_INFO("getUserCartInfo"),
    /**
     * 已登入用户购物车信息
     */
    GET_USER_CART_INFO("getUserCartInfo"),

    /**************************************支付相关接口*******************************************/
    /**
     * 支付宝支付回调接口
     */
    ALIPAY_CALLBACK("alipayCallback"),
    /**
     * 连连支付回调接口
     */
    LIANLIAN_PAY_CALLBACK("lianlianpayCallback"),
    /**
     * 连连退款回调接口
     */
    LIANLIAN_REFUND_CALLBACK("lianlianRefundCallback"),
    /**
     * 微信支付回调接口
     */
    WECHAT_PAY_CALLBACK("wechatPayCallback"),

    /**
     * 银联支付回调接口
     */
    UNION_PAY_CALLBACK("unionPayCallback"),
    UNION_PAY_CALLBACK_FOR_OLD_VERSION("unionPayCallbackForOldVersion"),
    /**
     * 确认已退款
     */
    CONFIRM_REFUND("confirmRefund"),
    /**
     * 添加支付方式
     */
    ADD_PAYMENT("addPayment"),
    /**
     * 删除支付方式
     */
    DELETE_PAYMENT("deletePayment"),
    //zhifu
    GET_PAYMENT_URL("getPaymentUrl"),
    GET_PAYMENT_URL_FOR_WAP("getPaymentUrlForWap"),
    GET_ORDER_STATISTIC("getOrderStatistic"),
    QUERY_USERCART_ITEMS_FOR_MULTIPLE_SHOPS("queryUsercartItemsForMultipleShops"),
    
    QUERY_USERCART_ITEMS_FOR_SUPPLIER("queryUsercartItemsForSupplier"),
    QUERY_USERCART_ITEMS_FOR_DISTRIBUTOR("queryUsercartItemsForDistributor"),
    QUERY_USERCART_ITEMS("queryUsercartItems"),

     INSERT_ORDER("insertOrder"),
    
	
     GET_ITEM_REFUND_DETAIL("getItemRefundDetail"),

     QUERY_REFUND_ORDER("queryRefundOrder"),




    SEND_MESSAGE("sendMessage"),
	
    GET_PICKUP_CODE("getPickupCode"),
    
   
    GET_PRE_ORDER("getPreOrder"),
    
    ADD_ACTIVITY_ORDER("addActivityOrder"),
   
    SUM_PAY_CALLBACK("sumPayCallBack"),
    
    GET_ALONE_ORDER("getAloneOrder"),
    
    SEND_WECHAT_MESSAGE("sendWechatMessage"),
    
    ADD_MULTISHOP_ORDER("addMultishopOrder"),
    
    QUERY_MALL_WITHDRAW_INFO("queryMallWithdrawInfo"),
    
    REFUSE_WITHDRAW_APPLY("refuseWithdrawApply"),
    
    APPLY_FROZEN("applyFrozen"),
    
    BATCH_AUDIT_WITHDRAW("batchAuditWithdraw"),
    
    QUERY_CATEGORY_TOP_SALE_ITEMS("queryCategoryTopSaleItems"),
    
    MARK_REFUND("markRefund"),
    
    ADD_SHOP_DEPOSIT("addShopDeposit"),
    
    BATCH_ADD_USER_CARTITEM("batchAddUserCartItem"),

    BATCH_ADD_USER_CARTITEM_J("batchAddUserCartItemJ"),


    SUMPAY_REFUND_CALLBACK("sumPayRefundCallback"),
    
    UPDATE_TRADE_CONFIG("updateTradeConfig"),
    
    QUERY_TRADE_CONFIG("queryTradeConfig"),
    
    
    REVISE_REFUND("revishRefund"),
    
    QUERY_ITEM_SALES("queryItemSales"),
    QUERY_ORDER_TRACK("queryOrderTrack"),
    
    UPDATE_ORDER_2_PAID("updateOrder2Paid"),

    UPDATE_ORDER_CONSIGNEE("updateOrderConsignee"),
    /**************************************订单操作记录相关接口*******************************************/
    /**
     * 查询订单的操作日志
     */
    GET_USER_ORDER_LOG("getUserOrderLog"),

	QUERY_TRADE_NOTIFY_LOG("queryTradeNotifyLog");


	
	
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

