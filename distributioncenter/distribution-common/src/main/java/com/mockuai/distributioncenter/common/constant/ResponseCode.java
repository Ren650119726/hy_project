package com.mockuai.distributioncenter.common.constant;

/**
 * Created by duke on 15/10/28.
 */
public enum ResponseCode {
    SUCCESS(10000, "success"),

    PARAMETER_NULL(20001, "parameter is null"),

    /**
     * 业务异常
     * */
    // 指定的app不存在
    BIZ_E_APP_NOT_EXIST(30001, "the specified app is not exist"),
    // 分拥错误
    DISTRIBUTION_ERROR(30002, "分拥出错"),

    /**
     * 系统异常
     * */
    SERVICE_EXCEPTION(40001, "server exception"),
    REQUEST_FORBBIDEN(40002, "request forbidden"),
    ACTION_NO_EXIST(40003, "action not exist"),
    INVOKE_SERVICE_EXCEPTION(40004, "invoke service error"),

    /**
     * 数据库异常
     */
    UPDATE_DB_ERROR(40101,"database operation error")
    ;

    private int code;
    private String message;
    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
