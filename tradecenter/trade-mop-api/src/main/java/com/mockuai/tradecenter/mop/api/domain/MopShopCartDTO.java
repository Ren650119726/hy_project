package com.mockuai.tradecenter.mop.api.domain;

import java.util.List;

import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;

public class MopShopCartDTO {
	
	private Long shopId;
	
	private String shopName;
	
	private List<MopCartItemDTO> itemList;

	private List<DiscountInfo>  discountInfoList; 

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public List<MopCartItemDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<MopCartItemDTO> itemList) {
		this.itemList = itemList;
	}

	public List<DiscountInfo> getDiscountInfoList() {
		return discountInfoList;
	}

	public void setDiscountInfoList(List<DiscountInfo> discountInfoList) {
		this.discountInfoList = discountInfoList;
	}
	
	
	
	

}
