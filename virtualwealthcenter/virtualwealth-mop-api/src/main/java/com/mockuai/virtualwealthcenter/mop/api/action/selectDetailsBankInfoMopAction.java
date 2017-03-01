package com.mockuai.virtualwealthcenter.mop.api.action;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.SEL_BANK_INFO;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Response;

public class selectDetailsBankInfoMopAction extends BaseAction{

	//查看银行卡详情
		public String getName() {
			return "/myaccount/bank/details";
		}

		public ActionAuthLevel getAuthLevel() {
			return ActionAuthLevel.AUTH_LOGIN;
		}

		public HttpMethodLimit getMethodLimit() {
			return HttpMethodLimit.ONLY_GET;
		}

		public MopResponse execute(
				com.mockuai.mop.common.service.action.Request request) {
		
			Long userId = (Long)request.getAttribute("user_id");
			//Long userId = Long.valueOf((String)request.getParam("user_id"));
	        Long id  = Long.valueOf((String)request.getParam("id"));
	        
	        String appKey = (String) request.getParam("app_key");
	        Request marketReq = new BaseRequest();
	        marketReq.setCommand(SEL_BANK_INFO.getActionName());
		 	marketReq.setParam("userId", userId);
		 	marketReq.setParam("id", id);
		 	marketReq.setParam("appKey", appKey);
	        Response marketResp = getVirtualWealthService().execute(marketReq);
	        MopResponse response;
			if (marketResp.isSuccess()) {
	            response = new MopResponse(marketResp.getModule());
	        } else {
	            response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
	        }
	        return response;
		}

}
