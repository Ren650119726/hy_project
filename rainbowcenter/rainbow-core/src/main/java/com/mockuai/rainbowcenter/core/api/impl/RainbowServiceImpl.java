package com.mockuai.rainbowcenter.core.api.impl;

import com.mockuai.rainbowcenter.common.api.RainbowService;

import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.common.api.Response;
import com.mockuai.rainbowcenter.core.service.RequestDispatcher;
import org.springframework.beans.factory.annotation.Autowired;

public class RainbowServiceImpl implements RainbowService {

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
