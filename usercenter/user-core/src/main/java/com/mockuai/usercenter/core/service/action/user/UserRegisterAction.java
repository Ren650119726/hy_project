package com.mockuai.usercenter.core.service.action.user;

import java.util.HashMap;
import java.util.Map;

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
public class UserRegisterAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(UserRegisterAction.class);

	@Resource
	private UserManager userManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest request = context.getRequest();
		Map<Object, Object> userDtoMap = new HashMap<Object, Object>();

		String mobile = (String)request.getParam("mobile");//手机号
        String verifyCode = (String)request.getParam("verifyCode");//验证码
        String password = (String)request.getParam("password");//登录密码        
        Long invitationId = (Long)request.getParam("invitationId");//邀请码id
        String registerFlag = (String)request.getParam("registerFlag");//注册标示(0app注册1网页注册)
        Integer appType = (Integer)context.get("appType");
		Long inviterId = (Long)request.getParam("inviterId");
		log.info("[{}] inviterId:{user}",inviterId);
        String appKey = (String) request.getParam("appKey");

		if (mobile == null) {
			log.error("mobile is null when user register");
		}
		
		if (verifyCode == null) {
			log.error("verifyCode is null when register");
		}

		if (password == null) {
			log.error("password is null when register");
		}
		
		if (registerFlag == null) {
			log.error("registerFlag is null when register");
		}
		
		userDtoMap.put("mobile", mobile);
		userDtoMap.put("verifyCode", verifyCode);
		userDtoMap.put("password", password);
		userDtoMap.put("invitationId", invitationId);
		userDtoMap.put("inviterId", inviterId);
		userDtoMap.put("registerFlag", registerFlag);
		userDtoMap.put("appType", appType);
		userDtoMap.put("appKey", appKey);
		
		UserDTO userDto = userManager.userRegister(userDtoMap);		
		return new UserResponse(userDto);
	}

	public String getName() {
		return ActionEnum.USER_REGISTER.getActionName();
	}
}
