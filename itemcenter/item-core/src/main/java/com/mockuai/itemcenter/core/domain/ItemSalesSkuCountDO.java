package com.mockuai.itemcenter.core.domain;

import java.util.Date;

public class ItemSalesSkuCountDO {	
    private Long id;   
    private Long itemId;
    private Long skuId;
    private Long skuSalesCount;   
    private Integer deleteMark;    
    private Date gmtCreated;    
    private Date gmtModified;

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

	public Long getSkuSalesCount() {
		return skuSalesCount;
	}

	public void setSkuSalesCount(Long skuSalesCount) {
		this.skuSalesCount = skuSalesCount;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
}