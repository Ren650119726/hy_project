package com.mockuai.seckillcenter.core.service.action.seckill;

import com.mockuai.distributioncenter.common.domain.dto.DistShopForMopDTO;
import com.mockuai.itemcenter.common.domain.mop.MopItemDTO;
import com.mockuai.marketingcenter.common.constant.ActivityLifecycle;
import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.seckillcenter.common.api.SeckillResponse;
import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.common.constant.SeckillStatus;
import com.mockuai.seckillcenter.common.domain.dto.SeckillCacheDTO;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.common.domain.dto.SeckillForMopDTO;
import com.mockuai.seckillcenter.core.component.ComponentHelper;
import com.mockuai.seckillcenter.core.component.impl.LifecycleForUser;
import com.mockuai.seckillcenter.core.component.impl.LoadMopItemCache;
import com.mockuai.seckillcenter.core.component.impl.LoadSeckillCache;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.DistributorManager;
import com.mockuai.seckillcenter.core.manager.RedissonManager;
import com.mockuai.seckillcenter.core.service.RequestContext;
import com.mockuai.seckillcenter.core.service.action.BaseAction;
import com.mockuai.seckillcenter.core.util.DateUtils;
import com.mockuai.seckillcenter.core.util.ModelUtil;
import com.mockuai.seckillcenter.core.util.SeckillPreconditions;
import org.redisson.RedissonClient;
import org.redisson.core.RMapCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.*;

/**
 * 秒杀详情接口
 * <p/>
 * Created by edgar.zr on 12/14/15.
 */
