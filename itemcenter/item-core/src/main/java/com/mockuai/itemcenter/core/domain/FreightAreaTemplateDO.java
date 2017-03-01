package com.mockuai.itemcenter.core.domain;

import java.util.Date;

public class FreightAreaTemplateDO {

    private Long id;

    private String bizCode;

    private String logisticsWay;

    private Long sellerId;

    private Long basicCharge;

    private Integer basicCount;

    private Long extraCharge;

    private Integer extraCount;

    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;
    private Long parentId;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getLogisticsWay() {
        return logisticsWay;
    }

    public void setLogisticsWay(String logisticsWay) {
        this.logisticsWay = logisticsWay;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getBasicCharge() {
        return basicCharge;
    }

    public void setBasicCharge(Long basicCharge) {
        this.basicCharge = basicCharge;
    }

    public Integer getBasicCount() {
        return basicCount;
    }

    public void setBasicCount(Integer basicCount) {
        this.basicCount = basicCount;
    }

    public Long getExtraCharge() {
        return extraCharge;
    }

    public void setExtraCharge(Long extraCharge) {
        this.extraCharge = extraCharge;
    }

    public Integer getExtraCount() {
        return extraCount;
    }

    public void setExtraCount(Integer extraCount) {
        this.extraCount = extraCount;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
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

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }
}