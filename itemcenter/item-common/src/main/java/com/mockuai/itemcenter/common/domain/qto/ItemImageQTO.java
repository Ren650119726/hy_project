package com.mockuai.itemcenter.common.domain.qto;

import java.util.Date;
import java.util.List;

import com.mockuai.itemcenter.common.page.PageInfo;

public class ItemImageQTO extends PageInfo {
	private Long id;

	private Long itemId;// 商品ID

	private Long sellerId;// 供应商ID

	private Integer imageType;// 图片类型：1代表商品主图，2代表商品属性图，3代表商品详情图

	private String imageName;// 图片名称

	private String imageUrl;// 图片地址
	
	private String bizCode;

	private Date gmtCreated;

    private Integer deleteMark;
	private List<Long> itemIdList;

	public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

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

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	
	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public void setItemIdList(List<Long> itemIdList) {
		this.itemIdList = itemIdList;
	}

	public List<Long> getItemIdList() {
		return itemIdList;
	}
}