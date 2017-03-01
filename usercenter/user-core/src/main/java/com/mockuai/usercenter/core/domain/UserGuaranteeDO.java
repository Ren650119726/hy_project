package com.mockuai.usercenter.core.domain;

import java.util.Date;

/**
 * Created by yeliming on 16/1/23.
 */
public class UserGuaranteeDO extends BaseDO {
    private Long id;
    private Long guaranteeAmount;
    private Long userId;
    private String bizCode;
    private Integer type;
    private Date gmtCreated;
    private Date gmtModified;
    private Integer deleteMark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGuaranteeAmount() {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(Long guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
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
        this.bizCode = bizCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    @Override
    public String toString() {
        return "UserGuaranteeDO{" +
                "id=" + id +
                ", guaranteeAmount=" + guaranteeAmount +
                ", userId=" + userId +
                ", bizCode=" + bizCode +
                ", type=" + type +
                '}';
    }
}
