package com.hanshu.employee.core.filter;

import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.service.RequestContext;

import java.util.List;


public class FilterChainImpl implements FilterChain {
    List<Filter> filters;

    public FilterChainImpl() {
    }

    public FilterChainImpl(List<Filter> filters) {
        this.filters = filters;
    }

    public EmployeeResponse before(RequestContext ctx) throws EmployeeException {
        try {
            for (Filter filter : filters) {
                if (filter.isAccept(ctx) == false) {
                    continue;
                }

                //执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
                EmployeeResponse result = filter.before(ctx);
                if (result.isSuccess() == false) {
                    return result;
                }
            }
        } catch (EmployeeException e) {
            return new EmployeeResponse(e.getResponseCode(), e.getMessage());
        } catch (Exception e) {
            return new EmployeeResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
        return new EmployeeResponse(ResponseCode.REQUEST_SUCCESS);
    }

    public EmployeeResponse after(RequestContext ctx) throws EmployeeException {
        try {
            for (Filter filter : filters) {
                if (filter.isAccept(ctx) == false) {
                    continue;
                }

                //执行当前filter，如果执行失败，则直接return，否则继续执行下一个filter
                EmployeeResponse result = filter.after(ctx);
                if (result.isSuccess() == false) {
                    return result;
                }
            }
        } catch (EmployeeException e) {
            return new EmployeeResponse(e.getResponseCode(), e.getMessage());
        } catch (Exception e) {
            return new EmployeeResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
        return new EmployeeResponse(ResponseCode.REQUEST_SUCCESS);
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