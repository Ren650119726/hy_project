package com.mockuai.virtualwealthcenter.common.domain.dto.mop;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class MopWealthAccountDTO {
    private String wealthAccountUid;
    private Integer wealthType;
    /**
     * 累计收入
     */
    private Long totalAmount;
    private Long amount;
    private String mobile;
    private Long transitionAmount;
    private Long willExpireAmount;
    private String exchangeRate;
    private Integer upperLimit;

    public String getWealthAccountUid() {
        return wealthAccountUid;
    }

    public void setWealthAccountUid(String wealthAccountUid) {
        this.wealthAccountUid = wealthAccountUid;
    }

    public Integer getWealthType() {
        return wealthType;
    }

    public void setWealthType(Integer wealthType) {
        this.wealthType = wealthType;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }
}