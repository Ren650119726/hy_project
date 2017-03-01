package com.mockuai.usercenter.core.service.action.user;

import java.util.List;

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
public class QueryUserAction implements Action {
	private final static Logger log = LoggerFactory.getLogger(QueryUserAction.class);

	@Resource
	private UserManager userManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest request = context.getRequest();
		UserQTO userQto = (UserQTO) request.getParam("userQTO");
		String bizCode = (String)context.get("bizCode");

		if (userQto == null) {
			log.error("user qto is null when query user by userQto");
		}

		userQto.setBizCode(bizCode);

		List<UserDTO> users = userManager.queryUser(userQto);
		Long total = userManager.getTotalCount(userQto);

		return new UserResponse(users, total);
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_USER.getActionName();
	}

}
