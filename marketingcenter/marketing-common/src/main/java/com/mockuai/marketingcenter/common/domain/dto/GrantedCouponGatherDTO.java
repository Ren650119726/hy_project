package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GrantedCouponGatherDTO implements Serializable {
    private Long id;
    private Long couponId;
    private Long couponCreatorId;
    private Integer couponType;
    private String couponCode;
    private Long activityId;
    private Long activityCreatorId;
    private Date startTime;
    private Date endTime;
    private Long orderId;
    private Long granterId;
    private Long receiverId;
    private Integer status;
    private Long discountAmount;
    private List<PropertyDTO> propertyList;
    private String toolCode;
    private String bizCode;
    private Integer number;
    private String name;
    private String content;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCouponId() {
        return this.couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getCouponCreatorId() {
        return this.couponCreatorId;
    }

    public void setCouponCreatorId(Long couponCreatorId) {
        this.couponCreatorId = couponCreatorId;
    }

    public Integer getCouponType() {
        return this.couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public String getCouponCode() {
        return this.couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Long getActivityId() {
        return this.activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getActivityCreatorId() {
        return this.activityCreatorId;
    }

    public void setActivityCreatorId(Long activityCreatorId) {
        this.activityCreatorId = activityCreatorId;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGranterId() {
        return this.granterId;
    }

    public void setGranterId(Long granterId) {
        this.granterId = granterId;
    }

    public Long getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDiscountAmount() {
        return this.discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public List<PropertyDTO> getPropertyList() {
        return this.propertyList;
    }

    public void setPropertyList(List<PropertyDTO> propertyList) {
        this.propertyList = propertyList;
    }

    public String getToolCode() {
        return this.toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
}