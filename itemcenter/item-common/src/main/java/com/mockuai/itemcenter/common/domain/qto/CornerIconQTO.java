package com.mockuai.itemcenter.common.domain.qto;

import com.mockuai.itemcenter.common.page.PageInfo;

import java.io.Serializable;
import java.util.List;

public class CornerIconQTO extends PageInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3362532062712847712L;

	private Long id;

    /**
     * id列表`
     */
    private List<Long> idList;

    private String iconName;

    private String iconUrl;

    private Long sellerId;

    private String iconDesc;
    
	private String bizCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
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
    
    public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

    @Override
	public String toString() {
		return "CornerIconQTO [id=" + id + ", iconName=" + iconName
				+ ", iconUrl=" + iconUrl + ", sellerId=" + sellerId + "]";
	}

	public void setIconDesc(String iconDesc) {
        this.iconDesc = iconDesc == null ? null : iconDesc.trim();
    }
}
