package com.mockuai.rainbowcenter.common.qto;

import java.util.Date;

/**
 * Created by yeliming on 16/3/13.
 */
public class SysFieldMapQTO extends BaseQTO {
    private Long id;
    private String fieldName;
    private String outValue;
    private String value;
    private String bizCode;
    private String type;
    private Date gmtCreatedStart;
    private Date gmtCreatedEnd;
    private Date gmtModifiedStart;
    private Date gmtModifiedEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
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

    public Date getGmtCreatedStart() {
        return gmtCreatedStart;
    }

    public void setGmtCreatedStart(Date gmtCreatedStart) {
        this.gmtCreatedStart = gmtCreatedStart;
    }

    public Date getGmtCreatedEnd() {
        return gmtCreatedEnd;
    }

    public void setGmtCreatedEnd(Date gmtCreatedEnd) {
        this.gmtCreatedEnd = gmtCreatedEnd;
    }

    public Date getGmtModifiedStart() {
        return gmtModifiedStart;
    }

    public void setGmtModifiedStart(Date gmtModifiedStart) {
        this.gmtModifiedStart = gmtModifiedStart;
    }

    public Date getGmtModifiedEnd() {
        return gmtModifiedEnd;
    }

    public void setGmtModifiedEnd(Date gmtModifiedEnd) {
        this.gmtModifiedEnd = gmtModifiedEnd;
    }
}
