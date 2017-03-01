package com.mockuai.marketingcenter.common.constant;

/**
 * 属性属主类型
 * Created by edgar.zr on 9/7/15.
 */
public enum PropertyOwnerType {

    /**
     * 属主为工具,目前没有使用
     */
    TOOL(1),

    /**
     * 属主为优惠活动
     */
    ACTIVITY(2);

    private Integer value;

    PropertyOwnerType(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
