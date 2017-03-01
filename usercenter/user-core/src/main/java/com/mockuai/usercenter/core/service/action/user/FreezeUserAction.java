package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FreezeUserAction extends TransAction {
    private final static Logger log = LoggerFactory.getLogger(FreezeUserAction.class);

    @Resource
    private UserManager userManager;

    @Override
    protected UserResponse doTransaction(RequestContext context) throws UserException {
        UserRequest request = context.getRequest();
        Long userId = (Long) request.getParam("userId");

        if (userId == null) {
            log.error("user id is null when freeze the user");
        }

        userManager.freezeUser(userId);

        return new UserResponse(true);
    }


    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return ActionEnum.FREEZE_USER.getActionName();
    }

}
