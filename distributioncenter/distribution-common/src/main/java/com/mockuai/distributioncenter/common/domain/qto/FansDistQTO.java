package com.mockuai.distributioncenter.common.domain.qto;

import java.io.Serializable;

/**
 * Created by hsq on 2016/9/1.
 */
public class FansDistQTO extends BaseQTO {
    private Long userId;

    private String name;

    private String gmtCreated;

    private Long moneyForUpper;

    private String mobile;

    private Long inviterId;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Long getMoneyForUpper() {
        return moneyForUpper;
    }

    public void setMoneyForUpper(Long moneyForUpper) {
        this.moneyForUpper = moneyForUpper;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
