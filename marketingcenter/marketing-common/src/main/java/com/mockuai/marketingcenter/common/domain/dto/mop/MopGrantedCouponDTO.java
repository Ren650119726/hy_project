package com.mockuai.marketingcenter.common.domain.dto.mop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class MopGrantedCouponDTO implements Serializable {
    private String couponUid;
    private String activityUid;
    private String toolCode;
    private String name;
    private String content;
    private String desc;
    private Integer status;
    private Long discountAmount;
    private String startTime;
    private String endTime;
    private Integer scope;
    private Integer nearExpireDate;
    private List<MopPropertyDTO> propertyList;

    public String getCouponUid() {
        return couponUid;
    }

    public void setCouponUid(String couponUid) {
        this.couponUid = couponUid;
    }

    public String getActivityUid() {
        return activityUid;
    }

    public void setActivityUid(String activityUid) {
        this.activityUid = activityUid;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
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

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public Integer getNearExpireDate() {
        return nearExpireDate;
    }

    public void setNearExpireDate(Integer nearExpireDate) {
        this.nearExpireDate = nearExpireDate;
    }

    public List<MopPropertyDTO> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<MopPropertyDTO> propertyList) {
        this.propertyList = propertyList;
    }
}