package com.hanshu.employee.common.constant;

/**
 * Created by yeliming on 16/5/9.
 * 管理员状态枚举值
 */
public enum EmployeeStatus {
    /**
     * 禁用状态
     */
    FORBIDDEN(0),
    /**
     * 正常状态
     */
    NOMARL(1);
    private int code;

    EmployeeStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }


}
