package com.mockuai.itemcenter.core.domain;

import java.util.Date;

public class ItemCategoryDO {
	private Long id;

	private String cateName; // 类目名称

	private Integer cateLevel;// 类目层级

	private Long parentId; // 父ID

    private Long tmplId;

	private Integer sort;

	private Long topId; // 所属一级类目

	private Integer deleteMark;

	private Date gmtCreated;

	private Date gmtModified;

    private Date gmtUsed;

    public Long getTmplId() {
        return tmplId;
    }

    public void setTmplId(Long tmplId) {
        this.tmplId = tmplId;
    }

    public Date getGmtUsed() {
        return gmtUsed;
    }

    public void setGmtUsed(Date gmtUsed) {
        this.gmtUsed = gmtUsed;
    }

	private String bizCode;

	private String imageUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Integer getCateLevel() {
		return cateLevel;
	}

	public void setCateLevel(Integer cateLevel) {
		this.cateLevel = cateLevel;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getTopId() {
		return topId;
	}

	public void setTopId(Long topId) {
		this.topId = topId;
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

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}