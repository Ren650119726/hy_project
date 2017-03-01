package com.mockuai.usercenter.core.service.action.session;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.CacheManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * get session接口
 * */
@Service
public class GetSessionAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(GetSessionAction.class);

	@Resource
	private CacheManager cacheManager;

	@Override
	public UserResponse doTransaction(RequestContext context) throws UserException {
		UserRequest userRequest = context.getRequest();
		String sessionKey = (String)userRequest.getParam("sessionKey");
		Object obj = cacheManager.get(sessionKey);
		return new UserResponse(obj);
	}

	@Override
	public String getName() {
		return ActionEnum.GET_SESSION.getActionName();
	}
}
