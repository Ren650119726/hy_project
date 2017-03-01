package com.mockuai.itemcenter.core.filter;

import java.util.List;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.RequestContext;

public interface FilterChain {

	/**
	 * 在拦截操作方法之前调用，如果所有filter成功调用，则返回true 否则返回false
	 * 
	 * @param ctx
	 *            当前请求上下文容器
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 *             处理异常时抛出
	 */
	public ItemResponse before(RequestContext ctx) throws ItemException;

	/**
	 * 在拦截方法后调用 调用成功返回true， 失败返回false
	 * 
	 * @param ctx
	 *            当前请求上下文容器
	 * @throws ItemException
	 *             处理异常时抛出
	 */
	public ItemResponse after(RequestContext ctx) throws ItemException;

	public List<Filter> getFilters() throws ItemException;
}
