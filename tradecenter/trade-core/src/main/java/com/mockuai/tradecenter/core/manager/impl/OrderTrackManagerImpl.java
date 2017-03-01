package com.mockuai.tradecenter.core.manager.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.common.domain.OrderTrackQTO;
import com.mockuai.tradecenter.core.dao.OrderTrackDAO;
import com.mockuai.tradecenter.core.domain.OrderTrackDO;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.OrderTrackManager;

public class OrderTrackManagerImpl extends BaseService implements OrderTrackManager{

	private static final Logger log = LoggerFactory.getLogger(OrderTrackManagerImpl.class);
	
	@Autowired
	OrderTrackDAO orderTrackDAO;
	
	
	@Override
	public Long addOrderTrack(OrderTrackDO record) {
		printIntoService(log,"addOrderTrack",record,"");
		return orderTrackDAO.addOrderTrack(record);
	}


	@Override
	public List<OrderTrackDO> queryOrderTrack(OrderTrackQTO query) {
		printIntoService(log,"queryOrderTrack",query,"");
		return orderTrackDAO.querOrderTrack(query);
	}

}
