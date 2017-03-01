package com.mockuai.itemcenter.mop.api.domain;


import java.util.List;

/**
 * Created by zengzhangqiang on 4/24/15.
 */
public class MopItemSkuDTO {
    private String skuUid;
    private String skuCode;
    private String itemUid;
    private String barCode;
    private String materialCode;
    private String imageUrl;
    private Long costPrice;
    private Long marketPrice;
    private Long promotionPrice;
    private Long wirelessPrice;
    private Long stockNum;
    private Long soldNum;
    private List<MopSkuPropertyDTO> skuPropertyList;

    public String getSkuUid() {
        return skuUid;
    }

    public void setSkuUid(String skuUid) {
        this.skuUid = skuUid;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Long promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Long getWirelessPrice() {
        return wirelessPrice;
    }

    public void setWirelessPrice(Long wirelessPrice) {
        this.wirelessPrice = wirelessPrice;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public Long getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Long soldNum) {
        this.soldNum = soldNum;
    }

    public List<MopSkuPropertyDTO> getSkuPropertyList() {
        return skuPropertyList;
    }

    public void setSkuPropertyList(List<MopSkuPropertyDTO> skuPropertyList) {
        this.skuPropertyList = skuPropertyList;
    }
}
