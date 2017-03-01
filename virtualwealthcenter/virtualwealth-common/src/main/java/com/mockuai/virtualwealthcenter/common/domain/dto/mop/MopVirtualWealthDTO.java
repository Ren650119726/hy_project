package com.mockuai.virtualwealthcenter.common.domain.dto.mop;

/**
 * Created by edgar.zr on 11/11/15.
 */
public class MopVirtualWealthDTO {
    private Double exchangeRate;
    private Integer upperLimit;

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }
}
