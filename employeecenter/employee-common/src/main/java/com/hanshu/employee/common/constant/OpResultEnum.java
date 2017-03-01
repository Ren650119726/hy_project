package com.hanshu.employee.common.constant;

/**
 * Created by yeliming on 16/5/19.
 */
public enum OpResultEnum {
    SUCCESS(1),
    FAILED(0)
    ;
    private int code;
    OpResultEnum(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
