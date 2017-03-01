package com.mockuai.rainbowcenter.common.qto;

/**
 * Created by yeliming on 16/3/22.
 */
public class SysConfigQTO extends BaseQTO {
    private Long id;
    private String account;
    private String fieldName;
    private Long sellerId;
    private String outValue;
    private String value;
    private String bizCode;
    private String type;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
}
