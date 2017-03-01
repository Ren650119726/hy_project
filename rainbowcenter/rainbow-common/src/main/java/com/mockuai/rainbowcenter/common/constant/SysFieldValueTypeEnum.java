package com.mockuai.rainbowcenter.common.constant;

/**
 * Created by zengzhangqiang on 9/28/15.
 */
public enum SysFieldValueTypeEnum {
    /**
     * 字符串类型
     */
    TYPE_STRING(1),
    /**
     * 整型
     */
    TYPE_INTEGER(2),

    /**
     * 长整型
     */
    TYPE_LONG(3);

    private int value;

    private SysFieldValueTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
