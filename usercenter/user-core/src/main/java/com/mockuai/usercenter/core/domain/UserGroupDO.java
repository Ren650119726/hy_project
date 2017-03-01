package com.mockuai.usercenter.core.domain;

import java.util.Date;

public class UserGroupDO extends BaseDO {
	private Integer id;

	private String name;

	private Integer groupNo;

	private Long maxIntegral;

	private Long minIntegral;

	private Integer discount;

	private Date gmtCreated;

	private Date gmtModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Long getMaxIntegral() {
		return maxIntegral;
	}

	public void setMaxIntegral(Long maxIntegral) {
		this.maxIntegral = maxIntegral;
	}

	public Long getMinIntegral() {
		return minIntegral;
	}

	public void setMinIntegral(Long minIntegral) {
		this.minIntegral = minIntegral;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
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

	public Integer getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(Integer groupNo) {
		this.groupNo = groupNo;
	}

}