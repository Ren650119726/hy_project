package com.mockuai.dts.core.service;

import com.mockuai.dts.common.api.action.DtsRequest;
import com.mockuai.dts.common.api.action.DtsResponse;


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
	private DtsRequest request;

	/**
	 * 返回的response对象
	 */
	@SuppressWarnings("rawtypes")
	private DtsResponse response;

	/**
	 * @param appContext
	 * @param request
	 */
	public RequestContext(AppContext appContext, DtsRequest request) {
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
	public DtsRequest getRequest() {
		return request;
	}

	/**
	 * @return the response
	 */
	@SuppressWarnings("rawtypes")
	public DtsResponse getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	@SuppressWarnings("rawtypes")
	public void setResponse(DtsResponse response) {
		this.response = response;
	}
}
