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
public class AddEmployeeAction extends TransAction {
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
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "employeeDTO不能为空");
        }
        if (employeeDTO.getUserName() == null) {
            log.error("user name is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "用户名不能为空");
        }
        if (employeeDTO.getPassword() == null) {
            log.error("password is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "密码不能为空");
        }
        if (employeeDTO.getName() == null) {
            log.error("name is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "姓名不能为空");
        }
        if (employeeDTO.getRoleId() == null) {
            log.error("user role is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "管理员角色类型为空");
        }
        if (employeeDTO.getStatus() == null) {
            log.error("status is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "管理员状态不能为空");
        }

        //参数有效校验
        //用户名判重名
        EmployeeDTO storedEmployeeDTO = this.employeeManager.getEmployeeByUserName(employeeDTO.getUserName());
        if (storedEmployeeDTO != null) {
            log.error("user name already exist, user name = {}", employeeDTO.getUserName());
            throw new EmployeeException(ResponseCode.P_PARAM_INVALID, "用户名已存在");
        }


        employeeDTO.setIsSuper(0);
        Long id = this.employeeManager.addEmployee(employeeDTO);

        EmployeeDTO rtnEmployeeDTO = this.employeeManager.getEmployeeById(id);
        rtnEmployeeDTO.setPassword(null);

        return new EmployeeResponse(rtnEmployeeDTO);


    }

    @Override
    public String getName() {
        return ActionEnum.ADD_EMPLOYEE.getActionName();
    }
}
