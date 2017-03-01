package com.mockuai.usercenter.core.service.action.session;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.constant.RoleType;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.*;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;
import com.mockuai.usercenter.core.util.UserUtil;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * put session接口
 * */
@Service
public class PutSessionAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(PutSessionAction.class);

	@Resource
	private CacheManager cacheManager;

	@Override
	public UserResponse doTransaction(RequestContext context) throws UserException {
		UserRequest userRequest = context.getRequest();
		String sessionKey = (String)userRequest.getParam("sessionKey");
		Object sessionObject = userRequest.getParam("sessionObject");
		cacheManager.set(sessionKey,60*30,sessionObject);
		return new UserResponse(ResponseCode.REQUEST_SUCCESS);
	}

	@Override
	public String getName() {
		return ActionEnum.PUT_SESSION.getActionName();
	}
}
