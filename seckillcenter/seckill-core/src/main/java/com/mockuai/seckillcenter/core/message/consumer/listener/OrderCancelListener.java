package com.mockuai.seckillcenter.core.message.consumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.seckillcenter.common.constant.RMQMessageType;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.component.ComponentHelper;
import com.mockuai.seckillcenter.core.component.impl.LoadSeckillCache;
import com.mockuai.seckillcenter.core.domain.SeckillDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.RedissonManager;
import com.mockuai.seckillcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.core.message.consumer.BaseListener;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import org.redisson.RedissonClient;
import org.redisson.core.RMapCache;
import org.redisson.core.RSemaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.SECKILL_COUNT;
import static com.mockuai.seckillcenter.common.constant.CacheKeyEnum.USER_KEY;

/**
 * 订单取消(未支付订单取消消息) 将用户参与记录移除,商品冻结信息更新,参与名额数退还
 * <p/>
 * Created by edgar.zr on 6/23/2016.
 */
@Component
public class OrderCancelListener extends BaseListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderCancelListener.class);

	@Autowired
	private SeckillManager seckillManager;
	@Autowired
	private RedissonManager redissonManager;
	@Autowired
	private ComponentHelper componentHelper;

	@Override
	public void init() {

	}

	@Override
	public String getName() {
		return RMQMessageType.TRADE_ORDER_CANCEL.combine();
	}

	@Override
	public void consumeMessage(JSONObject msg, String appKey) throws SeckillException {

		LOGGER.info("{}, appKey : {}", getName(), appKey);

		RedissonClient client = redissonManager.getRedissonClient();

		Long userId = msg.getLong("userId");
		Long orderId = msg.getLong("id");
		String bizCode = null;
		Long sellerId = null;
		Long seckillId = null;

		try {
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

			bizCode = seckillDTO.getBizCode();
			sellerId = seckillDTO.getSellerId();
			seckillId = seckillDTO.getId();

			// 强制更新缓存
			componentHelper.execute(LoadSeckillCache.wrapParams(null, seckillDTO.getSkuId(), appKey));

			RMapCache seckillUserMap = client.getMapCache(USER_KEY.getValue());
			seckillUserMap.fastRemove(redissonManager.cacheKey(USER_KEY.getKey(bizCode, sellerId, seckillId, userId)));// 移除用户参与记录

			// 已参与过,退还秒杀名额
			RSemaphore seckillCount = client.getSemaphore(redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, sellerId, seckillId)));
			seckillCount.release();
		} catch (SeckillException e) {
			LOGGER.error("", e);
			RSemaphore seckillCount = client.getSemaphore(redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, sellerId, seckillId)));
			seckillCount.release();
		} catch (Exception e) {
			LOGGER.error("orderCancel ", e);
			RSemaphore seckillCount = client.getSemaphore(redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, sellerId, seckillId)));
			seckillCount.release();
		}
	}

	@Override
	public Logger getLogger() {
		return LOGGER;
	}
}