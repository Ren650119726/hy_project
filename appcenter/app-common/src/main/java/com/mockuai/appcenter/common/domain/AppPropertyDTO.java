package com.mockuai.appcenter.common.domain;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class AppPropertyDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String bizCode;
    private Long appId;
    private String pKey;
    private String value;
    private Integer valueType;

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

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getpKey() {
        return pKey;
    }

    public void setpKey(String pKey) {
        this.pKey = pKey;
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
}
