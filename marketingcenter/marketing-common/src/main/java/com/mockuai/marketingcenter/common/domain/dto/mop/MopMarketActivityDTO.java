package com.mockuai.marketingcenter.common.domain.dto.mop;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class MopMarketActivityDTO implements Serializable {
    private String activityUid;
    private Integer scope;
    /**
     * 活动名称或标题
     */
    private String activityName;
    /**
     * 活动内容或规则
     */
    private String activityContent;
    private String icone;
    private Integer couponMark;
    /**
     * {@link com.mockuai.marketingcenter.common.constant.CouponType}
     */
    private Integer couponType;
    private Long discountAmount;
    private Long toolId;
    private String toolCode;
    private String startTime;
    private String endTime;
    private List<MopMarketItemDTO> itemList;
    private List<MopMarketItemDTO> targetItemList;
    private List<MopPropertyDTO> propertyList;
    private Integer publishStatus;//发布状态	
	private String activityTag;//活动标签	
	private String limitTagStatus;//限时活动标签状态(0未开始1已经开始2已结束)	
	private Long limitTagDate;//限时活动标签时

    public String getActivityUid() {
        return activityUid;
    }

    public void setActivityUid(String activityUid) {
        this.activityUid = activityUid;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
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

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<MopMarketItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<MopMarketItemDTO> itemList) {
        this.itemList = itemList;
    }

    public List<MopMarketItemDTO> getTargetItemList() {
        return targetItemList;
    }

    public void setTargetItemList(List<MopMarketItemDTO> targetItemList) {
        this.targetItemList = targetItemList;
    }

    public List<MopPropertyDTO> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<MopPropertyDTO> propertyList) {
        this.propertyList = propertyList;
    }

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	public String getActivityTag() {
		return activityTag;
	}

	public void setActivityTag(String activityTag) {
		this.activityTag = activityTag;
	}

	public String getLimitTagStatus() {
		return limitTagStatus;
	}

	public void setLimitTagStatus(String limitTagStatus) {
		this.limitTagStatus = limitTagStatus;
	}

	public Long getLimitTagDate() {
		return limitTagDate;
	}

	public void setLimitTagDate(Long limitTagDate) {
		this.limitTagDate = limitTagDate;
	}
}