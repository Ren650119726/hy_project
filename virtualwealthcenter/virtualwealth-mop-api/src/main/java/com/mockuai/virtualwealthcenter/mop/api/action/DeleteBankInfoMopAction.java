package com.mockuai.virtualwealthcenter.mop.api.action;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.DEL_BANK_INFO;
import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.LIST_BANK_INFO;






import java.util.List;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopBankInfoAppDTO;

public class DeleteBankInfoMopAction extends BaseAction{

	//删除银行卡，只剩一个卡，默认更改为默认卡,记得判断如果是最后一个默认设置成默认卡。
	public String getName() {
		return "/myaccount/bank/del";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_POST;
	}

	public MopResponse execute(
			com.mockuai.mop.common.service.action.Request request) {
		Long userId = (Long)request.getAttribute("user_id");
		
		//Long userId = Long.valueOf((String)request.getParam("user_id"));
        Long id  = Long.valueOf((String)request.getParam("id"));
        
        String appKey = (String) request.getParam("app_key");
        Request marketReq = new BaseRequest();
        marketReq.setCommand(DEL_BANK_INFO.getActionName());
	 	marketReq.setParam("userId", userId);
	 	marketReq.setParam("id", id);
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
