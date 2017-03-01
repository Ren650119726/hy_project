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
public class GetMenuByUrlAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetMenuByUrlAction.class);

    @Resource
    private MenuManager menuManager;

    @Override
    public EmployeeResponse execute(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        String url = (String) request.getParam("url");
        String version = (String) request.getParam("version");

        //参数判空校验
        if (url == null) {
            log.error("url is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "url不能为空");
        }

        if (version == null) {
            log.error("version is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "version不能为空");
        }

        MenuDTO menuDTO = this.menuManager.getMenuByUrl(url,version);

        return new EmployeeResponse(menuDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_MENU_BY_URL.getActionName();
    }
}
