package com.mockuai.virtualwealthcenter.mop.api.action;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.ADD_WD_SUBMIT;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;


public class AddWithdrawalsMopAction  extends BaseAction{

		//提现申请
		public String getName() {
			return "/myaccount/withdrawals/apply";
		}
		
		public ActionAuthLevel getAuthLevel() {
			return ActionAuthLevel.AUTH_LOGIN;
		}

		public HttpMethodLimit getMethodLimit() {
			return HttpMethodLimit.ONLY_POST;
		}
		
		
		public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
			Long userId = (Long)request.getAttribute("user_id");
			
			//Long userId = Long.valueOf((String)request.getParam("user_id"));
			
			
			String withdrawals_amount  = (String) request.getParam("withdrawals_amount");
	        String withdrawals_no = (String) request.getParam("withdrawals_no");

	        
	        String appKey = (String) request.getParam("app_key");
	        Request marketReq = new BaseRequest();
	        marketReq.setCommand(ADD_WD_SUBMIT.getActionName());
		 	marketReq.setParam("userId", userId);
	        marketReq.setParam("withdrawals_amount", withdrawals_amount);
	        marketReq.setParam("withdrawals_no", withdrawals_no);
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
