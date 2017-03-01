package com.mockuai.marketingcenter.common.constant;

/**
 * Created by zengzhangqiang on 7/23/15.
 * 营销工具类型
 */
public enum ToolType {
	/**
	 * 简单工具
	 * 优惠券/单层满减送 TODO 底层逻辑对单层满减送更改，直接作为复合的满减送，子节点的数量为 1，减少业务复杂度
	 */
	SIMPLE_TOOL(1, "SYS_MARKET_TOOL_000001"),
	/**
	 * 复合工具
	 * 满减送
	 */
	COMPOSITE_TOOL(2, "ReachMultipleReduceTool"),

	/**
	 * 换购
	 */
	BARTER_TOOL(3, "BarterTool"),
	/**
	 * 首单立减
	 */
	FIRST_ORDER_DISCOUNT(4, "FirstOrderDiscount"),
	/**
	 * 限时购
	 */
	TIME_RANGE_DISCOUNT(5, "TimeRangeDiscount"),;

	private int value;
	private String code;

	ToolType(int value, String code) {
		this.value = value;
		this.code = code;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}