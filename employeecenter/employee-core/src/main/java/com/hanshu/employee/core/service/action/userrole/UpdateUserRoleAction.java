package com.hanshu.employee.core.service.action.userrole;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.MenuDTO;
import com.hanshu.employee.common.dto.UserRoleDTO;
import com.hanshu.employee.common.qto.MenuQTO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.MenuManager;
import com.hanshu.employee.core.manager.UserRoleManager;
import com.hanshu.employee.core.service.RequestContext;
import com.hanshu.employee.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/9.
 */
@Service
public class UpdateUserRoleAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserRoleAction.class);

    @Resource
    private UserRoleManager userRoleManager;
    @Resource
    private MenuManager menuManager;

    @Override
    protected EmployeeResponse doTransaction(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        UserRoleDTO userRoleDTO = (UserRoleDTO) request.getParam("userRoleDTO");

        //参数判空校验
        if (userRoleDTO == null) {
            log.error("userRoleDTO is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "userRoleDTO不能为空");
        }

        //参数校验
        String role = userRoleDTO.getRole();
        List<Long> idList = JSON.parseObject(role, new TypeReference<List<Long>>() {
        });

        MenuQTO menuQTO = new MenuQTO();
        menuQTO.setIdList(idList);
        List<MenuDTO> menuDTOList = this.menuManager.queryMenu(menuQTO);
        if (menuDTOList.size() != idList.size()) {
            throw new EmployeeException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "some record is not exist");
        }

        Boolean result = this.userRoleManager.updateUserRole(userRoleDTO);
        return new EmployeeResponse(result);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_USER_ROLE.getActionName();
    }
}
