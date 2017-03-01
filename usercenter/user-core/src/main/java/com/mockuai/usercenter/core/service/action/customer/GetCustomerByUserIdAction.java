/*package com.mockuai.usercenter.core.service.action.customer;

import com.mockuai.customer.common.dto.MemberDTO;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.CustomerManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

*//**
 * Created by duke on 15/12/17.
 *//*
@Service
public class GetCustomerByUserIdAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetCustomerByUserIdAction.class);

    @Resource
    private CustomerManager customerManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        Request request = context.getRequest();
        Long userId = (Long)request.getParam("userId");
        String appKey = (String) context.get("appKey");
        String bizCode = (String) context.get("bizCode");

        if (userId == null) {
            log.error("user id is null, bizCode: {}", bizCode);
            throw new UserException(ResponseCode.P_PARAM_NULL);
        }
        MemberDTO memberDTO = customerManager.getMemberByUserId(userId, appKey);
        return new UserResponse(memberDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_CUSTOMER_BY_USER_ID.getActionName();
    }
}
*/