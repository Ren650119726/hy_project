package com.mockuai.messagecenter.core.filter;

import java.util.List;





import com.mockuai.messagecenter.common.api.Response;
import com.mockuai.messagecenter.common.api.MessageResponse;
import com.mockuai.messagecenter.core.exception.MessageException;
import com.mockuai.messagecenter.core.service.RequestContext;


public interface FilterChain{
	
	/**
     * 在拦截操作方法之前调用，如果所有filter成功调用，则返回true
     * 否则返回false
     * @param ctx 当前请求上下文容器
	 * @throws MessageException 处理异常时抛出
     */
	public MessageResponse before(RequestContext ctx)  throws MessageException;
	
	/**
	 * 在拦截方法后调用
	 * 调用成功返回true， 失败返回false
	 * @param ctx 当前请求上下文容器
	 * @throws MessageException 处理异常时抛出
	 */
	public MessageResponse after(RequestContext ctx)  throws MessageException;
	
	public List<Filter> getFilters() throws MessageException;
}
