package com.mockuai.tradecenter.core.base.request;

import com.mockuai.tradecenter.common.domain.BaseDTO;
import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;

public class InnerRequest extends BaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5925561224302613020L;

	String appKey;
	
	String bizCode;
	
	Long userId;
	
	Integer appType;
	
	SettlementRequest settlementRequest;
	
	ItemRequest itemRequest;
	
	OrderRequest orderRequest;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public SettlementRequest getSettlementRequest() {
		return settlementRequest;
	}

	public void setSettlementRequest(SettlementRequest settlementRequest) {
		this.settlementRequest = settlementRequest;
	}

	public ItemRequest getItemRequest() {
		return itemRequest;
	}

	public void setItemRequest(ItemRequest itemRequest) {
		this.itemRequest = itemRequest;
	}

	public OrderRequest getOrderRequest() {
		return orderRequest;
	}

	public void setOrderRequest(OrderRequest orderRequest) {
		this.orderRequest = orderRequest;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}
	
	

}
