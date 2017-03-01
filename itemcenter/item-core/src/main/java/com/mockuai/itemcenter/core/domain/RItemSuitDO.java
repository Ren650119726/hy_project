package com.mockuai.itemcenter.core.domain;

import java.util.Date;

public class RItemSuitDO {

    private Long id;

    private Long sellerId;


    private String bizCode;


    private Byte deleteMark;


    private Date gmtCreated;


    private Date gmtModified;


    private Long suitId;


    private Long suitSkuId;




    private Long subItemId;


    private Long subItemSkuId;

    private String subItemName;

    private String subItemSkuCode;

    private String subItemSkuDesc ;


    private String subItemIcon;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Long getSellerId() {
        return sellerId;
    }


    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }


    public String getBizCode() {
        return bizCode;
    }


    public void setBizCode(String bizCode) {
        this.bizCode = bizCode == null ? null : bizCode.trim();
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


    public Long getSuitId() {
        return suitId;
    }


    public void setSuitId(Long suitId) {
        this.suitId = suitId;
    }


    public Long getSuitSkuId() {
        return suitSkuId;
    }


    public void setSuitSkuId(Long suitSkuId) {
        this.suitSkuId = suitSkuId;
    }


    public Long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(Long subItemId) {
        this.subItemId = subItemId;
    }


    public Long getSubItemSkuId() {
        return subItemSkuId;
    }


    public void setSubItemSkuId(Long subItemSkuId) {
        this.subItemSkuId = subItemSkuId;
    }


    public String getSubItemName() {
        return subItemName;
    }


    public void setSubItemName(String subItemName) {
        this.subItemName = subItemName == null ? null : subItemName.trim();
    }


    public String getSubItemIcon() {
        return subItemIcon;
    }


    public void setSubItemIcon(String subItemIcon) {
        this.subItemIcon = subItemIcon == null ? null : subItemIcon.trim();
    }

    public String getSubItemSkuCode() {
        return subItemSkuCode;
    }

    public void setSubItemSkuCode(String subItemSkuCode) {
        this.subItemSkuCode = subItemSkuCode;
    }

    public String getSubItemSkuDesc() {
        return subItemSkuDesc;
    }

    public void setSubItemSkuDesc(String subItemSkuDesc) {
        this.subItemSkuDesc = subItemSkuDesc;
    }
}