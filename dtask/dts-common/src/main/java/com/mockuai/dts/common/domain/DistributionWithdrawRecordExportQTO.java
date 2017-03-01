package com.mockuai.dts.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lotmac on 16/3/10.
 */
public class DistributionWithdrawRecordExportQTO implements Serializable {

    private String bizCode;

    private Long sellerId;

    private Date startTime;

    private Date endTime;

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
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
