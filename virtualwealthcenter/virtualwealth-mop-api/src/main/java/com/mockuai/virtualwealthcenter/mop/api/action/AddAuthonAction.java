package com.mockuai.virtualwealthcenter.mop.api.action;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.USER_AUTHON_ADD;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;

public class AddAuthonAction extends BaseAction {

	public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
		Long userId = (Long) request.getAttribute("user_id");
		String userName = (String) request.getParam("bank_realname");
		String userPersonalId = (String) request.getParam("bank_personal_id");
		String pictureFront = (String) request.getParam("picture_front");
		String pictureBack = (String) request.getParam("picture_back");
		String appKey = (String) request.getParam("app_key");
		Request authonReq = new BaseRequest();
		authonReq.setCommand(USER_AUTHON_ADD.getActionName());
		authonReq.setParam("userId", userId);
		authonReq.setParam("bank_realname", userName);
		authonReq.setParam("bank_personal_id", userPersonalId);
		authonReq.setParam("picture_front", pictureFront);
		authonReq.setParam("picture_back", pictureBack);
		authonReq.setParam("appKey", appKey);

		Response marketResp = getVirtualWealthService().execute(authonReq);
		MopResponse response;
		
		if (marketResp.isSuccess()) {
            response = new MopResponse(MopRespCode.REQUEST_SUCESS);
        } else {
            response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }
        return response;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "/user/authon/save";
	}

	public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }

}
