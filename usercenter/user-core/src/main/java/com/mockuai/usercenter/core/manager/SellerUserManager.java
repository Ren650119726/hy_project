package com.mockuai.usercenter.core.manager;

import java.util.List;

import com.mockuai.usercenter.common.dto.SellerUserRelateDTO;
import com.mockuai.usercenter.common.qto.SellerUserQTO;
import com.mockuai.usercenter.core.domain.SellerUserDO;
import com.mockuai.usercenter.core.exception.UserException;

public interface SellerUserManager {
	
	SellerUserDO getBySellerIdAndUserId(Long sellerId,Long userId)throws UserException;
	
	Long addSellerUser(SellerUserDO sellerUserDO)throws UserException;
	
	int updateSellerUser(SellerUserDO sellerUserDO)throws UserException;
	
	List<SellerUserRelateDTO> querySellerUserRelate(SellerUserQTO query) throws UserException;
	
	Integer getQuerySellerUserRelateCount(SellerUserQTO query) throws UserException;
	

}
