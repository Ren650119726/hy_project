package com.mockuai.usercenter.core.service.action.selleruser;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.domain.SellerUserDO;
import com.mockuai.usercenter.core.domain.UserOrderRelateDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.SellerUserManager;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.manager.UserOrderRelateManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.action.Action;


@Service
public class SellerUserRelateAction implements Action {

	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Resource
	private SellerUserManager sellerUserManager;
	
	@Resource
	private UserManager userManager;

	@Resource
	private UserOrderRelateManager userOrderRelateManagerImpl;
	
    @Resource
    private TransactionTemplate transactionTemplate;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		
		final String bizCode = (String)context.get("bizCode");
		
		final Long userId = (Long)context.getRequest().getParam("userId");
		
		final Long sellerId = (Long)context.getRequest().getParam("sellerId");
		
		final Long orderId = (Long)context.getRequest().getParam("orderId");
		
		final Long orderAmount = (Long)context.getRequest().getParam("orderAmount");
		
		//交易类别 
		final String tradeType = (String)context.getRequest().getParam("tradeType");
		
		
		//参数校验
		if(bizCode == null){
			return new UserResponse(ResponseCode.P_PARAM_NULL, "bizCode is null");
		}
		
		if(null==userId){
			return new UserResponse(ResponseCode.P_PARAM_NULL, "userId is null");
		}
		
		if(null == orderId){
			return new UserResponse(ResponseCode.P_PARAM_NULL, "orderId is null");
		}
		
		if(null==sellerId){
			return new UserResponse(ResponseCode.P_PARAM_NULL, "sellerId is null");
		}
		
		if(StringUtils.isBlank(tradeType)){
			return new UserResponse(ResponseCode.P_PARAM_NULL, "tradeType is null");
		}
		
		if(null == orderAmount){
			return new UserResponse(ResponseCode.P_PARAM_NULL, "orderAmount is null");
		}
		
		UserDTO userDTO = userManager.getUserById(userId);
		
//		if(null == userDTO){
//			return new UserResponse(ResponseCode.P_PARAM_ERROR, "user no exist");
//		}
		
		
			
			UserResponse userResult = (UserResponse) transactionTemplate.execute(new TransactionCallback() {

				@Override
				public Object doInTransaction(TransactionStatus transactionStatus) {
					try{
						UserOrderRelateDO userOrderRelateDO = new UserOrderRelateDO();
						userOrderRelateDO.setUserId(userId);
						userOrderRelateDO.setSellerId(sellerId);
						userOrderRelateDO.setOrderId(orderId);
						userOrderRelateDO.setOrderAmount(orderAmount);
						userOrderRelateDO.setBizCode(bizCode);
						
						userOrderRelateManagerImpl.joinUserAndOrders(userOrderRelateDO, tradeType);
						
						SellerUserDO relateDTO = sellerUserManager.getBySellerIdAndUserId(sellerId,userId);
						
						
						if(null!=relateDTO){
							sellerUserManager.updateSellerUser(relateDTO);
						}else{
							relateDTO = new SellerUserDO();
							BeanUtils.copyProperties(userOrderRelateDO, relateDTO);
							sellerUserManager.addSellerUser(relateDTO);
						}
					}catch(UserException e){
						log.error("SellerUserRelateAction error",e);
						transactionStatus.setRollbackOnly();
					 	return new UserResponse(e.getResponseCode(), e.getMessage());
					}catch(Exception e){
						log.error("SellerUserRelateAction error",e);
						 	transactionStatus.setRollbackOnly();
						 	return new UserResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getMessage());
					}
					
					
					
					return new UserResponse(true);
				}
		    	
		    });
			
			
			return userResult;
			
				
			
	}

	@Override
	public String getName() {
		return ActionEnum.SELLER_USER_RELATE.getActionName();
	}

}
