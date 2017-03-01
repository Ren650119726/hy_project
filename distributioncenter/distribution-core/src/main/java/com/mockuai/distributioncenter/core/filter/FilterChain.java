package com.mockuai.distributioncenter.core.filter;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

public interface FilterChain {

    /**
     * 在拦截操作方法之前调用，如果所有filter成功调用，则返回true
     * 否则返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws DistributionException 处理异常时抛出
     */
     DistributionResponse before(RequestContext ctx) throws DistributionException;

    /**
     * 在拦截方法后调用
     * 调用成功返回true， 失败返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws DistributionException 处理异常时抛出
     */
     DistributionResponse after(RequestContext ctx) throws DistributionException;

     List<Filter> getFilters() throws DistributionException;
}