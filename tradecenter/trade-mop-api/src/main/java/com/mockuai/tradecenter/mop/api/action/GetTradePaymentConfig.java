package com.mockuai.tradecenter.mop.api.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.TradePaymentConfigDTO;
import com.mockuai.tradecenter.common.domain.TradePaymentConfigQTO;
import com.mockuai.tradecenter.common.enums.EnumDeleteMark;
import com.mockuai.tradecenter.common.enums.EnumPaymentConfigStatus;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;

/**
 * 
 */
public class GetTradePaymentConfig extends BaseAction {
    @Override
    public MopResponse execute(Request request) {


        String appKey = (String)request.getParam("app_key");

        TradePaymentConfigQTO tradePaymentConfigQTO = new TradePaymentConfigQTO();
        tradePaymentConfigQTO.setDeleteMark(EnumDeleteMark.UN_DELETE.getCode());
        tradePaymentConfigQTO.setStatus(EnumPaymentConfigStatus.OPEN.getCode());
        
        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.GET_PAYMENT_CONFIG.getActionName());
        tradeReq.setParam("tradePaymentConfigQTO", tradePaymentConfigQTO);
        tradeReq.setParam("appKey", appKey);


        Response<List<TradePaymentConfigDTO>> tradeResp = getTradeService().execute(tradeReq);

        if (tradeResp.getCode() == ResponseCode.RESPONSE_SUCCESS.getCode()) {
            
            Map data = new HashMap();
            data.put("trade_payment_config", tradeResp.getModule());
            
            return new MopResponse(data);
        }
        return MopApiUtil.transferResp(tradeResp);
    }

    @Override
    public String getName() {
        return "/trade/payment/method/get";
    }
    
    public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.NO_LIMIT;
	}
}
