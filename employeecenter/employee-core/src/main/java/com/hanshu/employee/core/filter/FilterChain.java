package com.hanshu.employee.core.filter;

import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.service.RequestContext;

import java.util.List;


public interface FilterChain {

    /**
     * 在拦截操作方法之前调用，如果所有filter成功调用，则返回true
     * 否则返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws EmployeeException 处理异常时抛出
     */
    public EmployeeResponse before(RequestContext ctx) throws EmployeeException;

    /**
     * 在拦截方法后调用
     * 调用成功返回true， 失败返回false
     *
     * @param ctx 当前请求上下文容器
     * @throws EmployeeException 处理异常时抛出
     */
    public EmployeeResponse after(RequestContext ctx) throws EmployeeException;

    public List<Filter> getFilters() throws EmployeeException;
}
