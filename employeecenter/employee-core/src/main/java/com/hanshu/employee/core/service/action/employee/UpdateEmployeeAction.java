package com.hanshu.employee.core.service.action.employee;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.EmployeeDTO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.EmployeeManager;
import com.hanshu.employee.core.service.RequestContext;
import com.hanshu.employee.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/5/9.
 */
@Service
public class UpdateEmployeeAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AddEmployeeAction.class);

    @Resource
    private EmployeeManager employeeManager;

    @Override
    protected EmployeeResponse doTransaction(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getParam("employeeDTO");

        //参数判空校验
        if (employeeDTO == null) {
            log.error("employeeDTO is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "managerDTO不能为空");
        }

        Boolean result = this.employeeManager.updateEmployee(employeeDTO);
        return new EmployeeResponse(result);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_EMPLOYEE.getActionName();
    }
}
