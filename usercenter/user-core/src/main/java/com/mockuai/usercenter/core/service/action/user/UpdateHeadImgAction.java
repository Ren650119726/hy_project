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
public class UpdateHeadImgAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdateHeadImgAction.class);

	@Resource
	private UserManager userManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest request = context.getRequest();
		String headImg = (String) request.getParam("headImg");
		Long userId = (Long) request.getParam("user_id");
		String appKey = (String) request.getParam("appKey");

		if (headImg == null) {
			log.error("head image is null when update head image");
		}

		if (userId == null) {
			log.error("user id is null when update head image");
		}

		userManager.updateHeadImg(userId, headImg, appKey);

		return new UserResponse(true);
	}

	public String getName() {
		return ActionEnum.UPDATE_HEADIMG.getActionName();
	}

}
