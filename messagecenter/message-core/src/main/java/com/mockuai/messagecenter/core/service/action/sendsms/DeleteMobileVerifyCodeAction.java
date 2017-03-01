package com.mockuai.messagecenter.core.service.action.sendsms;

import com.mockuai.messagecenter.common.action.ActionEnum;
import com.mockuai.messagecenter.common.api.MessageResponse;
import com.mockuai.messagecenter.common.dto.SendSmsDTO;
import com.mockuai.messagecenter.core.exception.MessageException;
import com.mockuai.messagecenter.core.manager.SendSmsManager;
import com.mockuai.messagecenter.core.manager.SmsServiceManager;
import com.mockuai.messagecenter.core.service.RequestContext;
import com.mockuai.messagecenter.core.service.UserRequest;
import com.mockuai.messagecenter.core.service.action.Action;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 发送短信接口
 */
@Service
public class DeleteMobileVerifyCodeAction implements Action {

	@Resource
	private SmsServiceManager smsServiceManager;

	@Override
    public MessageResponse execute(RequestContext context) throws MessageException {

		UserRequest userRequest = context.getRequest();
		SendSmsDTO sendSmsDto = (SendSmsDTO) userRequest.getParam("sendSmsDTO");
		Boolean result = smsServiceManager.deleteMobileVerifyCode(sendSmsDto);
		return new MessageResponse(result);
	}

	@Override
	public String getName() {
		return ActionEnum.DELETE_MOBILE_VERIFY_CODE.getActionName();
    }
}
