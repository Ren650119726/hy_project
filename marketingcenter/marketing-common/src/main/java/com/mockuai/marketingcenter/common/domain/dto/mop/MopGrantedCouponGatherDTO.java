package com.mockuai.marketingcenter.common.domain.dto.mop;

import java.util.List;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class MopGrantedCouponGatherDTO {
    private String couponUid;
    private String toolCode;
    private String name;
    private String content;
    private Integer status;
    private Long discountAmount;
    private String startTime;
    private String endTime;
    private Integer number;
    private List<MopPropertyDTO> propertyList;

    public String getCouponUid() {
        return couponUid;
    }

    public void setCouponUid(String couponUid) {
        this.couponUid = couponUid;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<MopPropertyDTO> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<MopPropertyDTO> propertyList) {
        this.propertyList = propertyList;
    }
}