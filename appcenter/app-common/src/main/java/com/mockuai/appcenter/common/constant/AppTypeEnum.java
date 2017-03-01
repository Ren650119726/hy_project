package com.mockuai.appcenter.common.constant;

/**
 * Created by zengzhangqiang on 6/28/15.
 */
public enum AppTypeEnum {
    /**
     * ios应用
     */
    APP_IOS(1, "ios客户端"),
    /**
     * android应用
     */
    APP_ANDROID(2, "android客户端"),
    /**
     * wap应用
     */
    APP_WAP(3, "h5站点"),
    /**
     * pc商城
     */
    APP_PC_MALL(4, "pc商城"),
    /**
     * 店铺管理系统
     */
    APP_SELLER_MANAGER(11, "店铺管理后台"),
    /**
     * boss管理系统（一站式商城管理系统）
     */
    APP_BOSS_MANAGER(12, "一站式商城管理后台"),
    /**
     * 商城管理系统
     */
    APP_MALL_MANAGER(13, "商城管理后台"),

    /**
     * 对接中心
     */
    APP_RAINBOW(14, "内部对接系统"),

    /**
     * 外部对接系统
     */
    APP_OUT_SYSTEM(101, "外部系统");

    /**
     * 应用类型值
     */
    private int value;
    /**
     * 与类型匹配的应用名称后缀
     */
    private String nameSuffix;

    private AppTypeEnum(int value, String nameSuffix){
        this.value = value;
        this.nameSuffix = nameSuffix;
    }

    public int getValue() {
        return value;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public static AppTypeEnum getAppType(Integer appTypeValue){
        if(appTypeValue == null){
            return null;
        }

        for(AppTypeEnum appTypeEnum: AppTypeEnum.values()){
            if(appTypeEnum.getValue() == appTypeValue){
                return appTypeEnum;
            }
        }

        return null;
    }
}
