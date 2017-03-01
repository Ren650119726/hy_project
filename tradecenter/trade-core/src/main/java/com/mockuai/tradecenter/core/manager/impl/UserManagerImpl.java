package com.mockuai.tradecenter.core.manager.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.UserManager;
import com.mockuai.usercenter.client.ConsigneeClient;
import com.mockuai.usercenter.client.SellerUserRelateClient;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.dto.UserInfoDTO;

public class UserManagerImpl implements UserManager{
	private static final Logger log = LoggerFactory.getLogger(UserManagerImpl.class);
	
	@Resource
	private ConsigneeClient consigneeClient;
	@Resource
	private UserClient userClient;
	
	@Resource
	private SellerUserRelateClient selllerUserRelateClient;

	public String getUserName(long userId, String appKey) throws TradeException {
		UserDTO userDTO = getUser(userId, appKey);
		if(userDTO != null){
			return userDTO.getName();
		}else{
			return null;
		}
	}

	public UserDTO getUser(long userId, String appKey) throws TradeException {
		try{
			Response<UserDTO> response = userClient.getUserById(userId, appKey);
			if(response.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()){
				//TODO error handle
			}else{
				return response.getModule();
			}
		}catch(Exception e){
			log.error("userId:{}", userId, e);
		}

		return null;
	}

    public UserDTO getUserByMobile(String mobile, String appKey) throws TradeException {
        try{
            Response<UserDTO> response = userClient.getUserByMobile(mobile, appKey);
            if(response.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()){
                //TODO error handle
            }else{
                return response.getModule();
            }
        }catch(Exception e){
            log.error("mobile:{}", mobile, e);
        }

        return null;
    }

	public UserConsigneeDTO getUserConsignee(long userId, long consigneeId, String appKey)throws TradeException{
		try{
			Response<UserConsigneeDTO> response = consigneeClient.getConsigneeById(userId, consigneeId, appKey);
			if(response.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()){
				//TODO error handle
			}else{
				return response.getModule();
			}
		}catch(Exception e){
			log.error("userId:{}, consigneeId:{}", userId, consigneeId, e);
		}


		return null;
	}

	@Override
	public Boolean addSellerUserRelate(long userId, long sellerId, long orderId,String tradeType, long orderAmt,String appkey)
			throws TradeException {
		try{
			Response<Boolean> response = selllerUserRelateClient.addSellerUserRelate(userId, sellerId, orderId, tradeType, orderAmt, appkey);
			if(response.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()){
				log.error("response:"+response);
				throw new TradeException(response.getMessage());
			}else{
				return response.getModule();
			}
		}catch(Exception e){
			log.error("addSellerUserRelate error", e);
			throw new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.SYS_E_DATABASE_ERROR,e);
		}
	}

	@Override
	public boolean checkUserPayPwd(long userId, String payPwd, String appKey) throws TradeException {
		try{
			Response<Void> response = userClient.checkUserPayPwd(userId, payPwd, appKey);
			if(response.getCode() == ResponseCode.REQUEST_SUCCESS.getValue()){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			log.error("checkUserPayPwd error", e);
			throw new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.SYS_E_DATABASE_ERROR,e);
		}

	}

	//	@Override
//	public UserInfoDTO getUserOpenInfo(long userId, String appKey) throws TradeException {
//		// TODO Auto-generated method stub
//		userClient.getUserOpenInfo(openType, openUid, appKey)
//		return null;
//	}
}
