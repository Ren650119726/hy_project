package com.mockuai.imagecenter.common.constant;

public enum ResponseCode {

    SUCCESS(10000, "系统调用成功"),

    /**
     * 参数异常
     */
    PARAM_E_INVALID(20001, "参数不正确"),
    PARAM_E_MISSING(20002, "缺少参数"),

    /**
     * 业务异常
     */
    BASE_PARAM_E_RECORD_NOT_EXIST(30001, "请求的记录不存在"),
    BASE_STATE_E_NO_PERMISSION(30002, "没有权限"),
    BASE_STATE_E_ACTION_NO_EXIST(30003, "访问的接口不存在"),
    BASE_STATE_E_REQUEST_FORBBIDEN(30004, "请求被禁止"),
    BASE_STATE_E_USER_NO_EXIST(30005, "用户不存在"),
    GEN_IMAGE_ERROR(30006,"生成图片失败"),
    GEN_QR_CODE_ERROR(30007,"生成二维码失败" ),


    /**
     * 系统异常
     */
    SYS_E_DEFAULT_ERROR(40001, "系统开小差中，请稍后再试"),
    SYS_E_SERVICE_EXCEPTION(40002, "服务端异常"),

    SYS_E_SERVICE_UNAVAIABLE(40003, "服务端不可用"),
    SYS_E_SERVICE_TIMEOUT(40004, "服务端超时"),
    SYS_E_DB_DELETE(40005, "数据库删除异常"),
    SYS_E_DB_UPDATE(40006, "数据库更新异常"),
    SYS_E_DB_INSERT(40007, "数据库插入异常"),
    SYS_E_DB_QUERY(40008, "数据库查询异常"),
    MULTIPLE_GEN_REQUEST(40010, "重复提交生成二维码请求"),
    SYS_ITEM_SERVICE_EXCEPTION(40009, "调用商品查询服务异常！"),
    B_E_SHEET_NOT_EXIST(50001, "sheet does not exist"),
    B_E_TITLE_ILEGAL(50002, "title is ilegal"),

    B_E_SUBMIT_TASK_FAILED(60001, "提交异步任务失败"),;

    private int code;
    private String comment;

    private ResponseCode(int code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
