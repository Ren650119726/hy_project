package com.mockuai.marketingcenter.core.message.consumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.marketingcenter.common.constant.RMQMessageType;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.core.domain.OrderRecordDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.message.consumer.BaseListener;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDiscountInfoDTO;
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
	public void consumeMessage(JSONObject msg, String appKey) throws MarketingException {

		LOGGER.info("{}, appKey : {}", getName(), appKey);

		Long userId = msg.getLong("userId");
		Long orderId = msg.getLong("id");
		Long subActivityId;

		try {
			OrderDTO orderDTO = tradeManager.getOrder(orderId, userId, appKey);
			if (orderDTO == null) {
				LOGGER.error("the order is null, orderId : {}, userId : {}", orderId, userId);
			}
			for (OrderDiscountInfoDTO orderDiscountInfoDTO : orderDTO.getOrderDiscountInfoDTOs()) {
				if (orderDiscountInfoDTO.getDiscountCode().equals(ToolType.COMPOSITE_TOOL.getCode())) {
					// 子节点 id
					subActivityId = orderDiscountInfoDTO.getSubMarketActivityId();
					LOGGER.info("grant activity coupon with order unpaid, orderId : {}, activityId : {}",
							orderId, subActivityId);
//					MarketActivityDO marketActivityDO = marketActivityManager.getActivity(subActivityId, null);
//					if (marketActivityDO == null) {
//						LOGGER.error("the sub market doesn't exist, subActivityId : {}", subActivityId);
//						return;
//					}
					OrderRecordDO orderRecordDO = new OrderRecordDO();
					orderRecordDO.setActivityId(orderDiscountInfoDTO.getMarketActivityId());
					orderRecordDO.setUserId(userId);
					orderRecordDO.setOrderId(orderId);
					Long id = orderRecordManager.addOrderRecord(orderRecordDO);
					if (id == null) {
						LOGGER.error("error to add orderRecord, {}", JsonUtil.toJson(orderRecordDO));
					}
					break;
				}
			}

		} catch (MarketingException e) {
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