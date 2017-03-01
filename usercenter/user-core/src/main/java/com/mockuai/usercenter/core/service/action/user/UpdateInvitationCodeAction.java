package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
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
 * Created by cocoa on 15/5/14.
 */
@Service
public class UpdateInvitationCodeAction implements Action
{
    private static final Logger log = LoggerFactory.getLogger(UpdateInvitationCodeAction.class);

    @Resource
    private UserManager userManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException{

        UserRequest request = context.getRequest();
        String invitationCode = (String) request.getParam("invitationCode");
        Long userId = (Long) request.getParam("userId");

        if (userId == null) {
            log.error("user id is null when update invitation code");
        }

        userManager.updateInvitationCode(userId, invitationCode);

        return new UserResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_INVITATION_CODE.getActionName();
    }
}
