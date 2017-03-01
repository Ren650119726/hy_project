package com.mockuai.usercenter.client.impl;

import com.mockuai.usercenter.client.UserOpenClient;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import com.mockuai.usercenter.common.qto.UserOpenInfoQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/5/13.
 */
public class UserOpenClientImpl implements UserOpenClient {
    @Resource
    private UserDispatchService userDispatchService;

    public Response<List<UserOpenInfoDTO>> queryUserOpenInfo(UserOpenInfoQTO userOpenInfoQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("userOpenInfoQTO", userOpenInfoQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_USER_OPEN_INFO.getActionName());
        Response<List<UserOpenInfoDTO>> response = this.userDispatchService.execute(request);
        return response;
    }
}
