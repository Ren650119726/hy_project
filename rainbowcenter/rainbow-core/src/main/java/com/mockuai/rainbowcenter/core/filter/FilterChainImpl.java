package com.mockuai.rainbowcenter.core.filter;

import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.service.RequestContext;

import java.util.List;

public class FilterChainImpl implements FilterChain {
	List<Filter>  filters;
	
	public FilterChainImpl(){}
	
	public FilterChainImpl(List<Filter>  filters) {
		this.filters = filters;
	}
	
	public boolean before(RequestContext ctx)  throws RainbowException {
		try {
			for (Filter filter : filters) {
				if (filter.isAccept(ctx) == false){
					continue;
				} 
				
				//执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
				boolean result = filter.before(ctx);
				if(result == false){
					return false;
				}
			}
		} catch (Exception e) {
			throw new RainbowException(e);
		}
		return true;
	}

	public boolean after(RequestContext ctx)  throws RainbowException {
		try {
			for (Filter filter : filters) {
				if (filter.isAccept(ctx) == false){
					continue;
				}
				
				//执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
				boolean result = filter.after(ctx);
				if(result == false){
					return false;
				}
			}
		} catch (Exception e) {
			throw new RainbowException(e);
		}
		return true;
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