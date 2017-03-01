package com.mockuai.dts.client.impl;

import javax.annotation.Resource;

import com.mockuai.dts.client.SellerUserExportClient;
import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.constant.ActionEnum;
import com.mockuai.dts.common.domain.SellerUserExportQTO;

public class SellerUserExportClientImpl implements SellerUserExportClient {

	@Resource
	private DtsService dtsService;
	
	@SuppressWarnings("unchecked")
	public Response<Boolean> exportDatas(SellerUserExportQTO query, String appKey) {
		Request request = new DtsRequest();
		request.setParam("sellerUserExportQTO", query);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.EXPORT_SELLER_USER.getActionName());
		return dtsService.execute(request);
	}

}
