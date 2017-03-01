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
public class UpdateMobileAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdateMobileAction.class);

	@Resource
	private UserManager userManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest request = context.getRequest();

		String mobile = (String) request.getParam("mobile");
		Long userId = (Long) request.getParam("user_id");
        String verify_code = (String)request.getParam("verify_code");
        String mFlag = (String)request.getParam("mFlag");

		if (userId == null) {
			log.error("user id is null when update mobile");
		}
		
		if (verify_code == null) {
			log.error("verify_code is null when update mobile");
		}

		if (mobile == null) {
			log.error("mobile is null when update mobile");
		}
		
		if (mFlag == null) {
			log.error("mFlag is null when update mobile");
		}

		userManager.updateMobile(userId, mobile, verify_code, mFlag);
		
		return new UserResponse(true);
	}

	public String getName() {
		return ActionEnum.UPDATE_MOBILE.getActionName();
	}

}
