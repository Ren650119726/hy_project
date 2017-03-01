package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

public class ItemSalesSpuCountDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private Long id;   
    private Long itemId;
    private Long spuSalesCount;   
    private Integer deleteMark;    
    private Date gmtCreated;    
    private Date gmtModified;
    private Long sellerId;
    private String bizCode;

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

	public Long getSpuSalesCount() {
		return spuSalesCount;
	}

	public void setSpuSalesCount(Long spuSalesCount) {
		this.spuSalesCount = spuSalesCount;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
}