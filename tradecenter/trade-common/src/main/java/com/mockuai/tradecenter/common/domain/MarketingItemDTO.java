package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;

public class MarketingItemDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6175501392358143432L;

	private Long itemSkuId; // 特定规格商品id

	private Long supplierId; // 供应商id
	
	private Integer number;
	
	private Long unitPrice;
	
	private String itemName;
	
	private String picUrl;

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Long unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

}
