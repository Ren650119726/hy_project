package com.mockuai.usercenter.common.constant;

/**
 * Created by duke on 15/8/22.
 */
public enum BindFansAndInviterActionEnum {


    FANS_USER_NOT_EXIST(1L),
    FANS_ALREADY_BIND(2L),
    READY_TO_BIND(3L);


    private Long type;

    BindFansAndInviterActionEnum(Long type) {
        this.type = type;
    }

    public Long getValue() {
        return type;
    }
}
