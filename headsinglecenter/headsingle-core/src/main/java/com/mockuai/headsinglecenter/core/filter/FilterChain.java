package com.mockuai.headsinglecenter.core.filter;

import java.util.List;

import com.mockuai.headsinglecenter.common.api.HeadSingleResponse;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.headsinglecenter.core.service.RequestContext;

public interface FilterChain {

    /**
     * 在拦截操作方法之前调用，如果所有filter成功调用，则返回true
     * 否则返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws HeadSingleException 处理异常时抛出
     */
    public HeadSingleResponse before(RequestContext ctx) throws HeadSingleException;

    /**
     * 在拦截方法后调用
     * 调用成功返回true， 失败返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws HeadSingleException 处理异常时抛出
     */
    public HeadSingleResponse after(RequestContext ctx) throws HeadSingleException;

    public List<Filter> getFilters() throws HeadSingleException;
}