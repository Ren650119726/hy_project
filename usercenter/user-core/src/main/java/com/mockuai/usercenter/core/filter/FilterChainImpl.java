package com.mockuai.usercenter.core.filter;

import java.util.List;

import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.service.RequestContext;



public class FilterChainImpl implements FilterChain {
	List<Filter>  filters;
	
	public FilterChainImpl(){}
	
	public FilterChainImpl(List<Filter>  filters) {
		this.filters = filters;
	}
	
	public UserResponse before(RequestContext ctx)  throws UserException{
		try {
			for (Filter filter : filters) {
				if (filter.isAccept(ctx) == false){
					continue;
				} 
				
				//执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
				UserResponse result = filter.before(ctx);
				if(result.isSuccess() == false){
					return result;
				}
			}
		} catch (UserException e) {
			return new UserResponse(e.getResponseCode(), e.getMessage());
		} catch (Exception e) {
			return new UserResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
		return new UserResponse(ResponseCode.REQUEST_SUCCESS);
	}

	public UserResponse after(RequestContext ctx)  throws UserException {
		try {
			for (Filter filter : filters) {
				if (filter.isAccept(ctx) == false){
					continue;
				}
				
				//执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
				UserResponse result = filter.after(ctx);
				if(result.isSuccess() == false){
					return result;
				}
			}
		} catch (UserException e) {
			return new UserResponse(e.getResponseCode(), e.getMessage());
		} catch (Exception e) {
			return new UserResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
		return new UserResponse(ResponseCode.REQUEST_SUCCESS);
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