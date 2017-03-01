package com.mockuai.messagecenter.client.impl;

import com.mockuai.messagecenter.client.SendSmsClient;
import com.mockuai.messagecenter.common.action.ActionEnum;
import com.mockuai.messagecenter.common.api.BaseRequest;
import com.mockuai.messagecenter.common.api.MessageDispatchService;
import com.mockuai.messagecenter.common.api.Request;
import com.mockuai.messagecenter.common.api.Response;
import com.mockuai.messagecenter.common.api.MessageResponse;
import com.mockuai.messagecenter.common.dto.SendSmsDTO;
import com.mockuai.messagecenter.common.api.*;

import javax.annotation.Resource;

/**
 * Created by duke on 15/8/18.
 */
public class SendSmsClientImpl implements SendSmsClient {
    @Resource
    private MessageDispatchService userDispatchService;

    public Response<SendSmsDTO> sendSmsToMobile(String mobile, String appKey) {
        Request request = new BaseRequest();
        SendSmsDTO dto = new SendSmsDTO();
        dto.setMobile(mobile);
        request.setParam("sendSmsDTO", dto);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.SEND_SMS.getActionName());
        MessageResponse<SendSmsDTO> response = userDispatchService.execute(request);
        return response;
    }
}
