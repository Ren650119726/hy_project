package com.mockuai.shopcenter.core.filter;

import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.service.RequestContext;

import java.util.List;

public interface FilterChain {

	/**
	 * 在拦截操作方法之前调用，如果所有filter成功调用，则返回true 否则返回false
	 * 
	 * @param ctx
	 *            当前请求上下文容器
	 */
	public boolean before(RequestContext ctx) throws ShopException;

	/**
	 * 在拦截方法后调用 调用成功返回true， 失败返回false
	 * 
	 * @param ctx
	 *            当前请求上下文容器
	 */
	public boolean after(RequestContext ctx) throws ShopException;

	public List<Filter> getFilters() throws ShopException;
}
