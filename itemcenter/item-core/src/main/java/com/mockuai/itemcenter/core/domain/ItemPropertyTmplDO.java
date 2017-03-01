package com.mockuai.itemcenter.core.domain;

import java.util.Date;
import java.util.List;

public class ItemPropertyTmplDO {
	
	private Long id;

	private String tmplName; // 模板名称

	private Long sellerId;// owner_type=1，则owner_id为0，如果owner_type=2，则owner_id为用户ID

	private String name;// 属性名

	private Integer valueType;// 属性值

	private Integer sort;// 排序字段，如果该字段值相同，则按照属性key来排序

	private Long bizMark;// 业务标志，例如是否包含图片等等
	
	private String code;

	private Long categoryId;// 所属类目ID
	
	private Integer must; //字段是否是必填  前端需要星号提示   －－ updated by cwr
	
	private Integer userDefined; //是否允许用户自修改
	
	private Integer fieldType; // 字段类型
	
	private String bizCode;

	private Integer deleteMark;

	private Date gmtCreated;

	private Date gmtModified;
	

	private List<ItemPropertyValueDO> propertyValues;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTmplName() {
		return tmplName;
	}

	public void setTmplName(String tmplName) {
		this.tmplName = tmplName;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getMust() {
		return must;
	}

	public void setMust(Integer must) {
		this.must = must;
	}

	public Integer getUserDefined() {
		return userDefined;
	}

	public void setUserDefined(Integer userDefined) {
		this.userDefined = userDefined;
	}

	public Integer getFieldType() {
		return fieldType;
	}

	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
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
	 
	public List<ItemPropertyValueDO> getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(List<ItemPropertyValueDO> propertyValues) {
		this.propertyValues = propertyValues;
	}

}