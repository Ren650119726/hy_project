package com.mockuai.deliverycenter.core.api.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.deliverycenter.common.api.DeliveryService;
import com.mockuai.deliverycenter.common.api.Request;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.core.service.RequestDispatcher;

public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	private RequestDispatcher requestDispatcher;

	/**
	 * 服务端接口执行
	 **/
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Response execute(Request request) {
		// 代理基础的request
		Response response = requestDispatcher.dispatch(new RequestAdapter(
				request));
		return response;
	}
}
