package com.mockuai.messagecenter.client.impl;

import javax.annotation.Resource;

import com.mockuai.messagecenter.client.SmsServiceClient;
import com.mockuai.messagecenter.common.action.ActionEnum;
import com.mockuai.messagecenter.common.api.BaseRequest;
import com.mockuai.messagecenter.common.api.MessageDispatchService;
import com.mockuai.messagecenter.common.api.Request;
import com.mockuai.messagecenter.common.api.Response;
import com.mockuai.messagecenter.common.api.MessageResponse;
import com.mockuai.messagecenter.common.dto.SendSmsDTO;
import com.mockuai.messagecenter.common.qto.VerifySmsQTO;

public class SmsServiceClientImpl implements SmsServiceClient {

    @Resource
    private MessageDispatchService messageDispatchService;
    
	public Response<String> smsMobileVerify(VerifySmsQTO verifySmsQTO,
			String appKey) {
		Request request = new BaseRequest();

        request.setParam("verifySmsQTO", verifySmsQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.SMS_SERVICE.getActionName());
        Response<String> response = messageDispatchService.execute(request);
        return response;
	}
	
	public Response<SendSmsDTO> getMobileVerifyCode(String mobile, String appKey) {
        Request request = new BaseRequest();
        SendSmsDTO dto = new SendSmsDTO();
        dto.setMobile(mobile);
        request.setParam("sendSmsDTO", dto);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_MOBILE_VERIFY_CODE.getActionName());
        Response<SendSmsDTO> response = messageDispatchService.execute(request);
        return response;
    }

	public Response<Boolean> deleteMobileVerifyCode(String mobile,
			String appKey) {
		Request request = new BaseRequest();
        SendSmsDTO dto = new SendSmsDTO();
        dto.setMobile(mobile);
        request.setParam("sendSmsDTO", dto);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_MOBILE_VERIFY_CODE.getActionName());
        Response<Boolean> response = messageDispatchService.execute(request);
        return response;
	}
	
}
