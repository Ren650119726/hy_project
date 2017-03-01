package com.mockuai.tradecenter.core.manager;

import com.mockuai.tradecenter.core.domain.OrderStoreDO;
import com.mockuai.tradecenter.core.exception.TradeException;

/**
 * 订单门店
 *
 */
public interface OrderStoreManager {

	public Long addOrderStore(OrderStoreDO orderStoreDO) throws TradeException;

	public OrderStoreDO getOrderStore(Long orderId);
}
