package com.mockuai.virtualwealthcenter.mop.api.action;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.CHOICE_WD_BANK;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;

public class ChoiceBankWithdrawalsMopAction extends BaseAction{
	//选择银行卡提现
		public String getName() {
			return "/myaccount/withdrawals/choicebank";
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

	        //银行卡号
	        String authon_no = (String)request.getParam("bank_no");
	        
	        String appKey = (String) request.getParam("app_key");
		
	        Request marketReq = new BaseRequest();
	        marketReq.setCommand(CHOICE_WD_BANK.getActionName());
		 	marketReq.setParam("userId", userId);
	        marketReq.setParam("bank_no", authon_no);
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
