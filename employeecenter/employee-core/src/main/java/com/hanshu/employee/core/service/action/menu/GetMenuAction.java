package com.hanshu.employee.core.service.action.menu;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.MenuDTO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.MenuManager;
import com.hanshu.employee.core.service.RequestContext;
import com.hanshu.employee.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/5/9.
 */
@Service
public class GetMenuAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetMenuAction.class);

    @Resource
    private MenuManager menuManager;

    @Override
    public EmployeeResponse execute(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        Long menuId = (Long) request.getParam("menuId");

        //参数判空校验
        if (menuId == null) {
            log.error("menuId is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "menuId不能为空");
        }

        MenuDTO menuDTO = this.menuManager.getMenuById(menuId);


        return new EmployeeResponse(menuDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_MENU_BY_ID.getActionName();
    }
}
