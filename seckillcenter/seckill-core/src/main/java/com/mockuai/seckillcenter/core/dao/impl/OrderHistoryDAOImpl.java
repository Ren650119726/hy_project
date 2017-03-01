package com.mockuai.seckillcenter.core.dao.impl;

import com.mockuai.seckillcenter.common.domain.qto.OrderHistoryQTO;
import com.mockuai.seckillcenter.core.dao.OrderHistoryDAO;
import com.mockuai.seckillcenter.core.domain.OrderHistoryDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class OrderHistoryDAOImpl extends SqlMapClientDaoSupport implements OrderHistoryDAO {

	public OrderHistoryDAOImpl() {
		super();
	}

	public Long insert(OrderHistoryDO record) {
		Long id = (Long) getSqlMapClientTemplate().insert("order_history.insert", record);
		return id;
	}

	public List<OrderHistoryDO> selectOrderHistory(OrderHistoryQTO orderHistoryQTO) {
		orderHistoryQTO.setTotalCount((Integer) getSqlMapClientTemplate().queryForObject("order_history.countOfSelectOrderHistory", orderHistoryQTO));
		List<OrderHistoryDO> orderHistoryDOs =
				getSqlMapClientTemplate().queryForList("order_history.selectOrderHistory", orderHistoryQTO);
		return orderHistoryDOs;
	}

//	public int updateOrderHistory(OrderHistoryDO record) {
//		int rows = getSqlMapClientTemplate().update("order_history.updateOrderHistory", record);
//		return rows;
//	}
}