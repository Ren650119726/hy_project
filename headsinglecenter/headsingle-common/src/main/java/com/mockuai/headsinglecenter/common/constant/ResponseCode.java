package com.mockuai.headsinglecenter.common.constant;

public enum ResponseCode {

    SUCCESS(10000, "success"),

    //参数错误
    PARAMETER_NULL(20001, "parameter is null"),
    PARAMETER_ERROR(20002, "parameter is error"),
    PARAMETER_MISSING(20003, "some required parameter is missing"),


    //业务异常
    DELETE_ERROR(30001, "delete error"),
    UPDATE_ERROR(30002, "update error"),
    QUERY_HEADSINGLE_ERROR(30003, "查询首单立减信息失败"),
    ADD_HEADSINGLE_NULL(30004, "新增首单立减信息为空"),
    ADD_HEADSINGLE_ERROR(30005, "新增首单立减信息失败"),
    HEADSINGLE_PRIVILEGE_AMT_ERROR(30006, "优惠金额必须为正整数"),
    HEADSINGLE_PRIVILIMIT_AMT_ERROR(30007, "优惠金额不能大于限满金额"),
    MODIFY_HEADSINGLE_NULL(30008, "修改首单立减信息为空"),
    MODIFY_HEADSINGLE_ERROR(30009, "修改首单立减信息失败"),
    QUERY_HEADSINGLE_INFO_ERROR(30010, "查询订单数据失败"),
    QUERY_HEADSINGLE_USER_NULL(30011, "查询首单立减参数不可为空"),
    QUERY_HEADSINGLE_USER_ERROR(30012, "查询首单立减用户信息失败"),

    /**
     * 指定的app不存在
     */
    BIZ_E_APP_NOT_EXIST(31015, "the specified app is not exist"),

    /**
     * 系统异常
     */
    SERVICE_EXCEPTION(40002, "server exception"),
    REQUEST_FORBIDDEN(40003, "request forbidden"),    
    ACTION_NO_EXIST(40004, "action not exist"),
    DB_OP_ERROR(40005, "database operation error"),
    DB_OP_ERROR_OF_DUPLICATE_ENTRY(41001, "duplicate entry for specified key"),;


    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }
}