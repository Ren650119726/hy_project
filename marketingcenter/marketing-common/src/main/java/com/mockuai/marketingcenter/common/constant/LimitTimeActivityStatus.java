package com.mockuai.marketingcenter.common.constant;

/**
 * 限时购活动状态
 */
public enum LimitTimeActivityStatus {
    /**
     * 未开始
     */
    NOBEGIN(0),
    /**
     * 进行中
     */
    PROCESS(1),
    
    /**
     * 已结束
     */
    ALREADYOVER(2);

    private Integer value;

    LimitTimeActivityStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
