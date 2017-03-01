package com.mockuai.tradecenter.client.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.mockuai.tradecenter.client.PreOrderClient;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.DataDTO;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.dataCenter.SalesVolumeDTO;
import com.mockuai.tradecenter.common.domain.message.OrderMessageDTO;

public class PreOrderClientImpl implements PreOrderClient{

	@Resource
	private TradeService tradeService;
	
	
	
	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	
	

	public TradeService getTradeService() {
		return tradeService;
	}



	public Response<OrderDTO> addPreOrder(OrderDTO orderDTO, String appKey) {
//		System.out.println( "【 addPreOrder client 】" );
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_PRE_ORDER.getActionName());
		request.setParam("orderDTO", orderDTO);
		request.setParam("appKey", appKey);
		Response<OrderDTO> response = tradeService.execute(request);
		return response;
	}

	public Response<OrderDTO> queryPreOrder(OrderQTO query,String appKey) {

		Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_PRE_ORDER.getActionName());
		request.setParam("orderQTO", query);
		request.setParam("appKey", appKey);
		Response<OrderDTO> response = tradeService.execute(request);
		return response;
	}







	
}
