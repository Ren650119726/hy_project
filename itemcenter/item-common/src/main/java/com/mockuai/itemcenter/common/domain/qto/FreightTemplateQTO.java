package com.mockuai.itemcenter.common.domain.qto;

import com.mockuai.itemcenter.common.page.PageInfo;

import java.io.Serializable;
import java.util.Date;

public class FreightTemplateQTO extends PageInfo implements Serializable {

    private Long id;

    private String name;

    private Integer free;

    private Integer pricingMethod;

    private String logisticsWay;

    private Long sellerId;

    private Long basicCharge;

    private Integer basicCount;

    private Long extraCharge;

    private Integer extraCount;

    private String bizCode;

    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public Integer getPricingMethod() {
        return pricingMethod;
    }

    public void setPricingMethod(Integer pricingMethod) {
        this.pricingMethod = pricingMethod;
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
}