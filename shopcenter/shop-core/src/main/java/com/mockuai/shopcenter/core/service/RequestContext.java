package com.mockuai.shopcenter.core.service;


import com.mockuai.shopcenter.api.ShopResponse;

/**
 * 当前请求的上下文容器
 * 
 * @author wujin.zzq
 */
public class RequestContext extends Context {
	private static final long serialVersionUID = 1L;

	/**
	 * 系统级别的上下文容器
	 */
	private AppContext appContext;

	/**
	 * 当前请求的请求对象
	 */
	private ShopRequest request;

	/**
	 * 返回的response对象
	 */
	@SuppressWarnings("rawtypes")
	private ShopResponse response;

	/**
	 * @param appContext
	 * @param request
	 */
	public RequestContext(AppContext appContext, ShopRequest request) {
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
	public ShopRequest getRequest() {
		return request;
	}

	/**
	 * @return the response
	 */
	@SuppressWarnings("rawtypes")
	public ShopResponse getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	@SuppressWarnings("rawtypes")
	public void setResponse(ShopResponse response) {
		this.response = response;
	}
}
