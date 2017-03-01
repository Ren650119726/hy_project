package com.mockuai.imagecenter.core.service.action;

import com.mockuai.imagecenter.common.api.action.ImageRequest;
import com.mockuai.imagecenter.common.api.action.ImageResponse;
import com.mockuai.imagecenter.core.service.AppContext;
import com.mockuai.imagecenter.core.service.Context;

public class RequestContext extends Context {

    private static final long serialVersionUID = -1054539809433963262L;

    /**
     * 系统级别的上下文容器
     */
    private AppContext appContext;

    /**
     * 当前请求的请求对象
     */
    private ImageRequest request;

    /**
     * 返回的response对象
     */
    @SuppressWarnings("rawtypes")
    private ImageResponse response;

    /**
     * @param appContext
     * @param request
     */
    public RequestContext(AppContext appContext, ImageRequest request) {
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
    public ImageRequest getRequest() {
        return request;
    }

    /**
     * @return the response
     */
    @SuppressWarnings("rawtypes")
    public ImageResponse getResponse() {
        return response;
    }

    /**
     * @param response
     *            the response to set
     */
    @SuppressWarnings("rawtypes")
    public void setResponse(ImageResponse response) {
        this.response = response;
    }
}
