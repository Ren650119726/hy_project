package com.mockuai.tradecenter.core.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.core.dao.OrderPaymentDAO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPaymentDAOImpl extends SqlMapClientDaoSupport implements OrderPaymentDAO {

	@Override
	public long addOrderPayment(OrderPaymentDO orderPaymentDO) {
		long id = (Long)this.getSqlMapClientTemplate().insert("order_payment.addOrderPayment", orderPaymentDO);
		return id;
	}
	
	@Override
	public OrderPaymentDO getOrderPayment(long orderId, Long userId){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("userId", userId);
		return (OrderPaymentDO)this.getSqlMapClientTemplate().queryForObject(
				"order_payment.getOrderPayment", params);
	}

	@Override
	public OrderPaymentDO getOrderPaymentList(long orderId, Long userId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("userId", userId);
		List<OrderPaymentDO> queryForList = (List<OrderPaymentDO>)this.getSqlMapClientTemplate().queryForList(
				"order_payment.getOrderPayment", params);
		OrderPaymentDO orderPaymentDO = null;
		if(queryForList != null && !queryForList.isEmpty() ){
			orderPaymentDO = queryForList.get(0);
		}
		return orderPaymentDO;
	}

	public int updatePaymentType(long orderId, long userId, int paymentId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("userId", userId);
		params.put("paymentId", paymentId);
		return this.getSqlMapClientTemplate().update("order_payment.updatePaymentType", params);
	}

	@Override
	public int paySuccess(OrderPaymentDO orderPaymentDO){
		return this.getSqlMapClientTemplate().update("order_payment.paySuccess", orderPaymentDO);
	}

}

