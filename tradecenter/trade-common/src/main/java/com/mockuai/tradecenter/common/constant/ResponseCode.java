package com.mockuai.tradecenter.common.constant;

public enum ResponseCode {
    RESPONSE_SUCCESS(10000, "success"),

    //参数异常
    PARAM_E_PARAM_INVALID(20001, "param is invalid"),
    PARAM_E_PARAM_MISSING(20002, "param is null"),
    PARAM_E_ORDER_ITEM_LIST_EMPTY(20003, "orderItem list is empty"),
    PARAM_E_CONSIGNEE_INVALID(20004, "the specified consignee is invalid"),
    //TODO action不存在的异常归类待定
    PARAM_E_ACTION_NOT_EXIST(20005, "the action does not exist"),
    PARAM_E_PARAM_ILLEGAL(20006, "param is illegal"),
    //业务异常
    BIZ_E_RECORD_NOT_EXIST(30001, "请求的记录不存在"),
    BIZ_E_CART_EMPTY(30002, "购物车为空"),
    BIZ_E_CART_MAX_LIMITATION(30003, "exceed the cart max limitation"),
    BIZ_E_PAYMENT_TYPE_ERROR(30004, "支付方式错误"),
    BIZ_E_COUPON_CONDITION_ERROR(30005, "未达到优惠券的使用条件"),
    BIZ_E_SPECIFIED_COUPON_UNAVAILABLE(30006, "指定的优惠券不可用"),
    BIZ_E_SPECIFIED_WEALTH_UNAVAILABLE(30007, "指定的虚拟账户不可用"),
    BIZ_E_SPECIFIED_WEALTH_BALANCE_NOT_ENOUGH(30008, "指定的虚拟账户余额不足"),

    BIZ_E_COD_NOT_SUPPORTED(30009, "不支持货到付款"),
    BIZ_E_COD_MAX_LIMITATION(30010, "cod max limitation"),
    BIZ_E_COD_MIN_LIMITATION(30011, "cod min limitation"),

    BIZ_E_ITEM_NOT_EXIST(30012, "商品不存在"),
    BIZ_E_ITEM_SALE_NOT_START(30013, "the item not begin yet"),
    BIZ_E_ITEM_SALE_END(30014, "the item is over"),
    /**
     * 商品sku库存不足
     */
    BIZ_E_ITEM_OUT_OF_STOCK(30015, "库存不足"),

    BIZ_E_ITEM_BUY_MAX_AMOUNT(30016, "超出最大购买数量"),
    BIZ_E_ITEM_BUY_MIN_AMOUNT(30017, "less than the min limitaion"),

    BIZ_E_PAYMENT_NOTICE_NOT_EXIST(30018, "支付单不存在"),
    BIZ_E_PAYMENT_SIGN_ERROR(30019, "数字签名错误"),

    BIZ_E_PAYMENT_COD_NOT_SUPPORTED(30020, "cod not supported"),

    BIZ_E_ORDER_STATUS_ERROR(30021, "order status error"),
    BIZ_E_ORDER_CLOSED(30022, "order already closed "),
    BIZ_E_ORDER_TIMEOUT(30023, "order timeout"),
    BIZ_E_ORDER_AFTERSALE(30024, "aftersale already in process"),
    BZI_E_ORDER_UNPAID_CANNOT_DELIVERY(30025, "未支付订单不能发货"),
    BZI_E_ORDER_UNDELIVERED_CANNOT_RECEIPT(30026, "未发货订单不能直接收货"),
    BIZ_E_ORDER_NOT_EXIST(30027, "order is not exist"),
    BIZ_E_PAYMNET_ALIPAY_ERROR(30028, "alipay status error"),
    BIZ_E_PAYMNET_WECHAT_ERROR(30029, "wechat status error"),
    BIZ_E_PAY_FAILED(30030, "pay failed"),
    BIZ_E_REQUEST_FORBIDDEN(30031, "the request is forbidden"),
    BIZ_E_PRE_USE_COUPON_ERROR(30032, "error to pre use user coupon"),
    BIZ_E_PRE_USE_WEALTH_ERROR(30033, "error to pre use user wealth"),
    BIZ_E_RELEASE_COUPON_ERROR(30034, "error to release user coupon"),
    BIZ_E_RELEASE_WEALTH_ERROR(30035, "error to release user wealth"),
    BIZ_E_USE_COUPON_ERROR(30036, "error to use user coupon"),
    BIZ_E_USE_WEALTH_ERROR(30037, "error to use user wealth"),
    BIZ_E_APP_NOT_EXIST(30038, "the specified app is not exist"),
    /**
     * 订单商品所属的原商品无效或已经被删除
     */
    BIZ_E_ORDER_ITEM_INVALID(30039, "商品不存在或已下架"),

