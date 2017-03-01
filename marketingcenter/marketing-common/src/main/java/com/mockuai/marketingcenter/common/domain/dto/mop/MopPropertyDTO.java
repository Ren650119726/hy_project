package com.mockuai.marketingcenter.common.domain.dto.mop;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 5/26/15.
 */
public class MopPropertyDTO implements Serializable{
    private String name;
    private String value;
    private Integer valueType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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