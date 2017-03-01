package com.mockuai.tradecenter.mop.api.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.common.domain.dto.mop.ParamMarketItemDTO;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.mop.api.util.JsonUtil;


public class OrderSettlementGet extends BaseAction {

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
    	String orderItemListStr = (String) request.getParam("market_item_list");
        String consigneeUid = (String) request.getParam("consignee_uid");
        Long userId = (Long) request.getAttribute("user_id");
        String appKey = (String) request.getParam("app_key");       

        java.lang.reflect.Type type = new TypeToken<List<ParamMarketItemDTO>>() {
        }.getType();
        List<ParamMarketItemDTO> paramMarketItemDTOs = JsonUtil.parseJson(orderItemListStr, type);

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.ORDER_SETTLEMENT_GET.getActionName());
        tradeReq.setParam("consigneeId", com.mockuai.marketingcenter.common.util.MopApiUtil.parseConsigneeId(consigneeUid));
        tradeReq.setParam("itemList", com.mockuai.marketingcenter.common.util.MopApiUtil.genMarketingItemDTOList(paramMarketItemDTOs));
        tradeReq.setParam("userId", userId);
        tradeReq.setParam("appKey", appKey);
        Response tradeResp = getTradeService().execute(tradeReq);
        MopResponse response;
        if (tradeResp.isSuccess()) {
            SettlementInfo settlementInfo = (SettlementInfo) tradeResp.getModule();
            Map data = new HashMap();
            data.put("settlement_info", com.mockuai.marketingcenter.common.util.MopApiUtil.genMopSettlementInfo(settlementInfo));
            response = new MopResponse(data);
        } else {
            return new MopResponse(tradeResp.getCode(), tradeResp.getMessage());
        }

        return response;
    }

    public String getName() {
        return "/trade/order/confirm/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
