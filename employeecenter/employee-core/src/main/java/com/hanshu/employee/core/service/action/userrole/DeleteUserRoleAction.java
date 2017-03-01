package com.hanshu.employee.core.service.action.userrole;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.UserRoleManager;
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
public class DeleteUserRoleAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(DeleteUserRoleAction.class);

    @Resource
    private UserRoleManager userRoleManager;

    @Override
    protected EmployeeResponse doTransaction(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        Long userRoleId = (Long) request.getParam("userRoleId");

        //参数判空校验
        if (userRoleId == null) {
            log.error("userRoleId id is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "userRoleId不能为空");
        }

        Boolean result = this.userRoleManager.deleteUserRole(userRoleId);
        return new EmployeeResponse(result);
    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_USER_ROLE.getActionName();
    }
}
