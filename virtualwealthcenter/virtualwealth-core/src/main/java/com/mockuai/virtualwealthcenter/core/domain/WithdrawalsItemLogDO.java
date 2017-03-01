package com.mockuai.virtualwealthcenter.core.domain;

import java.util.Date;

public class WithdrawalsItemLogDO {

    private Long id;
    private Long userId;
    private String wdLogNumber;
    private Integer wdLogStatus;
    private Date wdLogTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWdLogNumber() {
        return wdLogNumber;
    }

    public void setWdLogNumber(String wdLogNumber) {
        this.wdLogNumber = wdLogNumber == null ? null : wdLogNumber.trim();
    }

    public Integer getWdLogStatus() {
        return wdLogStatus;
    }

    public void setWdLogStatus(Integer wdLogStatus) {
        this.wdLogStatus = wdLogStatus;
    }

    public Date getWdLogTime() {
        return wdLogTime;
    }

    public void setWdLogTime(Date wdLogTime) {
        this.wdLogTime = wdLogTime;
    }
}