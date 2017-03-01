package com.hanshu.employee.core.service.action.employee;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.qto.EmployeeQTO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.EmployeeManager;
import com.hanshu.employee.core.service.RequestContext;
import com.hanshu.employee.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by yeliming on 16/5/21.
 */
@Controller
public class TotalRoleGroupCountAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(TotalRoleGroupCountAction.class);
    @Resource
    private EmployeeManager employeeManager;

    @Override
    public EmployeeResponse execute(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        EmployeeQTO employeeQTO = (EmployeeQTO) request.getParam("employeeQTO");
        if (employeeQTO == null) {
            log.error("employeeQTO is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "employeeQTO is null");
        }


        Map<Long, Long> map = this.employeeManager.totalRoleGroupCount(employeeQTO);
        Long total = this.employeeManager.getRoleGroupCount(employeeQTO);
        return new EmployeeResponse(map, total);
    }

    @Override
    public String getName() {
        return ActionEnum.TOTAL_ROLE_GROUP_COUNT.getActionName();
    }
}
