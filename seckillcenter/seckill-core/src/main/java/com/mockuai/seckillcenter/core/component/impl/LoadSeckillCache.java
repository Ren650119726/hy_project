package com.mockuai.seckillcenter.core.component.impl;

import com.mockuai.seckillcenter.common.constant.ComponentType;
import com.mockuai.seckillcenter.common.domain.dto.SeckillCacheDTO;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.component.Component;
import com.mockuai.seckillcenter.core.domain.SeckillDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.ItemManager;
import com.mockuai.seckillcenter.core.manager.RedissonManager;
import com.mockuai.seckillcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.core.util.Context;
import com.mockuai.seckillcenter.core.util.SeckillUtils;
import org.redisson.RedissonClient;
import org.redisson.core.RMapCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.SECKILL_KEY;
import static com.mockuai.seckillcenter.common.constant.ComponentType.LOAD_SECKILL_CACHE;

/**
 * 加载秒杀活动数据
 * Created by edgar.zr on 8/05/2016.
 */
@org.springframework.stereotype.Component
public class LoadSeckillCache implements Component {

	public static final Logger LOGGER = LoggerFactory.getLogger(LoadSeckillCache.class);

	@Autowired
	private SeckillManager seckillManager;
	@Autowired
	private ItemManager itemManager;
	@Autowired
	private RedissonManager redissonManager;

	public static Context wrapParams(Long itemId, Long skuId, String appKey) {

		Context context = new Context();
		context.setParam("itemId", itemId);
		context.setParam("skuId", skuId);
		context.setParam("appKey", appKey);
		context.setParam("component", LOAD_SECKILL_CACHE);
		return context;
	}

	@Override
	public void init() {

	}

	@Override
	public SeckillCacheDTO execute(Context context) throws SeckillException {

		Long itemId = (Long) context.getParam("itemId");
		Long skuId = (Long) context.getParam("skuId");
		String appKey = (String) context.getParam("appKey");

		RedissonClient client = redissonManager.getRedissonClient();

		SeckillDO getSeckill = new SeckillDO();
		if (itemId != null) {
			getSeckill.setItemId(itemId);
		} else {
			getSeckill.setSkuId(skuId);
		}
		SeckillDTO seckillDTO = seckillManager.getSeckill(getSeckill);

		seckillManager.fillUpSeckillDTO(Arrays.asList(seckillDTO));
		itemManager.fillUpItem(Arrays.asList(seckillDTO), appKey);

		SeckillCacheDTO seckillCacheDTO = new SeckillCacheDTO();

		// 将活动信息加入缓存
		seckillCacheDTO.setSeckillId(seckillDTO.getId());
		seckillCacheDTO.setSellerId(seckillDTO.getSellerId());
		seckillCacheDTO.setItemId(seckillDTO.getItemId());
		seckillCacheDTO.setSkuId(seckillDTO.getSkuId());
		seckillCacheDTO.setStartTime(seckillDTO.getStartTime());
		seckillCacheDTO.setEndTime(seckillDTO.getEndTime());
		seckillCacheDTO.setStatus(seckillDTO.getStatus());
		seckillCacheDTO.setOriginStockNum(seckillDTO.getStockNum());
		seckillCacheDTO.setStockNum(seckillDTO.getCurrentStockNum());
		seckillCacheDTO.setFrozenNum(seckillDTO.getFrozenNum());

		RMapCache seckillUidMap = client.getMapCache(SECKILL_KEY.getValue()); // 缓存秒杀活动详情
		seckillUidMap.fastPut(
				redissonManager.cacheKey(SECKILL_KEY.getKey(seckillDTO.getBizCode(), seckillDTO.getSellerId(), seckillDTO.getId()))
				, seckillCacheDTO, SeckillUtils.expireTime(), TimeUnit.MILLISECONDS);
		return seckillCacheDTO;
	}

	@Override
	public String getComponentCode() {
		return ComponentType.LOAD_SECKILL_CACHE.getCode();
	}
}