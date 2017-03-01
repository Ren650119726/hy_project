package com.mockuai.tradecenter.core.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.core.dao.OrderViewDAO;
import com.mockuai.tradecenter.core.domain.OrderViewDO;

public class OrderViewDAOImpl extends SqlMapClientDaoSupport implements OrderViewDAO{

	@Override
	public long addOrderView(OrderViewDO viewDO) {
		long id = (Long)this.getSqlMapClientTemplate().insert("order_view.add", viewDO);
		return id;
	}

	@Override
	public OrderViewDO getOrderViewByOrderId(Long id) {
		OrderViewDO query = new OrderViewDO();
		query.setOrderId(id);
		return (OrderViewDO) this.getSqlMapClientTemplate().queryForObject("order_view.getOrderViewByOrderId",query);
	}

}
