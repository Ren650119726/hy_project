package com.mockuai.virtualwealthcenter.common.domain.dto.mop;

/**
 * Created by duke on 16/4/19.
 */
public class MopVirtualWealthItemDTO {
    private String itemUid;
    private String skuUid;
    private Long amount;
    private Double discount;
    private Long realAmount;
    private int itemType;

    public Long getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Long realAmount) {
        this.realAmount = realAmount;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    public String getSkuUid() {
        return skuUid;
    }

    public void setSkuUid(String skuUid) {
        this.skuUid = skuUid;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
