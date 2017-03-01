package com.mockuai.tradecenter.core.domain;

public class SalesRatioDO {
	
	private Long categoryId;
	
	private Long totalAmount;
	
	private Long itemBrandId;
	
	


	public Long getItemBrandId() {
		return itemBrandId;
	}

	public void setItemBrandId(Long itemBrandId) {
		this.itemBrandId = itemBrandId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	

}
