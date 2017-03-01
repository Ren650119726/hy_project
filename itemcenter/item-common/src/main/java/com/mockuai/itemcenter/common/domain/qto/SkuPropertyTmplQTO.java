package com.mockuai.itemcenter.common.domain.qto;

import java.io.Serializable;

import com.mockuai.itemcenter.common.page.PageInfo;

/**
 * SKU模板属性QTO
 * 
 * @author chen.huang
 * @date 2015年1月21日
 */
public class SkuPropertyTmplQTO extends PageInfo implements Serializable {
	private Long categoryId; // 所属类目ID

	private Integer isDeleted;
	
	private String bizCode;  //后期每个应用一个编码 updated by cwr
	
	private Long sellerId;//

	private Long id;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
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

}
