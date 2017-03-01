package com.mockuai.usercenter.core.service.action.useropeninfo;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserOpenInfoManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by duke on 15/12/28.
 */
@Service
public class GetOpenInfoByUserIdAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetOpenInfoByUserIdAction.class);

    @Resource
    private UserOpenInfoManager userOpenInfoManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        Request request = context.getRequest();
        Long userId = (Long) request.getParam("userId");
        String bizCode = (String) context.get("bizCode");

        if (userId == null) {
            log.error("userId is null, bizCode: {}", bizCode);
            throw new UserException(ResponseCode.P_PARAM_NULL, "userId is null");
        }

        UserOpenInfoDTO userOpenInfoDTO = userOpenInfoManager.getOpenInfoByUserId(userId, bizCode);
        return new UserResponse<UserOpenInfoDTO>(userOpenInfoDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_USER_OPEN_INFO_BY_USER_ID.getActionName();
    }
}
