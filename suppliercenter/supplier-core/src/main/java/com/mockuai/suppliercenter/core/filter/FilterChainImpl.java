package com.mockuai.suppliercenter.core.filter;

import com.mockuai.suppliercenter.common.api.SupplierResponse;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.service.RequestContext;

import java.util.List;


public class FilterChainImpl implements FilterChain {
    List<Filter> filters;

    public FilterChainImpl() {
    }

    public FilterChainImpl(List<Filter> filters) {
        this.filters = filters;
    }

    public SupplierResponse before(RequestContext ctx) throws SupplierException {
        try {
            for (Filter filter : filters) {
                if (filter.isAccept(ctx) == false) {
                    continue;
                }

                //执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
                SupplierResponse result = filter.before(ctx);
                if (result.isSuccess() == false) {
                    return result;
                }
            }
        } catch (SupplierException e) {
            return new SupplierResponse(e.getResponseCode(), e.getMessage());
        } catch (Exception e) {
            return new SupplierResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
        return new SupplierResponse(ResponseCode.REQUEST_SUCCESS);
    }

    public SupplierResponse after(RequestContext ctx) throws SupplierException {
        try {
            for (Filter filter : filters) {
                if (filter.isAccept(ctx) == false) {
                    continue;
                }

                //执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
                SupplierResponse result = filter.after(ctx);
                if (result.isSuccess() == false) {
                    return result;
                }
            }
        } catch (SupplierException e) {
            return new SupplierResponse(e.getResponseCode(), e.getMessage());
        } catch (Exception e) {
            return new SupplierResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
        return new SupplierResponse(ResponseCode.REQUEST_SUCCESS);
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