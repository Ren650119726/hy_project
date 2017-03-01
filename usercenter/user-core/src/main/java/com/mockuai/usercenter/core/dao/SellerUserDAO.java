package com.mockuai.usercenter.core.dao;

import java.util.List;

import com.mockuai.usercenter.common.qto.SellerUserQTO;
import com.mockuai.usercenter.core.domain.SellerUserDO;

public interface SellerUserDAO {
	
	Long addSellerUser(SellerUserDO userDO);

	SellerUserDO getByUserIdAndSellerId(Long userId, Long sellerId);

	int updateSellerUser(SellerUserDO userDO);
	
	List<SellerUserDO> querySellerUser(SellerUserQTO query);
	
	Integer getQuerySellerUserCount(SellerUserQTO query);
}
