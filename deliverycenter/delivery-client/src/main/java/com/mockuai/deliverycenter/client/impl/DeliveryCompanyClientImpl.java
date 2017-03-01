package com.mockuai.deliverycenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.deliverycenter.client.DeliveryCompanyClient;
import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.express.LogisticsCompanyDTO;

public class DeliveryCompanyClientImpl implements DeliveryCompanyClient{

	
	@Resource
	private DeliveryService deliveryService;

	public Response<List<LogisticsCompanyDTO>> getDeliveryCompany(String appkey) {
		Request request = new BaseRequest();
		request.setParam("appKey", appkey);
		request.setCommand(ActionEnum.QUERY_DELIVERY_COMPANY.getActionName());
		// 执行分发执行对应Action
		Response<List<LogisticsCompanyDTO>> response = deliveryService.execute(request);
		return response;
	}
	
	
}
