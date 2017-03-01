package com.mockuai.virtualwealthcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by edgar.zr on 5/13/2016.
 */
public class UsedLogDTO implements Serializable {
    private Long id;
    private String bizCode;
    /**
     * 用户账号
     */
    private Long wealthAccountId;
    /**
     * 输入记录表
     */
    private Long grantedLogId;
    /**
     * 虚拟财富使用记录表
     */
    private Long usedWealthId;
    /**
     * 消费数量
     */
    private Long amount;
    private Long orderId;
    private String orderSn;
    /**
     * 使用状态：1代表预使用 2代表使用 3代表取消使用 4已退还
     */
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
        this.bizCode = bizCode;
    }

    public Long getWealthAccountId() {
        return wealthAccountId;
    }

    public void setWealthAccountId(Long wealthAccountId) {
        this.wealthAccountId = wealthAccountId;
    }

    public Long getGrantedLogId() {
        return grantedLogId;
    }

    public void setGrantedLogId(Long grantedLogId) {
        this.grantedLogId = grantedLogId;
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
        this.orderSn = orderSn;
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