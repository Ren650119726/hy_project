package com.mockuai.rainbowcenter.core.domain;

import java.util.Date;

/**
 * Created by yeliming on 16/3/13.
 */
public class SysConfigDO extends BaseDO {
    private Long id;
    private String account;
    private String appKey;
    private String appSecret;
    private String token;
    private String serverUrl;
    private String fieldName;
    private Long sellerId;
    private String outValue;
    private Integer outValueType;
    private String value;
    private Integer valueType;
    private String bizCode;
    private String type;
    private Date gmtCreated;
    private Date gmtModified;
    private Integer deleteMark;
    private Integer itemPollOn;
    private Integer itemOrderPollOn;
    private Integer itemStockPollOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getOutValue() {
        return outValue;
    }

    public void setOutValue(String outValue) {
        this.outValue = outValue;
    }

    public Integer getOutValueType() {
        return outValueType;
    }

    public void setOutValueType(Integer outValueType) {
        this.outValueType = outValueType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public Integer getItemPollOn() {
        return itemPollOn;
    }

    public void setItemPollOn(Integer itemPollOn) {
        this.itemPollOn = itemPollOn;
    }

    public Integer getItemOrderPollOn() {
        return itemOrderPollOn;
    }

    public void setItemOrderPollOn(Integer itemOrderPollOn) {
        this.itemOrderPollOn = itemOrderPollOn;
    }

    public Integer getItemStockPollOn() {
        return itemStockPollOn;
    }

    public void setItemStockPollOn(Integer itemStockPollOn) {
        this.itemStockPollOn = itemStockPollOn;
    }
}
