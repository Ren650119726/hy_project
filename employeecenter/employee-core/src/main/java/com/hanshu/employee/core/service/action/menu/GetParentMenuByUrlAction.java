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
 * Created by yeliming on 16/5/19.
 */
@Service
public class GetParentMenuByUrlAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetParentMenuByUrlAction.class);

    @Resource
    private MenuManager menuManager;

    @Override
    public EmployeeResponse execute(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        String url = (String) request.getParam("url");
        String version = (String) request.getParam("version");
        if (url == null) {
            log.error("url is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "url is null");
        }
        if (version == null) {
            log.error("version is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "version is null");
        }
        MenuDTO menuDTO = this.menuManager.getParentMenuByUrl(url, version);
        return new EmployeeResponse(menuDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_PARENT_MENU_BY_URL.getActionName();
    }
}
