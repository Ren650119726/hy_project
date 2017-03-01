package com.mockuai.usercenter.core.service.action.browselog;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserBrowseLogDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserBrowseLogManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

@Service
public class GetBrowseLogByUserIdAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetBrowseLogByUserIdAction.class);

	@Resource
	private UserBrowseLogManager userBrowseLogManager;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest request = context.getRequest();		
		Long userId = (Long)request.getParam("user_id");//用户id

		if (userId == null) {
			log.error("userId is null when add user browselog");
		}

		UserBrowseLogDTO userBrowseLog= userBrowseLogManager.getBrowseLogByUserId(userId);

		return new UserResponse(userBrowseLog);
	}

	public String getName() {
		return ActionEnum.GETBROWSELOGBYUSERID.getActionName();
	}

}
