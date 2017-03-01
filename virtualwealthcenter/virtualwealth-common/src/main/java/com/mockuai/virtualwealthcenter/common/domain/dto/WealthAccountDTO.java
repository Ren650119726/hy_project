package com.mockuai.virtualwealthcenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 5/25/15.
 */
public class WealthAccountDTO implements Serializable {
    private Long id;
    private Long userId;
    private String mobile;
    private String bizCode;
    private Integer wealthType;
    /**
     * 累计发放虚拟财富,包含当前冻结中的
     */
    private Long total;
    private Long amount;
    /**
     * 冻结中/待确认的虚拟财富
     */
    private Long transitionAmount;
    /**
     * 即将过期的虚拟财富
     */
    private Long willExpireAmount;
    /**
     * 兑换人民币的汇率
     */
    private Double exchangeRate;
    private Integer upperLimit;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Integer getWealthType() {
        return wealthType;
    }

    public void setWealthType(Integer wealthType) {
        this.wealthType = wealthType;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getTransitionAmount() {
        return transitionAmount;
    }

    public void setTransitionAmount(Long transitionAmount) {
        this.transitionAmount = transitionAmount;
    }

    public Long getWillExpireAmount() {
        return willExpireAmount;
    }

    public void setWillExpireAmount(Long willExpireAmount) {
        this.willExpireAmount = willExpireAmount;
    }

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