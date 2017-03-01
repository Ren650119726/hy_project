package com.mockuai.tradecenter.mop.api.domain;

import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;

import java.util.List;

public class MopDistributorCartDTO {

	private Long distributorId;
	
	private String distributorName;
	
	private List<MopCartItemDTO> itemList;

	private List<DiscountInfo>  discountInfoList;

	public Long getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorShopName) {
		this.distributorName = distributorShopName;
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
