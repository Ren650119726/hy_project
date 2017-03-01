package com.mockuai.marketingcenter.common.domain.qto;

import java.util.List;

/**
 * Created by zengzhangqiang on 7/30/15.
 */
public class ActivityItemQTO extends PageQTO {

    private Long id;
    private String bizCode;
    /**
     * 所属营销活动id
     */
    private Long activityId;
    /**
     * 所属活动 id 列表
     */
    private List<Long> activityIds;
    /**
     * 营销活动创建者id
     */
    private Long activityCreatorId;
    /**
     * 商品所属卖家 id
     */
    private Long sellerId;
    /**
     * 参加活动的商品id
     */
    private Long itemId;

    /**
     * 活动商品id列表
     */
    private List<Long> itemIdList;
    /**
     * 品牌id列表
     */
    private List<Long> brandIdList;
    /**
     * 品类id列表
     */
    private List<Long> categoryIdList;

    /**
     * 活动id列表
     */
    private List<Long> activityIdList;

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

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public List<Long> getItemIdList() {
        return itemIdList;
    }

    public void setItemIdList(List<Long> itemIdList) {
        this.itemIdList = itemIdList;
    }

    public List<Long> getActivityIdList() {
        return activityIdList;
    }

    public void setActivityIdList(List<Long> activityIdList) {
        this.activityIdList = activityIdList;
    }

    public List<Long> getBrandIdList() {
        return brandIdList;
    }

    public void setBrandIdList(List<Long> brandIdList) {
        this.brandIdList = brandIdList;
    }

    public List<Long> getCategoryIdList() {
        return categoryIdList;
    }

    public void setCategoryIdList(List<Long> categoryIdList) {
        this.categoryIdList = categoryIdList;
    }

    public List<Long> getActivityIds() {
        return activityIds;
    }

    public void setActivityIds(List<Long> activityIds) {
        this.activityIds = activityIds;
    }
}
