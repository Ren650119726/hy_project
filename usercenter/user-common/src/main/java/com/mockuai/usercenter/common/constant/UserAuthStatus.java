package com.mockuai.usercenter.common.constant;

/**
 * Created by zengzhangqiang on 8/6/15.
 * 用户实名认证状态
 */
public enum  UserAuthStatus {
    /**
     * 等待认证
     */
    AUTH_WAIT(1),
    /**
     * 认证通过
     */
    AUTH_PASS(2),
    /**
     * 认证不通过/驳回
     */
    AUTH_REJECT(3),
    /**
     * 认证通过,等待交纳保证金
     */
    WAIT_GUARANTEE(4);

    private int value;
    private UserAuthStatus(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
