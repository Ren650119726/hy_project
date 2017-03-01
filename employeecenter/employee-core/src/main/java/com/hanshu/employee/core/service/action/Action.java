package com.hanshu.employee.core.service.action;

import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.service.RequestContext;
import org.springframework.stereotype.Service;

/**
 * 操作对像基类
 *
 * @author wujin.zzq
 */
@Service
public interface Action {

    @SuppressWarnings("rawtypes")
    public EmployeeResponse execute(RequestContext context) throws EmployeeException;

    public String getName();
}
