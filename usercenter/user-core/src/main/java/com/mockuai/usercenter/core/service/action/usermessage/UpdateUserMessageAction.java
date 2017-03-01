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
 * 修改消息状态
 */
@Service
public class UpdateUserMessageAction implements Action {

	@Resource
	private UserMessageManager userMessageManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest request = context.getRequest();

		Long id = (Long) request.getParam("id");
		Long userId = (Long) request.getParam("userId");
        int status=(Integer)request.getParam("status");
		try {
			userMessageManager.updateUserMessageStatus(id,userId,status);
			return new UserResponse(true);
		}catch (UserException e){
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {

		return ActionEnum.UPDATE_USER_MESSAGE_STATUS.getActionName();
	}
}
