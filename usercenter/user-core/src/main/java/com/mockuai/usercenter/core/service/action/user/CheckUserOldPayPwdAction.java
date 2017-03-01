package com.mockuai.usercenter.core.service.action.user;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

@Service
public class CheckUserOldPayPwdAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(CheckUserOldPayPwdAction.class);

	@Resource
	private UserManager userManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {
		try {
			UserRequest request = context.getRequest();
			
			Long userId = (Long) request.getParam("user_id");       
	        String pay_pwd = (String)request.getParam("pay_pwd");
	        
	        if(null == pay_pwd){
	        	return new UserResponse(ResponseCode.P_PARAM_NULL, "校验支付密码不可为空");
	        }

			if (userId == null) {
				return new UserResponse(ResponseCode.P_PARAM_NULL, "用户id不可为空");
			}

			userManager.checkUserOldPayPwd(userId, pay_pwd);
			return new UserResponse(true);
		} catch (UserException e) {
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}		
	}

	public String getName() {
		return ActionEnum.CHECKUSEROLDPAYPWD.getActionName();
	}

}
