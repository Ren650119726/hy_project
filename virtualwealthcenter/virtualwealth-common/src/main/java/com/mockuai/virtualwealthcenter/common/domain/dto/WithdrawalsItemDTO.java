package com.mockuai.virtualwealthcenter.common.domain.dto;


import java.io.Serializable;
import java.util.Date;

public class WithdrawalsItemDTO  implements Serializable {


    private Long id ;
    private Long userId;

    private String withdrawalsNumber;

    private Byte withdrawalsStatus;

    private String withdrawalsNo;

    private long withdrawalsAmount;

    private Date withdrawalsTime;
    private byte withdrawalsType;

    private String realName ;

//    private String bankNo;
    private String bankName;

    private String  withdrawalsRefuse;

    private String withdrawalsBanklog;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

//    public String getBankNo() {
//        return bankNo;
//    }
//
//    public void setBankNo(String bankNo) {
//        this.bankNo = bankNo;
//    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public byte getWithdrawalsType() {
        return withdrawalsType;
    }

    public void setWithdrawalsType(byte withdrawalsType) {
        this.withdrawalsType = withdrawalsType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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