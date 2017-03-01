package com.mockuai.tradecenter.mop.api.action;


import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.ResponseFormat;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;


public class LianlianpayCallback extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {

    	String reqStr = request.getRequestInputStream();
//    	System.out.println(" reqStr : "+reqStr);
        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.LIANLIAN_PAY_CALLBACK.getActionName());
        tradeReq.setParam("reqStr", reqStr);

        Response tradeResp = getTradeService().execute(tradeReq);

        return new MopResponse(tradeResp.getModule());
    }


    public String getName() {
        return "/trade/order/payment/callback/lianlianpay_notify";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }

    public ResponseFormat getResponseFormat() {
        return ResponseFormat.CUSTOMIZE;
    }
}