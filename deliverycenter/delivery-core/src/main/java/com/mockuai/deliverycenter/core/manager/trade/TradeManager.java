package com.mockuai.deliverycenter.core.manager.trade;

import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.tradecenter.common.domain.OrderDTO;

public interface TradeManager {

	/**
	 * 获取订单详情
	 * @param orderId
	 * @param userId
	 * @param appkey
	 * @return
	 */
	public OrderDTO getOrderDTO(Long orderId,Long userId,String appkey)throws DeliveryException;
	
}
