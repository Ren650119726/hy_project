package com.mockuai.usercenter.core.service.action.user;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;

@Service
public class RestoreUserAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(RestoreUserAction.class);

	@Resource
	private UserManager userManager;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.RESTORE_USER.getActionName();
	}

	@Override
	protected UserResponse doTransaction(RequestContext context)
			throws UserException {

		// TODO Auto-generated method stub

		UserRequest request = context.getRequest();
		Long userId = (Long) request.getParam("userId");

		if (userId == null) {
			log.error("user id is null when restore user action");
		}

		UserDTO userDto = userManager.getRecycleUser(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user not in recycle");
		}

		userManager.restoreUser(userId);
		return new UserResponse(true);

	}

}
