package com.mockuai.virtualwealthcenter.core.filter;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;

import java.util.List;

public interface FilterChain {

    /**
     * 在拦截操作方法之前调用，如果所有filter成功调用，则返回true
     * 否则返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws VirtualWealthException 处理异常时抛出
     */
    public VirtualWealthResponse before(RequestContext ctx) throws VirtualWealthException;

    /**
     * 在拦截方法后调用
     * 调用成功返回true， 失败返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws VirtualWealthException 处理异常时抛出
     */
    public VirtualWealthResponse after(RequestContext ctx) throws VirtualWealthException;

    public List<Filter> getFilters() throws VirtualWealthException;
}