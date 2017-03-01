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
public class UpdateUserTypeAction implements Action{
    private static final Log log = LogFactory.getLog(UpdateUserTypeAction.class);

    @Resource
    private UserManager userManager;
    public UserResponse execute(RequestContext context) throws UserException {
        UserRequest request = context.getRequest();
        String bizCode = (String) context.get("bizCode");

        Long userId = (Long)request.getParam("userId");
        Integer userType = (Integer)request.getParam("userType");

        if(userId == null){
            log.error("user id is null");
            throw new UserException(ResponseCode.P_PARAM_NULL,"user id is null");
        }
        if(userType == null){
            log.error("role type is null");
            throw new UserException(ResponseCode.P_PARAM_NULL,"user type is null");
        }

        userManager.updateUserType(userId, userType, bizCode);

        return new UserResponse(true);
    }

    public String getName() {
        return ActionEnum.UPDATE_USER_TYPE.getActionName();
    }
}
