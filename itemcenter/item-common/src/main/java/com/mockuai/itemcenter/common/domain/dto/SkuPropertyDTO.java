package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * SKU属性DTO
 * 
 * @author chen.huang
 * @date 2015年1月21日
 */

public class SkuPropertyDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1635930837748435730L;

	private Long id;

	private Long skuId;

	private Long sellerId;// 卖家ID

	private String name;// 属性名
	
	private String code;

	private String value;// 属性名

	private Integer valueType;// 属性值类型

	private Integer sort;// 排序字段，如果该字段值相同，则按照属性key来排序

	private Long bizMark;// 业务标志，例如是否包含图片等等

	private String imgUrl;// 属性图

	private Integer deleteMark;

	private Date gmtCreated;
	
	private Date gmtModified; 
	
	private Long itemId; // 商品的id updated by cwr
	
	private String bizCode;
	
	private Long propertyValueId; //   具体的属性值的id
	
	private Long skuPropertyTmplId; // sku属性模版的id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Long getPropertyValueId() {
		return propertyValueId;
	}

	public void setPropertyValueId(Long propertyValueId) {
		this.propertyValueId = propertyValueId;
	}

	public Long getSkuPropertyTmplId() {
		return skuPropertyTmplId;
	}

	public void setSkuPropertyTmplId(Long skuPropertyTmplId) {
		this.skuPropertyTmplId = skuPropertyTmplId;
	}

	@Override
	public String toString() {
		return "SkuPropertyDO [id=" + id + ", skuId=" + skuId + ", sellerId="
				+ sellerId + ", name=" + name + ", value=" + value + "]";
	}

}