package com.mockuai.marketingcenter.common.domain.dto;

/**
 * Created by edgar.zr on 7/21/2016.
 */
public class PropertyActivityCouponDTO {
	private Long id;
	private String name;
	// 优惠券减金额
	private Long discountAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}
}