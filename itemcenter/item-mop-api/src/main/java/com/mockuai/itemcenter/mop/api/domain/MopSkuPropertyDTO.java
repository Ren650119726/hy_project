package com.mockuai.itemcenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 4/24/15.
 */
public class MopSkuPropertyDTO {
    private String skuPropertyUid;
    private String code;
    private String name;
    private String value;
    private Integer valueType;
    private Integer sort;

    public String getSkuPropertyUid() {
        return skuPropertyUid;
    }

    public void setSkuPropertyUid(String skuPropertyUid) {
        this.skuPropertyUid = skuPropertyUid;
    }

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

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
