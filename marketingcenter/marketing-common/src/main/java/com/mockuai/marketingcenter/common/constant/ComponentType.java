package com.mockuai.marketingcenter.common.constant;

/**
 * Created by edgar.zr on 1/12/16.
 */
public enum ComponentType {

	ITEM_TOTAL_PRICE("itemTotalPrice", "商品总价计算"),
	DELIVERY_FEE("deliveryFee", "快递费用"),
	SAME_SELLER("sameSeller", "相同卖家"),
	GET_HIGO_SETTLEMENT("getHigoSettlement", "跨境购结算信息"),
	CLASSIFY_BY_ITEM_TYPE("classifyByItemType", "根据商品类型归类商品"),
	EXECUTE_ACTIVITY_TOOL("executeActivityTool", "执行优惠计算"),
	FILL_UP_SKU_INFO("fillUpSkuInfo", "填充商品信息"),
	FILL_UP_SKU_INFO_SIMPLE("fillUpSkuInfoSimple", "填充商品基本信息"),
	SORT_OF_COMPOSITE_ACTIVITY("sortOfCompositeActivity", "满减送子项降序排序"),
	ELIMINATE_CONFLICT_FOR_COMPOSITE_ACTIVITY("eliminateConflictForCompositeActivity", "消除满减送范围冲突"),
	LINK_ACTIVITY_WITH_ITEM_LIST("linkActivityWithItemList", "根据商品查询优惠活动,并做初步过滤"),
	FILTER_OUT_ACTIVITY_BY_CURRENT_DATE("filterOutActivityByCurrentDate", "根据当前时间过滤时间无效的活动"),
	/**
	 * 普通商品/非普通商品结算
	 */
	VALIDATE_SETTLEMENT_OF_COMMON("validateSettlementOfCommon", "通用商品结算"),
	VALIDATE_SETTLEMENT_OF_BARTER("validateSettlementOfBarter", "换购活动结算"),
	VALIDATE_SETTLEMENT_OF_SUIT("validateSettlementOfSuit", "套装活动结算"),
	VALIDATE_SETTLEMENT_OF_SECKILL("validateSettlementOfSeckill", "秒杀活动结算"),
	VALIDATE_SETTLEMENT_OF_GROUP_BUY("validateSettlementOfGroupBuy", "团购活动结算"),
	VALIDATE_SETTLEMENT_OF_AUCTION("validateSettlementOfAuction", "竞拍活动结算"),
	VALIDATE_SETTLEMENT_OF_CROWD_FUNDING("validateSettlementOfCrowdFunding", "一元夺宝活动结算"),
	VALIDATE_SETTLEMENT_OF_TIME_LIMIT("validateSettlementOfTimeLimit", "限时购结算"),
	VALIDATE_SETTLEMENT_OF_FIRST_ORDER("validateSettlementOfFirstOrder", "首单立减"),
	SAME_SUPPLIER("sameSupplier", "处于同一个供应商下");

	private String code;
	private String intro;

	ComponentType(String code, String intro) {
		this.code = code;
		this.intro = intro;
	}

	public String getCode() {
		return code;
	}

	public String getIntro() {
		return intro;
	}
}