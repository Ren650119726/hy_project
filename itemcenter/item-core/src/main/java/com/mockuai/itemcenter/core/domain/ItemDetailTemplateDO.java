package com.mockuai.itemcenter.core.domain;

import java.util.Date;

/**
 * 商品模板;
 * Created by luliang on 15/7/21.
 */
public class ItemDetailTemplateDO {

    private long id;
    private String bizCode;
    private long sellerId;
    private long tmsId;

    public long getTmsId() {
        return tmsId;
    }

    public void setTmsId(long tmsId) {
        this.tmsId = tmsId;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    private String templateName;
    private String headerHtml;
    private String headerTmsId;
    private String footerHtml;
    private String footerTmsId;
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getHeaderHtml() {
        return headerHtml;
    }

    public void setHeaderHtml(String headerHtml) {
        this.headerHtml = headerHtml;
    }

    public String getHeaderTmsId() {
        return headerTmsId;
    }

    public void setHeaderTmsId(String headerTmsId) {
        this.headerTmsId = headerTmsId;
    }

    public String getFooterHtml() {
        return footerHtml;
    }

    public void setFooterHtml(String footerHtml) {
        this.footerHtml = footerHtml;
    }

    public String getFooterTmsId() {
        return footerTmsId;
    }

    public void setFooterTmsId(String footerTmsId) {
        this.footerTmsId = footerTmsId;
    }

    public int getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(int deleteMark) {
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
