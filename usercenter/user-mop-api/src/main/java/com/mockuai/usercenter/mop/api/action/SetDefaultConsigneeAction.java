package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.mop.api.domain.ConsigneeUidDTO;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

public class SetDefaultConsigneeAction extends BaseAction{

	public MopResponse execute(Request request) {
		String consigneeUid  = (String)request.getParam("consignee_uid");
		String appKey = (String)request.getParam("app_key");
		ConsigneeUidDTO dto = MopApiUtil.parseConsigneeUid(consigneeUid);
		com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
		userReq.setParam("consigneeId", dto.getConsigneeId());
		userReq.setParam("userId", dto.getUserId());
		userReq.setParam("appKey", appKey);
		userReq.setCommand(ActionEnum.SET_DEFAULT_CONSIGNEE.getActionName());
		Response<UserConsigneeDTO> userResp = this.getUserDispatchService().execute(userReq);
		return MopApiUtil.transferResp(userResp);
	}

	public String getName() {
		return "/user/consignee/default/set";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_POST;
	}
	
}