package com.mockuai.marketingcenter.core.domain;

import java.util.Date;

public class MarketActivityDO {

    private Long id;
    private String bizCode;
    private Integer scope;
    /**
     * 活动编辑所属的编辑范围
     */
    private Integer level;
    private String activityCode;
    /**
     * 优惠活动名称或标题，例如：注册有礼赠送券、分享有礼赠送券等等
     */
    private String activityName;
    /**
     * 优惠活动内容/规则/说明，例如：满100减50
     * 此字段在 mop 中展示时放置于 desc 下, 而 content 用于存储规则  满XX减YY
     */
    private String activityContent;
    private Date startTime;
    private Date endTime;
    /**
     * 0 : 不用券 1 : 用券
     */
    private Integer couponMark;
    /**
     * 优惠券类型,不直接使用,在 activityCoupon 中判断类型
     */
    private Integer couponType;
    private Integer exclusiveMark;
    private Long toolId;
    /**
     * 工具类型：简单工具/复合工具
     */
    private Integer toolType;
    private String toolCode;
    private Integer creatorType;
    private Long creatorId;
    /**
     * 商品售空的时间
     */
    private Date itemInvalidTime;
    /**
     * 优惠活动状态，具体值参考{@link com.mockuai.marketingcenter.common.constant.ActivityStatus}
     */
    private Integer status;
    private Integer commonItem;
    /**
     * 复合活动的父活动
     */
    private Long parentId;
	/**
     * 角标
     */
    private String icon;
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;
    
    //发布状态 by csy 2016-10-12
    private Integer publishStatus;

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

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCouponMark() {
        return couponMark;
    }

    public void setCouponMark(Integer couponMark) {
        this.couponMark = couponMark;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getExclusiveMark() {
        return exclusiveMark;
    }

    public void setExclusiveMark(Integer exclusiveMark) {
        this.exclusiveMark = exclusiveMark;
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public Integer getCreatorType() {
        return creatorType;
    }

    public void setCreatorType(Integer creatorType) {
        this.creatorType = creatorType;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getItemInvalidTime() {
        return itemInvalidTime;
    }

    public void setItemInvalidTime(Date itemInvalidTime) {
        this.itemInvalidTime = itemInvalidTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getToolType() {
        return toolType;
    }

    public void setToolType(Integer toolType) {
        this.toolType = toolType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getCommonItem() {
        return commonItem;
    }

    public void setCommonItem(Integer commonItem) {
        this.commonItem = commonItem;
    }

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}
}