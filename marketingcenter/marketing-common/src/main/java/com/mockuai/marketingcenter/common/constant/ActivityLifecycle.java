package com.mockuai.marketingcenter.common.constant;

/**
 * Created by zengzhangqiang on 7/26/15.
 * 优惠活动生命周期
 */
public enum ActivityLifecycle {
    /**
     * 未开始
     */
    LIFECYCLE_NOT_BEGIN(1),
    /**
     * 进行中
     */
    LIFECYCLE_IN_PROGRESS(2),
    /**
     * 已结束
     */
    LIFECYCLE_ENDED(3);

    private int value;

    ActivityLifecycle(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
