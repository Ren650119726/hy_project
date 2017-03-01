package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.constant.RoleType;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.NotifyManager;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.usercenter.core.service.action.TransAction;
import com.mockuai.usercenter.core.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加
 * 绑定粉丝和上级的关系
 */
@Service
public class AddFansToInviterAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddFansToInviterAction.class);
	@Resource
	private UserManager userManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest request = context.getRequest();

		Long inviterId = (Long) request.getParam("inviterId");
		Long userId = (Long) request.getParam("userId");
		String appKey = (String) request.getParam("appKey");

        if(null == appKey){
            log.error("appKey  is null when update inviterId");
            return new UserResponse(ResponseCode.P_PARAM_NULL, "appKey is null");
        }

		if(null == userId){
			log.error("userId is null when update inviterId");
			return new UserResponse(ResponseCode.P_PARAM_NULL, "userId is null");
		}

		if(null == inviterId){
			log.error("invite id is null when update inviterId");
			return new UserResponse(ResponseCode.P_PARAM_NULL, "inviterId is null");
		}

		userManager.addFansToInviterId(userId, inviterId);

		return new UserResponse(ResponseCode.REQUEST_SUCCESS);
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_FANS_TO_INVITER_ID.getActionName();
	}
}
