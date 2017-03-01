package com.mockuai.deliverycenter.common.constant;

/**
 * 类RetCodeEnum.java的实现描述：返回结果代码
 * 
 * @author dong.dong
 */
public enum RetCodeEnum {
	/**
	 * 成功
	 */
	SUCCESS(10000, "success"),

	/************************************ 参数级别错误 ********************************************/
	/**
	 * 参数为空
	 */
//	PARAMETER_NULL("P0001", "parameter is null"),
	PARAMETER_NULL(20001, "parameter is null"),
	/**
	 * 参数传值错误
	 */
//	PARAMETER_ERROR("P0002", "parameter is error");
	PARAMETER_ERROR(20002, "parameter is error"),
	/**
	 * 访问的接口不存在
	 */
//	ACTION_NO_EXIST("S0004", "action not exist"),
	ACTION_NO_EXIST(20003, "action not exist"),

	/************************************ 业务逻辑错误 ********************************************/
	/**
	 * 删除出错
	 */
//	DELETE_ERROR("B0001", "delete error"),
	DELETE_ERROR(30001, "delete error"),
	/**
	 * 更新出错
	 */
//	UPDATE_ERROR("B0002", "update error"),
	UPDATE_ERROR(30002, "update error"),
	/**
	 * 存在子地区，不能删除
	 */
//	SUB_REGION_EXIST("B1001", "sub region exist,can't delete"),
	SUB_REGION_EXIST(30003, "sub region exist,can't delete"),
	/************************************ 系统级别错误 ********************************************/
	/**
	 * 系统错误
	 */
	//SYSTEM_ERROR("S0001", "system error"),
	SYSTEM_ERROR(40001, "system error"),
	/**
	 * 服务端发生异常
	 */
//	SERVICE_EXCEPTION("S0002", "server exception,please view log"),
	SERVICE_EXCEPTION(40002, "server exception,please view log"),
	/**
	 * 没有权限，请求被禁止
	 */
	REQUEST_FORBBIDEN(40003, "request forbidden")
	;

	private int code;
	private String message;

	private RetCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}
}
