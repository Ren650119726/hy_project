package com.mockuai.usercenter.core.service.action.user;

import javax.annotation.Resource;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.core.manager.AppManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

import java.util.List;

@Service
public class GetUserByMobileAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetUserByMobileAction.class);

	@Resource
	private UserManager userManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest request = context.getRequest();
		String mobile = (String) request.getParam("mobile");
		String bizCode = (String) context.get("bizCode");

		if (mobile == null) {
			log.error("mobile is null when using mobile to get user");
		}

		if (bizCode == null) {
			log.error("biz code is null when using mobile to get user");
		}
		UserDTO userDTO = null;
		try {
			userDTO = userManager.getUserByMobile(mobile);
			log.info("根据手机号查询用户信息action"+userDTO);
		} catch (UserException err) {
			return new UserResponse(err.getResponseCode(), err.getMessage());
		}
		return new UserResponse(userDTO);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.GET_USER_BY_MOBILE.getActionName();
	}
}
