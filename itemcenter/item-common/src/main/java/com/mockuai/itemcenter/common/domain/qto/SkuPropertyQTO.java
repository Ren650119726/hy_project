package com.mockuai.itemcenter.common.domain.qto;

import com.mockuai.itemcenter.common.page.PageInfo;

import java.util.List;

/**
 * SKU属性QTO
 * 
 * @author chen.huang
 * @date 2015年1月21日
 */

public class SkuPropertyQTO extends PageInfo {
	private Long id;

	private Long skuId;

	private Long sellerId;// 卖家ID

	private Long itemId; // updated by cwr

	private Long propertyValueId;
	private List<Long> skuIdList;

	public Long getPropertyValueId() {
		return propertyValueId;
	}

	public void setPropertyValueId(Long propertyValueId) {
		this.propertyValueId = propertyValueId;
	}

	private String name;
	
	private String bizCode;
	
	private String value;

	private Long skuPropertyTmplId;

    private Integer deleteMark;

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

	public Long getSkuPropertyTmplId() {
		return skuPropertyTmplId;
	}

	public void setSkuPropertyTmplId(Long skuPropertyTmplId) {
		this.skuPropertyTmplId = skuPropertyTmplId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setSkuIdList(List<Long> skuIdList) {
		this.skuIdList = skuIdList;
	}

	public List<Long> getSkuIdList() {
		return skuIdList;
	}
}