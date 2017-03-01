package com.mockuai.usercenter.core.manager;

import com.mockuai.usercenter.core.domain.UserOrderRelateDO;
import com.mockuai.usercenter.core.exception.UserException;

public interface UserOrderRelateManager {
	
	
	
	void joinUserAndOrders(UserOrderRelateDO relateDO,String tradeType)throws UserException;
	
	/**
	 * 
	 * @param query
	 * @return
	 * @throws UserException
	 */
//	UserOrderRelateDO getByQuery(UserOrderRelateQTO query)throws UserException;

}
