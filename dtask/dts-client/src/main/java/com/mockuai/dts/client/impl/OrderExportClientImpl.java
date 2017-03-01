package com.mockuai.dts.client.impl;

import javax.annotation.Resource;

import com.mockuai.dts.client.OrderExportClient;
import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.domain.OrderExportQTO;

public class OrderExportClientImpl implements OrderExportClient {

	@Resource
	private DtsService dtsService;

	public Response<Boolean> exportOrders(OrderExportQTO query, String appKey) {
		Request request = new DtsRequest();
		request.setParam("orderExportQTO", query);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.EXPORT_ORDER.getActionName());
		return dtsService.execute(request);
	}

}
