package com.mockuai.toolscenter.common.constant;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public enum  BizTypeEnum {
    /**
     * 企业业务
     */
    BIZ_ENTERPRISE(1),
    /**
     * 个人业务
     */
    BIZ_PERSONAL(2);

    private int value;
    private BizTypeEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
