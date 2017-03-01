package com.mockuai.tradecenter.core.filter;

import java.util.List;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;

public class FilterChainImpl implements FilterChain {
	List<Filter>  filters;
	
	public FilterChainImpl(){}
	
	public FilterChainImpl(List<Filter>  filters) {
		this.filters = filters;
	}
	
	public TradeResponse before(RequestContext ctx)  throws TradeException{
		try {
			for (Filter filter : filters) {
				if (filter.isAccept(ctx) == false){
					continue;
				}
				
				//执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
				TradeResponse result = filter.before(ctx);
				if(result.isSuccess() == false){
					return result;
				}
			}
		} catch (Exception e) {
			throw new TradeException(e);
		}
		return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
	}

	public TradeResponse after(RequestContext ctx)  throws TradeException {
		try {
			for (Filter filter : filters) {
				if (filter.isAccept(ctx) == false){
					continue;
				}
				
				//执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
				TradeResponse result = filter.after(ctx);
				if(result.isSuccess() == false){
					return result;
				}
			}
		} catch (Exception e) {
			throw new TradeException(e);
		}
		return new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
	}
	
	public void insertFilters(List<Filter>  newFilters) {
		filters.addAll(0, newFilters);
	}
	
	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public String toString(){
		String str = "";
		for (Filter filter : filters) {
			str+= filter.getClass().getCanonicalName()+":";
		}
		return str;
	}
}