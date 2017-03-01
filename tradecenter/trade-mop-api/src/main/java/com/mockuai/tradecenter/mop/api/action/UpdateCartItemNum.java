package com.mockuai.tradecenter.mop.api.action;


import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.common.domain.CartItemUidDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;


public class UpdateCartItemNum extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String cartItemUid = (String) request.getParam("cart_item_uid");
        String numberStr = (String) request.getParam("number");
        String appKey = (String)request.getParam("app_key");

        String command = null;
        CartItemDTO cartItemDTO = new CartItemDTO();
        long userId = 0L;
        userId = ((Long) request.getAttribute("user_id")).longValue();
        command = "updateUserCartItem";

        CartItemUidDTO cartItemUidDTO = ModelUtil.parseCartItemUid(cartItemUid);

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(command);
        tradeReq.setParam("cartItemId", Long.valueOf(cartItemUidDTO.getCartItemId()));
        tradeReq.setParam("userId", Long.valueOf(userId));
        tradeReq.setParam("number", Integer.valueOf(numberStr));
        tradeReq.setParam("appKey", appKey);

        Response tradeResp = getTradeService().execute(tradeReq);
        if (tradeResp.getCode() == ResponseCode.RESPONSE_SUCCESS.getCode()) {
            return new MopResponse(MopRespCode.REQUEST_SUCESS);
        }

        return new MopResponse(tradeResp.getCode(), tradeResp.getMessage());
    }

    public String getName() {
        return "/trade/cart/item/number/update";
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