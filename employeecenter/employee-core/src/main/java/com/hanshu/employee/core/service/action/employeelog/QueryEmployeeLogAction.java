package com.hanshu.employee.core.service.action.employeelog;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.EmployeeLogDTO;
import com.hanshu.employee.common.qto.EmployeeLogQTO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.EmployeeLogManager;
import com.hanshu.employee.core.service.RequestContext;
import com.hanshu.employee.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/19.
 */
@Controller
public class QueryEmployeeLogAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GenEmployeeLogAction.class);
    @Resource
    private EmployeeLogManager employeeLogManager;

    @Override
    public EmployeeResponse execute(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        EmployeeLogQTO employeeLogQTO = (EmployeeLogQTO) request.getParam("employeeLogQTO");
        if (employeeLogQTO == null) {
            log.error("employeeLogQTO is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "employeeLogQTO is null");
        }

        List<EmployeeLogDTO> employeeLogDTOs = this.employeeLogManager.queryEmployeeLog(employeeLogQTO);
        Long totalCount = this.employeeLogManager.getTotalCount(employeeLogQTO);
        return new EmployeeResponse(employeeLogDTOs, totalCount);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_EMPLOYEE_OP_LOG.getActionName();
    }
}
