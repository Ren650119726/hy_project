package com.mockuai.itemcenter.common.domain.qto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
public class ItemSearchQTO implements Serializable{

    private String itemUid;
    private List<String> itemUids;

    public List<String> getItemUids() {
        return itemUids;
    }

    public void setItemUids(List<String> itemUids) {
        this.itemUids = itemUids;
    }

    private String bizCode;
    private Long categoryId;
    private List<Long> categoryIds;
    private Long brandId;

    public String getItemUid() {
        return itemUid;
    }

    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    private List<Long> brandIds;
    private String keyword;
    private Long minPrice;
    private Long maxPrice;

    private Long groupId;

    private Long shopId;

    private Long maxCommission;

    private Long minCommission;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    private Integer facet;

    public Integer getFacet() {
        return facet;
    }

    public void setFacet(Integer facet) {
        this.facet = facet;
    }

    //PC商城查询时优惠价，APP H5查询时无线价，现阶段统一无线价
    private Integer priceType;
    private Integer orderBy;
    private Integer asc;
    private Integer offset = 0;

    private Long totalCount;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Long> getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(List<Long> brandIds) {
        this.brandIds = brandIds;
    }

    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public Long getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    private Integer count = 100;

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getAsc() {
        return asc;
    }

    public void setAsc(Integer asc) {
        this.asc = asc;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Long getMaxCommission() {
        return maxCommission;
    }

    public void setMaxCommission(Long maxCommission) {
        this.maxCommission = maxCommission;
    }

    public Long getMinCommission() {
        return minCommission;
    }

    public void setMinCommission(Long minCommission) {
        this.minCommission = minCommission;
    }
}
