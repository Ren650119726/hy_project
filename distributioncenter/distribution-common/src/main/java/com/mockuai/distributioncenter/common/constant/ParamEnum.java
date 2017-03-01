package com.mockuai.distributioncenter.common.constant;

/**
 * Created by duke on 15/10/28.
 */
public enum ParamEnum {
    SYS_APP_CODE("appCode"),

    SYS_APP_PASSWORD("appPwd"),

    SYS_ACTION("action");

    private String value;

    ParamEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
