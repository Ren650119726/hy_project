package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/1/11.
 */
@Service
public class UpdateRoleTypeAction implements Action {
    private static final Log log = LogFactory.getLog(UpdateRoleTypeAction.class);
    @Resource
    private UserManager userManager;

    public UserResponse execute(RequestContext context) throws UserException {
        UserRequest request = context.getRequest();
        Long userId = (Long)request.getParam("userId");
        Long roleType = (Long) request.getParam("roleType");
        String bizCode = (String) context.get("bizCode");

        if(userId == null){
            log.error("user id is null");
            throw new UserException(ResponseCode.P_PARAM_NULL,"user id is null");
        }
        if(roleType == null){
            log.error("role type is null");
            throw new UserException(ResponseCode.P_PARAM_NULL,"role type is null");
        }

        userManager.updateRoleType(userId, roleType, bizCode);

        return new UserResponse(true);
    }

    public String getName() {
        return ActionEnum.UPDATE_ROLE_TYPE.getActionName();
    }
}
