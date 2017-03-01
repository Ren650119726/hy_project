package com.mockuai.marketingcenter.common.constant;

/**
 * 是否开放给用户领取
 * <p/>
 * Created by edgar.zr on 7/15/2016.
 */
public enum CouponOpenEnum {
	OPEN(1),
	NOT_OPEN(0);

	private int value;

	CouponOpenEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}