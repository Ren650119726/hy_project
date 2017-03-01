package com.hanshu.employee.core.service.action.employee;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.EmployeeDTO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.EmployeeManager;
import com.hanshu.employee.core.service.action.Action;
import com.hanshu.employee.core.service.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/5/9.
 */
@Service
public class GetEmployeeAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetEmployeeAction.class);

    @Resource
    private EmployeeManager employeeManager;

    @Override
    public EmployeeResponse execute(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        Long employeeId = (Long) request.getParam("employeeId");

        //参数判空校验
        if (employeeId == null) {
            log.error("employeeId is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "employeeId不能为空");
        }

        EmployeeDTO employeeDTO = this.employeeManager.getEmployeeById(employeeId);
        employeeDTO.setPassword(null);


        return new EmployeeResponse(employeeDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_EMPLOYEE_BY_ID.getActionName();
    }
}
