package com.mockuai.virtualwealthcenter.core.domain;

import java.util.Date;

/**
 * Created by duke on 16/4/18.
 */
public class RechargeRecordDO {
    /**
     * ID
     * */
    private Long id;

    /**
     * 企业标识
     * */
    private String bizCode;

    /**
     * 充值商品ID
     * */
    private Long itemId;

    /**
     * 充值金额
     * */
    private Long rechargeAmount;

    /**
     * 实付金额
     * */
    private Long payAmount;

    /**
     * 用户ID
     * */
    private Long userId;

    /**
     * 用户名称
     * */
    private String userName;

    /**
     * 订单流水号
     * */
    private String orderSn;

    /**
     * 订单ID
     * */
    private Long orderId;

    /**
     * 支付类型
     * */
    private Integer paymentId;

    /**
     * 订单状态
     * */
    private Integer orderStatus;

    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(Long rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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
