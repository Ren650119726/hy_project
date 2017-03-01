package com.mockuai.usercenter.common.constant;

public enum ResponseCode {
    /**
     * 请求成功
     **/
    REQUEST_SUCCESS(10000, "request success"),


    /***********
     * 参数错误
     ************/
    // NAME_LENGTH_ERROR("P001","用户名长度错误"),
    // EMAIL_FORMAT_ERROR("P003","邮箱格式错误"),
    // MOBILE_FORMAT_ERROR("P004","手机格式错误"),
    // NAME_AND_PWD_IS_NULL("P005","用户名或密码为空"),
    // NAME_ALREADY_REG("P006","用户名已经被注册"),
    // EMAIL_ALREADY_REG("P007","邮箱已经被注册"),
    // MOBILE_ALREADY_REG("P008","号码已经被注册"),
    // USER_NOT_EXIST("P009","用户不存在"),
    // PARAM_E_PARAM_INVALID("P010","参数错误"),
    // USRE_ID_IS_NULL("P011","用户id为空"),
    P_PARAM_ERROR(20001, "param error"),
    P_PARAM_NULL(20002, "param null"),
    PWD_FORMAT_ERROR(20003, "密码的长度必须为6-20，并且由数字、字母或下划线组成"),
    P_PARAM_INVALID(20004, "param invalid"),

    /*********
     * 业务错误
     ***********/
    // B_OLDPWD_NEWPWD_IS_SAME("B001","新老密码相同"),
    // B_PWD_IS_ERROR("B002","密码错误"),
    // B_RECORD_IS_EXIST("B003","记录已存在,重复添加记录"),
    // B_RECORD_IS_NOT_EXIST("B004","不存在指定的记录，不能修改");
    B_ADD_ERROR(30001, "add error"),
    B_UPDATE_ERROR(30002, "update error"),
    B_DELETE_ERROR(30003, "delete error"),
    B_SELECT_ERROR(30004, "select error"),
    /**
     * 访问的接口不存在
     */
    B_ACTION_NO_EXIST(30005, "action no exist"),
    /**
     * 请求被禁用
     */
    B_REQUEST_FORBBIDEN(30006, "request forbbiden"),
    /**
     * 邮箱已存在
     */
    B_EMAIL_IS_EXIST(30007, "邮箱已经存在"),
    /**
     * 手机号 已存在
     */
    B_MOBILE_IS_EXIST(30008, "手机号已经存在"),

    /**
     * 原密码错误
     */
    B_OLDPWD_ERROR(30009, "old password is error"),

    /**
     * 账号不存在
     */
    B_ACCOUNT_NOT_EXIST(30010, "账号不存在"),

    /**
     * 密码错误
     */
    B_PASSWORD_ERROR(30011, "密码错误"),
    B_BIND_DUPLICATE(30012, "duplicate bind error"),
    B_INVITATION_CODE_INVALID(30013, "请输入正确的邀请码"),
    B_OPEN_ACCOUNT_NOT_EXIST(30014, "the specified open account is not exist"),
    B_MOBILE_HAS_BINDED_BY_OTHER_PERSON(30015, "the specified mobile has binded by other people"),
    B_ID_CARD_NO_HAS_BEEN_AUTHED(30016, "idCardNo has been authed"),
    B_USER_AUTH_INFO_NOT_EXIST(30017, "userAuthInfo not exist"),
    B_USER_AUTH_INFO_IS_EXIST(30018, "userAuthInfo is exist already"),
    B_APP_NOT_EXIST(30019, "the specified app is not exist"),
    B_BIND_CONFLICT(30020, "bind conflict error"),
    B_INVITER_NOT_EXIST(30021, "the specified inviter is not exist"),
    B_USER_FREEZE(30022, "用户账号被冻结"),
    /**
     * 身份证信息不合法
     */
    B_ID_CARD_ERROR(30022, "身份证信息不合法"),
    B_MOBILE_FORMAT_ERROR(30023, "手机号格式错误"),
    B_INVITATION_CODE_FORMAT_ILLEGAL(30024, "邀请码格式错误"),
    B_APPTYPE_IS_NULL(30025, "访问的设备类型为空"),
    B_MIGRATE_INTEGRAL_ERROR(30026, "数据迁移错误"),
    B_USER_DIRECTORY_ERROR(30027, "您的手机通讯录中没有找到嗨云用户"),
    /**
     * 验证提示错误
     * 
     */ 
    HS_REQUEST_SUCCESS(10000, "修改成功"),
    HS_PWD_MESSAGE_FORMAT(30027,"密码不正确"),
    HS_MOBILE_ALREADY_EXISTS(30028,"手机号已被使用"),
    HS_MOBILE_NEWANDOLD_SAME(30029,"新号码与原号码相同"),
    HS_MOBILE_IDCODE_NOTEXISTS(30030,"验证错误,请重新输入"),
    HS_NICK_NAME_LIMIT(30031,"昵称必须由2-15个汉字、英文或数字组成"),
    HS_LOGIN_NAME_ERROR(30032,"请輸入正确的手机号"),
    HS_PWD_MESSAGE_LIMIT(30033,"密码只能设置英文或数字"),
    B_E_APP_KEY_INVALID(30034, "appKey无效"),
    HS_VERIFY_CODE_SELL(30035,"邀请码对应邀请人必须是卖家"),
    HS_PAY_CODE_FORMAT(30036,"输入支付密码不正确或不符合格式"),
    HS_USER_INVITER_UPDATE_ERROR(30037,"更新用户邀请人失败"),
    HS_USER_INVITER_UPDATE_NULL(30038,"更新用户邀请人id不可为空"),
    HS_USER_INFO_NULL(30039,"客户信息不存在或未登录"),
    HS_USER_PAY_PASSWORD(30040,"更新支付密码失败"),
    HS_USER_PAY_CHECK_EXP(30041,"两次输入支付密码不一样,请重新输入"),
    HS_USER_PAY_TWICE_ALILE(30042,"新支付密码不能与原支付密码相同"),
    HS_USER_PAYPASSWORD_NULLSET(30043,"未设置支付密码,不能进行修改操作"),
    HS_USER_IDCARD_NULL(30044,"身份证号码不可为空"),
    HS_USER_PWD_TWICE_ALILE(30045,"新密码与原密码相同,请重新设置"),
    /**
     * 验证码
     */
    B_E_VERYFY_CODE_TIMEOUT(30046, "验证码已失效"),
    B_E_VERYFY_CODE_INVALID(30047, "验证码错误"),
    B_E_VERYFY_CODE_FORMAT(30048, "验证码格式错误"), 
    B_USER_HIKECONDITION_ERROR(30049, "错误消息"),
    
    /**
     * 系统异常
     */
    SYS_E_SERVICE_EXCEPTION(40001, "system exception");
    
     

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
