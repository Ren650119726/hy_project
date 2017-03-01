package com.hanshu.employee.core.service.action.employeelog;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.EmployeeLogDTO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.EmployeeLogManager;
import com.hanshu.employee.core.service.RequestContext;
import com.hanshu.employee.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/5/19.
 */
@Controller
public class GenEmployeeLogAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GenEmployeeLogAction.class);

    @Resource
    private EmployeeLogManager employeeLogManager;

    @Override
    public EmployeeResponse execute(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        EmployeeLogDTO employeeLogDTO = (EmployeeLogDTO) request.getParam("employeeLogDTO");
        if (employeeLogDTO == null) {
            log.error("employeeLogDTO is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "employeeLogDTO is null");
        }

        Long id = null;
        if (employeeLogDTO.getObjIdList() != null && employeeLogDTO.getObjIdList().size() != 0) {
            for (int i = 0; i < employeeLogDTO.getObjIdList().size(); i++) {
                employeeLogDTO.setObjId(employeeLogDTO.getObjIdList().get(i));
                if (employeeLogDTO.getObjNameList() != null)
                    employeeLogDTO.setObjName(employeeLogDTO.getObjNameList().get(i));
                id = this.employeeLogManager.genEmployeeLog(employeeLogDTO);
            }
        } else {
            id = this.employeeLogManager.genEmployeeLog(employeeLogDTO);
        }

        return new EmployeeResponse(id);
    }

    @Override
    public String getName() {
        return ActionEnum.GEN_EMPLOYEE_OP_LOG.getActionName();
    }
}
