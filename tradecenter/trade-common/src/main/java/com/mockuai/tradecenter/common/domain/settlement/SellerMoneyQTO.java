package com.mockuai.tradecenter.common.domain.settlement;

import java.util.List;

import com.mockuai.tradecenter.common.domain.BaseQTO;

public class SellerMoneyQTO extends BaseQTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5031047314361214232L;

	private String bizCode;
	
	private Long sellerId;
	
	private Integer shopType;
	
	private List<Long> sellerIds;
	
	private Integer isAllSubShop;

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getShopType() {
		return shopType;
	}

	public void setShopType(Integer shopType) {
		this.shopType = shopType;
	}

	public List<Long> getSellerIds() {
		return sellerIds;
	}

	public void setSellerIds(List<Long> sellerIds) {
		this.sellerIds = sellerIds;
	}

	public Integer getIsAllSubShop() {
		return isAllSubShop;
	}

	public void setIsAllSubShop(Integer isAllSubShop) {
		this.isAllSubShop = isAllSubShop;
	}

	
	
	

}
