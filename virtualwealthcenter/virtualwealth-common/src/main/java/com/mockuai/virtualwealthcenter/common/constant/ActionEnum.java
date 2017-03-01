package com.mockuai.virtualwealthcenter.common.constant;

public enum ActionEnum {


    /**
     * 查询提现配置
     */
    FIND_WITHDRAWALS_CONFIG ("FindWithdrawalsConfig"),


    /**
     * 新增提现规则
     */
    ADD_WITHDRAWALS_CONFIG("AddWithdrawalsConfig"),

    /***
     * 查询提现记录
     */
    FIND_WITHDRAWALS_ITEM("findWithdrawalsItem"),
    /***
     * 更新提现记录
     */
    UPDATE_WITHDRAWALS_ITEM("updateWithdrawalsItem"),

    /**
     * 为用户添加虚拟财富账号
     */
    ADD_WEALTH_ACCOUNT("addWealthAccount"),
    /**
     * 初始化平台虚拟财富帐号
     */
    INIT_VIRTUAL_WEALTH("initVirtualWealth"),
    /**
     * 创建财富发放规则
     */
    ADD_GRANT_RULE("addGrantRule"),
    /**
     * 删除财富发放规则
     */
    DELETE_GRANT_RULE("deleteGrantRule"),
    /**
     * 更新财富发放规则
     */
    UPDATE_GRANT_RULE("updateGrantRule"),
    /**
     * 查看财富发放规则
     */
    GET_GRANT_RULE("getGrantRule"),
    /**
     * 通过属主查看发放规则
     */
    GET_GRANT_RULE_BY_OWNER("getGrantRuleByOwner"),
    /**
     * 查询财富发放规则
     */
    QUERY_GRANT_RULE("queryGrantRule"),
    /**
     * 财富发放规则开关
     */
    SWITCH_GRANT_RULE("switchGrantRule"),
    /**
     * 更新商家虚拟财富
     */
    UPDATE_VIRTUAL_WEALTH("updateVirtualWealth"),
    /**
     * 查看商家虚拟财富信息
     */
    GET_VIRTUAL_WEALTH("getVirtualWealth"),
    /**
     * 根据发放规则发放财富
     */
    GRANT_VIRTUAL_WEALTH_WITH_GRANT_RULE("grantVirtualWealthWithGrantRule"),

    ///////////////////////

    /**
     * 生成商家虚拟财富帐号
     */
    GENERATE_VIRTUAL_WEALTH("generateVirtualWealth"),
    /**
     * 发放虚拟财富
     */
    GRANT_VIRTUAL_WEALTH("grantVirtualWealth"),
    /**
     * 批量发放虚拟财富
     */
    GRANT_VIRTUAL_WEALTH_BATCH("grantVirtualWealthBatch"),
    /**
     * 查询虚拟帐号
     */
    QUERY_WEALTH_ACCOUNT("queryWealthAccount"),
    /**
     * 批量查询帐号
     */
    QUERY_WEALTH_ACCOUNT_BATCH("queryWealthAccountBatch"),
    /**
     * 预使用用户虚拟财富
     */
    PRE_USE_USER_WEALTH("preUseUserWealth"),
    /**
     * 批量预使用用户虚拟财富
     */
    PRE_USE_MULTI_USER_WEALTH("preUseMultiUserWealth"),
    /**
     * 虚拟财富由预使用转换到正式使用
     */
    USE_USER_WEALTH("useUserWealth"),
    /**
     * 虚拟财富由预使用转换到正式使用
     */
    USE_MULTI_USER_WEALTH("useMultiUserWealth"),
    /**
     * 释放预使用的虚拟财富
     */
    RELEASE_USED_WEALTH("releaseUsedWealth"),
    /**
     * 批量释放预使用的虚拟财富
     */
    RELEASE_MULTI_USED_WEALTH("releaseMultiUsedWealth"),
    /**
     * 回滚部分已使用的虚拟财富
     */
    GIVE_BACK_PARTIAL_USED_WEALTH("giveBackPartialUsedWealth"),
    /**
     * 查询商家虚拟财富
     */
    QUERY_VIRTUAL_WEALTH("queryVirtualWealth"),
    /**
     * 查询商家虚拟财富和对应的财富发放规则
     */
    QUERY_VIRTUAL_WEALTH_WITH_GRANT_RULE("queryVirtualWealthWithGrantRule"),
    /**
     * 直接扣减用户的虚拟财富
     */
    DEDUCT_VIRTUAL_WEALTH("deductVirtualWealth"),
/**
 * *
 * *
 * *
 * * 充值
 * *
 * *
 * *
 */
    /**
     * 添加充值配置项
     */
    UPDATE_VIRTUAL_WEALTH_ITEM("updateVirtualWealthItem"),
    /**
     * 查询充值配置项
     */
    QUERY_VIRTUAL_WEALTH_ITEM("queryVirtualWealthItem"),
    /**
     * 删除配置项
     */
    DELETE_VIRTUAL_WEALTH_ITEM("deleteRechargeConfig"),
    /**
     * 添加充值记录
     */
    ADD_RECHARGE_RECORD("addRechargeRecord"),
    /**
     * 查询充值记录
     */
    QUERY_RECHARGE_RECORD("queryRechargeRecord"),
    /**
     * 更新充值记录
     */
    UPDATE_RECHARGE_RECORD("updateRechargeRecord"),
    /**
     * 分佣虚拟财富发放
     */
    DISTRIBUTOR_GRANT("distributorGrant"),
    /**
     * 更新嗨币状态
     */
    UPDATE_STATUS_OF_VIRTUAL_WEALTH_DISTRIBUTOR_GRANTED("updateStatusOfVirtualWealthDistributorGranted"),
    /**
     * 获取用户嗨币总览信息
     */
    GET_HI_COIN("getHiCoin"),
    /**
     * 获取用户也额总览信息
     */
    GET_BALANCE("getBalance"),
    /**
     * 虚拟财富交易记录
     */
    LIST_RECORD_OF_WEALTH("listRecordOfWealth"),
    /**
     * 提现详情
     */
    DETAIL_OF_WITHDRAWALS("detailOfWithdrawals"),
    /**
     * 查询累计收入、本日收入、本日嗨币
     */
    QUERY_TOTAL_VIRTUAL_WEALTH_COMBINE("queryTotalVirtualWealthCombine"),
    /**
     * 获取指定用户累计虚拟财富
     */
    LIST_TOTAL_VIRTUAL_WEALTH("listTotalVirtualWealth"),

