package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.tradecenter.core.domain.OrderServiceDO;
import com.mockuai.tradecenter.core.exception.TradeException;

/**
 *
 */
public interface OrderServiceManager {

	public Long addOrderService(OrderServiceDO record) throws TradeException;

	public List<OrderServiceDO> queryOrderService(Long orderId, Long orderItemId) throws TradeException;
}
