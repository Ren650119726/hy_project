package com.mockuai.dts.client.impl;

import javax.annotation.Resource;

import com.mockuai.dts.client.TranslogExportClient;
import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.domain.TranslogExportQTO;

public class TranslogExportClientImpl implements TranslogExportClient{

	@Resource
	private DtsService dtsService;
	
	@Override
	public Response<Boolean> exportDatas(TranslogExportQTO query, String appkey) {
		Request request = new DtsRequest();
		request.setCommand(ActionEnum.EXPORT_TRANSLOG.getActionName());
		request.setParam("translogExportQTO", query);
		request.setParam("appKey", appkey);
		Response response = dtsService.execute(request);
		return response;
	}

}
