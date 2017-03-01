package com.mockuai.virtualwealthcenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 5/25/15.
 * 商家虚拟财富是平台级别的，可以通过 bizCode 查询
 * 包括平台发放规则同样通过 bizCode 做关联
 */
public class VirtualWealthDTO implements Serializable {
    private Long id;
    private Long creatorId;
    private String bizCode;
    /**
     * 虚拟财富类型
     */
    private Integer type;
    /**
     * 虚拟财富名称
     */
    private String name;
    /**
     * 虚拟财富总量
     */
    private Long amount;
    /**
     * 虚拟财富发放量
     */
    private Long grantedAmount;
    /**
     * 积分在订单中兑现占支付金额的百分比
     */
    private Integer upperLimit;

    /**
     * 交易标记，0代表不可以在交易中使用，1代表可以在交易中使用
     */
    private Integer tradeMark;
    /**
     * 虚拟财富对人民币（以分为单位）的汇率
     */
    private Double exchangeRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getGrantedAmount() {
        return grantedAmount;
    }

    public void setGrantedAmount(Long grantedAmount) {
        this.grantedAmount = grantedAmount;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Integer getTradeMark() {
        return tradeMark;
    }

    public void setTradeMark(Integer tradeMark) {
        this.tradeMark = tradeMark;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
