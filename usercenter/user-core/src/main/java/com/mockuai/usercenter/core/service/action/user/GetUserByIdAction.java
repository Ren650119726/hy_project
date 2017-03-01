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
import com.mockuai.usercenter.core.service.action.Action;

/**
 * 根据用户id查找指定用户
 * */
@Service
public class GetUserByIdAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetUserByIdAction.class);

	@Resource
	private UserManager userManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		UserDTO userDto = null;
		UserRequest request = context.getRequest();
		Long userId = (Long) request.getParam("userId");

		if (userId == null) {
			log.error("user id is null when using id to get user");
		}

		userDto = userManager.getUserById(userId);

		return new UserResponse(userDto);
	}

	@Override
	public String getName() {
		return ActionEnum.GET_USER_BY_ID.getActionName();
	}

}
