package com.mockuai.itemcenter.common.domain.dto;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺分组查询的商品结果,跟搜索的结果一样
 * Created by luliang on 15/8/5.
 */
public class GroupItemDTO implements Serializable {


    private String itemUid;
    private String bizCode;
    private String itemName;
    private Long sellerId;
    private Long categoryId;
    private Long brandId;
    private String iconUrl;
    private Long marketPrice;
    private Long promotionPrice;
    private Long wirelessPrice;
    private Date saleBegin;
    // 商品类目名称条形码
    private String categoryName;
    private String barCode;

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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

    public Date getSaleBegin() {
        return saleBegin;
    }

    public void setSaleBegin(Date saleBegin) {
        this.saleBegin = saleBegin;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
