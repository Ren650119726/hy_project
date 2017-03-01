package com.mockuai.marketingcenter.common.constant;

public enum ResponseCode {

    SUCCESS(10000, "success"),

    //参数错误
    PARAMETER_NULL(20001, "parameter is null"),
    PARAMETER_ERROR(20002, "parameter is error"),
    PARAMETER_MISSING(20003, "some required parameter is missing"),
    /**
     * 活动名称不能为空
     */
    PARAM_E_ACTIVITY_NAME_CANNOT_BE_NULL(20004, "活动名称不能为空"),
    /**
     * 活动内容不能为空
     */
    PARAM_E_ACTIVITY_CONTENT_CANNOT_BE_NULL(20005, "互动内容不能空"),
    /**
     * 活动时间不能为空
     */
    PARAM_E_ACTIVITY_TIME_CANNOT_BE_NULL(20006, "活动时间不能为空"),
    /**
     * 工具code不能为空
     */
    PARAM_E_TOOL_CODE_CANNOT_BE_NULL(20007, "工具 code 不能为空"),
    /**
     * 活动创建者id不能为空
     */
    PARAM_E_ACTIVITY_CREATOR_ID_CANNOT_BE_NULL(20008, "sellerId 不能为空"),

    /**
     * 营销活动商品列表不能为空
     */
    PARAM_E_ACTIVITY_ITEM_CANNOT_BE_NULL(20009, "条件商品不能为空"),

    /**
     * 工具id无效
     */
    PARAM_E_TOOL_ID_INVALID(20010, "the tool id is invalid"),

    /**
     * 工具码无效
     */
    PARAM_E_TOOL_CODE_INVALID(20011, "工具 code 无效"),

    PARAM_E_ACTIVITY_TIME_INVALID(20012, "活动时间无效"),


    //业务异常

    NO_ENOUGH_COUPON(31003, "优惠券数量不足"),

    /**
     * 优惠券已经被预使用了
     */
    COUPON_PRE_USE_ALREADY(31005, "优惠券已经被预使用了"),
    /**
     * 优惠券已经被使用了
     */
    COUPON_USED_ALREADY(31006, "优惠券已经被使用了"),
    /**
     * 优惠券不存在
     */
    COUPON_NOT_EXIST(31007, "优惠券不存在"),
    /**
     * 优惠券状态非法
     */
    COUPON_STATUS_ILLEGAL(31008, "优惠券状态非法"),
    /**
     * 商品sku不存在
     */
    ITEM_SKU_NOT_EXIST(31009, "商品 sku 不存在"),
    /**
     * 虚拟财富不存在
     */
    VIRTUAL_WEALTH_NOT_EXIST(31010, "虚拟财富不存在"),
    /**
     * 虚拟财富余额不足
     */
    VIRTUAL_WEALTH_NOT_ENOUGH(31011, "虚拟财富不足"),
    /**
     * 虚拟账户余额不足
     */
    ACCOUNT_BALANCE_NOT_ENOUGH(31012, "虚拟账户余额不足"),
    /**
     * 预使用虚拟财富记录不存在
     */
    PRE_USE_WEALTH_NOT_EXIST(31013, "预使用虚拟财富记录不存在"),
    /**
     * 虚拟财富使用记录的状态不合法
     */
    WEALTH_USED_RECORD_STATUS_ILLEGAL(31014, "虚拟财富使用记录状态不合法"),

    /**
     * 指定的app不存在
     */
    BIZ_E_APP_NOT_EXIST(31015, "the specified app is not exist"),

    /**
     * 营销活动不存在
     */
    BIZ_E_MARKET_ACTIVITY_NOT_EXIST(31016, "指定活动不存在"),

    /**
     * 营销工具不存在
     */
    BIZ_E_MARKET_TOOL_NOT_EXIST(31017, "营销工具不存在"),

    /**
     * 优惠活动范围无效
     */
    BIZ_E_ACTIVITY_SCOPE_INVALID(31018, "活动范围无效"),

    /**
     * 活动优惠券不存在
     */
    BIZ_E_ACTIVITY_COUPON_NOT_EXIST(31019, "活动优惠券不存在"),

    /**
     * 活动优惠券状态非法
     */
    BIZ_E_ACTIVITY_COUPON_STATUS_ILLEGAL(31020, "活动优惠券状态非法"),

    /**
     * 指定的营销活动商品为空
     */
    ACTIVITY_ITEM_NOT_EXIST(31021, "指定营销活动商品为空"),

    /**
     * 同一个商家下的商品不能参与多个活动
     */
    ACTIVITY_ITEM_NOT_UNIQUE(31023, "一个商品不能同时参与多个活动"),

