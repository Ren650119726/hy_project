package com.mockuai.seckillcenter.common.constant;

/**
 * 用户参与订单记录
 * Created by edgar.zr on 6/23/2016.
 */
public enum OrderOfUserStatus {
	/**
	 * 预单
	 */
	PRE_ORDER(0),
	/**
	 * 未付款正式订单
	 */
	UNPAID_ORDER(1),
	/**
	 * 已付款正式订单
	 */
	PAID_ORDER(2);

	private int value;

	OrderOfUserStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}