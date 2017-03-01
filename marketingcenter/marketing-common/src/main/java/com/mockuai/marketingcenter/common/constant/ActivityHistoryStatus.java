package com.mockuai.marketingcenter.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 换购用户历史记录
 * 实时性要求不高，只需要统计已付款的数量即可
 * <p/>
 * Created by edgar.zr on 12/3/15.
 */
public enum ActivityHistoryStatus {

    PAYING(1, "未支付"),
    PAY_DONE(2, "已付款"),
    PAY_CANCEL(3, "已退单");

    private static Map<Integer, ActivityCouponStatus> map = new HashMap<Integer, ActivityCouponStatus>();

    static {
        for (ActivityCouponStatus activityCouponStatus : ActivityCouponStatus.values()) {
            map.put(activityCouponStatus.getValue(), activityCouponStatus);
        }
    }

    private int value;
    private String name;

    ActivityHistoryStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static ActivityCouponStatus getByValue(int value) {
        return map.get(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}