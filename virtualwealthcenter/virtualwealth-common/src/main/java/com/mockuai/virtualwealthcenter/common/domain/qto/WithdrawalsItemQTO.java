package com.mockuai.virtualwealthcenter.common.domain.qto;

import java.io.Serializable;

public class WithdrawalsItemQTO extends PageQTO implements Serializable {



    private Long userId;
    private String refuse;

    private byte dotype;

    private String banklog;



    private float startAmount ;

    private float endAmount ;


    private String realName ;

    private byte   withdrawalsStatus ;

    private String startTime;

    private String endTime ;


    private String withdrawalsNumber;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public byte getWithdrawalsStatus() {
        return withdrawalsStatus;
    }

    public void setWithdrawalsStatus(byte withdrawalsStatus) {
        this.withdrawalsStatus = withdrawalsStatus;
    }

    public String getWithdrawalsNumber() {
        return withdrawalsNumber;
    }

    public void setWithdrawalsNumber(String withdrawalsNumber) {
        this.withdrawalsNumber = withdrawalsNumber;
    }

    public float getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(float startAmount) {
        this.startAmount = startAmount;
    }

    public float getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(float endAmount) {
        this.endAmount = endAmount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }



    public String getRefuse() {
        return refuse;
    }

    public void setRefuse(String refuse) {
        this.refuse = refuse;
    }

    public byte getDotype() {
        return dotype;
    }

    public void setDotype(byte dotype) {
        this.dotype = dotype;
    }

    public String getBanklog() {
        return banklog;
    }

    public void setBanklog(String banklog) {
        this.banklog = banklog;
    }
}