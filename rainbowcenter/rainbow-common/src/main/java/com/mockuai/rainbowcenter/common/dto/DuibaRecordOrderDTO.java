package com.mockuai.rainbowcenter.common.dto;

import java.util.Date;

/**
 * Created by lizg on 2016/7/19.
 */
public class DuibaRecordOrderDTO extends BaseDTO {

    private Long id;

    private String uid;    //用户id

    private String bizNum;  //订单流水号
    private String orderNum;  //兑吧订单号

    private Long credits;   //本次兑换扣除的积分

    private String description; //本次积分消耗的描述

    private String type;     //兑换类型：alipay(支付宝), qb(Q币), coupon(优惠券), object(实物), phonebill(话费), phoneflow(流量), virtual(虚拟商品), turntable(大转盘),    singleLottery(单品抽奖)，hdtoolLottery(活动抽奖),manualLottery(手动开奖),gameLottery(游戏)'

    private Integer facePrice; //兑换商品的市场价值，单位是分

    private Integer actualPrice; //此次兑换实际扣除开发者账户费用，单位为分

    private String ip;

    private Integer waitAudit;  //是否需要审核 0不需要 1 需要

    private Integer status;   //兑换状态 0成功 1 失败 2 兑换中

    private String params;   //详情参数

    private Date exchangeTime;   //兑换时间

    private String errorMessage;   //出错原因

    private boolean isSuccess;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBizNum() {
        return bizNum;
    }

    public void setBizNum(String bizNum) {
        this.bizNum = bizNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFacePrice() {
        return facePrice;
    }

    public void setFacePrice(Integer facePrice) {
        this.facePrice = facePrice;
    }

    public Integer getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getWaitAudit() {
        return waitAudit;
    }

    public void setWaitAudit(Integer waitAudit) {
        this.waitAudit = waitAudit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Date getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
