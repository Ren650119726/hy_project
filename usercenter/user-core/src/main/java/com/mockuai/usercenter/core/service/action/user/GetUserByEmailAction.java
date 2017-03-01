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
public class GetUserByEmailAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetUserByEmailAction.class);

	@Resource
	private UserManager userManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {
		// TODO Auto-generated method stub
		UserDTO userDto = null;

		UserRequest request = context.getRequest();
		String email = (String) request.getParam("email");// 用户邮箱
		String bizCode = (String) context.get("bizCode");

		if (email == null) {
			log.error("email is null when using email to get user");
		}

		userDto = userManager.getUserByEmail(email, bizCode);

		return new UserResponse(userDto);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.GET_USER_BY_EMAIL.getActionName();
	}

}
