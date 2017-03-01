package com.mockuai.virtualwealthcenter.mop.api.action;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.LIST_WD_BANK;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;

public class ListWithdrawalsMopAction extends BaseAction{
		//提现选择银行卡，默认要有一张，否则提示进入实名认证流程
		public String getName() {
			return "/myaccount/withdrawals/choicebanklist";
		}

		public ActionAuthLevel getAuthLevel() {
			return ActionAuthLevel.AUTH_LOGIN;
		}

		public HttpMethodLimit getMethodLimit() {
			return HttpMethodLimit.ONLY_GET;
		}
		
		
		public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
			Long userId = (Long)request.getAttribute("user_id");
			
			//Long userId = Long.valueOf((String)request.getParam("user_id"));
			
	        String appKey = (String) request.getParam("app_key");
		
	        Request marketReq = new BaseRequest();
	        marketReq.setCommand(LIST_WD_BANK.getActionName());
		 	marketReq.setParam("userId", userId);

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
