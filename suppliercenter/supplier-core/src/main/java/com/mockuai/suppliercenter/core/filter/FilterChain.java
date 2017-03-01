package com.mockuai.suppliercenter.core.filter;

import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.service.RequestContext;

import java.util.List;


public interface FilterChain {

    /**
     * 在拦截操作方法之前调用，如果所有filter成功调用，则返回true
     * 否则返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws SupplierException 处理异常时抛出
     */
    public SupplierResponse before(RequestContext ctx) throws SupplierException;

    /**
     * 在拦截方法后调用
     * 调用成功返回true， 失败返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws SupplierException 处理异常时抛出
     */
    public SupplierResponse after(RequestContext ctx) throws SupplierException;

    public List<Filter> getFilters() throws SupplierException;
}
