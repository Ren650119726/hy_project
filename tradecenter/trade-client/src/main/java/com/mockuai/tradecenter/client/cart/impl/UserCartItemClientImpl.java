package com.mockuai.tradecenter.client.cart.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.tradecenter.client.cart.UserCartItemClient;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.CartItemDTO;

public class UserCartItemClientImpl implements UserCartItemClient{
	@Resource
	private TradeService tradeService;
	
	public Response<CartItemDTO> addUserCartItem(CartItemDTO cartItemDTO, String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_USER_CART_ITEM.getActionName());
		request.setParam("cartItemDTO", cartItemDTO);
		request.setParam("appKey", appKey);
		return  tradeService.execute(request);
	}

	public Response<CartItemDTO> deleteUserCartItem(Long cartItemId, Long userId, String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELETE_USER_CART_ITEM.getActionName());
		request.setParam("cartItemId", cartItemId);
		request.setParam("userId", userId);
		request.setParam("appKey", appKey);
		return  tradeService.execute(request);
	}

	public Response<CartItemDTO> cleanUserCart(Long userId, String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.CLEAN_USER_CART.getActionName());
		request.setParam("userId", userId);
		request.setParam("appKey", appKey);
		return  tradeService.execute(request);
	}

	@Override
	public Response<Boolean> syncUserCart(List<CartItemDTO> cartItemDTOs, String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.BATCH_ADD_USER_CARTITEM.getActionName());
		request.setParam("cartItemDTOList", cartItemDTOs);
		request.setParam("appKey", appKey);
		return  tradeService.execute(request);
	}

}
