package com.mockuai.tradecenter.core.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.dao.OrderViewDAO;
import com.mockuai.tradecenter.core.domain.OrderViewDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderViewManager;
@Component("orderViewManager")
public class OrderViewManagerImpl implements OrderViewManager {
	private static final Logger log = LoggerFactory.getLogger(OrderViewManagerImpl.class);
	@Autowired
	private OrderViewDAO orderViewDAO;

	@Override
	public Long addOrderView(OrderViewDO viewDO) throws TradeException {
		log.info("enter OrderViewDO");
		long id = 0L;
		try{
			 id = this.orderViewDAO.addOrderView(viewDO);
		}catch(Exception e){
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
		}
		log.info("exit OrderViewDO id: " + id);
		return id;
	}

}
