package com.mockuai.dts.common.constant;

/**
 * Created by luliang on 15/7/2.
 */
public enum TaskTypeEnum {

    // 商品列表导出
    ITEM_EXPORT_TYPE(1),
    // 订单列表导出
    BIZ_ORDER_EXPORT_TYPE(2),
    
    SELLER_USER_EXPORT_TYPE(3),
    
    SELLER_TRANS_LOG(4),

    LABEL_EXPORT_TYPE(5),

    MEMBER_EXPORT_TYPE(6),

    DISTRIBUTION_STATISTICS_EXPORT_TYPE(7),

    MEMBER_DATA_MIGRATE_TYPE(8),

    MOBILE_SEGMENT_EXPORT_TYPE(9),

    MOBILE_VIRTUAL_ITEM_EXPORT_TYPE(10),

    MOBILE_FARE_ITEM_EXPORT_TYPE(11),

    MOBILE_FLOW_ITEM_EXPORT_TYPE(12),

    MOBILE_RECHARGE_ORDER_EXPORT_TYPE(13),

    DISTRIBUTION_WITHDRAW_RECORD_EXPORT_TYPE(14),

    MINISHOP_STATISTIC_EXPORT_TYPE(15),

    MOBILE_SEGMENT_IMPORT_TYPE(101),
    ;

    private int type;

    private String name;

    private TaskTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return "export_item_task";
    }

}
