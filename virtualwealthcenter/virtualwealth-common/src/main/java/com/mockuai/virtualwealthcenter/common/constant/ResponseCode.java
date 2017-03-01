package com.mockuai.virtualwealthcenter.common.constant;

public enum ResponseCode {

    SUCCESS(10000, "success"),

    //参数错误
    PARAMETER_NULL(20001, "parameter is null"),
    PARAMETER_ERROR(20002, "parameter is error"),
    PARAMETER_MISSING(20003, "some required parameter is missing"),

    //业务异常
    BIZ_E_UPDATE_GRANTED_WEALTH_FAILED(31001, "更新发放记录失败"),
    BIZ_E_UPDATE_FROZEN_BALANCE_FAILED(31002, "更新冻结虚拟财富失败"),
    GRANTED_WEALTH_NOT_EXIST(31003, "虚拟财富发放记录不存在"),
    BIZ_E_DECREASE_GRANTED_WEALTH(31004, "减少发放记录失败"),
    BIZ_E_UPDATE_TOTAL_BALANCE_FAILED(31003, "更新累计虚拟财富失败"),

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
     * 财富账户不存在
     */
    WEALTH_ACCOUNT_IS_NOT_FOUND(31026, "财富账户不存在"),

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
    /**
     * 虚拟财富帐号不存在
     */
    BIZ_E_VIRTUAL_WEALTH_NOT_EXISTS(32007, "平台虚拟财富帐号不存在"),
    BIZ_E_USED_WEALTH_UNDER_ORDER_NOT_EXISTS(32007, "订单下没有使用的虚拟财富"),
    /**
     * 您还未同意嗨客协议
     */
    NOT_AGREE_PROTOCOL(33001, "请确认您的嗨客身份"),
    /**
     * 您还未绑定提现账号，是否先绑定？
     */
    NOT_AUTON(32071, "您还未实名认证，是否去认证？"),
    /**
     * 您还未进行实名认证
     */
    NOT_BANK_AUTON(32091, "您还未进行实名认证"),
    /**
     * 实名认证失败
     */
    ERROR_AUTHON(32079,"实名认证失败,请重试"),
    
    /**
     * 银行卡校验失败,请重试
     */
    ERROR_BANKBIN_AUTHON(32092,"银行卡验证失败,请重试"),
    
    /**
     * 实名认证不支持信用卡验证
     */
    ERROR_CREDIT_AUTHON(32093,"实名认证不支持信用卡验证"),
    
    /**
     * 发卡银行与银行卡号不匹配
     */
    ERROR_MATCH_NAME(32095,"发卡银行与银行卡号不匹配"),
    
    /**
     * 身份证格式不正确
     */
    ERROR_IDCARD_INFO(32096,"身份证格式不正确"),
    
    /**
     * 银行卡管理不支持信用卡添加
     */
    ERROR_CREDIT_ADDBANK(32094,"银行卡管理不支持信用卡添加"),
    
    /**
     * 实名认证校验结果返回为null,请重试
     */
    ERROR_NULL_AUTHON(32083,"实名认证校验结果返回为null,请重试"),
    
    /**
     * 验证超时，请24时后重试；24小时之后才可重新验证
     */
    ERROR_EXCEED_AUTHON(32088,"验证超时，请次日重试"),

    /**
     * 此号码已被其他账号绑定
     */
    EXISTS_ACCOUNT_BIND(32089,"此银行卡已被其他账号绑定"),
    
    /**
     * 提现金额不能小于最小提现金额
     */
    ERROR_LESS_AMOUNT(32084,"提现金额不能小于最小提现金额"),
    /**
     * 账户余额信息不存在
     */
    ERROR_NULL_AMOUNT(32085,"账户余额信息不存在"),
    /**
     * 提现配置不允许提现
     * NO_CONFIG_WD(32076,"当前时段不允许提现"),
     */
    /**
     * 提现关闭，下次提现于7月25-30日开放！
     */
    NO_CONFIG_WD(32076,"提现关闭，下次提现于7月25-30日开放！"),
    /**
     * 提现金额超限
     */
    ERROR_MAX_WD(32077,"可提现余额不足"),
    /**
     * 不能小于最小提现金额
     */
    ERROR_MINI_WD(32078,"单笔提现金额不可少于X元"),
    WITHDRAWALS_ITEM_NOT_EXISTS(43002, "提现记录不存在"),
    
