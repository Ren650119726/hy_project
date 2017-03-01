package com.mockuai.tradecenter.core.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.core.dao.OrderStoreDAO;
import com.mockuai.tradecenter.core.domain.OrderStoreDO;
import com.mockuai.tradecenter.core.manager.OrderStoreManager;

public class OrderStoreManagerImpl implements OrderStoreManager {

	@Autowired
	private OrderStoreDAO orderStoreDAO;

	@Override
	public Long addOrderStore(OrderStoreDO orderStoreDO) {
		return orderStoreDAO.addOrderStore(orderStoreDO);
	}

	@Override
	public OrderStoreDO getOrderStore(Long orderId) {
		return orderStoreDAO.getOrderStore(orderId);
	}
}
