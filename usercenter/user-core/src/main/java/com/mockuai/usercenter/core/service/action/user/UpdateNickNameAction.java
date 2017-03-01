package com.mockuai.usercenter.core.service.action.user;

import javax.annotation.Resource;

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

@Service
public class UpdateNickNameAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdateNickNameAction.class);

	@Resource
	private UserManager userManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserResponse execute(RequestContext context) throws UserException {
		try {	
			UserRequest request = context.getRequest();
			String nick_name = (String) request.getParam("nick_name");
			Long userId = (Long) request.getParam("user_id");
		
			if (nick_name == null) {
				log.error("nick_name is null when update nick_name");
			}
		
			if (userId == null) {
				log.error("user id is null when update nick_name");
			}
			
			UserDTO userDto = userManager.getUserByNickName(nick_name.trim());
			
			if(null != userDto){//是否重复
				log.error("更改昵称*********************************************************");
				return new UserResponse(ResponseCode.HS_NICK_NAME_LIMIT, "昵称已经存在");
			}
			
			userManager.updateNickName(userId,nick_name);
		
			return new UserResponse(true);
	  } catch (UserException e) {
			return new UserResponse(e.getResponseCode(), e.getMessage());
	  }
	}

	public String getName() {
		return ActionEnum.UPDATE_NICKNAME.getActionName();
	}

}
