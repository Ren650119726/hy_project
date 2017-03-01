package com.mockuai.usercenter.mop.api.action;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.constant.ResponseCode;

import java.util.HashMap;

public class GenerateInvitationCodeAction extends BaseAction {
    public MopResponse execute(Request request) {
        String appKey = (String)request.getParam("app_key");
        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.GENERATE_INVITATION_CODE.getActionName());
        Response<String> res = getUserDispatchService().execute(userReq);

        if (res.getCode() != ResponseCode.REQUEST_SUCCESS.getValue())
        {
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR);
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("invitataion_code", res.getModule());
        return new MopResponse(map);
    }

    public String getName() {
        return "/user/invitation_code/generate";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
