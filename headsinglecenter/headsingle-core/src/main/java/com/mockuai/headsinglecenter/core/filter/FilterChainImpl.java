package com.mockuai.headsinglecenter.core.filter;

import java.util.List;

import com.mockuai.headsinglecenter.common.api.HeadSingleResponse;
import com.mockuai.headsinglecenter.common.constant.ResponseCode;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.headsinglecenter.core.service.RequestContext;

public class FilterChainImpl implements FilterChain {
    List<Filter> filters;

    public FilterChainImpl() {
    }

    public FilterChainImpl(List<Filter> filters) {
        this.filters = filters;
    }

    public HeadSingleResponse before(RequestContext ctx) throws HeadSingleException {
        try {
            for (Filter filter : filters) {
                if (filter.isAccept(ctx) == false) {
                    continue;
                }

                //执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
                HeadSingleResponse result = filter.before(ctx);
                if (result.isSuccess() == false) {
                    return result;
                }
            }
        } catch (Exception e) {
            throw new HeadSingleException(e);
        }
        return new HeadSingleResponse(ResponseCode.SUCCESS);
    }

    public HeadSingleResponse after(RequestContext ctx) throws HeadSingleException {
        try {
            for (Filter filter : filters) {
                if (filter.isAccept(ctx) == false) {
                    continue;
                }

                //执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
                HeadSingleResponse result = filter.after(ctx);
                if (result.isSuccess() == false) {
                    return result;
                }
            }
        } catch (Exception e) {
            throw new HeadSingleException(e);
        }
        return new HeadSingleResponse(ResponseCode.SUCCESS);
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