package com.mockuai.toolscenter.core.api.impl;

import com.mockuai.toolscenter.common.api.ToolsService;
import com.mockuai.toolscenter.common.api.Request;
import com.mockuai.toolscenter.common.api.Response;
import com.mockuai.toolscenter.core.service.RequestDispatcher;
import org.springframework.beans.factory.annotation.Autowired;

public class ToolsServiceImpl implements ToolsService {

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
