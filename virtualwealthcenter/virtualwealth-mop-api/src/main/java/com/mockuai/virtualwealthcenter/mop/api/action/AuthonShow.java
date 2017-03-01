package com.mockuai.virtualwealthcenter.mop.api.action;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.SELECT_AUDIT_STATUS;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;

public class AuthonShow extends BaseAction {

	public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
		Long userId = (Long) request.getAttribute("user_id");
		String appKey = (String) request.getParam("app_key");
		Request authonReq = new BaseRequest();
		authonReq.setCommand(SELECT_AUDIT_STATUS.getActionName());
		authonReq.setParam("userId", userId);
		authonReq.setParam("appKey", appKey);
		   Response marketResp = getVirtualWealthService().execute(authonReq);
	        MopResponse response;
			if (marketResp.isSuccess()) {
	            response = new MopResponse(marketResp.getModule());
	        } else {
	            response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
	        }
	        return response;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "/user/authon/Auditing";
	}

	public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }


}
