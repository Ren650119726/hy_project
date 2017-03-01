package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 更新
 * 绑定粉丝和上级的关系
 */
@Service
public class UpdateInviterIdAction implements Action
{
    private static final Logger log = LoggerFactory.getLogger(UpdateInviterIdAction.class);
    @Resource
    private UserManager userManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        UserRequest request = context.getRequest();
        
        Long inviterId = (Long) request.getParam("inviter_id");
        Long userId = (Long) request.getParam("user_id");

        if(null == userId){
            log.error("use id is null when update inviter id");
            return new UserResponse(ResponseCode.P_PARAM_NULL, "userId is null");
        }

        if(null == inviterId){
            log.error("invite id is null when update inviter id");
            return new UserResponse(ResponseCode.P_PARAM_NULL, "inviterId is null");
        }
        
        userManager.updateInviterId(userId, inviterId);       

        return new UserResponse(ResponseCode.REQUEST_SUCCESS);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_INVITER_ID.getActionName();
    }
}
