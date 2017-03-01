package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.action.Action;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cocoa on 15/5/14.
 */
@Service
public class GenerateInvitationCodeAction implements Action
{
    @Resource
    private UserManager userManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException
    {
        String bizCode = (String)context.get("bizCode");
        String invitationCode = userManager.generateInvitationCode(bizCode);

        return new UserResponse(invitationCode);
    }

    @Override
    public String getName() {
        return ActionEnum.GENERATE_INVITATION_CODE.getActionName();
    }
}
