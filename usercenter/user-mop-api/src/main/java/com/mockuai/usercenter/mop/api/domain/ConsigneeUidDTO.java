package com.mockuai.usercenter.mop.api.domain;

public class ConsigneeUidDTO {
	
	public long getConsigneeId() {
		return consigneeId;
	}
	public void setConsigneeId(long consigneeId) {
		this.consigneeId = consigneeId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	private long consigneeId;
	private long userId;
}
