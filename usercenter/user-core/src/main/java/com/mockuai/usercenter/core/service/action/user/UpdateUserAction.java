package com.mockuai.usercenter.core.service.action.user;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

@Service
public class UpdateUserAction implements Action {
	private final static Logger log = LoggerFactory.getLogger(UpdateUserAction.class);
	@Resource
	private UserManager userManager;

	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest request = context.getRequest();
		UserDTO userDto = (UserDTO) request.getParam("userDTO");

		if (userDto == null) {
			log.error("user dto is null when update user");
		}

        Integer result = userManager.updateUser(userDto);
        log.info("update user success, result: {}", result);
		return new UserResponse(true);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.UPDATE_USER.getActionName();
	}
}
