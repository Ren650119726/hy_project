package com.mockuai.usercenter.core.filter;

import java.util.List;


import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.service.RequestContext;


public interface FilterChain{
	
	/**
     * 在拦截操作方法之前调用，如果所有filter成功调用，则返回true
     * 否则返回false
     * @param ctx 当前请求上下文容器
	 * @throws UserException 处理异常时抛出
     */
	public UserResponse before(RequestContext ctx)  throws UserException;
	
	/**
	 * 在拦截方法后调用
	 * 调用成功返回true， 失败返回false
	 * @param ctx 当前请求上下文容器
	 * @throws UserException 处理异常时抛出
	 */
	public UserResponse after(RequestContext ctx)  throws UserException;
	
	public List<Filter> getFilters() throws UserException;
}
