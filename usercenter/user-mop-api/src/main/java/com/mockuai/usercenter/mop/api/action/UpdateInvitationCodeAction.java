package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import org.apache.commons.lang.StringUtils;

public class UpdateInvitationCodeAction extends BaseAction
{
    public MopResponse execute(Request request)
    {
        String invitationCode = (String)request.getParam("invitation_code");
        Long userId = (Long)request.getAttribute("user_id");
        String appKey = (String)request.getParam("app_key");

        if(StringUtils.isEmpty(invitationCode)) {
            return new MopResponse(20000, "邀请码不能为空");
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("userId", userId);
        userReq.setParam("invitationCode", invitationCode);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.UPDATE_INVITATION_CODE.getActionName());
        Response<Boolean> res = getUserDispatchService().execute(userReq);
        if (res == null)
        {
            return new MopResponse(20000, "更新失败");
        }
        return new MopResponse(MopRespCode.REQUEST_SUCESS);
    }

    public String getName() {
        return "/user/invitation_code/update";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
