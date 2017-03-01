package com.mockuai.tradecenter.client.impl;

import javax.annotation.Resource;

import com.mockuai.tradecenter.client.OrderMessageClient;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.message.WxTemplateDTO;

public class OrderMessageClientImpl implements OrderMessageClient{

	@Resource
	private TradeService tradeService;
	
	@Override
	public Response<?> sendWechatMessage(WxTemplateDTO wxtplDTO,String appKey) {
		
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.SEND_WECHAT_MESSAGE.getActionName());
		request.setParam("wxTemplateDTO", wxtplDTO);
		request.setParam("appKey", appKey);
		Response<?> response = tradeService.execute(request);
		return response;
	}

}
