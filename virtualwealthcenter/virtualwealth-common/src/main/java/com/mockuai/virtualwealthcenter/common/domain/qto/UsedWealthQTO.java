package com.mockuai.virtualwealthcenter.common.domain.qto;

import java.io.Serializable;

/**
 * Created by edgar.zr on 5/16/2016.
 */
public class UsedWealthQTO extends PageQTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3486065071864857810L;
	private Long id;
    private String bizCode;
    private Long wealthAccountId;
    private Long userId;
    private Long orderId;
    private Integer status;
    private Long parentId;
    
    private String wealthType;

    public String getWealthType() {
		return wealthType;
	}

	public void setWealthType(String wealthType) {
		this.wealthType = wealthType;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getWealthAccountId() {
        return wealthAccountId;
    }

    public void setWealthAccountId(Long wealthAccountId) {
        this.wealthAccountId = wealthAccountId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}