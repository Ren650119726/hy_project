package com.mockuai.marketingcenter.common.domain.dto.mop;

import java.util.List;

public class MopOrderItemDTO {
    private String orderItemUid;
    private String skuUid;
    private String skuSnapshot;
    private String itemUid;
    private String itemName;
    private String iconUrl;
    private List<String> serviceList;
    private Long price;
    private Integer number;
    private Integer itemType;
    private MopActivityInfo activityInfo;

    public String getOrderItemUid() {
        return this.orderItemUid;
    }

    public void setOrderItemUid(String orderItemUid) {
        this.orderItemUid = orderItemUid;
    }

    public String getSkuUid() {
        return this.skuUid;
    }

    public void setSkuUid(String skuUid) {
        this.skuUid = skuUid;
    }

    public String getSkuSnapshot() {
        return this.skuSnapshot;
    }

    public void setSkuSnapshot(String skuSnapshot) {
        this.skuSnapshot = skuSnapshot;
    }

    public String getItemUid() {
        return this.itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public List<String> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;
    }

    public Long getPrice() {
        return this.price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public MopActivityInfo getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(MopActivityInfo activityInfo) {
        this.activityInfo = activityInfo;
    }
}