package com.mockuai.usercenter.common.qto;

import java.io.Serializable;

public class UserOrderRelateQTO extends QueryPage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4666504509475544429L;

	private Long userId;

	private Long sellerId;

	private Long orderId;

	private String isPaid;

	private String isRefund;

	private String relateStatus;

	private Long orderAmount;
	

	public String getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}

	public String getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(String isRefund) {
		this.isRefund = isRefund;
	}

	public String getRelateStatus() {
		return relateStatus;
	}

	public void setRelateStatus(String relateStatus) {
		this.relateStatus = relateStatus;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}
