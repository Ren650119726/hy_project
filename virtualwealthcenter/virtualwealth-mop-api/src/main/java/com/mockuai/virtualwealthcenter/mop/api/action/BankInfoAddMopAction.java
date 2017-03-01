package com.mockuai.virtualwealthcenter.mop.api.action;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.ADD_BANK_INFO;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
public class BankInfoAddMopAction extends BaseAction{

	
	
	//添加银行卡，如果没卡，默认去走实名认证流程。
	public String getName() {
		return "/myaccount/bank/save";
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
		
		//发卡银行
        String authon_bankname = (String)request.getParam("bank_name");
        //银行卡号
        String authon_no = (String)request.getParam("bank_no");
        
        String appKey = (String) request.getParam("app_key");
	
        Request marketReq = new BaseRequest();
        marketReq.setCommand(ADD_BANK_INFO.getActionName());
	 	marketReq.setParam("userId", userId);
        marketReq.setParam("bank_name", authon_bankname);
        marketReq.setParam("bank_no", authon_no);
	 	marketReq.setParam("appKey", appKey);
        Response marketResp = getVirtualWealthService().execute(marketReq);
        MopResponse response;
		if (marketResp.isSuccess()) {
            response = new MopResponse(MopRespCode.REQUEST_SUCESS);
        } else {
            response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }
        return response;

	}

	
}
