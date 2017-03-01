package com.mockuai.virtualwealthcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by edgar.zr on 5/21/2016.
 */
public class WithdrawalsItemLogDTO implements Serializable {
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
        this.wdLogNumber = wdLogNumber;
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