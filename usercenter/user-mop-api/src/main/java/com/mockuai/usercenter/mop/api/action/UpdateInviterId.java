package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;
import org.apache.commons.lang.StringUtils;

public class UpdateInviterId extends BaseAction {

    public MopResponse execute(Request request) {
        Long userId = (Long)request.getAttribute("user_id");
        String inviterIdStr = (String)request.getParam("inviter_id");
        String appKey = (String)request.getParam("app_key");

        if(StringUtils.isEmpty(inviterIdStr)) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "inviter_id is null");
        }

        Long inviterId = null;
        try{
            inviterId = Long.valueOf(inviterIdStr);
        }catch (Exception e){
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "the format of inviter_id is invalid");
        }

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("userId", userId);
        userReq.setParam("inviterId", inviterId);
        userReq.setParam("appKey", appKey);
        userReq.setCommand(ActionEnum.UPDATE_INVITER_ID.getActionName());
        Response<Void> res = getUserDispatchService().execute(userReq);

        return MopApiUtil.transferResp(res);
    }

    public String getName() {
        return "/";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }

    public static void main(String[] args){
        Long inviterId = Long.valueOf(null);
        System.out.println("inviterId:"+inviterId);
    }
}
