package com.hanshu.employee.core.service.action.employee;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.EmployeeManager;
import com.hanshu.employee.core.service.RequestContext;
import com.hanshu.employee.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/6/24.
 */
@Service
public class UpdatePasswordAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(UpdatePasswordAction.class);
    @Resource
    private EmployeeManager employeeManager;

    @Override
    protected EmployeeResponse doTransaction(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        Long employeeId = (Long) request.getParam("employeeId");
        String password = (String) request.getParam("password");

        if(employeeId == null){
            log.error("employee id is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL,"employee id is null");
        }

        if(password == null){
            log.error("password id is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL,"password id is null");
        }

        Boolean result = employeeManager.updatePassword(employeeId, password);
        return new EmployeeResponse(result);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_PASSWORD.getActionName();
    }
}
