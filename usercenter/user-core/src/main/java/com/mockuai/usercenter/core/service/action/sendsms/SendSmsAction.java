package com.mockuai.usercenter.core.service.action.sendsms;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.SendSmsDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.SendSmsManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 发送短信接口
 */
@Service
public class SendSmsAction implements Action {

	@Resource
	private SendSmsManager sendSmsManager;

	@Override
    public UserResponse execute(RequestContext context) throws UserException {

		UserRequest userRequest = context.getRequest();
		SendSmsDTO sendSmsDto = (SendSmsDTO) userRequest.getParam("sendSmsDTO");
		SendSmsDTO dto = sendSmsManager.sendSms(sendSmsDto);
		return new UserResponse(dto);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.SEND_SMS.getActionName();
    }
}
