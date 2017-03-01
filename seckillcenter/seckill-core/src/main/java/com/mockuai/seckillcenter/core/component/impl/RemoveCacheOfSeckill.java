//package com.mockuai.seckillcenter.core.component.impl;
//
//import com.mockuai.seckillcenter.common.constant.CacheKeyEnum;
//import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
//import com.mockuai.seckillcenter.core.component.Component;
//import com.mockuai.seckillcenter.core.domain.SeckillDO;
//import com.mockuai.seckillcenter.core.exception.SeckillException;
//import com.mockuai.seckillcenter.core.util.Context;
//import org.redisson.RedissonClient;
//import org.redisson.core.RMapCache;
//
//import static com.mockuai.seckillcenter.common.constant.ComponentType.STORE_DETAIL_OF_SECKILL_IN_CACHE;
//
///**
// * 移除 商品详情 和 活动详情
// * <p/>
// * Created by edgar.zr on 6/15/2016.
// */
//@org.springframework.stereotype.Component
//public class RemoveCacheOfSeckill implements Component {
//
//    public static Context wrapParams(Long itemId, Long itemSellerId, Long seckillId, Long sellerId, String appKey) {
//
//        Context context = new Context();
//        context.setParam("itemId", itemId);
//        context.setParam("itemSellerId", itemSellerId);
//        context.setParam("seckillId", seckillId);
//        context.setParam("sellerId", sellerId);
//        context.setParam("appKey", appKey);
//        context.setParam("component", STORE_DETAIL_OF_SECKILL_IN_CACHE);
//        return context;
//    }
//
//    @Override
//    public void init() {
//
//    }
//
//    @Override
//    public <T> T execute(Context context) throws SeckillException {
//
//        /**
//         * 移除失效活动的缓存数据
//         *
//         * @param seckillId
//         * @throws SeckillException
//         */
//        public void updateCache(Long seckillId) throws SeckillException {
//
//            SeckillDO seckillDO = new SeckillDO();
//            seckillDO.setId(seckillId);
//            seckillDO.setBizCode();
//            SeckillDTO seckillDTO = seckillManager.getSeckill(seckillDO);
//
//            RedissonClient client = redissonManager.getRedissonClient();
//            RMapCache seckillIdUidMap = client.getMapCache(CacheKeyEnum.SECKILL_KEY.getValue());
//            String seckillUid = seckillDTO.getSellerId() + "_" + seckillDTO.getId();
//            long numOfseckill = seckillIdUidMap.fastRemove(seckillUid);
//
//            RMapCache itemUidMap = client.getMapCache(CacheKeyEnum.ITEM_KEY.getValue());
//            String itemUid = seckillDTO.getItemSellerId() + "_" + seckillDTO.getItemId();
//            long numOfItem = itemUidMap.fastRemove(itemUid);
//            LOGGER.info("remove cache of seckill, seckillUid : {}, value : {}, itemUid : {}, value : {}",
//                    seckillUid, numOfseckill, itemUid, numOfItem);
//        }
//
//        return null;
//    }
//
//    @Override
//    public String getComponentCode() {
//        return null;
//    }
//}
