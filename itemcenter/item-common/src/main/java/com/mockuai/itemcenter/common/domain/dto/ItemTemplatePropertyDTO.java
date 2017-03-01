package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

public class ItemTemplatePropertyDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8109447589638773623L;

	private Long id;

	private Long itemTemplateId;// 商品ID

	private Long sellerId;// 供应商ID

	private String name;// 属性名

	private String value;// 属性名

	private Integer valueType;// 属性值类型

	private Integer sort;// 排序字段，如果该字段值相同，则按照属性key来排序

	private Long bizMark;// 业务标志，例如是否包含图片等等

	private Integer deleteMark;
	
	private String bizCode;

	private Date gmtCreated;

	private Date gmtModified;
	
	private Long propertyValueId;  // 基本值的id updated  by cwr
	
	private Long itemPropertyTmplId; // 普通属性模版的id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemTemplateId() {
		return itemTemplateId;
	}

	public void setItemTemplateId(Long itemTemplateId) {
		this.itemTemplateId = itemTemplateId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getValueType() {
		return valueType;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getBizMark() {
		return bizMark;
	}

	public void setBizMark(Long bizMark) {
		this.bizMark = bizMark;
	}

	public Integer getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
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

	public Long getItemPropertyTmplId() {
		return itemPropertyTmplId;
	}

	public void setItemPropertyTmplId(Long itemPropertyTmplId) {
		this.itemPropertyTmplId = itemPropertyTmplId;
	}

	@Override
	public String toString() {
		return "ItemTemplatePropertyDTO [id=" + id + ", itemTemplateId="
				+ itemTemplateId + ", sellerId=" + sellerId + ", name=" + name
				+ ", value=" + value + "]";
	}

}