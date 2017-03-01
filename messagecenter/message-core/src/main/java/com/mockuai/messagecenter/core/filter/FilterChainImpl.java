package com.mockuai.messagecenter.core.filter;

import java.util.List;

import com.mockuai.messagecenter.common.api.MessageResponse;
import com.mockuai.messagecenter.common.constant.ResponseCode;
import com.mockuai.messagecenter.core.exception.MessageException;
import com.mockuai.messagecenter.core.service.RequestContext;



public class FilterChainImpl implements FilterChain {
	List<Filter>  filters;
	
	public FilterChainImpl(){}
	
	public FilterChainImpl(List<Filter>  filters) {
		this.filters = filters;
	}
	
	public MessageResponse before(RequestContext ctx)  throws MessageException{
		try {
			for (Filter filter : filters) {
				if (filter.isAccept(ctx) == false){
					continue;
				} 
				
				//执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
				MessageResponse result = filter.before(ctx);
				if(result.isSuccess() == false){
					return result;
				}
			}
		} catch (MessageException e) {
			return new MessageResponse(e.getResponseCode(), e.getMessage());
		} catch (Exception e) {
			return new MessageResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
		return new MessageResponse(ResponseCode.REQUEST_SUCCESS);
	}

	public MessageResponse after(RequestContext ctx)  throws MessageException {
		try {
			for (Filter filter : filters) {
				if (filter.isAccept(ctx) == false){
					continue;
				}
				
				//执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
				MessageResponse result = filter.after(ctx);
				if(result.isSuccess() == false){
					return result;
				}
			}
		} catch (MessageException e) {
			return new MessageResponse(e.getResponseCode(), e.getMessage());
		} catch (Exception e) {
			return new MessageResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
		return new MessageResponse(ResponseCode.REQUEST_SUCCESS);
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