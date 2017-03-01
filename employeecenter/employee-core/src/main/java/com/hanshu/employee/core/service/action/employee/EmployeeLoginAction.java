package com.hanshu.employee.core.service.action.employee;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.EmployeeStatus;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.EmployeeDTO;
import com.hanshu.employee.common.qto.EmployeeQTO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.service.action.Action;
import com.hanshu.employee.core.manager.EmployeeManager;
import com.hanshu.employee.core.service.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/5/9.
 */
@Service
public class EmployeeLoginAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(EmployeeLoginAction.class);

    @Resource
    private EmployeeManager employeeManager;

    @Override
    public EmployeeResponse execute(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        EmployeeQTO employeeQTO = (EmployeeQTO) request.getParam("employeeQTO");

        //参数判空校验
        if (employeeQTO == null) {
            log.error("employeeQTO is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "managerQTO不能为空");
        }
        if (employeeQTO.getUserName() == null) {
            log.error("user name is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "用户名不能为空");
        }
        if (employeeQTO.getPassword() == null) {
            log.error("password is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "密码不能为空");
        }
        EmployeeDTO storedEmployeeDTO = this.employeeManager.employeeLogin(employeeQTO);

        if (storedEmployeeDTO == null) {
            log.error("user name or password is error");
            throw new EmployeeException(ResponseCode.B_PASSWORD_ERROR, "用户名或密码错误");
        }

        if (storedEmployeeDTO.getStatus().equals(EmployeeStatus.FORBIDDEN.getCode())) {
            log.error("user freeze, user name: {}", storedEmployeeDTO.getUserName());
            throw new EmployeeException(ResponseCode.B_USER_FREEZE, "账户被冻结");
        }

        this.employeeManager.updateLastLogin(storedEmployeeDTO.getId());
        return new EmployeeResponse(storedEmployeeDTO);


    }

    @Override
    public String getName() {
        return ActionEnum.EMPLOYEE_LOGIN.getActionName();
    }
}
