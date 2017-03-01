package com.mockuai.usercenter.common.qto;

/**
 * Created by yeliming on 16/5/17.
 */
public class UserConsigneeQTO extends BaseQTO {
    private Long userId;
    private String bizCode;

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
        this.bizCode = bizCode;
    }
}
