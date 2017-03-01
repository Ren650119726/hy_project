package com.mockuai.mainweb.common.domain.dto.publish;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ContentDTO<T> {

    private String valueType;
    private T value;


    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }


}
