package com.mockuai.itemcenter.common.domain.qto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.mockuai.itemcenter.common.page.PageInfo;

public class ItemCategoryQTO extends PageInfo implements Serializable{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String cateName; // 类目名称

	private Integer cateLevel;// 类目层级

	private Long parentId; // 父ID

    private Long tmplId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

	private Integer sort;

	private Long topId; // 所属一级类目
	
	private String  bizCode;

    private List<Long> idList;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

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

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

    private String orderBy;

    public Long getTmplId() {
        return tmplId;
    }

    public void setTmplId(Long tmplId) {
        this.tmplId = tmplId;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}