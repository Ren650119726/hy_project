package com.mockuai.usercenter.core.service.action.usermessage;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.constant.MessageType;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserMessageManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

/**
 * 添加用户消息接口
 * */
@Service
public class AddUserMessageAction implements Action {

	private final int TO_USERS = 1;

	private final int TO_ALL = 2;
	
	@Resource
	private UserMessageManager userMessageManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest userRequest = context.getRequest();

//		UserMessageDTO userMessageDto = (UserMessageDTO) userRequest.getParam("userMessageDTO");
		List<UserMessageDTO> userMessageList=(List<UserMessageDTO>)userRequest.getParam("messageList");
		String bizCode = (String)context.get("bizCode");

		if(userMessageList== null||userMessageList.isEmpty()){
			return new UserResponse(ResponseCode.P_PARAM_NULL, "userMessageList is null");
		}




		try{

			for(UserMessageDTO userMessageDto:userMessageList){
				userMessageDto.setBizCode(bizCode);
				if (userMessageDto.getType() == MessageType.P2P_MESSAGE.getValue()) {
					userMessageDto.setGlobalId(0L);
				}
				userMessageManager.addUserMessage(userMessageDto);
			}
			return new UserResponse(true);
		}catch(UserException e){
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.ADD_USER_MESSAGE.getActionName();
	}

}
