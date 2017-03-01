package com.mockuai.itemcenter.core.domain;

import java.util.Date;

public class SpuPropertyDO {
	private Long id;

	private Long spuId; // 所属spu记录ID

	private String keyName;// 属性名

	private String val;// 属性名

	private Integer valType;// 属性值类型

	private Integer sort;// 排序字段，如果该字段值相同，则按照属性key来排序

	private Long bizMark;// 业务标志，例如是否包含图片等等

	private String imgUrl;// 属性图

	private Integer isDeleted;

	private Date gmtCreated;

	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName == null ? null : keyName.trim();
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val == null ? null : val.trim();
	}

	public Integer getValType() {
		return valType;
	}

	public void setValType(Integer valType) {
		this.valType = valType;
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
		this.imgUrl = imgUrl == null ? null : imgUrl.trim();
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

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	@Override
	public String toString() {
		return "SpuPropertyDO [id=" + id + ", spuId=" + spuId + ", keyName=" + keyName + ", val=" + val + "]";
	}
}