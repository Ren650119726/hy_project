package com.mockuai.usercenter.core.domain;

import java.util.Date;

public class UserBabyInfoDO extends BaseDO {
	private Long id;

	private Long userId;

	private String babyName;

	private Byte babyStatus;

	private Byte babySex;

	private Integer babyBirthday;

	private Integer expectedChildbirthDate;

	private boolean isDefault;

	private Byte deleted;

	private Date gmtCreated;

	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Byte getBabyStatus() {
		return babyStatus;
	}

	public void setBabyStatus(Byte babyStatus) {
		this.babyStatus = babyStatus;
	}

	public Byte getBabySex() {
		return babySex;
	}

	public void setBabySex(Byte babySex) {
		this.babySex = babySex;
	}

	public Integer getBabyBirthday() {
		return babyBirthday;
	}

	public void setBabyBirthday(Integer babyBirthday) {
		this.babyBirthday = babyBirthday;
	}

	public Integer getExpectedChildbirthDate() {
		return expectedChildbirthDate;
	}

	public void setExpectedChildbirthDate(Integer expectedChildbirthDate) {
		this.expectedChildbirthDate = expectedChildbirthDate;
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

	public String getBabyName() {
		return babyName;
	}

	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}

	public boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

}