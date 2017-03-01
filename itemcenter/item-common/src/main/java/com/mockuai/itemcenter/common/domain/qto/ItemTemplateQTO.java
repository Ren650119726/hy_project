package com.mockuai.itemcenter.common.domain.qto;

import java.io.Serializable;
import java.util.Date;

import com.mockuai.itemcenter.common.page.PageInfo;

/**
 * 商品模版复合查询条件
 * @author cwr
 */
public class ItemTemplateQTO extends PageInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3214400467338715807L;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getItemBrandId() {
		return itemBrandId;
	}

	public void setItemBrandId(Integer itemBrandId) {
		this.itemBrandId = itemBrandId;
	}

	public Date getCreateTimeBegin() {
		return createTimeBegin;
	}

	public void setCreateTimeBegin(Date createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	private Long id;

	private Integer isDeleted; 
		
	private String templateName;

	private Long sellerId;
	
	private Long categoryId;

	private Integer itemBrandId;
	
	private Date createTimeBegin;
	
	private Date createTimeEnd;
	
	private String bizCode;
	
}
