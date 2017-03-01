package com.mockuai.seckillcenter.core.service.action.seckill;

import com.mockuai.seckillcenter.common.api.SeckillResponse;
import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.common.constant.OrderOfUserStatus;
import com.mockuai.seckillcenter.common.constant.ResponseCode;
import com.mockuai.seckillcenter.common.constant.SeckillStatus;
import com.mockuai.seckillcenter.common.domain.dto.SeckillCacheDTO;
import com.mockuai.seckillcenter.core.component.ComponentHelper;
import com.mockuai.seckillcenter.core.component.impl.LoadSeckillCache;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.RedissonManager;
import com.mockuai.seckillcenter.core.manager.TradeManager;
import com.mockuai.seckillcenter.core.service.RequestContext;
import com.mockuai.seckillcenter.core.service.action.BaseAction;
import com.mockuai.seckillcenter.core.util.DateUtils;
import com.mockuai.seckillcenter.core.util.SeckillPreconditions;
import com.mockuai.seckillcenter.core.util.SeckillUtils;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import org.redisson.RedissonClient;
import org.redisson.core.RLock;
import org.redisson.core.RMapCache;
import org.redisson.core.RSemaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.*;

/**
 * 取得秒杀名额
 * 拦截不合法/超出指定秒杀人数的用户，直接返回，不予处理
 * 为过滤后台合法用户创建预单
 * <p/>
 * Created by edgar.zr on 12/15/15.
 */
@Service
public class ApplySeckillAction extends BaseAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplySeckillAction.class);
	//	@Autowired
//	private SeckillManager seckillManager;
	@Autowired
	private TradeManager tradeManager;
	//	@Autowired
//	private ItemManager itemManager;
	@Autowired
	private ComponentHelper componentHelper;
	@Autowired
	private RedissonManager redissonManager;

	@Override
	public SeckillResponse execute(RequestContext context) throws SeckillException {
		LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@A@@@@@@@@@@@@@@@@@@@");
		Long seckillId = (Long) context.getRequest().getParam("seckillId");
		Long sellerId = (Long) context.getRequest().getParam("sellerId");
		Long distributorId = (Long) context.getRequest().getParam("distributorId");
		Long skuId = (Long) context.getRequest().getParam("skuId");
		Long userId = (Long) context.getRequest().getParam("userId");

		String bizCode = (String) context.get("bizCode");
		String appKey = (String) context.get("appKey");

		SeckillPreconditions.checkNotNull(seckillId, "seckillId");
		SeckillPreconditions.checkNotNull(sellerId, "sellerId");
		SeckillPreconditions.checkNotNull(distributorId, "distributorId");
		SeckillPreconditions.checkNotNull(skuId, "skuId");
		SeckillPreconditions.checkNotNull(userId, "userId");
		LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@B@@@@@@@@@@@@@@@@@@@");
		RedissonClient client = redissonManager.getRedissonClient();

		// 获取活动参与人数  拦截大量不同 id 的用户
//		RAtomicLong seckillCount = client.getAtomicLong(
//				redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, sellerId, seckillId)));
//		RLock lock = client.getLock(redissonManager.cacheKey(SECKILL_COUNT_LOCK.getKey(seckillId)));
//		if (seckillCount.get() <= 0) { // 目前暂没有秒杀名额
//			throw new SeckillException(ResponseCode.BIZ_E_SECKILL_STILL_HAVE_CHANCE);
//		}
		LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@C@@@@@@@@@@@@@@@@@@@");
		RSemaphore seckillCount = client.getSemaphore(redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, sellerId, seckillId)));
		LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@D@@@@@@@@@@@@@@@@@@@");
		if (!seckillCount.tryAcquire()) {
			LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@222222222222222222222222222222222@@@@@@@@@@@@@@@@@@@");
			throw new SeckillException(ResponseCode.BIZ_E_SECKILL_STILL_HAVE_CHANCE);
		}

