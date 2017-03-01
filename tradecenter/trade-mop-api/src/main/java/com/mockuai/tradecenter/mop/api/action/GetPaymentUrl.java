package com.mockuai.tradecenter.mop.api.action;


import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.OrderUidDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;


public class GetPaymentUrl extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String orderUidStr = (String) request.getParam("order_uid");
        String payType = (String) request.getParam("pay_type");
        String openId = (String)request.getParam("open_id");
        String appKey = (String)request.getParam("app_key");


        OrderUidDTO orderUidDTO = ModelUtil.parseOrderUid(orderUidStr);
        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.GET_PAYMENT_URL.getActionName());
        tradeReq.setParam("orderId", orderUidDTO.getOrderId());
        tradeReq.setParam("userId", orderUidDTO.getUserId());
        tradeReq.setParam("payType", payType);
        tradeReq.setParam("openId", openId);
        tradeReq.setParam("appKey", appKey);


        Response tradeResp = getTradeService().execute(tradeReq);

        return MopApiUtil.transferResp(tradeResp);
    }

    public String getName() {
        return "/trade/order/payment_url/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}

/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
 * Qualified Name:     com.mockuai.tradecenter.mop.api.action.GetPaymentUrl
 * JD-Core Version:    0.6.2
 */