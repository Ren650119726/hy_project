package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;

public class SalesRatioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5846776373538324380L;

	private Long sellerId;

	private Long categoryId;

	private Long salesAmount;

	private Double salesRatio;

	private Long itemBrandId;
	
	private java.util.Date timeStart;

	private java.util.Date timeEnd;
	
	private String brandName;
	
	private String categoryName;
	
	
	
	


	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(Long salesAmount) {
		this.salesAmount = salesAmount;
	}

	public Double getSalesRatio() {
		return salesRatio;
	}

	public void setSalesRatio(Double salesRatio) {
		this.salesRatio = salesRatio;
	}

	public Long getItemBrandId() {
		return itemBrandId;
	}

	public void setItemBrandId(Long itemBrandId) {
		this.itemBrandId = itemBrandId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public java.util.Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(java.util.Date timeStart) {
		this.timeStart = timeStart;
	}

	public java.util.Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(java.util.Date timeEnd) {
		this.timeEnd = timeEnd;
	}

}
