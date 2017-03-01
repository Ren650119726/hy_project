package com.mockuai.itemcenter.core.domain;

import java.util.Date;

public class SpuPropertyTmplDO {
	private Long id;

	private Integer ownerType; // 模板属主类型：1代表系统模板，2代表用户模板

	private Long ownerId; // owner_type=1，则owner_id为0，如果owner_type=2，则owner_id为用户ID

	private String keyName; // 属性名

	private String val;// 属性值

	private Integer valType; // 属性值类型

	private Integer sort; // 排序字段，如果该字段值相同，则按照属性key来排序

	private Integer inheritType;// 排序字段，如果该字段值相同，则按照属性key来排序

	private Long bizMark; // 业务标志，例如是否包含图片等等

	private String imgUrl;// 属性图

	private Integer categoryId; // 所属类目ID

	private Integer isDeleted; // 删除标志

	private Date gmtCreated;

	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
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

	public Integer getInheritType() {
		return inheritType;
	}

	public void setInheritType(Integer inheritType) {
		this.inheritType = inheritType;
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "SpuPropertyTmplDO [id=" + id + ", keyName=" + keyName + ", val=" + val + ", categoryId="
				+ categoryId + "]";
	}
}