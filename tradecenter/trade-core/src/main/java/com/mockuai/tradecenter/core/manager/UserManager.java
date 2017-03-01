package com.mockuai.tradecenter.core.manager;

import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.common.dto.UserDTO;

public interface UserManager {

	/**
	 * 获取用户名
	 * @param userId
	 * @return
	 * @throws TradeException
	 */
	public String getUserName(long userId, String appKey) throws TradeException;

	/**
	 * 查询用户信息
	 * @param userId
	 * @return
	 * @throws TradeException
	 */
	public UserDTO getUser(long userId, String appKey) throws TradeException;

	
	/**
	 * 
	 * @param userId
	 * @param consigneeId
	 * @return
	 */
	public UserConsigneeDTO getUserConsignee(long userId, long consigneeId, String appKey) throws TradeException;
	
	public Boolean addSellerUserRelate(long userId, long sellerId, long orderId,String tradeType, long orderAmt,String appkey)
			throws TradeException;

	public boolean checkUserPayPwd(long userId, String payPwd, String appKey) throws TradeException;
	
//	public UserInfoDTO getUserOpenInfo(long userId,String appKey)throws TradeException;

    public UserDTO getUserByMobile(String mobile, String appKey) throws TradeException ;

    }
