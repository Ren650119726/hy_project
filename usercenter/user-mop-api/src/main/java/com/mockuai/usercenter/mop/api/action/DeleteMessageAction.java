package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.mop.api.domain.MessageUidDTO;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

public class DeleteMessageAction extends BaseAction{

	public MopResponse execute(Request request) {

		String messageUid = (String)request.getParam("message_uid");
		Long userId = (Long)request.getAttribute("user_id");
		String appKey = (String)request.getParam("app_key");
		MessageUidDTO messageUidDTO = MopApiUtil.parseMessageUid(messageUid);

		if(messageUidDTO == null){
			return new MopResponse(MopRespCode.P_E_PARAM_ISNULL);
		}

		com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
		userReq.setParam("id", messageUidDTO.getId());
		userReq.setParam("userId", userId);
		userReq.setParam("appKey", appKey);
		userReq.setCommand(ActionEnum.DEL_USER_MESSAGE.getActionName());
		Response<UserMessageDTO> userResp = this.getUserDispatchService().execute(userReq);
		return MopApiUtil.transferResp(userResp);
	}

	public String getName() {
		return "/user/message/delete";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_POST;
	}
	
}
