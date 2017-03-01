package com.mockuai.tradecenter.core.service;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.core.base.result.TradeOperResult;


/**
 * 当前请求的上下文容器
 * @author wujin.zzq
 */
public class RequestContext extends Context {
	private static final long serialVersionUID = -1054539809433963262L;
	
	/**
	 * 系统级别的上下文容器
	 */
	private AppContext appContext;
	
	/**
	 * 当前请求的请求对象
	 */
	private TradeRequest request;

	/**
	 * 返回的response对象
	 */
	@SuppressWarnings("rawtypes")
	private TradeResponse response;
	
	private TradeOperResult tradeInnerResult;
	
//	private 
	
	/**
	 * @param appContext
	 * @param serverSideRequest
	 */
	public RequestContext(AppContext appContext,TradeRequest request) {
		this.appContext = appContext;
		this.request = request;
	}

	/**
	 * @return the appContext
	 */
	public AppContext getAppContext() {
		return appContext;
	}

	/**
	 * @return the request
	 */
	public TradeRequest getRequest() {
		return request;
	}

	/**
	 * @return the response
	 */
	@SuppressWarnings("rawtypes")
	public TradeResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	@SuppressWarnings("rawtypes")
	public void setResponse(TradeResponse response) {
		this.response = response;
	}

	public TradeOperResult getTradeInnerResult() {
		return tradeInnerResult;
	}

	public void setTradeInnerResult(TradeOperResult tradeInnerResult) {
		this.tradeInnerResult = tradeInnerResult;
	}
	
	
}
