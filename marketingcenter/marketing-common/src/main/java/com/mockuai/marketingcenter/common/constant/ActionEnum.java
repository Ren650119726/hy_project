package com.mockuai.marketingcenter.common.constant;

public enum ActionEnum {
	/**
	 * 定时任务更新限时购活动状态
	 */
	UP_DATE_LIMITED_PURCHASE_STATUS("updateLimtedPurchaseStatus"),
	/**
	 * 查看销售
	 */
	VIEW_SALES("viewSales"),
	/**
	 * mq下单成功后消息接收
	 */
	ORDER_PAY_SUCCESS_LISTENER("orderPaySuccessListener"),

	/**
	 * mq取消订单消息接收
	 */
	ORDER_CANCEL_SUCCESS_LISTENER("orderCancelSuccessListener"),
	/**
	 * 添加限时购活动列表
	 */
	ADD_TIME_PURCHASE("AddTimePurchase"),
	/**
	 * 失效限时购活动
	 */
	STOP_TIME_PURCHASE("DeleteTimePurchase"),
	/**
	 * 展示限时购活动
	 */
	TIME_PURCHASE_LIST("TimePurchaseList"),
	/**
	 * 发布限时购活动
	 */
	START_LIMITED_PURCHASE_ACTION("StartLimtedPurchaseAction"),
	/**
	 * 展示限时购单个活动和产品
	 */
	TIME_PURCHASE_BY_ID("TimePurchaseById"),
	/**
	 * 限时购单个活动和产品修改
	 */
	UPDATE_TIME_PURCHASE("UpdateTimePurchase"),
	/**
	 * 添加限时购产品
	 */
	ADD_TIME_PURCHASE_GOODS("AddTimePurchaseGoods"),
	/**
	 * 限时购产品删除
	 */
	DELETE_TIME_PURCHASE_GOODS("DeleteTimePurchaseGoods"),

	/**
	 * 查询用户优惠券
	 */
	QUERY_USER_COUPON("QueryUserCoupon"),
	/**
	 * 查询发放优惠券
	 */
	QUERY_GRANTED_COUPON("QueryGrantedCoupon"),
	/**
	 * 优惠券指定商品列表
	 */
	ACTIVITY_ITEM_LIST("ActivityItemList"),
	/**
	 * 聚集方式查询用户优惠券
	 */
	QUERY_USER_GATHER_COUPON("QueryUserGatherCoupon"),

	/**
	 * 创建活动优惠券
	 */
	GENERATE_ACTIVITY_COUPON("GenerateActivityCoupon"),
	/**
	 * 创建优惠券 new
	 */
	ADD_ACTIVITY_COUPON("addActivityCoupon"),
	/**
	 * 领取优惠券
	 */
	RECEIVE_ACTIVITY_COUPON("ReceiveActivityCoupon"),

	/**
	 * 兑换优惠券
	 */
	EXCHANGE_COUPON_CODE("ExchangeCouponCode"),

	/**
	 * 查看指定优惠券
	 */
	GET_ACTIVITY_COUPON("GetActivityCoupon"),

	/**
	 * 根据优惠券 idList 获取详情,不包含 activity 信息
	 */
	GET_ACTIVITY_COUPON_LIST_BY_IDS("GetActivityCouponListByIds"),

	/**
	 * 根据活动主体查看优惠券
	 */
	GET_ACTIVITY_COUPON_BY_ACTIVITY("GetActivityCouponByActivity"),

	/**
	 * 查询优惠码值
	 */
	QUERY_COUPON_CODE("QueryCouponCode"),

	/**
	 * 发放活动优惠券(为多个用户发放)
	 */
	GRANT_ACTIVITY_COUPON("GrantActivityCoupon"),
	/**
	 * 一次为相同用户发放多张优惠券
	 */
	GRANT_ACTIVITY_COUPON_BATCH_BY_NUMBER("grantActivityCouponByNumber"),
	/**
	 * 批量发放优惠券
	 */
	GRANT_ACTIVITY_COUPON_BATCH("grantActivityCouponBatch"),
	/**
	 * 查询活动优惠券
	 */
	QUERY_ACTIVITY_COUPON("QueryActivityCoupon"),

	/**
	 * 检查指定优惠券是否可以在订单中使用
	 */
	ACTIVITY_COUPON_AVAILABLE("ActivityCouponAvailable"),
	/**
	 * 使优惠券失效
	 */
	INVALID_ACTIVITY_COUPON("InvalidActivityCoupon"),

