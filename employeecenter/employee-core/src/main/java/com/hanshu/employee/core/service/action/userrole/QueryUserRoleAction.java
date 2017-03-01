package com.hanshu.employee.core.service.action.userrole;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.UserRoleDTO;
import com.hanshu.employee.common.qto.UserRoleQTO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.service.action.Action;
import com.hanshu.employee.core.manager.UserRoleManager;
import com.hanshu.employee.core.service.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/9.
 */
@Service
public class QueryUserRoleAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryUserRoleAction.class);

    @Resource
    private UserRoleManager userRoleManager;

    @Override
    public EmployeeResponse execute(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        UserRoleQTO userRoleQTO = (UserRoleQTO) request.getParam("userRoleQTO");

        //参数判空校验
        if (userRoleQTO == null) {
            log.error("userRoleQTO is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "userRoleQTO不能为空");
        }

        if (userRoleQTO.getOffset() == null) {
            userRoleQTO.setOffset(0L);
        }
        if (userRoleQTO.getCount() == null) {
            userRoleQTO.setCount(100);
        }
        if (userRoleQTO.getCount() > 500) {
            userRoleQTO.setCount(500);
        }

        List<UserRoleDTO> userRoleDTOs = this.userRoleManager.queryUserRole(userRoleQTO);
        Long totalCount = this.userRoleManager.getTotalCount(userRoleQTO);

        return new EmployeeResponse(userRoleDTOs, totalCount);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_USER_ROLE.getActionName();
    }
}
