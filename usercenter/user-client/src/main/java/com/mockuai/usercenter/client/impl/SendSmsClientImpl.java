package com.mockuai.usercenter.client.impl;

import com.mockuai.usercenter.client.SendSmsClient;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.*;
import com.mockuai.usercenter.common.dto.SendSmsDTO;

import javax.annotation.Resource;

/**
 * Created by duke on 15/8/18.
 */
public class SendSmsClientImpl implements SendSmsClient {
    @Resource
    private UserDispatchService userDispatchService;

    public Response<SendSmsDTO> sendSmsToMobile(String mobile, String appKey) {
        Request request = new BaseRequest();
        SendSmsDTO dto = new SendSmsDTO();
        dto.setMobile(mobile);
        request.setParam("sendSmsDTO", dto);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.SEND_SMS.getActionName());
        UserResponse<SendSmsDTO> response = userDispatchService.execute(request);
        return response;
    }
}
