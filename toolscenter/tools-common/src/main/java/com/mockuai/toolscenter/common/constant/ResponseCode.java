package com.mockuai.toolscenter.common.constant;

public enum ResponseCode {
    RESPONSE_SUCCESS(10000, "success"),

    //参数错误
    //TODO action不存在的异常归类待定
    PARAM_E_ACTION_NOT_EXIST(20001, "the action does not exist"),
    PARAM_E_PARAM_MISSING(20002, "the param is missing"),
    PARAM_E_PARAM_INVALID(20003, "the param is invalid"),

    //业务错误
    BIZ_E_REQUEST_FORBIDDEN(30001, "the request is forbidden"),
    BIZ_E_BIZ_INFO_NOT_EXIST(30002, "the specified bizInfo is not exist"),
    BIZ_E_BIZ_CODE_CONFLICT_WITH_OTHER_BIZ(30003, "the specified bizCode conflict with other biz"),
    BIZ_E_APP_INFO_NOT_EXIST(30004, "the specified appInfo is not exist"),
    BIZ_E_BIZ_PROPERTY_NOT_EXIST(30005, "the specified bizProperty is not exist"),

    //系统内部错误
    SYS_E_DEFAULT_ERROR(40001, "%s 系统开小差中，请稍后再试"),
    SYS_E_SERVICE_EXCEPTION(40002, "服务端异常"),
    SYS_E_DATABASE_ERROR(40003, "数据库操作异常"),
    SYS_E_REMOTE_CALL_ERROR(40004, "remote call error"),
    ;

    private int code;
    private String comment;

    private ResponseCode(int code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public int getCode() {
        return this.code;
    }

    public String getComment() {
        return this.comment;
    }
}

/* Location:           /work/tmp/trade-common-0.0.1-20150519.033122-8.jar
 * Qualified Name:     com.mockuai.tradecenter.common.constant.ResponseCode
 * JD-Core Version:    0.6.2
 */