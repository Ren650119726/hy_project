package com.mockuai.tradecenter.common.domain;

import java.util.Date;

public class OrderTrackDTO extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4133697809598754338L;
	
	String trackInfo;
	
	String operator;
	
	Date operateTime;
	
	Integer orderStatus;

	public String getTrackInfo() {
		return trackInfo;
	}

	public void setTrackInfo(String trackInfo) {
		this.trackInfo = trackInfo;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	

}
