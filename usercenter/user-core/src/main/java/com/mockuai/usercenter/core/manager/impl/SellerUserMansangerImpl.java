package com.mockuai.usercenter.core.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.SellerUserRelateDTO;
import com.mockuai.usercenter.common.qto.SellerUserQTO;
import com.mockuai.usercenter.common.qto.UserOrderRelateQTO;
import com.mockuai.usercenter.core.dao.SellerUserDAO;
import com.mockuai.usercenter.core.dao.UserOrderRelateDAO;
import com.mockuai.usercenter.core.domain.SellerUserDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.SellerUserManager;
@Service
public class SellerUserMansangerImpl implements SellerUserManager {

	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Resource
	private SellerUserDAO sellerUserDAO;
	
	@Resource
	private UserOrderRelateDAO userOrderRelateDAO;
	
	@Override
	public SellerUserDO getBySellerIdAndUserId(Long sellerId, Long userId) throws UserException{
		
		if (null == sellerId) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"sellerId is null");
		}
		
		if(null == userId){
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userId is null");
		}
		
		try{
			return sellerUserDAO.getByUserIdAndSellerId(userId, sellerId);
		}catch(Exception e){
			throw new UserException(ResponseCode.B_SELECT_ERROR
					);
		}
	}
	
	
	public void refreshSellerUser(SellerUserDO sellerUserDO)throws UserException{
		UserOrderRelateQTO query = new UserOrderRelateQTO();
		query.setUserId(sellerUserDO.getUserId());
		query.setSellerId(sellerUserDO.getSellerId());
		query.setIsPaid("N");
		query.setIsRefund("N");
		sellerUserDO.setCancleOrderNum(userOrderRelateDAO.getRecordsCountByQuery(query).longValue());
		query.setIsPaid("Y");
		Long finishedOrderNum = userOrderRelateDAO.getRecordsCountByQuery(query).longValue();
		sellerUserDO.setFinishedOrderNum(finishedOrderNum);
		if(finishedOrderNum>0){
			sellerUserDO.setRelateStatus("Y");
			sellerUserDO.setRelateTimes(new Date());
		}
		sellerUserDO.setFinishedOrderAmount(userOrderRelateDAO.getOrderTotalAmount(query));
		query.setIsPaid("N");
		query.setIsRefund("Y");
		sellerUserDO.setRefundOrderNum(userOrderRelateDAO.getRecordsCountByQuery(query).longValue());
		query.setIsPaid(null);
		query.setIsRefund(null);
		sellerUserDO.setTotalOrderNum(userOrderRelateDAO.getRecordsCountByQuery(query).longValue());
	}

	@Override
	public Long addSellerUser(SellerUserDO sellerUserDO) throws UserException{
		
		if(null == sellerUserDO){
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"sellerUserDO is null");
		}
		try{
			
			refreshSellerUser(sellerUserDO);
			
			return sellerUserDAO.addSellerUser(sellerUserDO);
		}catch(Exception e){
			e.printStackTrace();
			throw new UserException(ResponseCode.B_ADD_ERROR);
		}
	}

	


	@Override
	public int updateSellerUser(SellerUserDO sellerUserDO) throws UserException {
		if(null == sellerUserDO){
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"sellerUserDO is null");
		}
		try{
			refreshSellerUser(sellerUserDO);
			
			return sellerUserDAO.updateSellerUser(sellerUserDO);
		}catch(Exception e){
			throw new UserException(ResponseCode.B_UPDATE_ERROR);
		}
	}


	@Override
	public List<SellerUserRelateDTO> querySellerUserRelate(SellerUserQTO query)  throws UserException{
		
		if(null == query){
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"sellerUserDO is null");
		}
		try{
			List<SellerUserDO> dolist = sellerUserDAO.querySellerUser(query);
			List<SellerUserRelateDTO> dtolist = new ArrayList<SellerUserRelateDTO>();
			for(SellerUserDO sellerUserDO :dolist ){
				SellerUserRelateDTO sellerUserDTO = new SellerUserRelateDTO();
				BeanUtils.copyProperties(sellerUserDO, sellerUserDTO);
				dtolist.add(sellerUserDTO);
			}
			return dtolist;
		}catch(Exception e){
			log.error("SellerUserMansangerImpl.querySellerUserRelate error",e);
			throw new UserException(ResponseCode.B_SELECT_ERROR);
		}
	}


	@Override
	public Integer getQuerySellerUserRelateCount(SellerUserQTO query) throws UserException {
		if(null == query){
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"sellerUserDO is null");
		}
		try{
			return sellerUserDAO.getQuerySellerUserCount(query);
		}catch(Exception e){
			log.error("SellerUserMansangerImpl.getQuerySellerUserRelateCount error",e);
			throw new UserException(ResponseCode.B_SELECT_ERROR);
		}
	}

}
