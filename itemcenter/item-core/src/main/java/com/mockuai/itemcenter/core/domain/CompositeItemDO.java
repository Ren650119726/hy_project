package com.mockuai.itemcenter.core.domain;

import java.util.Date;

/**
 * 组合商品DO
 * @author cwr
 */
public class CompositeItemDO {
    private Long id;

    private Long itemId;

	private Long skuId;
	
	private Long supplierId;

	private Long subSkuId;

    private Long subItemId;

    private String comment;

    private Integer num;

    private Boolean isDeleted;

    private Date gmtCreated;

    private Date gmtModified;

    private String bizCode;

    private String subSkuCode;

    private String subSkuDesc ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	
	public Long getSupplierId() {
			return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getSubSkuId() {
        return subSkuId;
    }

    public void setSubSkuId(Long subSkuId) {
        this.subSkuId = subSkuId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
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

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode == null ? null : bizCode.trim();
    }

    public Long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(Long subItemId) {
        this.subItemId = subItemId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getSubSkuCode() {
        return subSkuCode;
    }

    public void setSubSkuCode(String subSkuCode) {
        this.subSkuCode = subSkuCode;
    }

    public String getSubSkuDesc() {
        return subSkuDesc;
    }

    public void setSubSkuDesc(String subSkuDesc) {
        this.subSkuDesc = subSkuDesc;
    }
}