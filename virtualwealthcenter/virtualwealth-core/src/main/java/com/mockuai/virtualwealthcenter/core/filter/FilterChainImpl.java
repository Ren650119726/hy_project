package com.mockuai.virtualwealthcenter.core.filter;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;

import java.util.List;

public class FilterChainImpl implements FilterChain {
    List<Filter> filters;

    public FilterChainImpl() {
    }

    public FilterChainImpl(List<Filter> filters) {
        this.filters = filters;
    }

    public VirtualWealthResponse before(RequestContext ctx) throws VirtualWealthException {
        try {
            for (Filter filter : filters) {
                if (filter.isAccept(ctx) == false) {
                    continue;
                }

                //执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
                VirtualWealthResponse result = filter.before(ctx);
                if (result.isSuccess() == false) {
                    return result;
                }
            }
        } catch (Exception e) {
            throw new VirtualWealthException(e);
        }
        return new VirtualWealthResponse(ResponseCode.SUCCESS);
    }

    public VirtualWealthResponse after(RequestContext ctx) throws VirtualWealthException {
        try {
            for (Filter filter : filters) {
                if (filter.isAccept(ctx) == false) {
                    continue;
                }

                //执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
                VirtualWealthResponse result = filter.after(ctx);
                if (result.isSuccess() == false) {
                    return result;
                }
            }
        } catch (Exception e) {
            throw new VirtualWealthException(e);
        }
        return new VirtualWealthResponse(ResponseCode.SUCCESS);
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