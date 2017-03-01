package com.mockuai.virtualwealthcenter.core.domain;

import java.util.Date;

public class VirtualWealthDO {

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
     * 总量为 －1 表示无限
     */
    private Long amount;
    /**
     * 虚拟财富发放量
     */
    private Long grantedAmount;

    private Integer upperLimit;
    /**
     * 交易标记，0代表不可以在交易中使用，1代表可以在交易中使用
     */
    private Integer tradeMark;
    /**
     * 虚拟财富对人民币（以分为单位）的汇率
     */
    private Double exchangeRate;
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;

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

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}