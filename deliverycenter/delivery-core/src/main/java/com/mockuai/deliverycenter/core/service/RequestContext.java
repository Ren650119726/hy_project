package com.mockuai.deliverycenter.core.service;

import com.mockuai.deliverycenter.common.api.DeliveryResponse;

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
	private DeliveryRequest request;

	/**
	 * 返回的response对象
	 */
	@SuppressWarnings("rawtypes")
	private DeliveryResponse response;

	/**
	 * @param appContext
	 * @param serverSideRequest
	 */
	public RequestContext(AppContext appContext, DeliveryRequest request) {
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
	public DeliveryRequest getRequest() {
		return request;
	}

	/**
	 * @return the response
	 */
	@SuppressWarnings("rawtypes")
	public DeliveryResponse getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	@SuppressWarnings("rawtypes")
	public void setResponse(DeliveryResponse response) {
		this.response = response;
	}
}
