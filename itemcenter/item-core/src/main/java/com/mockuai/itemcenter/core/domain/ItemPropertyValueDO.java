package com.mockuai.itemcenter.core.domain;

import java.util.Date;

public class ItemPropertyValueDO {
    private Long id;

    private String name;

    private String value;

    private Long itemPropertyTmplId;

    private String bizCode;
    
    private Integer deleteMark;
    
    private Date gmtCreated;

    private Date gmtModified;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getItemPropertyTmplId() {
		return itemPropertyTmplId;
	}

	public void setItemPropertyTmplId(Long itemPropertyTmplId) {
		this.itemPropertyTmplId = itemPropertyTmplId;
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

   
}