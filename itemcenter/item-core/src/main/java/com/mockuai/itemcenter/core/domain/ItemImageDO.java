package com.mockuai.itemcenter.core.domain;

import java.util.Date;

public class ItemImageDO {
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getItemId() {
		return itemId;
	}


	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}


	public Long getSellerId() {
		return sellerId;
	}


	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}


	public Integer getImageType() {
		return imageType;
	}


	public void setImageType(Integer imageType) {
		this.imageType = imageType;
	}


	public String getImageName() {
		return imageName;
	}


	public void setImageName(String imageName) {
		this.imageName = imageName;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public Integer getSort() {
		return sort;
	}


	public void setSort(Integer sort) {
		this.sort = sort;
	}

	
	public String getBizCode() {
		return bizCode;
	}


	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Integer getDeleteMark() {
		return deleteMark;
	}


	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}


	public Date getGmtCreated() {
		return gmtCreated;
	}


	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}


	public Date getGmtModified() {
		return gmtModified;
	}


	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}


	public Long getPropertyValueId() {
		return propertyValueId;
	}


	public void setPropertyValueId(Long propertyValueId) {
		this.propertyValueId = propertyValueId;
	}


	private Long id;

	private Long itemId;// 商品ID

	private Long sellerId;// 供应商ID

	private Integer imageType;// 图片类型：1代表商品主图，2代表商品属性图，3代表商品详情图

	private String imageName;// 图片名称

	private String imageUrl;// 图片地址

	private Integer sort;// 排序字段
	
	private String bizCode;

	private Integer deleteMark;

	private Date gmtCreated;

	private Date gmtModified;
	
	private Long propertyValueId; //该图片跟对应的具体的属性值的id 比如颜色蓝色的id   updated by cwr

	
	@Override
	public String toString() {
		return "ItemImageDO [id=" + id + ", itemId=" + itemId + ", sellerId=" + sellerId + "]";
	}
}