@Service
public class DetailOfSeckillByItemAction extends BaseAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetailOfSeckillByItemAction.class);

	@Autowired
	private RedissonManager redissonManager;
	@Autowired
	private ComponentHelper componentHelper;
	@Autowired
	private DistributorManager distributorManager;

	@Override
	public SeckillResponse execute(RequestContext context) throws SeckillException {
		Long itemId = (Long) context.getRequest().getParam("itemId");
		Long distributorId = (Long) context.getRequest().getParam("distributorId");
		Long sellerId = (Long) context.getRequest().getParam("sellerId");
		Long userId = (Long) context.getRequest().getParam("userId");
		String bizCode = (String) context.get("bizCode");
		String appKey = (String) context.get("appKey");

		SeckillPreconditions.checkNotNull(itemId, "itemId");
		SeckillPreconditions.checkNotNull(distributorId, "distributorId");

		RedissonClient client = redissonManager.getRedissonClient();

		long begin = System.nanoTime();
		// 获取分销商
		RMapCache distributorMap = client.getMapCache(DISTRIBUTOR_KEY.getValue());
		DistShopForMopDTO distShopForMopDTO =
				(DistShopForMopDTO) distributorMap.get(redissonManager.cacheKey(DISTRIBUTOR_KEY.getKey(distributorId)));
		if (distShopForMopDTO == null) {
			LOGGER.debug("cache hit failed, distShop : {}", distributorId);
			distShopForMopDTO = distributorManager.getShopForMopBySellerId(distributorId, appKey);
			distributorMap.fastPutIfAbsent(redissonManager.cacheKey(DISTRIBUTOR_KEY.getKey(distributorId)), distShopForMopDTO);
		}
		long end = System.nanoTime();
		LOGGER.debug("{}:{}", "getDistributor", (end - begin) / 1000000);

		begin = System.nanoTime();
		// 获取商品
		RMapCache itemUidMap = client.getMapCache(ITEM_KEY.getValue());
		MopItemDTO mopItemDTO = (MopItemDTO) itemUidMap.get(redissonManager.cacheKey(ITEM_KEY.getKey(bizCode, sellerId, itemId)));
		if (mopItemDTO == null) {
			LOGGER.debug("cache hit failed, key of cache : {}, itemId : {}, sellerId : {}, userId : {}, bizCode : {}, appKey : {}",
					ITEM_KEY.getKey(bizCode, sellerId, itemId), itemId, sellerId, userId, bizCode, appKey);
//			mopItemDTO = getMopItem(itemId, sellerId, userId, distributorId, bizCode, appKey, client);
			mopItemDTO =
					componentHelper.execute(LoadMopItemCache.wrapParams(itemId, sellerId, userId, distributorId, bizCode, appKey));
		}
		end = System.nanoTime();
		LOGGER.debug("{}:{}", "getItem", (end - begin) / 1000000);

		// 绑定经销商
		mopItemDTO.setDistributorInfo(distShopForMopDTO);

		begin = System.nanoTime();
		// 获取活动信息
		RMapCache seckillUidMap = client.getMapCache(SECKILL_KEY.getValue());
		SeckillCacheDTO seckillCacheDTO = (SeckillCacheDTO) seckillUidMap.get(mopItemDTO.getItemExtraInfo());

		if (seckillCacheDTO == null) {
			LOGGER.debug("cache hit failed, key of cache : {}, itemId : {}, sellerId : {}, userId : {}, bizCode : {}, appKey : {}",
					mopItemDTO.getItemExtraInfo(), itemId, sellerId, userId, bizCode, appKey);
//			seckillCacheDTO = getSeckillCache(itemId, appKey, client);
			seckillCacheDTO = componentHelper.execute(LoadSeckillCache.wrapParams(itemId, null, appKey));
		}
		end = System.nanoTime();
		LOGGER.debug("{}:{}", "getSeckillCache", (end - begin) / 1000000);

		begin = System.nanoTime();
		SeckillDTO seckillDTO = ModelUtil.genSeckillDTOFromSeckillCacheDTO(seckillCacheDTO);

		// 活动状态
		long currentTime = System.currentTimeMillis();
		if (currentTime < seckillDTO.getStartTime().getTime()) {
			seckillDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_NOT_BEGIN.getValue());
		} else if (currentTime >= seckillDTO.getStartTime().getTime()
				           && currentTime <= seckillDTO.getEndTime().getTime()) {
			seckillDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_IN_PROGRESS.getValue());
		} else {
			seckillDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_ENDED.getValue());
		}
		if (seckillDTO.getStatus().intValue() == ActivityStatus.INVALID.getValue().intValue()
				    || seckillDTO.getItemInvalidTime() != null) {
			seckillDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_ENDED.getValue());
		}

		seckillDTO.setLifecycle(seckillDTO.getLifecycle().intValue() == 3 ? 4 : seckillDTO.getLifecycle());

		if (seckillDTO.getLifecycle().intValue() == 2) {
			if (seckillCacheDTO.getStockNum().longValue() == 0) {
				seckillDTO.setLifecycle(4);
			} else if (seckillCacheDTO.getStockNum().longValue() == seckillCacheDTO.getFrozenNum().longValue()) {
				seckillDTO.setLifecycle(3);
			}
		}
		if (seckillDTO.getStatus().intValue() == SeckillStatus.INVALID.getValue()) seckillDTO.setLifecycle(4);

		//查询用户是否有预单存在
		if (userId != null) {
			componentHelper.execute(LifecycleForUser.wrapParams(seckillDTO, userId, bizCode));
		}

		SeckillForMopDTO seckillForMopDTO = ModelUtil.genSeckillForMopDTO(seckillDTO);
		mopItemDTO.setItemExtraInfo(seckillForMopDTO);

		Map<String, Object> data = new HashMap<>();
		data.put("item", mopItemDTO);
		data.put("currentTime", DateUtils.getCurrentDate().getTime());
		end = System.nanoTime();
		LOGGER.debug("{}:{}", "end", (end - begin) / 1000000);
		return new SeckillResponse(data);
	}

	@Override
	public String getName() {
		return ActionEnum.DETAIL_OF_SECKILL_BY_ITEM.getActionName();
	}
}