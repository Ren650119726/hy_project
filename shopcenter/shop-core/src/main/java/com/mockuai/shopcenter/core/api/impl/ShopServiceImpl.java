package com.mockuai.shopcenter.core.api.impl;

import com.mockuai.shopcenter.api.Request;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.api.ShopService;
import com.mockuai.shopcenter.core.service.RequestDispatcher;

import javax.annotation.Resource;

/**
 * @author ziqi
 */
public class ShopServiceImpl implements ShopService {

	@Resource
	private RequestDispatcher requestDispatcher;

	/**
	 * 服务端接口执行
	 **/
	@Override
	public Response execute(Request request) {

		Response response = requestDispatcher.dispatch(new RequestAdapter(request));
		return response;
	}

}
