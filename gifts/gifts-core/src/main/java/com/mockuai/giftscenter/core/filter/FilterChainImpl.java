package com.mockuai.giftscenter.core.filter;

import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.common.constant.ResponseCode;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.service.RequestContext;

import java.util.List;

public class FilterChainImpl implements FilterChain {
    List<Filter> filters;

    public FilterChainImpl() {
    }

    public FilterChainImpl(List<Filter> filters) {
        this.filters = filters;
    }

    public GiftsResponse before(RequestContext ctx) throws GiftsException {
        try {
            for (Filter filter : filters) {
                if (filter.isAccept(ctx) == false) {
                    continue;
                }

                //执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
                GiftsResponse result = filter.before(ctx);
                if (result.isSuccess() == false) {
                    return result;
                }
            }
        } catch (Exception e) {
            throw new GiftsException(e);
        }
        return new GiftsResponse(ResponseCode.SUCCESS);
    }

    public GiftsResponse after(RequestContext ctx) throws GiftsException {
        try {
            for (Filter filter : filters) {
                if (filter.isAccept(ctx) == false) {
                    continue;
                }

                //执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
                GiftsResponse result = filter.after(ctx);
                if (result.isSuccess() == false) {
                    return result;
                }
            }
        } catch (Exception e) {
            throw new GiftsException(e);
        }
        return new GiftsResponse(ResponseCode.SUCCESS);
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