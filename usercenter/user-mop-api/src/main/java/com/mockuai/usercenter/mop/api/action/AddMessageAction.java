package com.mockuai.usercenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

import java.util.HashMap;
import java.util.Map;

public class AddMessageAction extends BaseAction{

	public MopResponse execute(Request request) {
		String src = (String)request.getParam("message");
		Long sellerId = (Long)request.getAttribute("user_id");
		String appKey = (String)request.getParam("app_key");
		UserMessageDTO message = JsonUtil.parseJson(src, UserMessageDTO.class);
		message.setSenderId(sellerId);
		com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
		userReq.setParam("userMessageDTO", message);
		userReq.setParam("appKey", appKey);
		userReq.setCommand(ActionEnum.ADD_USER_MESSAGE.getActionName());
		Response<UserMessageDTO> userResp = this.getUserDispatchService().execute(userReq);
		if(ResponseCode.REQUEST_SUCCESS.getValue() == userResp.getCode()){
			Map<String,String> data = new HashMap<String, String>();
			data.put("message_uid",
					""+userResp.getModule().getReceiverId()+"_"+userResp.getModule().getId());//TODO 待重构
			return new MopResponse(data);
		}else{
			return new MopResponse(userResp.getCode(), userResp.getMessage());
		}
	}

	public String getName() {
		return "/user/message/add";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_POST;
	}
	
}
