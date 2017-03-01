package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by edgar.zr on 7/15/2016.
 */
public class GrantCouponInfoDTO implements Serializable {
	private Long userId;
	private Long couponId;
	private Integer number;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}