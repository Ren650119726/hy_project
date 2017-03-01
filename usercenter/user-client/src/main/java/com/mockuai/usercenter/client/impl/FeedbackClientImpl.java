package com.mockuai.usercenter.client.impl;

import com.mockuai.usercenter.client.FeedbackClient;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.api.UserDispatchService;
import com.mockuai.usercenter.common.dto.FeedbackDTO;
import com.mockuai.usercenter.common.qto.FeedbackQTO;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * Created by duanyytop on 15/5/14.
 */
public class FeedbackClientImpl implements FeedbackClient {

    @Resource
    private UserDispatchService userDispatchService;

    public Response<List<FeedbackDTO>> queryUserFeedback(FeedbackQTO feedbackQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("feedbackQTO", feedbackQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_USER_FEEDBACK.getActionName());
        Response response = this.userDispatchService.execute(request);

        return response;
    }
}
