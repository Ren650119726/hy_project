package com.mockuai.itemcenter.core.domain;

import java.util.Date;
/**
 * sku属性的默认值  比如颜色属性有  绿色  蓝色 红色 。。。。
 * @author cwr
 */
public class SkuPropertyValueDO {
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

	public Long getSkuPropertyTmplId() {
		return skuPropertyTmplId;
	}

	public void setSkuPropertyTmplId(Long skuPropertyTmplId) {
		this.skuPropertyTmplId = skuPropertyTmplId;
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

	private Long id;

    private String name;

    private String value;
    
    private Long skuPropertyTmplId;

    private String bizCode;
    
    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

}