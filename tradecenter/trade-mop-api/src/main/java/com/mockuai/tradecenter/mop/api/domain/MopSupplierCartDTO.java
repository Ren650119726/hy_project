package com.mockuai.tradecenter.mop.api.domain;

import java.util.List;

import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;

public class MopSupplierCartDTO {

	private Long supplierId;
	
	private String supplierName;
	
	private List<MopCartItemDTO> itemList;

	private List<DiscountInfo>  discountInfoList;

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
