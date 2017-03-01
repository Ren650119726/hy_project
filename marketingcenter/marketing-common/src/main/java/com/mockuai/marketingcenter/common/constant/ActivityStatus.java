package com.mockuai.marketingcenter.common.constant;

public enum ActivityStatus {
    /**
     * 正常状态
     */
    NORMAL(1),
    /**
     * 失效状态
     */
    INVALID(2),
    
    /**
     * 未发布显示状态
     */
    PUBLISH(3);

    private Integer value;

    ActivityStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
