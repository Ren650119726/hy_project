package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UpdateWechatAndQqCode implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdateWechatAndQqCode.class);

	@Resource
	private UserManager userManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest request = context.getRequest();
		String wechat = (String) request.getParam("wechat");
		String qqCode = (String) request.getParam("qqCode");
		Long userId = (Long) request.getParam("user_id");

		if (userId == null) {
			log.error("user id is null when update nick_name");
		}

		if ((null != wechat) || (null != qqCode)) {
			this.userManager.updateWechatAndQqCode(userId, wechat, qqCode);
		}

		return new UserResponse(Boolean.valueOf(true));
	}

	public String getName() {
		return ActionEnum.UPDATE_WECHATANDQQCODE.getActionName();
	}
}