package com.mockuai.virtualwealthcenter.core.domain;

import java.util.Date;

public class WealthAccountDO {

    private Long id;
    private Long userId;
    private String bizCode;
    private Integer wealthType;
    private Long total;
    private Long amount;
    private Long transitionAmount;
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;
    private Long sumAmount;
    private Long overAmount;
    private String pictureFront;
    private String pictureBack;
    
    
    public String getPictureFront() {
		return pictureFront;
	}

	public void setPictureFront(String pictureFront) {
		this.pictureFront = pictureFront;
	}

	public String getPictureBack() {
		return pictureBack;
	}

	public void setPictureBack(String pictureBack) {
		this.pictureBack = pictureBack;
	}

	public Long getOverAmount() {
		return overAmount;
	}

	public void setOverAmount(Long overAmount) {
		this.overAmount = overAmount;
	}

	public Long getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(Long sumAmount) {
		this.sumAmount = sumAmount;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Integer getWealthType() {
        return wealthType;
    }

    public void setWealthType(Integer wealthType) {
        this.wealthType = wealthType;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getTransitionAmount() {
        return transitionAmount;
    }

    public void setTransitionAmount(Long transitionAmount) {
        this.transitionAmount = transitionAmount;
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