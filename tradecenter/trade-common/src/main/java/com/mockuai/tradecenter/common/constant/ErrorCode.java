//package com.mockuai.tradecenter.common.constant;
//
//public enum ErrorCode {
//	/*
//	 * 系统级别的异常
//	 */
//    SYS_E_DEFAULT_ERROR(1001,"系统开小差中，请稍后再试",ErrorLevel.SYSTEM),
//    SYS_E_SERVICE_EXCEPTION(1002,"服务端异常",ErrorLevel.SYSTEM),
//    SYS_E_SERVICE_UNAVAIABLE(1003,"服务端不可用",ErrorLevel.SYSTEM),
//    SYS_E_SERVICE_TIMEOUT(1004,"服务端超时",ErrorLevel.SYSTEM),
//    SYS_E_DATABASE_ERROR(1005,"数据库操作异常",ErrorLevel.SYSTEM),
//
//    /*
//     * 业务级别的异常
//     */
//    BASE_PARAM_E_PARAM_INVALID(2001,"参数不正确",ErrorLevel.BASE),
//    BASE_PARAM_E_PARAM_MISSING(2002,"缺少参数",ErrorLevel.BASE),
//    BASE_PARAM_E_RECORD_NOT_EXIST(2003,"请求的记录不存在",ErrorLevel.BASE),
//    BASE_STATE_E_NO_LOGIN(2101,"没有登录或登录已超时，请重新登录",ErrorLevel.BASE),
//    BASE_STATE_E_NO_PERMISSION(2102,"没有权限",ErrorLevel.BASE),
//    BASE_STATE_E_ACTION_NO_EXIST(2103,"访问的接口不存在",ErrorLevel.BASE),
//    BASE_STATE_E_REQUEST_FORBBIDEN(2104,"请求被禁止",ErrorLevel.BASE),
//    BASE_STATE_E_USER_NO_EXIST(2105,"用户不存在",ErrorLevel.BASE);
//
//    private int code;
//    private String comment;
//    private ErrorLevel level;
//
//    private ErrorCode(int code, String comment, ErrorLevel level){
//        this.code = code;
//        this.comment = comment;
//        this.level = level;
//    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public String getComment() {
//        return comment;
//    }
//
//    public ErrorLevel getLevel() {
//        return level;
//    }
//}
