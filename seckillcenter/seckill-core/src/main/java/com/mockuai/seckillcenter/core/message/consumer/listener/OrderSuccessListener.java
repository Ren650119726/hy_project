package com.mockuai.seckillcenter.core.message.consumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.seckillcenter.common.constant.OrderOfUserStatus;
import com.mockuai.seckillcenter.common.constant.RMQMessageType;
import com.mockuai.seckillcenter.common.domain.dto.SeckillCacheDTO;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.component.ComponentHelper;
import com.mockuai.seckillcenter.core.component.impl.LoadSeckillCache;
import com.mockuai.seckillcenter.core.domain.SeckillDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.RedissonManager;
import com.mockuai.seckillcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.core.message.consumer.BaseListener;
import com.mockuai.seckillcenter.core.util.DateUtils;
import com.mockuai.seckillcenter.core.util.SeckillUtils;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import org.redisson.RedissonClient;
import org.redisson.core.RMapCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.USER_KEY;

/**
 * 订单支付成功,更新活动信息,商品信息,用户参与信息
 * <p/>
 * Created by edgar.zr on 6/22/2016.
 */
@Component
public class OrderSuccessListener extends BaseListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSuccessListener.class);

	@Autowired
	private RedissonManager redissonManager;
	@Autowired
	private SeckillManager seckillManager;
	@Autowired
	private ComponentHelper componentHelper;

	@Override
	public void init() {

	}

	@Override
	public String getName() {
		return RMQMessageType.TRADE_PAY_SUCCESS_NOTIFY.combine();
	}

	@Override
	public void consumeMessage(JSONObject msg, String appKey) throws SeckillException {
		LOGGER.info("{}, appKey : {}", getName(), appKey);

		try {

			Long userId = msg.getLong("userId");
			Long orderId = msg.getLong("id");

			OrderItemDTO orderItemDTO = filterOrder(orderId, userId, appKey);

			if (orderItemDTO == null) { // 非秒杀订单
				return;
			}

			SeckillDO seckillDO = new SeckillDO();
			seckillDO.setSkuId(orderItemDTO.getItemSkuId());
			SeckillDTO seckillDTO = seckillManager.getSeckill(seckillDO);

			if (seckillDTO == null) {
				LOGGER.error("cannot get seckillDTO with skuId, orderId : {}, skuId : {}, userId : {}"
						, orderId, orderItemDTO.getItemSkuId(), userId);
				return;
			}

			String bizCode = seckillDTO.getBizCode();
			Long sellerId = seckillDTO.getSellerId();
			Long seckillId = seckillDTO.getId();

			RedissonClient client = redissonManager.getRedissonClient();

//			RMapCache seckillUidMap = client.getMapCache(SECKILL_KEY.getValue()); // 更新活动详情
//			SeckillCacheDTO seckillCacheDTO = (SeckillCacheDTO) seckillUidMap.get(
//					redissonManager.cacheKey(SECKILL_KEY.getKey(bizCode, sellerId, seckillId)));
//
//			 更新活动状态
//			if (seckillCacheDTO == null) {

//			}

			// 强制更新
			SeckillCacheDTO seckillCacheDTO = componentHelper.execute(LoadSeckillCache.wrapParams(seckillDTO.getItemId(), null, appKey));

			if (seckillCacheDTO.getStockNum().longValue() == 0 && seckillCacheDTO.getFrozenNum().longValue() == 0) {
				SeckillDO toUpdate = new SeckillDO();
				toUpdate.setId(seckillId);
				toUpdate.setItemInvalidTime(new Date());
				int optNum = seckillManager.updateSeckill(toUpdate);
				if (optNum != 1) {
					LOGGER.error("error to update itemInvalidTime, seckillId : {}", seckillId);
				}
			}

			Date now = DateUtils.getCurrentDate();
			long expireTime = SeckillUtils.expireTime(seckillDTO.getEndTime(), now);

			RMapCache seckillUserMap = client.getMapCache(USER_KEY.getValue()); // 更新用户参与数据

			// 更新用户参与记录
			seckillUserMap.fastPut(redissonManager.cacheKey(USER_KEY.getKey(bizCode, sellerId, seckillId, userId))
					, OrderOfUserStatus.PAID_ORDER.getValue(), expireTime, TimeUnit.MILLISECONDS);

		} catch (SeckillException e) {
			LOGGER.error("", e);
		} catch (Exception e) {
			LOGGER.error("orderSuccess", e);
		}
	}

	@Override
	public Logger getLogger() {
		return LOGGER;
	}
}