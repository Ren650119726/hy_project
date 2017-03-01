package com.mockuai.marketingcenter.core.filter;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.service.RequestContext;

import java.util.List;

public interface FilterChain {

    /**
     * 在拦截操作方法之前调用，如果所有filter成功调用，则返回true
     * 否则返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws MarketingException 处理异常时抛出
     */
    public MarketingResponse before(RequestContext ctx) throws MarketingException;

    /**
     * 在拦截方法后调用
     * 调用成功返回true， 失败返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws MarketingException 处理异常时抛出
     */
    public MarketingResponse after(RequestContext ctx) throws MarketingException;

    public List<Filter> getFilters() throws MarketingException;
}