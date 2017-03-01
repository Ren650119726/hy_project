package com.mockuai.suppliercenter.common.constant;

/**
 * Created by duke on 15/8/19.
 */
public enum MessageType {

    P2P_MESSAGE(1),
    GLOBAL_MESSAGE(2);

    private Integer value;

    private MessageType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
