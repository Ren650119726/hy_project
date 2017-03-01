package com.mockuai.seckillcenter.core.manager.impl;

import com.mockuai.seckillcenter.common.constant.ResponseCode;
import com.mockuai.seckillcenter.common.domain.qto.OrderHistoryQTO;
import com.mockuai.seckillcenter.core.dao.OrderHistoryDAO;
import com.mockuai.seckillcenter.core.domain.OrderHistoryDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.OrderHistoryManager;
import com.mockuai.seckillcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by edgar.zr on 7/20/2016.
 */
public class OrderHistoryMangerImpl implements OrderHistoryManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderHistoryMangerImpl.class);

	@Autowired
	private OrderHistoryDAO orderHistoryDAO;

	@Override
	public Long addOrderHistory(OrderHistoryDO orderHistoryDO) throws SeckillException {
		try {
			Long id = orderHistoryDAO.insert(orderHistoryDO);
			return id;
		} catch (Exception e) {
			LOGGER.error("error to addOrderHistory, orderHistoryDO : {}", JsonUtil.toJson(orderHistoryDO), e);
			throw new SeckillException(ResponseCode.SERVICE_EXCEPTION);
		}
	}

	@Override
	public List<OrderHistoryDO> queryOrderHistory(OrderHistoryQTO orderHistoryQTO) throws SeckillException {
		try {
			List<OrderHistoryDO> orderHistoryDOs = orderHistoryDAO.selectOrderHistory(orderHistoryQTO);
			return orderHistoryDOs;
		} catch (Exception e) {
			LOGGER.error("error to queryOrderHistory, orderHistoryQTO : {}", JsonUtil.toJson(orderHistoryQTO), e);
			throw new SeckillException(ResponseCode.SERVICE_EXCEPTION);
		}
	}
}