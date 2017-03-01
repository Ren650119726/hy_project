package com.mockuai.seckillcenter.core.service.action.seckill;

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
import com.mockuai.seckillcenter.core.component.impl.LoadSeckillCache;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.RedissonManager;
import com.mockuai.seckillcenter.core.service.RequestContext;
import com.mockuai.seckillcenter.core.service.action.BaseAction;
import com.mockuai.seckillcenter.core.util.DateUtils;
import com.mockuai.seckillcenter.core.util.ModelUtil;
import com.mockuai.seckillcenter.core.util.SeckillPreconditions;
import com.mockuai.seckillcenter.core.util.SeckillUtils;
import org.redisson.RedissonClient;
import org.redisson.core.RMapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.SECKILL_KEY;

/**
 * 客户端秒杀轮询
 * <p/>
 * lifecycle 1代表未开始，2代表进行中，3还有机会，4代表已结束， 11去结算
 * <p/>
 * <p/>
 * Created by edgar.zr on 12/15/15.
 */
@Service
public class SeckillPollingAction extends BaseAction {

	@Autowired
	private ComponentHelper componentHelper;
	@Autowired
	private RedissonManager redissonManager;

	@Override
	public SeckillResponse execute(RequestContext context) throws SeckillException {

		Long seckillId = (Long) context.getRequest().getParam("seckillId");
		Long sellerId = (Long) context.getRequest().getParam("sellerId");
		Long skuId = (Long) context.getRequest().getParam("skuId");
		Long userId = (Long) context.getRequest().getParam("userId");
		String bizCode = (String) context.get("bizCode");
		String appKey = (String) context.get("appKey");

		SeckillPreconditions.checkNotNull(seckillId, "seckillId");
		SeckillPreconditions.checkNotNull(sellerId, "sellerId");
		SeckillPreconditions.checkNotNull(skuId, "skuId");

		RedissonClient client = redissonManager.getRedissonClient();

		RMapCache seckillUidMap = client.getMapCache(SECKILL_KEY.getValue());
		SeckillCacheDTO seckillCacheDTO =
				(SeckillCacheDTO) seckillUidMap.get(redissonManager.cacheKey(SECKILL_KEY.getKey(bizCode, sellerId, seckillId)));

		if (seckillCacheDTO == null) {
			LOGGER.info("cache hit failed, cacheKey : {}", redissonManager.cacheKey(SECKILL_KEY.getKey(bizCode, sellerId, seckillId)));
			seckillCacheDTO = componentHelper.execute(LoadSeckillCache.wrapParams(null, skuId, appKey));
//			LOGGER.info("cache hit failed, key of cache : {}, seckillId : {}, sellerId : {}, skuId : {}, userId : {}, bizCode : {}, appKey : {}",
//					SECKILL_KEY.getKey(bizCode, sellerId, seckillId), seckillId, sellerId, skuId, userId, bizCode, appKey);
//			Map<String, Object> data = getData(seckillId, sellerId, skuId, userId, bizCode, appKey);
//			return SeckillUtils.getSuccessResponse(data);
		}

		LOGGER.info("剩余库存："+seckillCacheDTO.getStockNum());
		LOGGER.info("冻结库存："+seckillCacheDTO.getFrozenNum());
		Map<String, Object> result = new HashMap<>();

		SeckillDTO seckillDTO = ModelUtil.genSeckillDTOFromSeckillCacheDTO(seckillCacheDTO);

//		seckillManager.fillUpSeckillDTO(Arrays.asList(seckillDTO));

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

		if (userId != null) {
			componentHelper.execute(LifecycleForUser.wrapParams(seckillDTO, userId, bizCode));
		}

		SeckillForMopDTO seckillForMopDTO = ModelUtil.genSeckillForMopDTO(seckillDTO);
		result.put("timeInterval", "999999");

		if (seckillForMopDTO.getLifecycle().intValue() != 4) {
			result.put("timeInterval", "5000");
		}
		result.put("seckillDTO", seckillForMopDTO);
		result.put("currentTime", DateUtils.getCurrentDate().getTime());

		return SeckillUtils.getSuccessResponse(result);
	}

	@Override
	public String getName() {
		return ActionEnum.SECKILL_POLLING.getActionName();
	}

}