package com.mockuai.virtualwealthcenter.core.domain;

import java.util.Date;

public class WithdrawalsItemDO {
    private Long id;

    private Long userId;

    private String bizCode;

    private String withdrawalsNumber;

    private Byte withdrawalsStatus;

    private String withdrawalsNo;

    private long withdrawalsAmount;

    private Date withdrawalsTime;

    private Byte deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

    private byte withdrawalsType;

    private String  withdrawalsRefuse;

    private String withdrawalsBanklog;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode == null ? null : bizCode.trim();
    }

    public String getWithdrawalsNumber() {
        return withdrawalsNumber;
    }

    public void setWithdrawalsNumber(String withdrawalsNumber) {
        this.withdrawalsNumber = withdrawalsNumber == null ? null : withdrawalsNumber.trim();
    }

    public Byte getWithdrawalsStatus() {
        return withdrawalsStatus;
    }

    public void setWithdrawalsStatus(Byte withdrawalsStatus) {
        this.withdrawalsStatus = withdrawalsStatus;
    }

    public String getWithdrawalsNo() {
        return withdrawalsNo;
    }

    public void setWithdrawalsNo(String withdrawalsNo) {
        this.withdrawalsNo = withdrawalsNo == null ? null : withdrawalsNo.trim();
    }

    public long getWithdrawalsAmount() {
        return withdrawalsAmount;
    }

    public void setWithdrawalsAmount(long withdrawalsAmount) {
        this.withdrawalsAmount = withdrawalsAmount;
    }

    public Date getWithdrawalsTime() {
        return withdrawalsTime;
    }

    public void setWithdrawalsTime(Date withdrawalsTime) {
        this.withdrawalsTime = withdrawalsTime;
    }

    public Byte getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Byte deleteMark) {
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

    public byte getWithdrawalsType() {
        return withdrawalsType;
    }

    public void setWithdrawalsType(byte withdrawalsType) {
        this.withdrawalsType = withdrawalsType;
    }

    public String getWithdrawalsRefuse() {
        return withdrawalsRefuse;
    }

    public void setWithdrawalsRefuse(String withdrawalsRefuse) {
        this.withdrawalsRefuse = withdrawalsRefuse;
    }

    public String getWithdrawalsBanklog() {
        return withdrawalsBanklog;
    }

    public void setWithdrawalsBanklog(String withdrawalsBanklog) {
        this.withdrawalsBanklog = withdrawalsBanklog;
    }
}