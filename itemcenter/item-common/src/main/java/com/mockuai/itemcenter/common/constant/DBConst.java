package com.mockuai.itemcenter.common.constant;

public enum DBConst {

	/**
	 * 商品正常状态
	 */
	ITEM_NORMAL(0),

	/**
	 * 商品进入回收站的状态
	 */
	ITEM_IN_TRASH(2),

	/**
	 * 品牌状态
	 */
	BRAND_NORMAL(0),

	/**
	 * 已删除
	 */
	DELETED(1),
	/**
	 * 未删除
	 */
	UN_DELETED(0),

	/**
	 * 待审核
	 */
	NOT_AUDITED(0),

	/**
	 * 审核通过
	 */
	AUDIT_PASS(1),

	/**
	 * 审核未通过
	 */
	AUDIT_UNPASS(2),

	PRICING_BY_COUNT(0),

    PRICING_BY_WEIGHT(1),

    PRICING_BY_SIZE(2),


	NORMAL_ITEM(1),

	/**
	 * 套装
	 */
	SUIT_ITEM(11),

	/**
	 * 充值
	 */
	RECHARGE_ITEM(12),

	/**
	 * 秒杀
	 */
	SECKILL_ITEM(13),

	/**
	 * 团购
	 */
	GROUP_BUY_ITEM(14),

	/**
	 * 竞拍
	 */
	ACTION_ITEM(15),

	/**
	 * 保证金
	 */
	DEPOSIT_ITEM(16),

    /**
	 * 换购商品占位,保留，不使用
	 */
    BARTER_ITEM(17);


	private int code;

	private DBConst(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
