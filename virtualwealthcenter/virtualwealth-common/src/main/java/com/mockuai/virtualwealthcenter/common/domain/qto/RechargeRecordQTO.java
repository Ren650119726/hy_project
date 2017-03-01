package com.mockuai.virtualwealthcenter.common.domain.qto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by duke on 16/4/18.
 */
public class RechargeRecordQTO extends PageQTO implements Serializable {
    /**
     * 企业标识
     * */
    private String bizCode;

    /**
     * 订单号
     * */
    private String orderSn;

    /**
     * 订单ID
     * */
    private Long orderId;

    /**
     * 用户ID
     * */
    private Long userId;

    /**
     * 充值账户
     * */
    private String userName;

    /**
     * 充值方式
     * */
    private Integer paymentId;

    /**
     * 开始时间
     * */
    private Date startTime;

    /**
     * 结束时间
     * */
    private Date endTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }
}
