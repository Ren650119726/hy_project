package com.mockuai.usercenter.core.service.action.user;

import java.util.Map;

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
public class QueryUserSafeInfoAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryUserSafeInfoAction.class);

	@Resource
	private UserManager userManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest request = context.getRequest();
		Long userId = (Long) request.getParam("user_id");
		String appKey = (String) request.getParam("appKey");

		if (userId == null) {
			log.error("user id is null when query user safe info");
		}

		Map<Object, Object> map= userManager.queryUserSafeInfo(userId, appKey);

		return new UserResponse(map);
	}

	public String getName() {
		return ActionEnum.QUERY_USERSAFEINFO.getActionName();
	}

}
