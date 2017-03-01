package com.mockuai.virtualwealthcenter.core.domain;

import java.util.Date;

public class UsedLogDO {
    private Long id;
    private String bizCode;
    private Long wealthAccountId;
    private Long grantedWealthId;
    private Long usedWealthId;
    private Long amount;
    private Long orderId;
    private String orderSn;
    private Integer status;
    private Boolean deleteMark;
    private Date gmtCreated;
    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode == null ? null : bizCode.trim();
    }

    public Long getWealthAccountId() {
        return wealthAccountId;
    }

    public void setWealthAccountId(Long wealthAccountId) {
        this.wealthAccountId = wealthAccountId;
    }

    public Long getGrantedWealthId() {
        return grantedWealthId;
    }

    public void setGrantedWealthId(Long grantedWealthId) {
        this.grantedWealthId = grantedWealthId;
    }

    public Long getUsedWealthId() {
        return usedWealthId;
    }

    public void setUsedWealthId(Long usedWealthId) {
        this.usedWealthId = usedWealthId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Boolean deleteMark) {
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