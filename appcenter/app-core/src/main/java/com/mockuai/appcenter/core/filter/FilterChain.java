package com.mockuai.appcenter.core.filter;

import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.service.RequestContext;

import java.util.List;

public interface FilterChain{
	/**
     * 在拦截操作方法之前调用，如果所有filter成功调用，则返回true
     * 否则返回false
     * @param ctx 当前请求上下文容器
	 * @throws AppException 处理异常时抛出
     */
	public boolean before(RequestContext ctx)  throws AppException;
	
	/**
	 * 在拦截方法后调用
	 * 调用成功返回true， 失败返回false
	 * @param ctx 当前请求上下文容器
	 * @throws AppException 处理异常时抛出
	 */
	public boolean after(RequestContext ctx)  throws AppException;
	
	public List<Filter> getFilters() throws AppException;
}
