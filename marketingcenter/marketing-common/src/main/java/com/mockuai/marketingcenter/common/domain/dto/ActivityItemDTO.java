package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 7/19/15.
 */
public class ActivityItemDTO implements Serializable {
    private Long id;
    private String bizCode;
    /**
     * 所属营销活动id
     */
    private Long activityId;
    /**
     * 营销活动创建者id
     */
    private Long activityCreatorId;
    /**
     * 参加活动的商品id
     */
    private Long itemId;
    /**
     * 参与活动的品牌 id
     */
    private Long brandId;
    private String brandName;
    /**
     * 参与活动的二级分类 id
     */
    private Long categoryId;
    /**
     * 一级类目名称
     */
    private String categoryOneName;
    private Long parentCategoryId;
    /**
     * 二级类目名称
     */
    private String categoryTwoName;
    private String skuSnapshot;
    private Long itemSkuId;
    /**
     * 商品所属卖家id
     */
    private Long sellerId;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商品url
     */
    private String iconUrl;
    /**
     * 虚拟商品标志
     */
    private Integer virtualMark;
    private Long unitPrice;

    private Integer number;

    /**
     * 库存
     */
    private Long stockNum;

    private Integer itemType;

    public ActivityItemDTO() {
    }

    public ActivityItemDTO(MarketItemDTO marketItemDTO) {
        this.activityCreatorId = marketItemDTO.getSellerId();
        this.skuSnapshot = marketItemDTO.getSkuSnapshot();
        this.itemId = marketItemDTO.getItemId();
        this.itemSkuId = marketItemDTO.getItemSkuId();
        this.sellerId = marketItemDTO.getSellerId();
        this.itemName = marketItemDTO.getItemName();
        this.iconUrl = marketItemDTO.getIconUrl();
        this.virtualMark = marketItemDTO.getVirtualMark();
        this.unitPrice = marketItemDTO.getUnitPrice();
        this.number = marketItemDTO.getNumber();
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

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getActivityCreatorId() {
        return activityCreatorId;
    }

    public void setActivityCreatorId(Long activityCreatorId) {
        this.activityCreatorId = activityCreatorId;
    }

    public String getSkuSnapshot() {
        return skuSnapshot;
    }

    public void setSkuSnapshot(String skuSnapshot) {
        this.skuSnapshot = skuSnapshot;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(Long itemSkuId) {
        this.itemSkuId = itemSkuId;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryOneName() {
        return categoryOneName;
    }

    public void setCategoryOneName(String categoryOneName) {
        this.categoryOneName = categoryOneName;
    }

    public String getCategoryTwoName() {
        return categoryTwoName;
    }

    public void setCategoryTwoName(String categoryTwoName) {
        this.categoryTwoName = categoryTwoName;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}