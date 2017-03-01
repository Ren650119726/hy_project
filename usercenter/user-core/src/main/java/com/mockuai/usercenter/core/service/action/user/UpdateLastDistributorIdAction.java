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
import com.mockuai.usercenter.core.service.action.Action;

@Service
public class UpdateLastDistributorIdAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdateLastDistributorIdAction.class);

	@Resource
	private UserManager userManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest request = context.getRequest();
		
		Long sellerId = (Long) request.getParam("seller_id");
		Long userId = (Long) request.getParam("user_id");

		if (null == userId) {
			log.error("userId is null when add seller_id to user");
		}

		if (null == sellerId) {
			log.error("sellerId is null when add seller_id to user");
		}

		userManager.UpdateLastDistributorId(userId, sellerId);

		return new UserResponse(true);
	}

	public String getName() {
		return ActionEnum.UPDATELASTDISTRIBUTORID.getActionName();
	}

}
