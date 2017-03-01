package com.mockuai.tradecenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.domain.OrderServiceQTO;
import com.mockuai.tradecenter.core.dao.OrderServiceDAO;
import com.mockuai.tradecenter.core.domain.OrderServiceDO;

public class OrderServiceDAOImpl extends SqlMapClientDaoSupport implements OrderServiceDAO{


	@Override
	public Long addOrderService(OrderServiceDO record) {
		long id = (Long)this.getSqlMapClientTemplate().insert("order_service.add", record);
		return id;
	}

	@Override
	public List<OrderServiceDO> queryOrderService(OrderServiceQTO query) {
		return this.getSqlMapClientTemplate().queryForList("order_service.query", query);
	}


}
