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
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/5/24.
 */
@Controller
public class UpdateEmployeeStatusAction extends TransAction {
    @Resource
    private EmployeeManager employeeManager;
    @Override
    protected EmployeeResponse doTransaction(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getParam("employeeDTO");
        if(employeeDTO == null){
            throw new EmployeeException(ResponseCode.P_PARAM_NULL,"employeeDTO is null");
        }

        Boolean result = this.employeeManager.updateemployeeStatus(employeeDTO);
        return new EmployeeResponse(result);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_EMPLOYEE_STATUS.getActionName();
    }
}
