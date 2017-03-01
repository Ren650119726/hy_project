package com.mockuai.usercenter.common.qto;

import java.util.Date;

/**
 * Created by yeliming on 16/1/23.
 */
public class UserGuaranteeQTO extends BaseQTO{
    private Long id;
    private Long guaranteeAmount;
    private Long userId;
    private String bizCode;
    private Integer type;
    private Date startTime;
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGuaranteeAmount() {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(Long guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}
