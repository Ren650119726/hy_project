package com.mockuai.marketingcenter.common.domain.dto.mop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class MopMarketItemDTO implements Serializable {
    private String itemName;
    private String itemSkuUid;
    private String itemUid;
    private String skuSnapshot;
    private Long sellerId;
    private Integer virtualMark;
    private Long unitPrice;
    private Integer number;
    private String iconUrl;
    private Integer itemType;
    private Integer higoMark;
    private List<MopAddedServiceDTO> serviceList;
    private MopHigoExtraInfoDTO higoExtraInfo;
    private MopDistributorInfoDTO distributorInfo;
    private String shareUserId;
    private Long limitNumber; //商品限购数量 

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSkuUid() {
        return itemSkuUid;
    }

    public void setItemSkuUid(String itemSkuUid) {
        this.itemSkuUid = itemSkuUid;
    }

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    public String getSkuSnapshot() {
        return skuSnapshot;
    }

    public void setSkuSnapshot(String skuSnapshot) {
        this.skuSnapshot = skuSnapshot;
    }

    public Integer getVirtualMark() {
        return virtualMark;
    }

    public void setVirtualMark(Integer virtualMark) {
        this.virtualMark = virtualMark;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public List<MopAddedServiceDTO> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<MopAddedServiceDTO> serviceList) {
        this.serviceList = serviceList;
    }

    public MopHigoExtraInfoDTO getHigoExtraInfo() {
        return higoExtraInfo;
    }

    public void setHigoExtraInfo(MopHigoExtraInfoDTO higoExtraInfo) {
        this.higoExtraInfo = higoExtraInfo;
    }

    public MopDistributorInfoDTO getDistributorInfo() {
        return distributorInfo;
    }

    public void setDistributorInfo(MopDistributorInfoDTO distributorInfo) {
        this.distributorInfo = distributorInfo;
    }

    public Integer getHigoMark() {
        return higoMark;
    }

    public void setHigoMark(Integer higoMark) {
        this.higoMark = higoMark;
    }

	public String getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(String shareUserId) {
		this.shareUserId = shareUserId;
	}

	public Long getLimitNumber() {
		return limitNumber;
	}

	public void setLimitNumber(Long limitNumber) {
		this.limitNumber = limitNumber;
	}
}