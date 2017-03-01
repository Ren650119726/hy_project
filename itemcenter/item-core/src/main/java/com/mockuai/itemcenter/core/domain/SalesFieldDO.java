package com.mockuai.itemcenter.core.domain;

import java.util.Date;

public class SalesFieldDO {
	private Integer id;

	private String fieldName;// 专场名

	private String fieldEnName;// 专场英文名

	private String logo; // 专场logo

	private String saleInfo;// 专场特价信息描述

	private Integer sort;// 排序字段

	private Date beginTime;// 专场开始时间

	private Date endTime;// 专场结束时间

	private Integer fieldStatus;// 状态：未开始/进行中/已下线

	private Integer isDeleted;

	private Date gmtCreated;

	private Date gmtModified;

	private String brief;// 专场描述

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName == null ? null : fieldName.trim();
	}

	public String getFieldEnName() {
		return fieldEnName;
	}

	public void setFieldEnName(String fieldEnName) {
		this.fieldEnName = fieldEnName == null ? null : fieldEnName.trim();
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo == null ? null : logo.trim();
	}

	public String getSaleInfo() {
		return saleInfo;
	}

	public void setSaleInfo(String saleInfo) {
		this.saleInfo = saleInfo == null ? null : saleInfo.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getFieldStatus() {
		return fieldStatus;
	}

	public void setFieldStatus(Integer fieldStatus) {
		this.fieldStatus = fieldStatus;
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

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief == null ? null : brief.trim();
	}

	@Override
	public String toString() {
		return "SalesFieldDO [id=" + id + ", fieldName=" + fieldName + "]";
	}
}