//		try {
//			if (lock.tryLock(100L, 1000L, TimeUnit.MILLISECONDS)) { // 资源级锁,针对所有关联 秒杀人数 的操作加上锁
//				if (seckillCount.get() <= 0) {
//					throw new SeckillException(ResponseCode.BIZ_E_SECKILL_STILL_HAVE_CHANCE);
//				}
//				seckillCount.decrementAndGet(); // 暂时给个名额,后续如果下预单失败或者预单失效或者订单取消,名额都会返回
//				lock.unlockAsync();
//			}
//		} catch (InterruptedException e) {
//			LOGGER.error("error to get cache of seckillCount, key : {}",
//					redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, sellerId, seckillId)), e);
//		}

/**
 * 以下均是有机会秒杀的用户
 *
 */
		LOGGER.info("apply seckill, userId : {}, seckillId : {}, skuId : {}", userId, seckillId, skuId);
		applyForSeckill(sellerId, distributorId, seckillId, userId, skuId, bizCode, appKey, client);

		return SeckillUtils.getSuccessResponse();
	}

	/**
	 * 执行预单请求
	 *
	 * @param sellerId
	 * @param distributorId
	 * @param seckillId
	 * @param userId
	 * @param skuId
	 * @param bizCode
	 * @param appKey
	 * @throws SeckillException
	 */
	public void applyForSeckill(Long sellerId
			                           , Long distributorId
			                           , Long seckillId
			                           , Long userId
			                           , Long skuId
			                           , String bizCode
			                           , String appKey
			                           , RedissonClient client) throws SeckillException {

		// 获取活动参与人数
//		RAtomicLong seckillCount = client.getAtomicLong(redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, sellerId, seckillId)));

		// 获取活动信息
		// 活动时间
		LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@E@@@@@@@@@@@@@@@@@@@");
		RMapCache seckillUidMap = client.getMapCache(SECKILL_KEY.getValue());
//		RLock lock = client.getLock(redissonManager.cacheKey(SECKILL_LOCK.getKey(seckillId))); // 活动锁,保证在进行中,不会受到活动失效的影响
//		Boolean getLock = false;
		try {
//			if ((getLock = lock.tryLock(100l, 1000L, TimeUnit.MILLISECONDS))) {
//			if (lock.tryLock(100l, 1000L, TimeUnit.MILLISECONDS)) {
			SeckillCacheDTO seckillCacheDTO = (SeckillCacheDTO) seckillUidMap.get(
					redissonManager.cacheKey(SECKILL_KEY.getKey(bizCode, sellerId, seckillId)));

			if (seckillCacheDTO == null) {
				//ZSX扣库存处
				seckillCacheDTO = componentHelper.execute(LoadSeckillCache.wrapParams(null, skuId, appKey));
			}
			Date now = DateUtils.getCurrentDate();
			if (now.before(seckillCacheDTO.getStartTime())) {
				LOGGER.error("the seckill is not starting, seckillId : {}, userId : {}, bizCode : {}, appKey : {}"
						, seckillId
						, userId
						, bizCode
						, appKey);
				throw new SeckillException(ResponseCode.BIZ_E_SECKILL_NOT_START);
			}

			if (now.after(seckillCacheDTO.getEndTime())) {
				LOGGER.error("the seckill is ending, seckillId : {}, userId : {}, bizCode : {}, appKey : {}"
						, seckillId
						, userId
						, bizCode
						, appKey);
				throw new SeckillException(ResponseCode.BIZ_E_SECKILL_ENDED);
			}
			if (seckillCacheDTO.getStatus().intValue() == SeckillStatus.INVALID.getValue()) { // 活动失效
				LOGGER.error("the seckill is invalid, seckillId : {}, userId : {}, bizCode : {}, appKey : {}",
						seckillId, userId, bizCode, appKey);
				throw new SeckillException(ResponseCode.BIZ_E_SECKILL_ENDED);
			}

			// 添加预单
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setUserId(userId);
			orderDTO.setSellerId(sellerId);

			orderDTO.setOrderItems(new ArrayList<OrderItemDTO>());
			orderDTO.getOrderItems().add(new OrderItemDTO());
			orderDTO.getOrderItems().get(0).setSellerId(sellerId);
			orderDTO.getOrderItems().get(0).setItemSkuId(skuId);
			orderDTO.getOrderItems().get(0).setNumber(1);
			orderDTO.getOrderItems().get(0).setDistributorId(distributorId);
			orderDTO.setActivityId(seckillId);
			orderDTO.setTimeoutCancelSeconds(900L);
//			orderDTO.setTimeoutCancelSeconds(60L);
//			orderDTO.setTimeoutCancelSeconds(30L);

			RLock lock = client.getLock(redissonManager.cacheKey(APPLY_SECKILL_LOCK.getKey(seckillId)));

			if (lock.tryLock(300L, 500L, TimeUnit.MILLISECONDS)) { // 不让同一个用户多次请求

				// 判断用户参与情况
				RMapCache seckillUserMap = client.getMapCache(USER_KEY.getValue());
				Integer status = (Integer) seckillUserMap.get(
						redissonManager.cacheKey(USER_KEY.getKey(bizCode, sellerId, seckillId, userId)));
				if (status != null) { // 已经存在参与记录
					LOGGER.error("the record of apply already exists, userKey : {}, seckillId : {}, sellerId : {}, skuId : {}, userId : {}"
							, redissonManager.cacheKey(USER_KEY.getKey(bizCode, sellerId, seckillId, userId))
							, seckillId
							, sellerId
							, skuId
							, userId);
					RSemaphore seckillCount = client.getSemaphore(redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, sellerId, seckillId)));
					seckillCount.release();
					throw new SeckillException(ResponseCode.BIZ_E_ALREADY_PARTICIPATE_IN);
				}

				tradeManager.addPreOrder(orderDTO, appKey);

				long expireTime = SeckillUtils.expireTime(seckillCacheDTO.getEndTime(), now);
				Boolean putStatus = seckillUserMap.fastPut(redissonManager.cacheKey(USER_KEY.getKey(bizCode, sellerId, seckillId, userId))
						, OrderOfUserStatus.PRE_ORDER.getValue()
						, expireTime, TimeUnit.MILLISECONDS); // 确认预单状态

				LOGGER.info("add preOrder , userCacheKey : {}, status : {}"
						, redissonManager.cacheKey(USER_KEY.getKey(bizCode, sellerId, seckillId, userId))
						, putStatus);

				// 强制更新缓存
				componentHelper.execute(LoadSeckillCache.wrapParams(null, skuId, appKey));

				lock.unlockAsync();
			} else {
				RSemaphore seckillCount = client.getSemaphore(redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, sellerId, seckillId)));
				seckillCount.release();
				throw new SeckillException(ResponseCode.BIZ_E_SECKILL_TRY_AGAIN);
			}
		} catch (InterruptedException e) {
			LOGGER.error("error to update the cache value, seckillLock : {}, seckillId : {}",
					redissonManager.cacheKey(SECKILL_LOCK.getKey(seckillId)), seckillId, e);
			RSemaphore seckillCount = client.getSemaphore(redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, sellerId, seckillId)));
			seckillCount.release();
			LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@33333333333333@@@@@@@@@@@@@@@@@@@");
			throw new SeckillException(ResponseCode.BIZ_E_SECKILL_STILL_HAVE_CHANCE);
		} catch (SeckillException e) {
			RSemaphore seckillCount = client.getSemaphore(redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, sellerId, seckillId)));
			seckillCount.release();
			throw e;
		} catch (Exception e) {
			LOGGER.error("error to add pre order, sellerId : {}, seckillId : {}, skuId : {}, userId : {}, bizCode : {}, appKey : {} "
					, sellerId, seckillId, skuId, userId, bizCode, appKey, e);
			RSemaphore seckillCount = client.getSemaphore(redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, sellerId, seckillId)));
			seckillCount.release();
			LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@444444444444444@@@@@@@@@@@@@@@@@@@");
			throw new SeckillException(ResponseCode.BIZ_E_SECKILL_STILL_HAVE_CHANCE);
		}
	}

	@Override
	public String getName() {
		return ActionEnum.APPLY_SECKILL.getActionName();
	}
}