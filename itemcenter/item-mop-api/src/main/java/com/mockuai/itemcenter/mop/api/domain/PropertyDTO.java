package com.mockuai.itemcenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 4/24/15.
 */
public class PropertyDTO {
    private String code;
    private String name;
    private String value;
    private int valueType;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }
}
