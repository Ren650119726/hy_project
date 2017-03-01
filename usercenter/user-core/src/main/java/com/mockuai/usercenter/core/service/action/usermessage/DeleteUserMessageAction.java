package com.mockuai.usercenter.core.service.action.usermessage;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserMessageManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 逻辑删除用户消息
 */
@Service
public class DeleteUserMessageAction implements Action {

	@Resource
	private UserMessageManager userMessageManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest userRequest = context.getRequest();
		Long id = (Long) userRequest.getParam("id");
		Long userId = (Long) userRequest.getParam("userId");

		try {
			userMessageManager.deleteUserMessage(id, userId);
			return new UserResponse(true);
		}catch (UserException e){
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.DEL_USER_MESSAGE.getActionName();
	}

}
