package com.mockuai.usercenter.core.dao;

import com.mockuai.usercenter.common.qto.UserOrderRelateQTO;
import com.mockuai.usercenter.core.domain.UserOrderRelateDO;

public interface UserOrderRelateDAO {
	
	Long addUserOrderRelate(UserOrderRelateDO userDO);

	UserOrderRelateDO getByUserOrderRelateQTO(UserOrderRelateQTO query);
	
	int updateUserOrderRelate(UserOrderRelateDO userDO);
	
	Integer getRecordsCountByQuery(UserOrderRelateQTO query);
	
	Long getOrderTotalAmount(UserOrderRelateQTO query);

}
