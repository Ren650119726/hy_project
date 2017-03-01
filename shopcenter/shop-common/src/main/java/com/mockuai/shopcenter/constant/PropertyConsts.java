package com.mockuai.shopcenter.constant;

/**
 * Created by yindingyu on 15/11/13.
 * 本类中定义shop_db纵表中的p_key值
 */
public interface PropertyConsts {

    /**
     * 是否开启商品详情页线下门店展示
     */
    static final String SUPPORT_STORE = "support_store";

    /**
     * 是否支持门店配送
     */
    static final String SUPPORT_DELIVERY = "support_delivery";

    /**
     * 是否支持门店自提
     */
    static final String SUPPORT_PICK_UP = "support_pick_up";

    /**
     * 对上门回收服务的支持情况
     */
    static final String SUPPORT_HOME_RECOVERY = "support_home_recovery";

    /**
     * 对门店回收服务的支持情况
     */
    static final String SUPPORT_STORE_RECOVERY = "support_store_recovery";

    /**
     *回收服务快递验货收货人
     */
    static  final String RECOVERY_RECEIVER = "recovery_receiver";

    /**
     *回收服务快递验货收货人联系方式
     */
    static  final String RECOVERY_MOBILE = "recovery_mobile";

    /**
     *回收服务快递验货收货地址
     */
    static  final String RECOVERY_ADDRESS = "recovery_address";

    /**
     * 回收服务支持的范围
     */
    static final String RECOVERY_RANGE = "recovery_range";



    /**
     * 对上门维修服务的支持情况
     */
    static final String SUPPORT_HOME_REPAIR = "support_home_repair";

    /**
     * 对门店维修服务的支持情况
     */
    static final String SUPPORT_STORE_REPAIR = "support_store_repair";

    /**
     *维修服务快递验货收货人
     */
    static  final String REPAIR_RECEIVER = "repair_receiver";

    /**
     *维修服务快递验货收货人联系方式
     */
    static  final String REPAIR_MOBILE = "repair_mobile";

    /**
     *维修服务快递验货收货地址
     */
    static  final String REPAIR_ADDRESS = "repair_address";

    /**
     * 维修服务支持的范围
     */
    static final String REPAIR_RANGE = "repair_range";
}
