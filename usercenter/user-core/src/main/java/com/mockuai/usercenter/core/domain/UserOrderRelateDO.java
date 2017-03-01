package com.mockuai.usercenter.core.domain;

import java.util.Date;

public class UserOrderRelateDO extends BaseDO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6507312136841586461L;

	private Long id;
	
	private String bizCode;
	
	private Long userId;
	
	private Long sellerId;
	
	private Long orderId;
	
	private String relateStatus;
	
	private Long orderAmount;
	
	private String isPaid;
	
	private String isRefund;
	
	
	private Long deleteMark;
	
	private Date gmtCreated;
	
	private Date gmtModified;
	
	
	

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public String getRelateStatus() {
		return relateStatus;
	}

	public void setRelateStatus(String relateStatus) {
		this.relateStatus = relateStatus;
	}




	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Long getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Long deleteMark) {
		this.deleteMark = deleteMark;
	}
	

}
