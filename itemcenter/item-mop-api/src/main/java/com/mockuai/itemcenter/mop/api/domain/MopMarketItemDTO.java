package com.mockuai.itemcenter.mop.api.domain;

/**
 * Created by yindingyu on 15/12/10.
 */
public class MopMarketItemDTO {

    private String itemUid;

    private String itemSkuUid;

    private String itemName;

    private Long unitPrice;

    private String iconUrl;

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    public String getItemSkuUid() {
        return itemSkuUid;
    }

    public void setItemSkuUid(String itemSkuUid) {
        this.itemSkuUid = itemSkuUid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
