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
public class DeleteEmployeeAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(DeleteEmployeeAction.class);

    @Resource
    private EmployeeManager employeeManager;

    @Override
    protected EmployeeResponse doTransaction(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        Long employeeId = (Long) request.getParam("employeeId");

        //参数判空校验
        if (employeeId == null) {
            log.error("employee id is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "id不能为空");
        }

        EmployeeDTO superEmployeeDTO = this.employeeManager.getEmployeeById(employeeId);
        if (superEmployeeDTO.getIsSuper() == 1) {
            log.error("can not delete super employee");
            throw new EmployeeException(ResponseCode.P_PARAM_INVALID, "不能删除超级管理员");
        }

        Boolean result = this.employeeManager.deleteEmployee(employeeId);
        return new EmployeeResponse(result);
    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_EMPLOYEE.getActionName();
    }
}
