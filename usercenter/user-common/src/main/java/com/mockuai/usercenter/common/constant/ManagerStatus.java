package com.mockuai.usercenter.common.constant;

/**
 * Created by yeliming on 16/5/9.
 * 管理员状态枚举值
 */
public enum ManagerStatus {
    /**
     * 禁用状态
     */
    FORBIDDEN(0),
    /**
     * 正常状态
     */
    NOMARL(1);
    private int code;

    ManagerStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    
}
