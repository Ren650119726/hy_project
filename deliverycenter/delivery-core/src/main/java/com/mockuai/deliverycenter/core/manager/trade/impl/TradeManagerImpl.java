package com.mockuai.deliverycenter.core.manager.trade.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.manager.trade.TradeManager;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
@Component("tradeManager")
public class TradeManagerImpl implements TradeManager {

	@Resource
	private OrderClient orderClient;

	@Override
	public OrderDTO getOrderDTO(Long orderId, Long userId, String appkey) throws DeliveryException {

		try {
			Response<OrderDTO> response = orderClient.getAloneOrder(orderId, userId, appkey);
			if (response.getCode() != 10000) {
				return null;
			}
			return response.getModule();
		} catch (Exception e) {
			throw new DeliveryException(e.getMessage());
		}
	}

}
