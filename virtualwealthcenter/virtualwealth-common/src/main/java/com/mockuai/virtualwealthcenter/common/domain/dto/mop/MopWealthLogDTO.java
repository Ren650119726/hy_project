package com.mockuai.virtualwealthcenter.common.domain.dto.mop;

import java.io.Serializable;

/**
 * Created by edgar.zr on 5/16/2016.
 */
public class MopWealthLogDTO implements Serializable {
    private String text;
    private String time;
    private Long amount;
    private Integer status;
    private String orderSN;
    private String orderUid;
    private String withdrawalsNumber;
    private String refusalReason;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderSN() {
        return orderSN;
    }

    public void setOrderSN(String orderSN) {
        this.orderSN = orderSN;
    }

    public String getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(String orderUid) {
        this.orderUid = orderUid;
    }

    public String getWithdrawalsNumber() {
        return withdrawalsNumber;
    }

    public void setWithdrawalsNumber(String withdrawalsNumber) {
        this.withdrawalsNumber = withdrawalsNumber;
    }

    public String getRefusalReason() {
        return refusalReason;
    }

    public void setRefusalReason(String refusalReason) {
        this.refusalReason = refusalReason;
    }
}