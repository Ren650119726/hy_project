package com.mockuai.itemcenter.common.domain.qto;

import java.util.Date;

import com.mockuai.itemcenter.common.page.PageInfo;

public class GlobalPropertyValueQTO extends PageInfo {
	private Long id;

	private Long globalPropertyId;// 全局属性表ID

	private String propertyValue;// 全局属性表ID

	private Integer isDeleted;

	private Date gmtCreated;

	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGlobalPropertyId() {
		return globalPropertyId;
	}

	public void setGlobalPropertyId(Long globalPropertyId) {
		this.globalPropertyId = globalPropertyId;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue == null ? null : propertyValue.trim();
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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
}