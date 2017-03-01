package com.mockuai.messagecenter.common.constant;

public enum ResponseCode {
    /*请求成功*/
    REQUEST_SUCCESS(10000, "request success"),

    /*参数错误*/
    P_PARAM_ERROR(20001, "param error"),
    P_PARAM_NULL(20002, "param null"),

    /*业务错误*/
    B_ADD_ERROR(30001, "add error"),
    B_UPDATE_ERROR(30002, "update error"),
    B_DELETE_ERROR(30003, "delete error"),
    B_SELECT_ERROR(30004, "select error"),
    /**
     * 访问的接口不存在
     */
    B_ACTION_NO_EXIST(30005, "action no exist"),
    /**
     * 请求被禁用
     */
    B_REQUEST_FORBBIDEN(30006, "request forbbiden"),

    B_APP_NOT_EXIST(30007, "the specified app is not exist"),
    
    B_BIZ_EXCEPTION(30008, "业务异常"),
    
    /**
     * 系统异常
     */
    SYS_E_SERVICE_EXCEPTION(40001, "system exception"),;

    private int value;
    private String desc;

    private ResponseCode(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
