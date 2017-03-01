package com.mockuai.imagecenter.core.api.impl;


import com.mockuai.imagecenter.common.api.ImageService;
import com.mockuai.imagecenter.common.api.action.Request;
import com.mockuai.imagecenter.common.api.action.Response;
import com.mockuai.imagecenter.core.service.RequestDispatcher;

import javax.annotation.Resource;
public class ImageServiceImpl implements ImageService {

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
