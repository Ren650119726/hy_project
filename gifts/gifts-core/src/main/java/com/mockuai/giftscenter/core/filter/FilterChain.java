package com.mockuai.giftscenter.core.filter;

import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.service.RequestContext;

import java.util.List;

public interface FilterChain {

    /**
     * 在拦截操作方法之前调用，如果所有filter成功调用，则返回true
     * 否则返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws GiftsException 处理异常时抛出
     */
    public GiftsResponse before(RequestContext ctx) throws GiftsException;

    /**
     * 在拦截方法后调用
     * 调用成功返回true， 失败返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws GiftsException 处理异常时抛出
     */
    public GiftsResponse after(RequestContext ctx) throws GiftsException;

    public List<Filter> getFilters() throws GiftsException;
}