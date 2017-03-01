package com.mockuai.tradecenter.common.domain.settlement;

import com.mockuai.tradecenter.common.domain.BaseDTO;

public class ApplyFrozenDTO extends BaseDTO{
	
	private static final long serialVersionUID = -2541136884889931484L;
	
	private Long sellerId;

	private Long amount;
	
	private String bizCode;
	
	private Integer type;
	
	private String reason;
	
	private Long shopId;

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}



	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
	

}
