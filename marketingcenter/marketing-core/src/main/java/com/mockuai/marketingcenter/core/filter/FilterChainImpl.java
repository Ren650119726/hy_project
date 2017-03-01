package com.mockuai.marketingcenter.core.filter;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.service.RequestContext;

import java.util.List;

public class FilterChainImpl implements FilterChain {
    List<Filter> filters;

    public FilterChainImpl() {
    }

    public FilterChainImpl(List<Filter> filters) {
        this.filters = filters;
    }

    public MarketingResponse before(RequestContext ctx) throws MarketingException {
        try {
            for (Filter filter : filters) {
                if (filter.isAccept(ctx) == false) {
                    continue;
                }

                //执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
                MarketingResponse result = filter.before(ctx);
                if (result.isSuccess() == false) {
                    return result;
                }
            }
        } catch (Exception e) {
            throw new MarketingException(e);
        }
        return new MarketingResponse(ResponseCode.SUCCESS);
    }

    public MarketingResponse after(RequestContext ctx) throws MarketingException {
        try {
            for (Filter filter : filters) {
                if (filter.isAccept(ctx) == false) {
                    continue;
                }

                //执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
                MarketingResponse result = filter.after(ctx);
                if (result.isSuccess() == false) {
                    return result;
                }
            }
        } catch (Exception e) {
            throw new MarketingException(e);
        }
        return new MarketingResponse(ResponseCode.SUCCESS);
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