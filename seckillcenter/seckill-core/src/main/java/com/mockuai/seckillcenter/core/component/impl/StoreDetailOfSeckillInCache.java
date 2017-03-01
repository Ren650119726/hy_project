package com.mockuai.seckillcenter.core.component.impl;

import com.mockuai.itemcenter.common.domain.mop.MopItemDTO;
import com.mockuai.seckillcenter.common.domain.dto.SeckillCacheDTO;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.component.Component;
import com.mockuai.seckillcenter.core.domain.SeckillDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.ItemManager;
import com.mockuai.seckillcenter.core.manager.RedissonManager;
import com.mockuai.seckillcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.core.util.Context;
import com.mockuai.seckillcenter.core.util.JsonUtil;
import com.mockuai.seckillcenter.core.util.SeckillUtils;
import org.redisson.RedissonClient;
import org.redisson.core.RMapCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.ITEM_KEY;
import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.SECKILL_KEY;
import static com.mockuai.seckillcenter.common.constant.ComponentType.STORE_DETAIL_OF_SECKILL_IN_CACHE;

/**
 * 创建商品缓存
 * 创建活动信息缓存
 * 使用商品详情时,只需要更新商品中库存
 * <p/>
 * Created by edgar.zr on 6/15/2016.
 */
@org.springframework.stereotype.Component
public class StoreDetailOfSeckillInCache implements Component {

	private static final Logger LOGGER = LoggerFactory.getLogger(StoreDetailOfSeckillInCache.class);

	@Autowired
	private ItemManager itemManager;
	@Autowired
	private RedissonManager redissonManager;
	@Autowired
	private SeckillManager seckillManager;

	public static Context wrapParams(String bizCode, Long itemId, Long itemSellerId, Long seckillId, Long sellerId, String appKey) {

		Context context = new Context();
		context.setParam("itemId", itemId);
		context.setParam("bizCode", bizCode);
		context.setParam("itemSellerId", itemSellerId);
		context.setParam("seckillId", seckillId);
		context.setParam("sellerId", sellerId);
		context.setParam("appKey", appKey);
		context.setParam("component", STORE_DETAIL_OF_SECKILL_IN_CACHE);
		return context;
	}

	@Override
	public void init() {

	}

	@Override
	public <T> T execute(Context context) throws SeckillException {

		Long itemId = (Long) context.getParam("itemId");
		Long itemSellerId = (Long) context.getParam("itemSellerId");
		Long seckillId = (Long) context.getParam("seckillId");
		Long sellerId = (Long) context.getParam("sellerId");
		String bizCode = (String) context.getParam("bizCode");
		String appKey = (String) context.getParam("appKey");

		MopItemDTO mopItemDTO = itemManager.getMopItem(itemId, itemSellerId, true, appKey);

		RedissonClient client = redissonManager.getRedissonClient();

		SeckillCacheDTO seckillCacheDTO = new SeckillCacheDTO();
		SeckillDO seckillDO = new SeckillDO();
		seckillDO.setId(seckillId);

		SeckillDTO seckillDTO = seckillManager.getSeckill(seckillDO);
		itemManager.fillUpItem(Arrays.asList(seckillDTO), appKey);

		// 将活动信息加入缓存
		seckillCacheDTO.setSeckillId(seckillDTO.getId());
		seckillCacheDTO.setSellerId(seckillDTO.getSellerId());
		seckillCacheDTO.setItemId(seckillDTO.getItemId());
		seckillCacheDTO.setSkuId(seckillDTO.getSkuId());
		seckillCacheDTO.setStartTime(seckillDTO.getStartTime());
		seckillCacheDTO.setEndTime(seckillDTO.getEndTime());
		seckillCacheDTO.setStatus(seckillDTO.getStatus());
		seckillCacheDTO.setOriginStockNum(seckillDTO.getStockNum());
		seckillCacheDTO.setStockNum(seckillDTO.getStockNum());
		seckillCacheDTO.setFrozenNum(seckillDTO.getFrozenNum());
		// 秒杀透传数据,对应缓存键值
		mopItemDTO.setItemExtraInfo(redissonManager.cacheKey(SECKILL_KEY.getKey(bizCode, sellerId, seckillId)));

		RMapCache itemUidMap = client.getMapCache(ITEM_KEY.getValue()); // 缓存商品详情
		itemUidMap.fastPut(redissonManager.cacheKey(ITEM_KEY.getKey(bizCode, itemSellerId, seckillDTO.getItemId()))
				, mopItemDTO, SeckillUtils.expireTime(), TimeUnit.MILLISECONDS); // 当活动时间结束时,此缓存自动失效,同时也不会再次加入到缓存中

		LOGGER.info("store item, key : {} cacheValue : {}", redissonManager.cacheKey(ITEM_KEY.getKey(bizCode, itemSellerId, seckillDTO.getItemId())), JsonUtil.toJson(mopItemDTO));

		RMapCache seckillUidMap = client.getMapCache(SECKILL_KEY.getValue()); // 缓存秒杀活动详情
		seckillUidMap.fastPut(redissonManager.cacheKey(SECKILL_KEY.getKey(bizCode, sellerId, seckillId))
				, seckillCacheDTO, SeckillUtils.expireTime(), TimeUnit.MILLISECONDS); // 同上

		LOGGER.info("store seckill key : {}, cacheValue : {}"
				, redissonManager.cacheKey(SECKILL_KEY.getKey(bizCode, sellerId, seckillId))
				, JsonUtil.toJson(seckillUidMap.get(redissonManager.cacheKey(SECKILL_KEY.getKey(bizCode, sellerId, seckillId)))));

		return null;
	}

	@Override
	public String getComponentCode() {
		return STORE_DETAIL_OF_SECKILL_IN_CACHE.getCode();
	}
}