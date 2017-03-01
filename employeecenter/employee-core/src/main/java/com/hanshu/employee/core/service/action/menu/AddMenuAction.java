package com.hanshu.employee.core.service.action.menu;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.MenuDTO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.MenuManager;
import com.hanshu.employee.core.service.RequestContext;
import com.hanshu.employee.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/5/9.
 */
@Service
public class AddMenuAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AddMenuAction.class);

    @Resource
    private MenuManager menuManager;

    @Override
    protected EmployeeResponse doTransaction(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        MenuDTO menuDTO = (MenuDTO) request.getParam("menuDTO");

        //参数判空校验
        if (menuDTO == null) {
            log.error("menuDTO is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "menuDTO不能为空");
        }
        Long id = this.menuManager.addMenu(menuDTO);

        MenuDTO rtnMenuDTO = this.menuManager.getMenuById(id);

        return new EmployeeResponse(rtnMenuDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_MENU.getActionName();
    }
}
