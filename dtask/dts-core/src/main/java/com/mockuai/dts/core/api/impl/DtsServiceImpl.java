package com.mockuai.dts.core.api.impl;

import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.api.action.DtsResponse;
import com.mockuai.dts.common.api.action.Request;
import com.mockuai.dts.common.api.action.Response;

import javax.annotation.Resource;

public class DtsServiceImpl implements DtsService {

	@Resource
	private RequestDispatcher requestDispatcher;

	/**
	 * 服务端接口执行
	 **/
	public Response execute(Request request) {
		// 代理基础的request
		Response response = requestDispatcher.dispatch(new RequestAdapter(request));
		return response;
	}

}
