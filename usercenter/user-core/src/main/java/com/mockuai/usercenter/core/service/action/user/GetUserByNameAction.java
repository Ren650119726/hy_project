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

@Service
public class GetUserByNameAction implements Action {

	private final static Logger log = LoggerFactory.getLogger(GetUserByNameAction.class);

	@Resource
	private UserManager userManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		UserDTO userDto = null;
		UserRequest request = context.getRequest();
		String username = (String) request.getParam("name");
		String bizCode = (String) context.get("bizCode");

		if (username == null) {
			log.error("user name is null when using username to get user");
		}

		userDto = userManager.getUserByName(username, bizCode);

		return new UserResponse(userDto);
	}

	@Override
	public String getName() {
		return ActionEnum.GET_USER_BY_NAME.getActionName();
	}

}
