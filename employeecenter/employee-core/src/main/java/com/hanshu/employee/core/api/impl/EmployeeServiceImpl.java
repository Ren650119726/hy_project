package com.hanshu.employee.core.api.impl;

import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.EmployeeService;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.core.service.RequestDispatcher;

import javax.annotation.Resource;

/**
 * Created by idoud on 4/24/15.
 */
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private RequestDispatcher requestDispatcher;

    public EmployeeResponse execute(Request request) {
        EmployeeResponse res = requestDispatcher.dispatch(new RequestAdapter(request));
        return res;
    }
}
