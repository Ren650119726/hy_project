package com.mockuai.tradecenter.core.service.action.cart;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.UserCartItemManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.TradeRequest;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.common.constant.ActionEnum;


/**
 * 移除用户购物车商品处理类
 * @author cwr
 */
public class DeleteUserCartItem implements Action{
	private static final Logger log = LoggerFactory.getLogger(DeleteUserCartItem.class);

	@Resource
	private UserCartItemManager userCartItemManager;
	

	@SuppressWarnings("unchecked")
	@Override
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		TradeRequest request = context.getRequest();
		if(request.getParam("cartItemId") == null){
			log.error("cartItemId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"cartItemId is null");
		}else if(request.getParam("userId") == null){
			log.error("userId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"userId is null");
		}
		
		long id = (Long)request.getParam("cartItemId");
		long userId = (Long)request.getParam("userId");
		
		int result=0;
		CartItemDO cartItemDO = new CartItemDO();
		cartItemDO.setId(id);
		cartItemDO.setUserId(userId);
		
		try{
			// 先删除该购物车项
			result = userCartItemManager.deleteUserCartItem(cartItemDO);
		}catch(TradeException e){
			log.error("db error : ",e);
			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_DATABASE_ERROR);
		}
		
		
			
		if(result > 0){
			return ResponseUtils.getSuccessResponse(true);
		}else{
			log.error("cartItem doesn't exist");
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"cartItem doesn't exist");
		}
	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_USER_CART_ITEM.getActionName();
	}
}
