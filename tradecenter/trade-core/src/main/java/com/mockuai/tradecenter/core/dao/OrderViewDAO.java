package com.mockuai.tradecenter.core.dao;

import com.mockuai.tradecenter.core.domain.OrderViewDO;

public interface OrderViewDAO {
	public long addOrderView(OrderViewDO viewDO);

	public OrderViewDO getOrderViewByOrderId(Long id);
}
