package com.mockuai.usercenter.common.qto;

import java.io.Serializable;
import java.util.Date;

public class FeedbackQTO extends QueryPage implements Serializable {
    private String bizCode;
    private Long userId;
    private Date startDate;
    private Date endDate;

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getStartDate()
    {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
