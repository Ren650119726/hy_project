package com.mockuai.tradecenter.core.api.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.core.service.RequestDispatcher;

import javax.annotation.Resource;

public class TradeServiceImpl implements TradeService{

	@Autowired
	private RequestDispatcher requestDispatcher;
	
	/**
	 * 服务端接口执行
	 **/
	@SuppressWarnings({"rawtypes" })
	@Override
	public Response execute(Request request) {
		// 代理基础的request
		Response response = requestDispatcher.dispatch(new RequestAdapter(request));
		return response;
	}

	public RequestDispatcher getRequestDispatcher() {
		return requestDispatcher;
	}

	public void setRequestDispatcher(RequestDispatcher requestDispatcher) {
		this.requestDispatcher = requestDispatcher;
	}
}
