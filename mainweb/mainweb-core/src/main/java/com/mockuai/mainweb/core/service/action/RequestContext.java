package com.mockuai.mainweb.core.service.action;

import com.mockuai.mainweb.common.api.action.MainWebRequest;
import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.core.service.AppContext;
import com.mockuai.mainweb.core.service.Context;

public class RequestContext extends Context {

    private static final long serialVersionUID = -1054539809433963262L;

    /**
     * 系统级别的上下文容器
     */
    private AppContext appContext;

    /**
     * 当前请求的请求对象
     */
    private MainWebRequest request;

    /**
     * 返回的response对象
     */
    @SuppressWarnings("rawtypes")
    private MainWebResponse response;

    /**
     * @param appContext
     * @param request
     */
    public RequestContext(AppContext appContext, MainWebRequest request) {
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
    public MainWebRequest getRequest() {
        return request;
    }

    /**
     * @return the response
     */
    @SuppressWarnings("rawtypes")
    public MainWebResponse getResponse() {
        return response;
    }

    /**
     * @param response
     *            the response to set
     */
    @SuppressWarnings("rawtypes")
    public void setResponse(MainWebResponse response) {
        this.response = response;
    }
}
