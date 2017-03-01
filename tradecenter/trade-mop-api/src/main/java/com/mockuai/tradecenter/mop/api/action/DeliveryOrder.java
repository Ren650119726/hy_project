package com.mockuai.tradecenter.mop.api.action;


import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.OrderUidDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.domain.MopDeliveryInfoDTO;
import com.mockuai.tradecenter.mop.api.util.JsonUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;

import java.util.List;

public class DeliveryOrder extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String orderUidStr = (String)request.getParam("order_uid");
        String deliveryInfoListStr = (String)request.getParam("delivery_info_list");
        String appKey = (String)request.getParam("app_key");


        if(StringUtils.isBlank(orderUidStr)){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "order_uid is null");
        }

        if(StringUtils.isBlank(deliveryInfoListStr)){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "deliveryInfoListStr is null");
        }


        List<MopDeliveryInfoDTO> deliveryInfoDTOs = null;
        try{
            java.lang.reflect.Type type = new TypeToken<List<MopDeliveryInfoDTO>>() {}.getType();
            deliveryInfoDTOs = JsonUtil.parseJson(deliveryInfoListStr, type);
        }catch(Exception e){
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "delivery_info_list's format is invalid");
        }


        OrderUidDTO orderUidDTO = null;
        try{
            orderUidDTO = ModelUtil.parseOrderUid(orderUidStr);
        }catch(Exception e){
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "order_uid's format is invalid");
        }

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.DELIVERY_GOODS.getActionName());
        tradeReq.setParam("orderId", orderUidDTO.getOrderId());
        tradeReq.setParam("userId", orderUidDTO.getUserId());
        tradeReq.setParam("sellerId", orderUidDTO.getSellerId());
        tradeReq.setParam("deliveryInfoList", MopApiUtil.genOrderDeliveryInfoDTOList(deliveryInfoDTOs));
        tradeReq.setParam("appKey", appKey);

        Response tradeResp = getTradeService().execute(tradeReq);
        return MopApiUtil.transferResp(tradeResp);
    }

    public String getName() {
        return "/trade/order/delivery";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}

/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
 * Qualified Name:     com.mockuai.tradecenter.mop.api.action.DeliveryOrder
 * JD-Core Version:    0.6.2
 */