package com.mockuai.toolscenter.common.constant;

/**
 * Created by zengzhangqiang on 9/28/15.
 */
public enum ValueTypeEnum {
    /**
     * 字符串类型
     */
    TYPE_STRING(1),
    /**
     * 整型
     */
    TYPE_INTEGER(2);
    private int value;

    private ValueTypeEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
