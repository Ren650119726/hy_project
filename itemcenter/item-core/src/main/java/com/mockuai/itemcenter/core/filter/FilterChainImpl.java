package com.mockuai.itemcenter.core.filter;

import java.util.List;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.service.RequestContext;

public class FilterChainImpl implements FilterChain {
	List<Filter> filters;

	public FilterChainImpl() {
	}

	public FilterChainImpl(List<Filter> filters) {
		this.filters = filters;
	}

	public ItemResponse before(RequestContext ctx) throws ItemException {
		try {
			for (Filter filter : filters) {
				if (filter.isAccept(ctx) == false){
					continue;
				}

				//执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
				ItemResponse result = filter.before(ctx);
				if(result.isSuccess() == false){
					return result;
				}
			}
		} catch (ItemException e) {
			return new ItemResponse(e.getResponseCode(), e.getMessage());
		} catch (Exception e) {
			return new ItemResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
		return new ItemResponse(ResponseCode.SUCCESS);
	}

	public ItemResponse after(RequestContext ctx) throws ItemException {
		try {
			for (Filter filter : filters) {
				if (filter.isAccept(ctx) == false){
					continue;
				}

				//执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
				ItemResponse result = filter.after(ctx);
				if(result.isSuccess() == false){
					return result;
				}
			}
		} catch (ItemException e) {
			return new ItemResponse(e.getResponseCode(), e.getMessage());
		} catch (Exception e) {
			return new ItemResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
		return new ItemResponse(ResponseCode.SUCCESS);
	}

	public void insertFilters(List<Filter> newFilters) {
		filters.addAll(0, newFilters);
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public String toString() {
		String str = "";
		for (Filter filter : filters) {
			str += filter.getClass().getCanonicalName() + ":";
		}
		return str;
	}

}