package com.mockuai.suppliercenter.common.constant;

/**
 * Created by duke on 15/11/19.
 */
public enum BizType {
    /**
     * 迁移话机世界的用户
     */
    ROCKET_MQ_MESSAGE_MIGRATE_USER(1);
    private int value;

    BizType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
