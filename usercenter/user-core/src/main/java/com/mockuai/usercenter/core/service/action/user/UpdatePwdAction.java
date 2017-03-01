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
public class UpdatePwdAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdatePwdAction.class);

	@Resource
	private UserManager userManager;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserResponse execute(RequestContext context) {
		try {
			UserRequest request = context.getRequest();
			Long userId = (Long)request.getParam("user_id");
		    String newPwd = (String)request.getParam("newPwd");
		    String verify_code = (String)request.getParam("verify_code");
		    String mobile = (String)request.getParam("mobile");
		    String mFlag = (String)request.getParam("mFlag");
		
			if (newPwd == null) {
				log.error("new password is null when update password");
			}
			
			if(null == verify_code){
				log.error("verify_code is null when update password");
			}
		
			if (userId == null) {
				log.error("user id is null when update password");
			}
			
			if (mFlag == null) {
				log.error("mFlag is null when update password");
			}
		
			userManager.updatePwd(userId, newPwd, mobile, verify_code, mFlag);
		
			return new UserResponse(true);
			
		} catch (UserException e) {
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}
		
	}

	public String getName() {
		return ActionEnum.UPDATE_PWD.getActionName();
	}

}
