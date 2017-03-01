package com.mockuai.usercenter.core.service.action.consignee;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserConsigneeManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

@Service
public class DeleteConsigneeAction implements Action {

	@Resource
	private UserConsigneeManager userConsigneeManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest userRequest = context.getRequest();
		Long userId = (Long) userRequest.getParam("userId");
		Long consigneeId = (Long) userRequest.getParam("consigneeId");

		userConsigneeManager.deleteConsignee(userId, consigneeId);
		return new UserResponse(true);
	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_CONSIGNEE.getActionName();
	}

}
