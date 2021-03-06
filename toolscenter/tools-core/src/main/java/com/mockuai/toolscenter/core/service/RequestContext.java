package com.mockuai.toolscenter.core.service;


import com.mockuai.toolscenter.common.api.ToolsResponse;

/**
 * 当前请求的上下文容器
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
	private ToolsRequest request;

	/**
	 * 返回的response对象
	 */
	@SuppressWarnings("rawtypes")
	private ToolsResponse response;
	
	/**
	 * @param appContext
	 * @param serverSideRequest
	 */
	public RequestContext(AppContext appContext,ToolsRequest request) {
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
	public ToolsRequest getRequest() {
		return request;
	}

	/**
	 * @return the response
	 */
	@SuppressWarnings("rawtypes")
	public ToolsResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	@SuppressWarnings("rawtypes")
	public void setResponse(ToolsResponse response) {
		this.response = response;
	}
}