	/**
	 * 更新活动优惠券
	 */
	UPDATE_ACTIVITY_COUPON("UpdateActivityCoupon"),
	/**
	 * 更新满减送
	 */
	UPDATE_MARKET_ACTIVITY("UpdateMarketActivity"),
	/**
	 * 预使用优惠券
	 */
	PRE_USE_USER_COUPON("PreUseUserCoupon"),

	/**
	 * 批量预使用优惠券
	 */
	PRE_MULTI_USE_USER_COUPON("PreUseUserCouponBatch"),

	/**
	 * 解除优惠券的预授权状态，还原到未使用状态
	 */
	RELEASE_USED_COUPON("ReleaseUsedCoupon"),

	/**
	 * 批量解除优惠券的预授权状态，还原到未使用状态
	 */
	RELEASE_MULTI_USED_COUPON("ReleaseMultiUsedCoupon"),

	/**
	 * 使用优惠券
	 */
	USE_USER_COUPON("UseActivityCoupon"),

	/**
	 * 批量使用优惠券
	 */
	USE_MULTI_USER_COUPON("UseMultiActivityCoupon"),

	/**
	 * 转移优惠券
	 */
	TRANSFER_USER_COUPON("transferUserCoupon"),

	ADD_ACTIVITY("addActivity"),
	ADD_COMPOSITE_ACTIVITY("addCompositeActivity"),
	UPDATE_ACTIVITY("updateActivity"),
	DELETE_ACTIVITY("deleteActivity"),
	INVALID_ACTIVITY("invalidActivity"),
	QUERY_ACTIVITY("queryActivity"),
	/**
	 * 根据商品查询优惠活动
	 */
	QUERY_ITEM_DISCOUNTINFO("queryItemDiscountInfo"),
	/**
	 * 根据商品查询优惠活动,返回 mop 结构数据
	 */
	QUERY_ITEM_DISCOUNTINFO_MOP("queryItemDiscountInfoMop"),
	/**
	 * 购物车商品优惠活动
	 */
	CART_DISCOUNT_INFO_LIST("cartDiscountInfoList"),
	/**
	 * 商品列表优惠查询
	 */
	DISCOUNT_INF_OF_ITEM_LITE("discountInfoOfItemList"),

	GET_ACTIVITY("getActivity"),
	/**
	 * 限时购与满减送时间冲突检验
	 */
	CONFLICT_TIME_OF_ACTIVITY_CHECK("conflictTimeOfActivityCheck"),

/**
 *＊
 *＊ 换购
 *＊
 */
	/**
	 * 添加
	 */
	ADD_BARTER("addBarterActivity"),
	/**
	 * 更新
	 */
	UPDATE_BARTER("updateBarterActivity"),
	/**
	 * get
	 */
	GET_BARTER("getBarterActivity"),
	/**
	 * 查询
	 */
	QUERY_BARTER("queryBarterActivity"),

	ADD_TOOL("addTool"),
	DELETE_TOOL("deleteTool"),
	UPDATE_TOOL("updateTool"),
	QUERY_TOOL("queryTool"),
	GET_TOOL("getTool"),
	ADD_COMPOSITE_TOOL("addCompositeTool"),

/**
 * *
 * *
 * *
 * *
 * 虚拟财富
 * *
 * *
 * *
 */

	/**
	 * 初始化平台虚拟财富帐号
	 */
	INIT_VIRTUAL_WEALTH("initVirtualWealth"),
	/**
	 * 创建财富发放规则
	 */
	ADD_GRANT_RULE("addGrantRule"),
	/**
	 * 删除财富发放规则
	 */
	DELETE_GRANT_RULE("deleteGrantRule"),
	/**
	 * 更新财富发放规则
	 */
	UPDATE_GRANT_RULE("updateGrantRule"),
	/**
	 * 查看财富发放规则
	 */
	GET_GRANT_RULE("getGrantRule"),
	/**
	 * 通过属主查看发放规则
	 */
	GET_GRANT_RULE_BY_OWNER("getGrantRuleByOwner"),
	/**
	 * 查询财富发放规则
	 */
	QUERY_GRANT_RULE("queryGrantRule"),
	/**
	 * 财富发放规则开关
	 */
	SWITCH_GRANT_RULE("switchGrantRule"),
	/**
	 * 更新商家虚拟财富
	 */
	UPDATE_VIRTUAL_WEALTH("updateVirtualWealth"),
	/**
	 * 查看商家虚拟财富信息
	 */
	GET_VIRTUAL_WEALTH("getVirtualWealth"),

	/**
	 * 根据发放规则发放财富
	 */
	GRANT_VIRTUAL_WEALTH_WITH_GRANT_RULE("grantVirtualWealthWithGrantRule"),

