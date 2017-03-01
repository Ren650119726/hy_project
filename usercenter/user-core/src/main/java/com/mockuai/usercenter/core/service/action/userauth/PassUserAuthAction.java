package com.mockuai.usercenter.core.service.action.userauth;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.constant.ResponseCode;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserAuthInfoManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

/**
 * 用户认证通过
 * */
@Service
public class PassUserAuthAction implements Action {

	@Resource
	private UserAuthInfoManager userAuthInfoManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest userRequest = context.getRequest();
		Long userAuthId = (Long) userRequest.getParam("userAuthId");
		Long userId = (Long) userRequest.getParam("userId");
		String remark = (String) userRequest.getParam("remark");// 备注
		try {
			userAuthInfoManager.passUserAuth(userAuthId, userId, remark);
			return new UserResponse(ResponseCode.REQUEST_SUCCESS);
		} catch (UserException e){
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		return ActionEnum.PASS_USER_AUTH_INFO.getActionName();
	}

}
