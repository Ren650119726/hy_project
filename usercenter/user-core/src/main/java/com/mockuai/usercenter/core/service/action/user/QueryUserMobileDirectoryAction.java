package com.mockuai.usercenter.core.service.action.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.usercenter.mop.api.util.JsonUtil;

@Service
public class QueryUserMobileDirectoryAction implements Action {
	@Resource
	private UserManager userManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest request = context.getRequest();
		List<String> userMobileDirJsonList = (List<String>) request.getParam("userMobileDirJsonList");
		String appKey = (String) request.getParam("appKey");		      	
      	
      	if(null == userMobileDirJsonList || userMobileDirJsonList.isEmpty()){
      		return new UserResponse(ResponseCode.B_USER_DIRECTORY_ERROR);
      	}

		Map<Object, Object> map= userManager.queryUserMobileDirectory(userMobileDirJsonList, appKey);

		return new UserResponse(map);
	}

	public String getName() {
		return ActionEnum.QUERY_USERMOBILEDIRECTORY.getActionName();
	}
}