    /**
     * 最多添加10张银行卡
     */
    MAX_BANKINFO_NUM(32072,"最多添加十张银行卡"),
    /**
     * 最多添加10张银行卡
     */
    DEL_IS_DEFAULT(32073,"默认卡无法删除"),
    
    /**
     * 该卡已存在
     */
    REPEAT_BANKINFO(32080,"此银行卡已被绑定"),
    
    /**
     * 该卡已删除
     */
    DEL_ALREADY_BANKINFO(32081,"该卡已删除"),
    
    /**
     * 该卡不存在
     */
    NO_EXIST_BANKINFO(32082,"该卡不存在"),
    
    /**
     * 该银行卡不支持实名认证，请更换银行卡
     */
    NOAUTHON_BANKINFO(32097,"该银行卡暂不支持实名认证，请更换银行卡"),
    
    /**
     * 用户不存在
     */
    NOT_EXIST_USER(32074,"用户不存在"),
    
    /**
     * 已开通店铺的用户才可发起提现
     */
    ROLE_MARK_USER(32075,"已开通店铺的用户才可发起提现"),
    
    /**
     * 已开通店铺的用户才可管理银行卡
     */
    ERRORAUTHON_BANK_INF(32087,"您还未实名认证，是否去认证？"),
    
    /**
     * 已开通店铺的用户才可实名认证
     */
    EXISTS_ATHON_BIND(32090,"已开通店铺的用户才可实名认证"),
   
    
    /**
     * 还未绑定手机号，是否绑定
     */
    NOBIND_MOBILE_USER(32086,"还未绑定手机号，是否绑定"),
    /**
     * 系统异常
     */
    SERVICE_EXCEPTION(40002, "server exception"),
    REQUEST_FORBIDDEN(40003, "request forbidden"),
    ACTION_NO_EXIST(40004, "action not exist"),
    DB_OP_ERROR(40005, "database operation error"),
    SYS_E_REMOTE_CALL_ERROR(40006, "remote call error"),
    DB_OP_ERROR_OF_DUPLICATE_ENTRY(41001, "duplicate entry for specified key"),
    COMPONENT_NOT_EXIST(41002, "component not exist"),
     /**
     * 身份证正面照片不存在
     */
    POSTIVE_PHOTO_OF_ID_card_DOES_NOT_EXIST(32100, "身份证正面照片不存在"),
    /**
     * 身份证背面照片不存在
     */
    THE_BACK_OF_ID_card_DOES_NOT_EXIST(32101, "身份证背面照片不存在"),
    /**
     * 姓名未输入
     */
    NAME_NO_INPUT(32102, "姓名未输入"),
    /**
     * 用户信息提交失败
     */
    USER_INFORMATION_SUBMISSION_FAILED(32103,"信息提交失败")   
    
    ;

    
//    ZHHRYH_BANK_INFO(8000,"珠海华润银行"),
//    GZYH_BANK_INFO(8001,"广州银行"),
//    YBYH_BANK_INFO(8002,"宁波银行"),
//    HZYH_BANK_INFO(8003,"杭州银行"),
//    
//    SHYH_BANK_INFO(04012900,"上海银行"),
//    BJYH_BANK_INFO(04031000,"北京银行"),
//    YCYH_BANK_INFO(01000000,"邮储银行"),
//    JTYH_BANK_INFO(03010000,"交通银行"),
//    PFYH_BANK_INFO(03100000,"浦发银行"),
//    GDYH_BANK_INFO(03030000,"光大银行"),
//    XYYH_BANK_INFO(03090000,"兴业银行"),
//    PAYH_BANK_INFO(03070000,"平安银行"),
//    GFYH_BANK_INFO(03060000,"广发银行"),
//    MSYH_BANK_INFO(03050000,"民生银行"),
//    HXYH_BANK_INFO(03040000,"华夏银行"),
//    ZXYH_BANK_INFO(03020000,"中信银行"),
//    ZSYH_BANK_INFO(03080000,"招商银行"),
//    GSYH_BANK_INFO(01020000,"工商银行"),
//    ZGYH_BANK_INFO(01040000,"中国银行"),
//    JSYH_BANK_INFO(01050000,"建设银行"),
//    NYYH_BANK_INFO(01030000,"农业银行"),
//    
    
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