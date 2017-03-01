package com.hanshu.employee.common.constant;

/**
 * Created by yeliming on 16/5/19.
 */
public enum OpObjTypeEnum {
    /**
     * 订单
     */
    ORDER(1, "订单"),
    /**
     * 商品
     */
    ITEM(2, "商品"),
    /**
     * 会员
     */
    USER(3, "会员"),
    /**
     * 角色
     */
    ROLE(4, "角色"),
    /**
     * 提现
     */
    WITHDRAW(5, "提现"),
    /**
     * 管理员
     */
    EMPLOYEE(6, "管理员"),
    /**
     * 基础权限
     */
    PERMISSION(7, "基础权限"),
    /**
     * 卖家等级配置
     */
    SELLCONFIG(8, "卖家等级配置"),
    /**
     * 供应商
     */
    SUPPLIER(9, "供应商"),
    /**
     * 仓库
     */
    STORE(10, "仓库"),
    /**
     * 提现配置
     */
    WITHDRAWCONFIG(11, "提现配置"),
    /**
     * 仓库配置
     */
    STORECONFIG(12, "仓库配置"),
    /**
     * 佣金配置
     */
    COMMISIONCONFIG(13, "佣金配置"),
    /**
     * 退款
     */
    REFUND(14, "退款"),
    /**
     * 佣金
     */
    DISTRIBUTE(15, "佣金"),
    /**
     * 秒杀
     */
    SECKILL(16,"秒杀活动"),
    /**
     * 满减送
     */
    FULL_REDUCTION(17,"满减送"),
    /**
     * 优惠券
     */
    COUPON(18,"优惠券"),
    /**
     * 限时购
     */
    TIME_PURCHASE(19,"限时购"),
    /**
     * 首单立减
     */
    INCOME_REDUCTION(20,"首单立减"),
    /**
     * 注册有礼
     */
    REGISTER_GIFT(21,"注册有礼"),
    /**
     * 邀请有礼
     */
    INVITATION_GIFT(22,"邀请有礼"),
    /**
     * 开店有礼
     */
    SHOP_GIFT(23,"开店有礼"),
    /**
     * 拼团,团购
     */
    GROUP_BUY(24,"团购"),
    
    /**
     * 收益
     */
    GAINS(25,"收益"),
    
    /**
     * 商品参数
     */
    ITEMSPEC(26,"商品参数");
    

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    OpObjTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(Integer code) {
        if (code != null) {
            for (OpObjTypeEnum opObjTypeEnum : OpObjTypeEnum.values()) {
                if (code.equals(opObjTypeEnum.getCode())) {
                    return opObjTypeEnum.getMsg();
                }
            }
        }
        return null;
    }


}
