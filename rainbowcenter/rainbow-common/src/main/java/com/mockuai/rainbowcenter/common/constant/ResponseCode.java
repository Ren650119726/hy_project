package com.mockuai.rainbowcenter.common.constant;

public enum ResponseCode {
    RESPONSE_SUCCESS(10000, "success"),
    /**
	 * 请求成功
	 **/
	REQUEST_SUCCESS(10000, "request success"),

    //参数错误
    //TODO action不存在的异常归类待定
    PARAM_E_ACTION_NOT_EXIST(20001, "the action does not exist"),
    PARAM_E_PARAM_MISSING(20002, "the param is missing"),
    PARAM_E_PARAM_INVALID(20003, "the param is invalid"),
    PARAM_E_PARAM_FORMAT_INVALID(20004, "the format of param is invalid"),
    PARAM_E_PARAM_NULL(20005, "the param is null"),

    //业务错误
    BIZ_E_REQUEST_FORBIDDEN(30001, "the request is forbidden"),
    BIZ_E_METHOD_NOT_EXIST(30002, "method does not exist"),

    //系统内部错误
    BIZ_ORDER_CANCEL_SUCCESS(31000,"订单取消成功"),
    BIZ_ORDER_CANCEL_FAILURE(31001,"订单取消失败"),
    
    //系统内部错误 
    SYS_E_DEFAULT_ERROR(40001, "%s 系统开小差中，请稍后再试"),
    SYS_E_SERVICE_EXCEPTION(40002, "服务端异常"),
    SYS_E_DATABASE_ERROR(40003, "数据库操作异常"),
    SYS_E_REMOTE_CALL_ERROR(40004, "remote call error"),

	GYERP_E_SERVICE_EXCEPTION(40005, "调管易接口服务端异常"),

	GYERP_ITEM_CODE_SERVICE_EXCEPTION(40006, "该商品编码在管易ERP不存在"),

    SYS_E_IMAGE_UPLOAD_EXCEPTION(50001, "图片上传失败");

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