    /**
     * 订单商品所属的原商品状态无效
     */
    BIZ_E_ORDER_ITEM_STATUS_INVALID(30040, "订单中存在下架商品，无法完成支付！"),


    /**
     * 订单商品所属的原商品sku无效或已经被删除
     */
    BIZ_E_ORDER_ITEM_SKU_INVALID(30041, "the order item sku is invalid"),
    
    /**
     * 商品已经发货
     * 
     */
    BIZ_E_ORDER_AHAVE_BEEN_SHIPPED(30043, "the order have been shipped"),
    
    BZI_E_ORDER_UNSIGN_OFF_CANNOT_COMMENT(30044,"未收货订单不能评论"),
    BZI_E_ORDER_DELIVERYED_CANNOT_DELIVERY(30043, "已发货订单不能再次发货"),
    
    BZI_E_ORDER_ADD_DELIVERY_IMFO_FAILED(30044, "添加发货信息失败"),

    BZI_E_ITEM_NOT_GROUNDING(30045,"该商品未上架"),
    
    BIZ_NOT_EXIST_PAYMENT(30046,"没有对应的支付方式"),
    
    BIZ_ITEM_NUMBER_ERROR(30047,"商品数量错误"),
    
    HAS_REFUND_ITEM_CAN_NOT_OPERATE(30048,"退款申请中，请勿操作！"),
    
    PICK_UP_CODE_NOT_MATCH(30049,"提货码不匹配"),
    
    PRE_ORDER_HAS_EXIST(30050,"预单已经存在"),
    
    ORDER_PAID(30051,"订单已经支付"),
    
    ORDER_REFUND_TIMEOUT(30052,"超过15天订单不能申请退款"),
    
    NO_REFUND_FUNCTION(30053,"没有开通退款功能"),

    BIZ_E_DIST_SHOP_NOT_EXIST(30054, "指定分销店铺不存在"),
    BIZ_E_GIFT_PACK_ORDER_BUY_LIMIT(30055, "开店礼包每人限购一次，请勿重复购买！"),
    BIZ_E_ORDER_NO_UN_DELIVERYED_CANNOT_DELIVERY(30056, "只有代发货状态的订单才能发货！"),
    BIZ_E_PAY_PASSWORD_ERROR(30057, "支付密码错误！"),
    NO_PAYMENT_METHOD(30058, "无可用的支付方式"),
    BIZ_E_LIMIT_ITEM_BUY_MAX_AMOUNT(30059, "业务异常专用code"),

    //系统异常
    SYS_E_DEFAULT_ERROR(40001, "%s 系统开小差中，请稍后再试"),
    SYS_E_SERVICE_EXCEPTION(40002, "服务端异常"),
    SYS_E_DATABASE_ERROR(40003, "数据库操作异常"),
    SYS_E_REMOTE_CALL_ERROR(40004, "remote call error"),
    ;

    private int code;
    private String comment;

    private ResponseCode(int code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public int getCode() {
        return this.code;
    }

    public String getComment() {
        return this.comment;
    }
}

/* Location:           /work/tmp/trade-common-0.0.1-20150519.033122-8.jar
 * Qualified Name:     com.mockuai.tradecenter.common.constant.ResponseCode
 * JD-Core Version:    0.6.2
 */