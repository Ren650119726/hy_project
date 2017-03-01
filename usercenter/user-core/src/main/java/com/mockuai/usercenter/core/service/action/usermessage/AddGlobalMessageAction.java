package com.mockuai.usercenter.core.service.action.usermessage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.GlobalMessageDTO;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserMessageManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

/**
 * 添加全局消息接口
 * */
@Service
public class AddGlobalMessageAction implements Action {

	@Resource
	private UserMessageManager userMessageManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {

		UserRequest userRequest = context.getRequest();
		if(userRequest.getParam("messageList") == null){
			return new UserResponse(ResponseCode.P_PARAM_NULL,"messageList is null");
		}
		
		List<GlobalMessageDTO> messageList = (List<GlobalMessageDTO>) userRequest.getParam("messageList");
		
		try{
			Long id;
			if(messageList != null && messageList.size()!=0){
				for(GlobalMessageDTO userMessageDto : messageList){
					//TODO 考虑使用批处理
					id = userMessageManager.addGlobalMessage(userMessageDto);
				}
				return new UserResponse(true);
			}else{
				return new UserResponse(ResponseCode.P_PARAM_NULL,"messageList is empty");
			}
		}catch(UserException e){
			return new UserResponse(e.getResponseCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.ADD_GLOBAL_MESSAGE.getActionName();
	}

}
