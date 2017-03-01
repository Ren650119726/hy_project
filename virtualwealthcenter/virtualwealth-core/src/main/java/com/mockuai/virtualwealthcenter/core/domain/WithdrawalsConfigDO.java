package com.mockuai.virtualwealthcenter.core.domain;

import java.math.BigDecimal;
import java.util.Date;

public class WithdrawalsConfigDO {
    private int id;

    private String bizCode;

    private int userId;

    private String wdConfigText;

    private Long wdConfigMinimum;

    private Long wdConfigMaximum;

    private Byte wdIsflag;

    private Date gmtCreated;

    private Date gmtModified;

    private Byte deleteMark;



    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode == null ? null : bizCode.trim();
    }



    public String getWdConfigText() {
        return wdConfigText;
    }

    public void setWdConfigText(String wdConfigText) {
        this.wdConfigText = wdConfigText == null ? null : wdConfigText.trim();
    }

    public double getWdConfigMinimum() {
        return wdConfigMinimum;
    }


    public void setWdConfigMinimum(Long wdConfigMinimum) {
        this.wdConfigMinimum = wdConfigMinimum;
    }

    public Long getWdConfigMaximum() {
        return wdConfigMaximum;
    }

    public void setWdConfigMaximum(Long wdConfigMaximum) {
        this.wdConfigMaximum = wdConfigMaximum;
    }

    public Byte getWdIsflag() {
        return wdIsflag;
    }

    public void setWdIsflag(Byte wdIsflag) {
        this.wdIsflag = wdIsflag;
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

    public Byte getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Byte deleteMark) {
        this.deleteMark = deleteMark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}