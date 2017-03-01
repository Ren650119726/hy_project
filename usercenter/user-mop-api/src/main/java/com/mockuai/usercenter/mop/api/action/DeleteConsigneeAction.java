package com.mockuai.usercenter.mop.api.action;

import java.util.List;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.mop.api.domain.ConsigneeUidDTO;
import com.mockuai.usercenter.mop.api.util.JsonUtil;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

public class DeleteConsigneeAction extends BaseAction{

	public MopResponse execute(Request request) {
		String consigneeUidList  = (String)request.getParam("consignee_uid_list");
		String appKey = (String)request.getParam("app_key");
		//返回的参数是一个列表
		List<String> uidList = JsonUtil.parseJson(consigneeUidList, List.class);
		for(String uid : uidList){
			ConsigneeUidDTO dto = MopApiUtil.parseConsigneeUid(uid);
			com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
			userReq.setParam("consigneeId", dto.getConsigneeId());
			userReq.setParam("userId", dto.getUserId());
			userReq.setParam("appKey", appKey);
			userReq.setCommand(ActionEnum.DELETE_CONSIGNEE.getActionName());
			Response<UserConsigneeDTO> userResp = this.getUserDispatchService().execute(userReq);
			//TODO  后续改为批量删除
			if(userResp.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()){
				return new MopResponse(userResp.getCode(),userResp.getMessage());
			}
		}
		return new MopResponse(true);
	}

	public String getName() {
		return "/user/consignee/delete";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_POST;
	}
	
}
