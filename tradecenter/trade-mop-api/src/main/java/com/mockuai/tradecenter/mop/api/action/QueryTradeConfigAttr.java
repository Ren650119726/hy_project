package com.mockuai.tradecenter.mop.api.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.TradeConfigDTO;

public class QueryTradeConfigAttr extends BaseAction{

	
	public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String appKey = (String)request.getParam("app_key");

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.QUERY_TRADE_CONFIG.getActionName());
        tradeReq.setParam("appKey", appKey);


        Response tradeResp = getTradeService().execute(tradeReq);
        if (tradeResp.getCode() == ResponseCode.RESPONSE_SUCCESS.getCode()) {
        	 Map data = new HashMap();
        	 List<TradeConfigDTO> tradeConfigList = (List<TradeConfigDTO>) tradeResp.getModule();
        	 data.put("trade_config_list", tradeConfigList);
        	 return new MopResponse(data);
        }
        return new MopResponse(tradeResp.getCode(), tradeResp.getMessage());
    }
	
	public String getName() {
        return "/trade/config/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
