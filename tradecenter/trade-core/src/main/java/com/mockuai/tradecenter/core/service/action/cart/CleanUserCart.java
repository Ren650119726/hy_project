package com.mockuai.tradecenter.core.service.action.cart;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.UserCartItemManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.TradeRequest;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.common.constant.ActionEnum;


public class CleanUserCart implements Action {
	private static final Logger log = LoggerFactory.getLogger(CleanUserCart.class);

	@Resource
	private UserCartItemManager userCartItemManager;

	@Override
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		TradeRequest request = context.getRequest();
		TradeResponse<Boolean> response = null;
		if(request.getParam("userId") == null){
			log.error("userId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"userId is null");
			
		}
		Long userId = (Long)request.getParam("userId");
		int result = 0;
		try{
			result = this.userCartItemManager.cleanUserCart(userId);
		}catch(TradeException e){
			log.error("db error: " , e);
			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_DATABASE_ERROR);
		}
		if(result > 0 ){
			return ResponseUtils.getSuccessResponse(result > 0? true : false);
		}else{
			log.error("user cart is empty ");
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"user cart is empty");
		}
		
	}

	@Override
	public String getName() {
		return ActionEnum.CLEAN_USER_CART.getActionName();
	}
}
