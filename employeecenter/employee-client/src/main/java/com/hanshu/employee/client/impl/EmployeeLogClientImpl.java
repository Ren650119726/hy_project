package com.hanshu.employee.client.impl;

import com.hanshu.employee.client.EmployeeLogClient;
import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.BaseRequest;
import com.hanshu.employee.common.api.EmployeeService;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.api.Response;
import com.hanshu.employee.common.dto.EmployeeLogDTO;
import com.hanshu.employee.common.qto.EmployeeLogQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/19.
 */
public class EmployeeLogClientImpl implements EmployeeLogClient {
    @Resource
    private EmployeeService employeeService;

    public Response<Long> genEmployeeOpLog(EmployeeLogDTO employeeLogDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("employeeLogDTO", employeeLogDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GEN_EMPLOYEE_OP_LOG.getActionName());
        Response<Long> response = this.employeeService.execute(request);
        return response;
    }

    public Response<List<EmployeeLogDTO>> queryEmployeeOpLog(EmployeeLogQTO employeeLogQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("employeeLogQTO", employeeLogQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_EMPLOYEE_OP_LOG.getActionName());
        Response<List<EmployeeLogDTO>> response = this.employeeService.execute(request);
        return response;
    }
}
