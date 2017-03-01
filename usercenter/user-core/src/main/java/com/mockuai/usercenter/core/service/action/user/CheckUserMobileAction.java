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
public class CheckUserMobileAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(CheckUserMobileAction.class);

	@Resource
	private UserManager userManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest request = context.getRequest();

		String mobile = (String) request.getParam("mobile");
		Long userId = (Long) request.getParam("user_id");
        String verify_code = (String)request.getParam("verify_code");
        String bank_personal_id = (String)request.getParam("bank_personal_id");
        String mFlag = (String)request.getParam("mFlag");
        String appKey = (String)request.getParam("appKey");
        
        if(null == mFlag){
        	log.error("mFlag is null when update mobile");
        }

		if (userId == null) {
			log.error("user id is null when update mobile");
		}

		userManager.checkUserMobile(userId, mobile, verify_code, bank_personal_id, mFlag, appKey);
		return new UserResponse(true);
	}

	public String getName() {
		return ActionEnum.CHECK_USERMOBILE.getActionName();
	}

}
