package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.FeedbackDTO;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

public class AddFeedbackAction extends BaseAction{

    public MopResponse execute(Request request) {
        Long userId = (Long)request.getAttribute("user_id");
        String mobile = (String) request.getParam("mobile");
        String content = (String) request.getParam("content");
        String appKey = (String)request.getParam("app_key");

        FeedbackDTO feedback = new FeedbackDTO();
        feedback.setMobile(mobile);
        feedback.setUserId(userId);
        feedback.setContent(content);

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("feedbackDTO", feedback);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.ADD_FEEDBACK.getActionName());
        Response<FeedbackDTO> userResp = this.getUserDispatchService().execute(userReq);
        return MopApiUtil.transferResp(userResp);
    }

    public String getName() {
        return "/user/feedback/add";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }

}

