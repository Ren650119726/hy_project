package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

/**
 * Created by yeliming on 16/3/15.
 */
public class ReadUserMessageStatus extends BaseAction {
    public MopResponse execute(Request request) {
        String idStr  = (String) request.getParam("id");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");

        Long id = Long.parseLong(idStr.split("_")[1]);

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("id", id);
        userReq.setParam("userId", userId);
        userReq.setParam("appKey", appKey);
        userReq.setParam("status", 1);//已读标志位
        userReq.setCommand(ActionEnum.UPDATE_USER_MESSAGE_STATUS.getActionName());
        Response<UserMessageDTO> userResp = this.getUserDispatchService().execute(userReq);
        return MopApiUtil.transferResp(userResp);
    }

    public String getName() {
        return "/user/message/status/read";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
