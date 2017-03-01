package com.mockuai.itemcenter.core.service;

import com.mockuai.itemcenter.common.api.ItemResponse;


/**
 * 当前请求的上下文容器
 * 
 * @author wujin.zzq
 */
public class RequestContext extends Context {
	private static final long serialVersionUID = -1054539809433963262L;

	/**
	 * 系统级别的上下文容器
	 */
	private AppContext appContext;

	/**
	 * 当前请求的请求对象
	 */
	private ItemRequest request;

	/**
	 * 返回的response对象
	 */
	@SuppressWarnings("rawtypes")
	private ItemResponse response;

	/**
	 * @param appContext
	 * @param serverSideRequest
	 */
	public RequestContext(AppContext appContext, ItemRequest request) {
		this.appContext = appContext;
		this.request = request;
	}

	/**
	 * @return the appContext
	 */
	public AppContext getAppContext() {
		return appContext;
	}

	/**
	 * @return the request
	 */
	public ItemRequest getRequest() {
		return request;
	}

	/**
	 * @return the response
	 */
	@SuppressWarnings("rawtypes")
	public ItemResponse getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	@SuppressWarnings("rawtypes")
	public void setResponse(ItemResponse response) {
		this.response = response;
	}
}
