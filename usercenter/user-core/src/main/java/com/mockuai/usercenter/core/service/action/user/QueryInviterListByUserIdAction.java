package com.mockuai.usercenter.core.service.action.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

@Service
public class QueryInviterListByUserIdAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryInviterListByUserIdAction.class);

	@Resource
	private UserManager userManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest request = context.getRequest();
		UserQTO userQTO = (UserQTO) request.getParam("userQTO");
		String appKey = (String) request.getParam("appKey");

		if (null == userQTO) {
			log.error("user id is null when query inviter by userId");
		}
		
		List<UserDTO> userDtoList= userManager.queryInviterListByUserId(userQTO, appKey);

		return new UserResponse(userDtoList);
	}

	public String getName() {
		return ActionEnum.QUERY_INVITERLISTBYUSERID.getActionName();
	}

}
