package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

public class CouponParamDTO extends BaseDTO implements Serializable {
    private String paramName;
    private String paramValue;

    public String getParamName() {
        return this.paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return this.paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}