package com.mockuai.appcenter.common.domain;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public class BizPropertyDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String bizCode;
    private String pKey;
    private String value;
    private Integer valueType;
    private String propertyDesc;

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

    public String getPropertyDesc() {
        return propertyDesc;
    }

    public void setPropertyDesc(String propertyDesc) {
        this.propertyDesc = propertyDesc;
    }
}
