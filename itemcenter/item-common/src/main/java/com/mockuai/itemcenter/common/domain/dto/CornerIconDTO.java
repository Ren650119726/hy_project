package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

public class CornerIconDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1718432160174603255L;

	
	private Long id;

    private String iconName;

    private String iconUrl;

    private Long sellerId;
    
    private String bizCode;

	private String iconDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName == null ? null : iconName.trim();
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl == null ? null : iconUrl.trim();
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getIconDesc() {
        return iconDesc;
    }

    public void setIconDesc(String iconDesc) {
        this.iconDesc = iconDesc == null ? null : iconDesc.trim();
    }

    public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
    
    @Override
	public String toString() {
		return "CornerIconDTO [id=" + id + ", iconName=" + iconName
				+ ", iconUrl=" + iconUrl + ", sellerId=" + sellerId
				+ ", iconDesc=" + iconDesc + "]";
	}

    
}
