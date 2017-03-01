package com.mockuai.rainbowcenter.core.service;


import com.mockuai.rainbowcenter.common.api.RainbowResponse;

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
    private RainbowContext rainbowContext;

    /**
     * 当前请求的请求对象
     */
    private RainbowRequest request;

    /**
     * 返回的response对象
     */
    @SuppressWarnings("rawtypes")
    private RainbowResponse response;

    /**
     * @param rainbowContext
     * @param request
     */
    public RequestContext(RainbowContext rainbowContext, RainbowRequest request) {
        this.rainbowContext = rainbowContext;
        this.request = request;
    }

    /**
     * @return the appContext
     */
    public RainbowContext getRainbowContext() {
        return rainbowContext;
    }

    /**
     * @return the request
     */
    public RainbowRequest getRequest() {
        return request;
    }

    /**
     * @return the response
     */
    @SuppressWarnings("rawtypes")
    public RainbowResponse getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    @SuppressWarnings("rawtypes")
    public void setResponse(RainbowResponse response) {
        this.response = response;
    }
}
