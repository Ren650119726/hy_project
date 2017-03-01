package com.mockuai.tradecenter.core.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.customer.client.CustomerClient;
import com.mockuai.customer.common.api.CustomerResponse;
import com.mockuai.customer.common.dto.MemberDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.CustomerManager;

public class CustomerManagerImpl extends BaseService implements CustomerManager {
	private static final Logger log = LoggerFactory.getLogger(CustomerManagerImpl.class);

	@Autowired
	CustomerClient customerClient;
	
	@Override
	public MemberDTO getMemberByUserId(Long userId, String appKey) throws TradeException {
		printIntoService(log, "getMemberByUserId", userId, "");
		
		CustomerResponse<MemberDTO> response = customerClient.getMemberByUserId(userId, appKey);
		printInvokeService(log,"invoke customercenter getMemberByUserId response",response,"");
		if(!response.isSuccess()){
			throw new TradeException(response.getMessage());
		}
		return response.getModule();
	}

}
