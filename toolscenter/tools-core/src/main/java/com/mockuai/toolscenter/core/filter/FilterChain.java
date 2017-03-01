package com.mockuai.toolscenter.core.filter;

import com.mockuai.toolscenter.core.exception.ToolsException;
import com.mockuai.toolscenter.core.service.RequestContext;

import java.util.List;

public interface FilterChain{
	/**
     * 在拦截操作方法之前调用，如果所有filter成功调用，则返回true
     * 否则返回false
     * @param ctx 当前请求上下文容器
	 * @throws ToolsException 处理异常时抛出
     */
	public boolean before(RequestContext ctx)  throws ToolsException;
	
	/**
	 * 在拦截方法后调用
	 * 调用成功返回true， 失败返回false
	 * @param ctx 当前请求上下文容器
	 * @throws ToolsException 处理异常时抛出
	 */
	public boolean after(RequestContext ctx)  throws ToolsException;
	
	public List<Filter> getFilters() throws ToolsException;
}
