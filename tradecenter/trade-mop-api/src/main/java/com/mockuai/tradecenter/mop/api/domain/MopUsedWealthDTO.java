package com.mockuai.tradecenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 5/29/15.
 */
public class MopUsedWealthDTO {
    private String wealthAccountUid;
    private Integer wealthType;
    private Long amount;
    private Double exchangeRate;//百分比

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

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	

	
    
    
}
