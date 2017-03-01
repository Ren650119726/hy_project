package com.mockuai.seckillcenter.common.constant;

/**
 * Created by edgar.zr on 1/12/16.
 */
public enum ComponentType {

    STORE_DETAIL_OF_SECKILL_IN_CACHE("storeDetailOfSeckillInCache", "缓存活动详情"),
    LIFECYCLE_OF_SECKILL_FOR_MOP("lifecycleOfSeckillForMop", "封装 mop 活动状态"),
    LIFECYCLE_FOR_USER("lifecycleForUser", "验证用户预单"),
    /**
     * 主要移除 商品详情 和 活动详情
     */
    REMOVE_CACHE_OF_SECKILL("removeCacheOfSeckill", "移除秒杀活动相关数据"),
    LOAD_SECKILL_CACHE("loadSeckillCache", "加载秒杀信息"),
    LOAD_MOP_ITEM_CACHE("loadMopItemCache","加载mop商品xinxi");


    private String code;
    private String intro;

    ComponentType(String code, String intro) {
        this.code = code;
        this.intro = intro;
    }

    public String getCode() {
        return code;
    }

    public String getIntro() {
        return intro;
    }
}