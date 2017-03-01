package com.mockuai.tradecenter.mop.api.action;


import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.CartItemUidDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.util.JsonUtil;

import java.util.List;


public class RemoveCartItem extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {

        //暂时只支持登陆之后操作购物车
        String cartItemUidListStr = (String) request.getParam("cart_item_uid_list");
        String appKey = (String)request.getParam("app_key");

        List<String> cartItemUidList = (List<String>) JsonUtil.parseJson(cartItemUidListStr, List.class);
        String command = null;
        Long userId = null;
        userId = (Long) request.getAttribute("user_id");
        command = "deleteUserCartItem";

        for (String cartItemUid : cartItemUidList) {
            CartItemUidDTO cartItemUidDTO = ModelUtil.parseCartItemUid(cartItemUid);
            com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
            tradeReq.setCommand(command);
            tradeReq.setParam("cartItemId", Long.valueOf(cartItemUidDTO.getCartItemId()));
            tradeReq.setParam("userId", userId);
            tradeReq.setParam("appKey", appKey);

            Response tradeResp = getTradeService().execute(tradeReq);
            if (tradeResp.getCode() != ResponseCode.RESPONSE_SUCCESS.getCode()) {
                return new MopResponse(tradeResp.getCode(), tradeResp.getMessage());
            }
        }

        return new MopResponse(MopRespCode.REQUEST_SUCESS);
    }

    public String getName() {
        return "/trade/cart/item/remove";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}

/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
 * Qualified Name:     com.mockuai.tradecenter.mop.api.action.RemoveCartItem
 * JD-Core Version:    0.6.2
 */