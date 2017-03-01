package com.mockuai.usercenter.client.impl;


import com.mockuai.usercenter.client.UserMessageClient;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.common.qto.UserMessageQTO;
import java.util.List;
import javax.annotation.Resource;

public class UserMessageClientImpl implements UserMessageClient {

    @Resource
    private UserDispatchService userDispatchService;

    public Response<Boolean> addUserMessage(List<UserMessageDTO> messageList, String appKey) {
        Request request = new BaseRequest();

        request.setParam("messageList", messageList);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_USER_MESSAGE.getActionName());

        Response response = this.userDispatchService.execute(request);
        return response;
    }

    public Response<List<UserMessageDTO>> queryUserMessage(UserMessageQTO userMessageQTO, String appKey) {
        Request request = new BaseRequest();

        request.setParam("userMessageQTO", userMessageQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_USER_MESSAGE.getActionName());

        Response response = this.userDispatchService.execute(request);
        return response;
    }
}
