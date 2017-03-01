package com.mockuai.rainbowcenter.mop.api.domain;


/**
 * Created by lizg on 16/5/23.
 */
public class MopCreditsDTO {

    private String status;  //查询状态，回复ok或者fail

    private String errorMessage;  //出错原因

    private String bizId;    //订单号

    private  Long credits;  //用户积分余额

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
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
}
