package com.hanshu.employee.core.service.action.userrole;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.UserRoleDTO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.service.action.Action;
import com.hanshu.employee.core.manager.UserRoleManager;
import com.hanshu.employee.core.service.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/5/9.
 */
@Service
public class GetUserRoleAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetUserRoleAction.class);

    @Resource
    private UserRoleManager userRoleManager;

    @Override
    public EmployeeResponse execute(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        Long userRoleId = (Long) request.getParam("userRoleId");

        //参数判空校验
        if (userRoleId == null) {
            log.error("userRoleId is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "userRoleId不能为空");
        }

        UserRoleDTO userRoleDTO = this.userRoleManager.getUserRoleById(userRoleId);


        return new EmployeeResponse(userRoleDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_USER_ROLE_BY_ID.getActionName();
    }
}
