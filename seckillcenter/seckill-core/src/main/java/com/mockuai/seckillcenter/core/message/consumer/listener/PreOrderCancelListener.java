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
 * 预单取消,将用户参与记录移除,商品冻结信息更新,参与名额数退还
 * <p/>
 * Created by edgar.zr on 6/23/2016.
 */
@Component
public class PreOrderCancelListener extends BaseListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(PreOrderCancelListener.class);

	@Autowired
	private RedissonManager redissonManager;
	@Autowired
	private SeckillManager seckillManager;
	@Autowired
	private ComponentHelper componentHelper;

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
			}

			String bizCode = seckillDTO.getBizCode();
			Long sellerId = seckillDTO.getSellerId();
			Long seckillId = seckillDTO.getId();

			RedissonClient client = redissonManager.getRedissonClient();

			// 强制更新缓存
			componentHelper.execute(LoadSeckillCache.wrapParams(seckillDTO.getItemId(), null, appKey));

			RMapCache seckillUserMap = client.getMapCache(USER_KEY.getValue()); // 更新用户参与数据
			seckillUserMap.fastRemove(redissonManager.cacheKey(USER_KEY.getKey(bizCode, sellerId, seckillId, userId)));// 移除用户参与记录

			RSemaphore seckillCount = client.getSemaphore(redissonManager.cacheKey(SECKILL_COUNT.getKey(bizCode, sellerId, seckillId)));
			seckillCount.release();
		} catch (SeckillException e) {
			LOGGER.error("", e);
		}
	}

	@Override
	public Logger getLogger() {
		return LOGGER;
	}

	@Override
	public void init() {

	}

	@Override
	public String getName() {
		return RMQMessageType.TRADE_PRE_ORDER_CANCEL.combine();
	}
}