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

/**
 * Created by yeliming on 16/3/16.
 */
@Service
public class ThawUserAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(ThawUserAction.class);
    @Resource
    private UserManager userManager;

    @Override
    protected UserResponse doTransaction(RequestContext context) throws UserException {
        UserRequest request = context.getRequest();
        Long userId = (Long) request.getParam("userId");

        if (userId == null) {
            log.error("user id is null when thaw the user");
        }

        userManager.thawUser(userId);

        return new UserResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.THAW_USER.getActionName();
    }
}
