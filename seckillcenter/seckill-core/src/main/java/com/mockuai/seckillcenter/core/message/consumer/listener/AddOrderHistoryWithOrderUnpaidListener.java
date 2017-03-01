package com.mockuai.seckillcenter.core.message.consumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.seckillcenter.common.constant.RMQMessageType;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.domain.OrderHistoryDO;
import com.mockuai.seckillcenter.core.domain.SeckillDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.message.consumer.BaseListener;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by edgar.zr on 7/26/2016.
 */
@Component
public class AddOrderHistoryWithOrderUnpaidListener extends BaseListener {

	public static final Logger LOGGER = LoggerFactory.getLogger(AddOrderHistoryWithOrderUnpaidListener.class);

	@Override
	public void consumeMessage(JSONObject msg, String appKey) throws SeckillException {
		Long userId = msg.getLong("userId");
		Long orderId = msg.getLong("id");

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
			OrderHistoryDO orderHistoryDO = new OrderHistoryDO();
			orderHistoryDO.setOrderId(orderId);
			orderHistoryDO.setUserId(userId);
			orderHistoryDO.setSeckillId(seckillDTO.getId());
			Long id = orderHistoryManager.addOrderHistory(orderHistoryDO);
			if (id == null) {
				LOGGER.error("error to add order history, orderId : {}, userId : {}, seckillId : {} "
						, orderId, userId, seckillDTO.getId());
			}
		} catch (SeckillException e) {
			LOGGER.error("error to get order, {}", orderId, userId);
		} catch (Exception e) {
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
		return RMQMessageType.TRADE_ORDER_UNPAID.combine();
	}
}