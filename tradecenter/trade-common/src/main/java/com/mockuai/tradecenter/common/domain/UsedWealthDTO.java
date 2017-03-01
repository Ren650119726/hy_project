package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 5/25/15.
 * 订单使用虚拟财富信息传输对象
 */
public class UsedWealthDTO implements Serializable {
    /**
     * 虚拟账户ID
     */
    private Long wealthAccountId;
    /**
     * 虚拟账户所属用户ID
     */
    private Long userId;
    /**
     * 虚拟财富类型
     */
    private Integer wealthType;
    /**
     * 虚拟财富使用数量
     */
    private Long amount;
//    private Double exchangeRate;//转化比率
    private Long point;//代表积分
    
    private Long orderId;
    
    public Long getWealthAccountId() {
        return wealthAccountId;
    }

    public void setWealthAccountId(Long wealthAccountId) {
        this.wealthAccountId = wealthAccountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

//	public Double getExchangeRate() {
//		return exchangeRate;
//	}
//
//	public void setExchangeRate(Double exchangeRate) {
//		this.exchangeRate = exchangeRate;
//	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
    
    
}
