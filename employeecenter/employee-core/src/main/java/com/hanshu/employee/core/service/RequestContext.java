package com.hanshu.employee.core.service;

import com.hanshu.employee.common.api.EmployeeResponse;


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
    private UserRequest request;

    /**
     * 返回的response对象
     */
    @SuppressWarnings("rawtypes")
    private EmployeeResponse response;

    /**
     * @param appContext
     * @param request
     */
    public RequestContext(AppContext appContext, UserRequest request) {
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
    public UserRequest getRequest() {
        return request;
    }

    /**
     * @return the response
     */
    @SuppressWarnings("rawtypes")
    public EmployeeResponse getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    @SuppressWarnings("rawtypes")
    public void setResponse(EmployeeResponse response) {
        this.response = response;
    }
}
