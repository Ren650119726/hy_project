package com.mockuai.tradecenter.core.dao;

import com.mockuai.tradecenter.core.domain.OrderStoreDO;

public interface OrderStoreDAO {

	public Long addOrderStore(OrderStoreDO orderStoreDO);
	
	public OrderStoreDO getOrderStore(Long orderId);
	
	public int updatePickupCode(Long orderId,String pickupCode);
	
}
