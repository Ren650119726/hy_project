package com.mockuai.headsinglecenter.core.service;

import com.mockuai.headsinglecenter.common.api.HeadSingleResponse;


/**
 * 当前请求的上下文容器
 *
 * @author wujin.zzq
 */
public class RequestContext<T> extends Context {
    private static final long serialVersionUID = -1054539809433963262L;

    /**
     * 系统级别的上下文容器
     */
    private AppContext appContext;

    /**
     * 当前请求的请求对象
     */
    private HeadSingleRequest request;

    /**
     * 返回的response对象
     */
    @SuppressWarnings("rawtypes")
	private HeadSingleResponse response;

    /**
     * @param appContext
     * @param request
     */
    public RequestContext(AppContext appContext, HeadSingleRequest request) {
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
    public HeadSingleRequest getRequest() {
        return request;
    }

	public HeadSingleResponse getResponse() {
		return response;
	}

	public void setResponse(HeadSingleResponse response) {
		this.response = response;
	}
}