    /**
     **
     **提现
     **
     **
     */

    /**
     * 提现选择银行卡，默认要有一张，否则提示进入实名认证流程
     */
    LIST_WD_BANK("listWdBank"),
    
    
    /**
     * 客户管理 余额提现流水 
     */
    FIND_CUSTOMER_WD_PAGELIST("find_customer_wd_pagelist"),
    
    /**
     * 客户管理 银行卡管理
     */
    FIND_CUSTOMER_BANKINFO_PAGELIST("find_customer_bankinfo_pagelist"),
    
    /**
     * 客户管理 余额或者嗨币的收入
     */
    FIND_CUSTOMER_GRANTED_PAGELIST("find_customer_granted_pagelist"),
    
    /**
     * 客户管理 余额或者嗨币的支出
     */
    FIND_CUSTOMER_USED_PAGELIST("find_customer_used_pagelist"),
    
    /**
     * 客户管理 嗨币 详情  overTime = 当前时间-10个月
     */
    FIND_CUSTOMER_Virtual_DETAIL("find_customer_virtual_detail"),
    
    /**
     * 客户管理 余额详情
     */
    FIND_CUSTOMER_BALANCE_DETAIL("find_customer_balance_detail"),
    
    /**
     * 选择银行卡提现
     */
    CHOICE_WD_BANK("choiceWdBank"),
    
    /**
     * 提现申请
     */
    ADD_WD_SUBMIT("addWdSubmit"),

    /**
     **
     **实名认证和绑卡
     **
     */
    /**
     * 实名认证验证接口
     */
    USER_AUTHON_ADD("userAuthonAdd"),
    
    /**
     * 根据用户ID获取实名认证信息
     */
    USER_AUTHON_SEL("userAuthonSel"),
    
    /**
     * 根据用户身份证号获取实名认证信息
     */
    USER_AUTHON_PERSONALID_SEL("userAuthonPersonal"),
    
    
    /**
     * 根据用户身份证号获取实名认证信息
     */
    USER_AUTHON_BYQTO_SEL("userAuthonByQtoSel"),

    /**
     * 根据用户ID集合获取实名认证信息
     */
    USER_AUTHON_USERIDLIST_SEL("userAuthonUserIdListSel"),
    /**
     * 获取银行卡list
     */
    LIST_BANK_INFO("lisBankInfo"),

    /**
     * 删除银行卡
     */
    DEL_BANK_INFO("delBankInfo"),

    /**
     * 查看银行卡详情
     */
    SEL_BANK_INFO("selBankInfo"),

    /**
     * 添加银行卡接口
     */
    ADD_BANK_INFO("addBankInfo"),
    /**
     * 查询审核列表
     */
    USER_AUTHON_ALL("userauthonselect"),
    /**
     * 通过审核
     */
    ACCEPT_AUDITING("acceptAuditing"),
    /**
     * 拒绝通过审核
     */
    REFUES_AUDITING("refuseAuditing"),
    /**
     * 查询审核状态
     */
    SELECT_AUDIT_STATUS("selectAuditStatus"),
    /**
     * 用户更改用户名时更新到实名认证表中
     */
    SYNC_PHONE_NUMBER("SyncPhoneNumber")
    ;

    private String actionName;

    ActionEnum(String actionName) {
        this.actionName = actionName;
    }

    public static ActionEnum getActionEnum(String actionName) {
        for (ActionEnum ae : values()) {
            if (ae.actionName.equals(actionName)) {
                return ae;
            }
        }
        return null;
    }

    public String getActionName() {
        return this.actionName;
    }
}