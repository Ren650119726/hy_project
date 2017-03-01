package com.hanshu.employee.client.impl;

import com.hanshu.employee.client.MenuClient;
import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.BaseRequest;
import com.hanshu.employee.common.api.EmployeeService;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.api.Response;
import com.hanshu.employee.common.dto.MenuDTO;
import com.hanshu.employee.common.qto.MenuQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/11.
 */
public class MenuClientImpl implements MenuClient {
    @Resource
    private EmployeeService employeeService;

    public Response<MenuDTO> addMenu(MenuDTO menuDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("menuDTO", menuDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_MENU.getActionName());
        Response<MenuDTO> response = this.employeeService.execute(request);
        return response;
    }

    public Response<Boolean> deleteMenu(Long menuId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("menuId", menuId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_MENU.getActionName());
        Response<Boolean> response = this.employeeService.execute(request);
        return response;
    }

    public Response<Boolean> updateMenu(MenuDTO menuDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("menuDTO", menuDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_MENU.getActionName());
        Response<Boolean> response = this.employeeService.execute(request);
        return response;
    }

    public Response<List<MenuDTO>> queryMenu(MenuQTO menuQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("menuQTO", menuQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_MENU.getActionName());
        Response<List<MenuDTO>> response = this.employeeService.execute(request);
        return response;
    }

    public Response<MenuDTO> getMenu(Long menuId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("menuId", menuId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_MENU_BY_ID.getActionName());
        Response<MenuDTO> response = this.employeeService.execute(request);
        return response;
    }

    public Response<MenuDTO> getParentMenuByUrl(String url, String version, String appKey) {
        Request request = new BaseRequest();
        request.setParam("url", url);
        request.setParam("version", version);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_PARENT_MENU_BY_URL.getActionName());
        Response<MenuDTO> response = this.employeeService.execute(request);
        return response;
    }

    public Response<MenuDTO> getMenuByUrl(String url, String version, String appKey) {
        Request request = new BaseRequest();
        request.setParam("url", url);
        request.setParam("version", version);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_MENU_BY_URL.getActionName());
        Response<MenuDTO> response = this.employeeService.execute(request);
        return response;
    }


}
