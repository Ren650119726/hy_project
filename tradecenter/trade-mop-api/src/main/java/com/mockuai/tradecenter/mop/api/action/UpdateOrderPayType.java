package com.mockuai.tradecenter.mop.api.action;


import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.common.domain.CartItemUidDTO;
import com.mockuai.tradecenter.common.domain.OrderUidDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;

public class UpdateOrderPayType extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String paymentIdStr = (String) request.getParam("payment_id");
        String orderUidStr = (String) request.getParam("order_uid");
        String appKey = (String)request.getParam("app_key");

        OrderUidDTO orderUidDTO = ModelUtil.parseOrderUid(orderUidStr);
        Long userId = ((Long) request.getAttribute("user_id")).longValue();

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.UPDATE_ORDER_PAY_TYPE.getActionName());
        tradeReq.setParam("orderId", orderUidDTO.getOrderId());
        tradeReq.setParam("userId", userId);
        tradeReq.setParam("paymentId", Integer.valueOf(paymentIdStr));
        tradeReq.setParam("appKey", appKey);

        Response tradeResp = getTradeService().execute(tradeReq);
        if (tradeResp.getCode() == ResponseCode.RESPONSE_SUCCESS.getCode()) {
            return new MopResponse(MopRespCode.REQUEST_SUCESS);
        }else{
            return new MopResponse(tradeResp.getCode(), tradeResp.getMessage());
        }
    }

    public String getName() {
        return "/trade/order/pay_type/update";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}

/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
 * Qualified Name:     com.mockuai.tradecenter.mop.api.action.UpdateCartItemNum
 * JD-Core Version:    0.6.2
 */