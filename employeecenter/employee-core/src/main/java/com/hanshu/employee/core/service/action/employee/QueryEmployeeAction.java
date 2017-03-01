package com.hanshu.employee.core.service.action.employee;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
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
import java.util.List;

/**
 * Created by yeliming on 16/5/9.
 */
@Service
public class QueryEmployeeAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryEmployeeAction.class);

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

        if (employeeQTO.getOffset() == null) {
            employeeQTO.setOffset(0L);
        }
        if (employeeQTO.getCount() == null) {
            employeeQTO.setCount(20);
        }
        if (employeeQTO.getCount() > 500) {
            employeeQTO.setCount(500);
        }

        List<EmployeeDTO> employeeDTOList = this.employeeManager.queryEmployee(employeeQTO);
        Long totalCount = this.employeeManager.getTotalCount(employeeQTO);

        return new EmployeeResponse(employeeDTOList, totalCount);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_EMPLOYEE.getActionName();
    }
}
