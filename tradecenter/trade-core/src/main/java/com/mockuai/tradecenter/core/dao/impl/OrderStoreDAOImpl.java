package com.mockuai.tradecenter.core.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.core.dao.OrderStoreDAO;
import com.mockuai.tradecenter.core.domain.OrderStoreDO;

public class OrderStoreDAOImpl extends SqlMapClientDaoSupport implements OrderStoreDAO{

	@Override
	public Long addOrderStore(OrderStoreDO orderStoreDO) {
		long id = (Long)this.getSqlMapClientTemplate().insert("order_store.add", orderStoreDO);
		return id;
	}

	@Override
	public OrderStoreDO getOrderStore(Long orderId) {
		OrderStoreDO orderStore = new OrderStoreDO();
		orderStore.setOrderId(orderId);
		return (OrderStoreDO) this.getSqlMapClientTemplate().queryForObject("order_store.getOrderStore", orderStore);
	}

	@Override
	public int updatePickupCode(Long orderId, String pickupCode) {
		OrderStoreDO orderStore = new OrderStoreDO();
		orderStore.setOrderId(orderId);
		orderStore.setPickupCode(pickupCode);
		return this.getSqlMapClientTemplate().update("order_store.update", orderStore);
	}

}
