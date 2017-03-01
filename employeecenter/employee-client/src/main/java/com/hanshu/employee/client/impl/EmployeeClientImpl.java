package com.hanshu.employee.client.impl;

import com.hanshu.employee.client.EmployeeClient;
import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.BaseRequest;
import com.hanshu.employee.common.api.EmployeeService;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.api.Response;
import com.hanshu.employee.common.dto.EmployeeDTO;
import com.hanshu.employee.common.qto.EmployeeQTO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by yeliming on 16/5/9.
 */
public class EmployeeClientImpl implements EmployeeClient {

    @Resource
    private EmployeeService employeeService;

    public Response<EmployeeDTO> addEmployee(EmployeeDTO employeeDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("employeeDTO", employeeDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_EMPLOYEE.getActionName());
        Response<EmployeeDTO> response = this.employeeService.execute(request);
        return response;
    }

    public Response<Boolean> updateEmployee(EmployeeDTO employeeDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("employeeDTO", employeeDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_EMPLOYEE.getActionName());
        Response<Boolean> response = this.employeeService.execute(request);
        return response;
    }

    public Response<Boolean> deleteEmployee(Long employeeId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("employeeId", employeeId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_EMPLOYEE.getActionName());
        Response<Boolean> response = this.employeeService.execute(request);
        return response;
    }

    public Response<List<EmployeeDTO>> queryEmployee(EmployeeQTO employeeQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("employeeQTO", employeeQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_EMPLOYEE.getActionName());
        Response<List<EmployeeDTO>> response = this.employeeService.execute(request);
        return response;
    }

    public Response<EmployeeDTO> getEmployeeById(Long employeeId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("employeeId", employeeId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_EMPLOYEE_BY_ID.getActionName());
        Response<EmployeeDTO> response = this.employeeService.execute(request);
        return response;
    }

    public Response<EmployeeDTO> employeeLogin(EmployeeQTO employeeQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("employeeQTO", employeeQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.EMPLOYEE_LOGIN.getActionName());
        Response<EmployeeDTO> response = this.employeeService.execute(request);
        return response;
    }

    public Response<Map<Long, Long>> totalRoleGroupCount(EmployeeQTO employeeQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("employeeQTO", employeeQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.TOTAL_ROLE_GROUP_COUNT.getActionName());
        Response<Map<Long, Long>> response = this.employeeService.execute(request);
        return response;
    }

    public Response<Boolean> updateEmployeeStatus(EmployeeDTO employeeDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("employeeDTO", employeeDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_EMPLOYEE_STATUS.getActionName());
        Response<Boolean> response = this.employeeService.execute(request);
        return response;
    }

    public Response<Boolean> updatePassword(Long employeeId, String password, String appKey) {
        Request request = new BaseRequest();
        request.setParam("employeeId",employeeId);
        request.setParam("password",password);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.UPDATE_PASSWORD.getActionName());
        Response<Boolean> response = this.employeeService.execute(request);
        return response;
    }
}
