package com.mockuai.usercenter.core.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.qto.UserOrderRelateQTO;
import com.mockuai.usercenter.core.dao.UserOrderRelateDAO;
import com.mockuai.usercenter.core.domain.UserOrderRelateDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserOrderRelateManager;

@Service
public class UserOrderRelateManagerImpl implements UserOrderRelateManager {

	@Resource
	private UserOrderRelateDAO userOrderRelateDAO;

	private void setUserOrderStatus(UserOrderRelateDO userOrderRelateDO,String tradeType){
		if(tradeType.equals("paid")){
			userOrderRelateDO.setIsPaid("Y");
		}else{
			userOrderRelateDO.setIsPaid("N");
		}
		if(tradeType.equals("refund")){
			userOrderRelateDO.setIsRefund("Y");
		}else{
			userOrderRelateDO.setIsRefund("N");
		}
			
			
	}

	@Override
	public void joinUserAndOrders(UserOrderRelateDO userRelateDO,String tradeType)throws UserException{

		
		if(null==userRelateDO){
			throw new UserException(ResponseCode.P_PARAM_NULL,"UserOrderRelateDO is null");
		}
		
		if(StringUtils.isBlank(tradeType)){
			throw new UserException(ResponseCode.P_PARAM_NULL,"tradeType is null");
		}
		
		try{
			
			UserOrderRelateQTO query = new UserOrderRelateQTO();
			query.setUserId(userRelateDO.getUserId());
			query.setSellerId(userRelateDO.getSellerId());
			query.setOrderId(userRelateDO.getOrderId());
			UserOrderRelateDO userOrderRelateDO = userOrderRelateDAO.getByUserOrderRelateQTO(query);
			if(null==userOrderRelateDO){
				setUserOrderStatus(userRelateDO,tradeType);
				userOrderRelateDAO.addUserOrderRelate(userRelateDO);
			}else{
				setUserOrderStatus(userOrderRelateDO,tradeType);
				userOrderRelateDAO.updateUserOrderRelate(userOrderRelateDO);
			}
			
		}catch(Exception e){
			throw new UserException(ResponseCode.B_UPDATE_ERROR);
		}
		
		
		
		
	}

	
	

	

}