package com.mockuai.mainweb.core.api.impl;


import com.mockuai.mainweb.common.api.MainWebService;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.api.action.Response;
import com.mockuai.mainweb.core.service.RequestDispatcher;

import javax.annotation.Resource;
public class MainWebServiceImpl implements MainWebService {

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
