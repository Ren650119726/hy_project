package com.mockuai.tradecenter.core.manager.impl;

import java.util.List;

import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.common.domain.OrderServiceQTO;
import com.mockuai.tradecenter.core.dao.OrderServiceDAO;
import com.mockuai.tradecenter.core.domain.OrderServiceDO;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.OrderServiceManager;

public class OrderServiceManagerImpl extends BaseService implements OrderServiceManager{
	private static Logger log = LoggerFactory.getLogger(OrderServiceManagerImpl.class);

	@Autowired
	OrderServiceDAO orderServiceDAO;

	@Override
	public Long addOrderService(OrderServiceDO record) throws TradeException{
		try {
			return orderServiceDAO.addOrderService(record);
		} catch (Exception e) {
			log.error("error to add orderService, orderServiceDO:{}", JsonUtil.toJson(record), e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, "add orderService error");
		}

	}

	@Override
	public List<OrderServiceDO> queryOrderService(Long orderId, Long orderItemId) throws TradeException {
		try {
			OrderServiceQTO query = new OrderServiceQTO();
			query.setOrderItemId(orderItemId);
			query.setOrderId(orderId);
			return orderServiceDAO.queryOrderService(query);
		} catch (Exception e) {
			log.error("error to query orderService, orderId:{}, orderItemId:{}", orderId, orderItemId, e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR, "query orderService error");
		}
	}

}
