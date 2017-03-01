package com.mockuai.marketingcenter.common.constant;

/**
 * 优惠券发放途径
 * <p/>
 * Created by edgar.zr on 7/15/2016.
 */
public enum GrantSourceEnum {
	SET_UP_SHOP(1, "开店"),
	REGISTER(2, "这册"),
	INVITE(3, "邀请"),
	MARKET_ACTIVITY(4, "满减送"),
	RECEIVE(5, "用户领取"),
	GRANT_BACKEND(6, "后台发放");

	private int value;
	private String name;

	GrantSourceEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}