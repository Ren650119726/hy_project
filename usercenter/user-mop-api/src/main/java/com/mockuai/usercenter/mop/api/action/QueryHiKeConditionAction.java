package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

public class QueryHiKeConditionAction extends BaseAction {

    @SuppressWarnings({ "rawtypes", "unchecked" })	
	public MopResponse execute(Request request) {
        Long userId = (Long) request.getAttribute("user_id");//用户id
        String appKey = (String)request.getParam("app_key");//应用接入标识码

        com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
        userReq.setParam("user_id", userId);
        userReq.setParam("appKey", appKey);        
        userReq.setCommand(ActionEnum.QUERY_HIKECONDITION.getActionName());
        Response<UserDTO> res = getUserDispatchService().execute(userReq);

        return MopApiUtil.transferResp(res);
    }

    public String getName() {
        return "/user/hikeCondition/query";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
