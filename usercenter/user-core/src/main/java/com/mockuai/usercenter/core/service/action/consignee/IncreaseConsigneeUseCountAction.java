package com.mockuai.usercenter.core.service.action.consignee;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserConsigneeManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.action.TransAction;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yeliming on 16/5/19.
 */
@Service
public class IncreaseConsigneeUseCountAction extends TransAction {

    @Resource
    private UserConsigneeManager userConsigneeManager;

    @Override
    protected UserResponse doTransaction(RequestContext context) throws UserException {
        Request request = context.getRequest();
        String bizCode = (String) context.get("bizCode");
        Long consigneeId = (Long) request.getParam("consigneeId");
        Long userId = (Long) request.getParam("userId");

        Boolean b = this.userConsigneeManager.increaseConsigneeUseCount(consigneeId, userId, bizCode);
        return new UserResponse(b);
    }

    @Override
    public String getName() {
        return ActionEnum.INCREASE_CONSIGNEE_USE_COUNT.getActionName();
    }
}
