package com.mockuai.dts.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by duke on 15/12/25.
 */
public class DistributionStatisticsExportQTO implements Serializable {
    private Date gmtTradeStart;
    private Date gmtTradeEnd;
    private Long sellerId;
    private Integer distType;

    public Integer getDistType() {
        return distType;
    }

    public void setDistType(Integer distType) {
        this.distType = distType;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Date getGmtTradeStart() {
        return gmtTradeStart;
    }

    public void setGmtTradeStart(Date gmtTradeStart) {
        this.gmtTradeStart = gmtTradeStart;
    }

    public Date getGmtTradeEnd() {
        return gmtTradeEnd;
    }

    public void setGmtTradeEnd(Date gmtTradeEnd) {
        this.gmtTradeEnd = gmtTradeEnd;
    }
}
