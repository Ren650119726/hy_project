package com.mockuai.usercenter.core.service.action.user;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;

/**
 * 将用户移入回收站
 * */
@Service
public class MoveToRecycleAction extends TransAction {

	private static final Logger log = LoggerFactory.getLogger(MoveToRecycleAction.class);

	@Resource
	private UserManager userManager;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.MOVE_USER.getActionName();
	}

	@Override
	protected UserResponse doTransaction(RequestContext context)
			throws UserException {

		UserRequest request = context.getRequest();
		Long userId = (Long) request.getParam("userId");

		if (userId == null) {
			log.error("user is is null");
		}

		userManager.moveToRecycle(userId);

		return new UserResponse(true);

	}

}
