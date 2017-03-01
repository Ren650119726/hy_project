package com.mockuai.marketingcenter.common.domain.dto.mop;

import java.util.List;

/**
 * Created by edgar.zr on 5/23/2016.
 */
public class ParamMarketItemDTO {
    private String itemSkuUid;
    private Integer number;
    private List<String> serviceList;
    private Integer itemType;
    private Long distributorId;
    //add by csy 2016-09-09 分享人id
    private String shareUserId;

    public String getItemSkuUid() {
        return itemSkuUid;
    }

    public void setItemSkuUid(String itemSkuUid) {
        this.itemSkuUid = itemSkuUid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<String> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

	public String getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(String shareUserId) {
		this.shareUserId = shareUserId;
	}
}