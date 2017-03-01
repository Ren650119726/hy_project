package com.mockuai.seckillcenter.common.constant;

public enum ResponseCode {

	SUCCESS(10000, "success"),

	//参数错误
	PARAMETER_NULL(20001, "parameter is null"),
	PARAMETER_ERROR(20002, "parameter is error"),
	PARAMETER_MISSING(20003, "some required parameter is missing"),

	//业务异常
	DELETE_ERROR(30001, "delete error"),
	UPDATE_ERROR(30002, "update error"),

	BIZ_E_INVALIDATE_TARGET_ITEM(31001, "秒杀目标商品不合法"),
	BIZ_E_ADD_TARGET_ITEM(31002, "创建秒杀商品失败"),
	BIZ_E_SECKILL_NOT_EXIST(31003, "秒杀不存在"),
	BIZ_E_ITEM_OF_SECKILL_NOT_EXIST(31004, "秒杀商品不存在"),
	BIZ_E_SECKILL_ENDED(31005, "秒杀活动已经结束"),
	BIZ_E_SECKILL_WITHOUT_PRE_ORDER(31006, "秒杀资格已过期"),
	BIZ_E_SECKILL_NOT_START(31007, "秒杀未开始"),
	BIZ_E_SECKILL_STILL_HAVE_CHANCE(31008, "温馨提示：请耐心等待！其他用户正在付款中，如15分钟内未成功付款，活动继续"),
	BIZ_E_SECKILL_FAIL_TO_PRE_ORDER(31009, "预下单失败"),
	BIZ_E_SECKILL_PRE_ORDER_ALREADY(31010, "已经秒杀过"),
	BIZ_E_SECKILL_TRY_AGAIN(31011, "还有机会"),
	BIZ_E_ADD_SECKILL(31012, "创建秒杀活动"),
	BIZ_E_MESSAGE_INVALID(31013, "消息不合法"),
	BIZ_E_MOVE_STOCK_NUM(31014, "消息不合法"),

	/**
	 * 指定的app不存在
	 */
	BIZ_E_APP_NOT_EXIST(31015, "the specified app is not exist"),

	BIZ_E_STOCK_NUM_OUT_OF_ITEM(31016, "秒杀商品数量不能大于库存"),

	BIZ_E_STATUS_OF_USER_IS_INVALID(32001, "用户参与秒杀状态不合法"),
	BIZ_E_ALREADY_PARTICIPATE_IN(32002, "已经参与过"),

	/**
	 * 系统异常
	 */
	SERVICE_EXCEPTION(40002, "server exception"),
	REQUEST_FORBIDDEN(40003, "request forbidden"),
	ACTION_NO_EXIST(40004, "action not exist"),
	DB_OP_ERROR(40005, "database operation error"),
	DB_OP_ERROR_OF_DUPLICATE_ENTRY(41001, "duplicate entry for specified key"),
	COMPONENT_NOT_EXIST(41002, "component not exist");

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