package com.mockuai.virtualwealthcenter.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edgar.zr on 11/11/15.
 */
public enum SourceType {
    /**
     * 只要不符合剩余项的，均为其他项
     */
    OTHER(0, "其他", -1),
    ORDER_PAY(1, "订单支付额", 1),
    COMMENT(2, "订单评论", 1),
    TRANSMIT(3, "转发有礼", -1),
    SIGN_IN(4, "签到", -1),
    GROUP_BUY(5, "团购", 0),
    RECHARGE(6, "充值", -1),
    SHOP(7, "开店", -1),
    SELL(8, "销售", -1),
    GROUP_SELL(9, "团队销售", -1),
    REFUND(10, "退款", -1),
    SHARE_DIST(13,"嗨客分享分拥",-1),
    NOSHARE_DIST(14,"嗨客分享分拥",-1),
    PURCHASE_DIST(15,"嗨客自购分拥",-1),
    NOPURCHASE_DIST(16,"非嗨客自购分拥",-1),
    OUTER_GRANT(101, "外部erp直接发放", -1);

    private static Map<Integer, SourceType> map = new HashMap<Integer, SourceType>();

    static {
        for (SourceType sourceType : SourceType.values()) {
            map.put(sourceType.value, sourceType);
        }
    }

    private int value;
    private String name;
    /**
     * type : -1, 无规则
     * type : 0, 单规则
     * type : 1, 组合规则
     */
    private int type;

    SourceType(int value, String name, int type) {
        this.value = value;
        this.name = name;
        this.type = type;
    }

    public static SourceType getByValue(Integer value) {
        return map.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}