	///////////////////////

	/**
	 * 生成商家虚拟财富帐号
	 */
	GENERATE_VIRTUAL_WEALTH("generateVirtualWealth"),
	/**
	 * 发放虚拟财富
	 */
	GRANT_VIRTUAL_WEALTH("grantVirtualWealth"),
	/**
	 * 批量发放虚拟财富
	 */
	GRANT_VIRTUAL_WEALTH_BATCH("grantVirtualWealthBatch"),

	/**
	 * 查询虚拟帐号
	 */
	QUERY_WEALTH_ACCOUNT("queryWealthAccount"),

	/**
	 * 批量查询帐号
	 */
	QUERY_WEALTH_ACCOUNT_BATCH("queryWealthAccountBatch"),

	/**
	 * 预使用用户虚拟财富
	 */
	PRE_USE_USER_WEALTH("preUseUserWealth"),

	/**
	 * 批量预使用用户虚拟财富
	 */
	PRE_USE_MULTI_USER_WEALTH("preUseMultiUserWealth"),

	/**
	 * 虚拟财富由预使用转换到正式使用
	 */
	USE_USER_WEALTH("useUserWealth"),

	/**
	 * 虚拟财富由预使用转换到正式使用
	 */
	USE_MULTI_USER_WEALTH("useMultiUserWealth"),

	/**
	 * 释放预使用的虚拟财富
	 */
	RELEASE_USED_WEALTH("releaseUsedWealth"),
	/**
	 * 释放预使用的虚拟财富
	 */
	RELEASE_MULTI_USED_WEALTH("releaseMultiUsedWealth"),

	/**
	 * 回滚部分已使用的虚拟财富
	 */
	GIVE_BACK_PARTIAL_USED_WEALTH("giveBackPartialUsedWealth"),

	/**
	 * 查询商家虚拟财富
	 */
	QUERY_VIRTUAL_WEALTH("queryVirtualWealth"),

	/**
	 * 查询商家虚拟财富和对应的财富发放规则
	 */
	QUERY_VIRTUAL_WEALTH_WITH_GRANT_RULE("queryVirtualWealthWithGrantRule"),
	/**
	 * 直接扣减用户的虚拟财富
	 */
	DEDUCT_VIRTUAL_WEALTH("deductVirtualWealth"),

/**
 * *
 * *
 * *
 * *
 * 转发有礼
 * *
 * *
 * *
 */

	/**
	 * 更新转发有礼状态
	 */
	UPDATE_TRANSMIT("updateTransmit"),

	/**
	 * 更新转发有礼的金额比率
	 */
	CHANGE_TRANSMIT_PERCENT("changeTransmitRecent"),

	/**
	 * 获得转发有礼信息
	 */
	GET_TRANSMIT("getTransmit"),

	/**
	 * 转方有礼业务
	 */
	TRANSMIT("transmit"),

	/**
	 * 结算信息
	 */
	GET_SETTLEMENT_INFO("getSettlementInfo"),

	/**
	 * 添加充值配置项
	 */
	UPDATE_VIRTUAL_WEALTH_ITEM("updateVirtualWealthItem"),

	/**
	 * 查询充值配置项
	 */
	QUERY_VIRTUAL_WEALTH_ITEM("queryVirtualWewalthItem"),

	/**
	 * 删除配置项
	 */
	DELETE_VIRTUAL_WEALTH_ITEM("deleteRechargeConfig"),

	/**
	 * 添加充值记录
	 */
	ADD_RECHARGE_RECORD("addRechargeRecord"),

	/**
	 * 查询充值记录
	 */
	QUERY_RECHARGE_RECORD("queryRechargeRecord"),

	/**
	 * 更新充值记录
	 */
	UPDATE_RECHARGE_RECORD("updateRechargeRecord"),
	/**
	 * 查询销售详情记录
	 */
	QUERY_ORDER_RECORD("queryOrderRecord"),
	/**
	 * 获取单个满减送活动 销售统计数据
	 */
	GET_ORDER_GENERAL("getOrderGeneral"),
	/**
	 * 获取用户领取的优惠券统计数据
	 */
	GET_GRANTED_COUPON_FOR_USER("getGrantedCouponForUser");

	private String actionName;

	ActionEnum(String actionName) {
		this.actionName = actionName;
	}

	public static ActionEnum getActionEnum(String actionName) {
		for (ActionEnum ae : values()) {
			if (ae.actionName.equals(actionName)) {
				return ae;
			}
		}
		return null;
	}

	public String getActionName() {
		return this.actionName;
	}
}