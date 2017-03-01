package com.mockuai.usercenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.usercenter.client.SellerUserRelateClient;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.dto.SellerUserRelateDTO;
import com.mockuai.usercenter.common.qto.SellerUserQTO;

public class SellerUserRelateClientImpl implements SellerUserRelateClient{

	@Resource
	private UserDispatchService userDispatchService;

	
	@SuppressWarnings("unchecked")
	public Response<Boolean> addSellerUserRelate(Long userId, Long sellerId, Long orderId,String tradeType, Long orderAmt,
			String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("appKey", appKey);
		request.setParam("sellerId", sellerId);
		request.setParam("orderId", orderId);
		request.setParam("tradeType", tradeType);
		request.setParam("orderAmount", orderAmt);
		
		request.setCommand(ActionEnum.SELLER_USER_RELATE.getActionName());
		Response<Boolean> response = userDispatchService.execute(request);
		return response;
		
		
	}


	@SuppressWarnings("rawtypes")
	public Response<List<SellerUserRelateDTO>> querySellerUserRelate(SellerUserQTO query,String appKey) {
		Request request = new BaseRequest();
		request.setParam("sellerUserQTO", query);
		request.setCommand(ActionEnum.QUERY_SELLER_USER_RELATE.getActionName());
		request.setParam("appKey", appKey);
		Response response = userDispatchService.execute(request);
		return response;
	}

}