    /**
     * 每个用户限领量达到最大值
     */
    ACTIVITY_COUPON_RECEIVED_OUT_OF_LIMIT(31024, "优惠券已领取完"),
    /**
     * 财富类型和财富值不对应
     */
    WEALTH_TYPES_AND_AMOUNTS_IS_ILLEGAL(31025, "the number of the wealthTypes is not the same as the number of amount"),
    /**
     * 财富账户不存在
     */
    WEALTH_ACCOUNT_IS_NOT_FOUND(31026, "财富账户不存在"),

    /**
     * 参与活动的商品不合法
     */
    BIZ_E_THE_ACTIVITY_ITEM_INVALID(31028, "活动商品不合法"),
    /**
     * 优惠券类型不对应
     */
    BIZ_E_NOT_THE_SAME_COUPON_TYPE(31029, "优惠券类型非法"),
    /**
     * 优惠码非法
     */
    BIZ_E_THE_COUPON_CODE_IS_INVALID(31030, "优惠码非法"),
    /**
     * 优惠码已经领取过了
     */
    BIZ_E_THE_COUPON_CODE_ACTIVATED(31031, "优惠码已领取过"),
    /**
     * 优惠券已经领完
     */
    BIZ_E_THE_ACTIVITY_CODE_IS_NONE(31032, "优惠券已经领完"),
    /**
     * 优惠码不存在
     */
    BIZ_E_THE_COUPON_CODE_DOES_NOT_EXIST(31033, "优惠码不存在"),
    /**
     * 优惠码的数量超过了允许的最大值
     */
    BIZ_E_THE_COUNT_OF_COUPON_CODE_IS_OUT_OF_RANGE(31034, "数量超过上限"),

    BIZ_ITEM_NOT_EXIST(31037, "商品不存在"),
    BIZ_ACTIVITY_COUPON_OVERDUE(31038, "领取的优惠券过期"),
    BIZ_ACTIVITY_COUPON_OVER(31039, "优惠券活动结束"),

    BIZ_E_BARTER_NOT_EXISTS(32001, "换购活动不存在"),
    BIZ_BARTER_IS_OUT_OF_LIMIT(32002, "重合时间下只能创建最多 5 个换购活动"),
    BIZ_BARTER_THE_SAME_ACTIVITY_ITEM(32003, "重合时间下商品只能关联同一个目标商品一次"),
    BIZ_E_ITEM_OF_BARTER_NOT_EXISTS(32004, "换购商品不合法"),
    BIZ_E_BARTER_OUT_OF_USER_LIMIT(32005, "换购商品已达到上限"),
    BIZ_E_BARTER_UNREACHABLE(32006, "换购条件未达到"),
    BIZ_E_ACTIVITY_COUPON_NEED_BE_USED(32007, "优惠券需用完再领"),
    BIZ_E_ACTIVITY_PUBLISH_STATUS(32100, "活动已发布不可修改"),

/**
 * *
 * *
 * *
 * 虚拟财富
 * *
 * *
 * *
 */

    /**
     * 同组下的发送规则，同时只能有一个是开启状态
     */
    BIZ_E_ONLY_ONE_GRANT_RULE_IS_ON(32001, "同组下的发放规则只能开启一个"),
    /**
     * 需要退还的财富值超出了使用掉的财富值
     */
    THE_GIVE_BACK_WEALTH_AMOUNT_EXCEED_THE_USED_WEALTH(32002, "退回的财富值超出使用掉的财富值"),
    /**
     * 不存在已使用财富
     */
    USED_WEALTH_NOT_EXISTS(32003, "不存在已使用财富"),
    /**
     * 财富发放规则不存在
     */
    BIZ_E_THE_GRANT_RULE_DOES_NOT_EXIST(32004, "财富发放规则不存在"),
    /**
     * 没有开启的财富发放规则
     */
    BIZ_E_THERE_IS_NOT_GRANT_RULE_BE_ON(32005, "没有开启的财富发放规则"),
    /**
     * 虚拟财富不能用于交易
     */
    BIZ_E_VIRTUAL_WEALTH_CANNOT_BE_IN_TRADE(32006, "虚拟财富不能用于交易"),
    /*
     * 虚拟财富帐号不存在
     */
    BIZ_E_VIRTUAL_WEALTH_NOT_EXISTS(32007, "平台虚拟财富帐号不存在"),
    BIZ_E_USED_WEALTH_UNDER_ORDER_NOT_EXISTS(32007, "订单下没有使用的虚拟财富"),

    /**
     * 各平台结算信息返回
     */
    BIZ_E_VALIDA_SETTLEMENT(33001, "各平台结算返回信息"),
    
    /**
     * 系统异常
     */
    SERVICE_EXCEPTION(40002, "server exception"),
    REQUEST_FORBIDDEN(40003, "request forbidden"),
    ACTION_NO_EXIST(40004, "action not exist"),
    DB_OP_ERROR(40005, "database operation error"),
    SYS_E_REMOTE_CALL_ERROR(40006, "remote call error"),
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