package com.mockuai.usercenter.common.constant;

/**
 * Created by duke on 15/9/8.
 */
public enum UserType {
    /**
     * 临时用户
     * */
    TEMP_USER(0),
    /**
     * 普通用户
     * */
    NORMAL_USER(1),

    /**
     * 第三方登录用户
     * */
    THIRD_PART_USER(2),

    /**
     * 迁入的旧用户
     * */
    OLD_USER(3),

    /**
     * 门店用户
     * */
    STORE_USER(4);

    private int value;
    private UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
