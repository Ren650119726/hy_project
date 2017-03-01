package com.mockuai.distributioncenter.core.service;

import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.core.api.RequestAdapter;

/**
 * Created by duke on 15/10/28.
 */
public class RequestContext<T> extends Context {
    /**
     * 系统级别的上下文容器
     */
    private AppContext appContext;

    /**
     * 当前请求的请求对象
     */
    private Request request;

    /**
     * 返回的response对象
     */
    @SuppressWarnings("rawtypes")
    private Response response;

    /**
     * @param appContext
     * @param request
     */
    public RequestContext(AppContext appContext, Request request) {
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
    public Request getRequest() {
        return request;
    }

    public RequestAdapter getRequestAdapter() {
        return new RequestAdapter(request);
    }

    /**
     * @return the response
     */
    @SuppressWarnings("rawtypes")
    public Response getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    @SuppressWarnings("rawtypes")
    public void setResponse(Response response) {
        this.response = response;
    }
}
