package com.mockuai.itemcenter.core.api.impl;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.core.service.RequestDispatcher;

public class ItemServiceImpl implements ItemService {

	@Resource
	private RequestDispatcher requestDispatcher;

	/**
	 * 服务端接口执行
	 **/
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Response execute(Request request) {

		long start = System.currentTimeMillis();
		// 代理基础的request
		Response response = requestDispatcher.dispatch(new RequestAdapter(
				request));
        long time = System.currentTimeMillis()-start;
		return response;
	}

	@Override
	public Response addItem(ItemDTO itemDTO) {
		return new ItemResponse(new ItemDTO());
	}
}
