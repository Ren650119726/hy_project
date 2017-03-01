package com.mockuai.rainbowcenter.common.dto;

/**
 * Created by lizg on 2016/7/19.
 */
public class DistDeductDTO extends BaseDTO {

    private String status;

    private String errorMessage;

    private String bizId;

    private Long balanceCredits;   //用户积分余额

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public Long getBalanceCredits() {
        return balanceCredits;
    }

    public void setBalanceCredits(Long balanceCredits) {
        this.balanceCredits = balanceCredits;
    }
}
