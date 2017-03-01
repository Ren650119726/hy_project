package com.hanshu.employee.client.impl;


import com.hanshu.employee.client.UserRoleClient;
import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.BaseRequest;
import com.hanshu.employee.common.api.EmployeeService;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.api.Response;
import com.hanshu.employee.common.dto.UserRoleDTO;
import com.hanshu.employee.common.qto.UserRoleQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/12.
 */
public class UserRoleClientImpl implements UserRoleClient {

    @Resource
    private EmployeeService employeeService;

    public Response<Long> addUserRole(UserRoleDTO userRoleDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userRoleDTO", userRoleDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_USER_ROLE.getActionName());
        Response<Long> response = this.employeeService.execute(request);
        return response;
    }

    public Response<Boolean> updateUserRole(UserRoleDTO userRoleDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userRoleDTO", userRoleDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_USER_ROLE.getActionName());
        Response<Boolean> response = this.employeeService.execute(request);
        return response;
    }

    public Response<Boolean> deleteUserRole(Long userRoleId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userRoleId", userRoleId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_USER_ROLE.getActionName());
        Response<Boolean> response = this.employeeService.execute(request);
        return response;
    }

    public Response<List<UserRoleDTO>> queryUserRole(UserRoleQTO userRoleQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userRoleQTO", userRoleQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_USER_ROLE.getActionName());
        Response<List<UserRoleDTO>> response = this.employeeService.execute(request);
        return response;
    }

    public Response<UserRoleDTO> getUserRole(Long userRoleId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userRoleId", userRoleId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_USER_ROLE_BY_ID.getActionName());
        Response<UserRoleDTO> response = this.employeeService.execute(request);
        return response;
    }
}
