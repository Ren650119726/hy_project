package com.mockuai.tradecenter.mop.api.action;


import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.OrderUidDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;


public class CancelOrder extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String orderUidStr = (String) request.getParam("order_uid");
        if(StringUtils.isBlank((String)request.getParam("app_key"))){
    		return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "app_key["+(String)request.getParam("app_key")+"]为空");
    	}
        String appKey = (String)request.getParam("app_key");

        if(StringUtils.isBlank(orderUidStr)){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "order_uid is null");
        }

        OrderUidDTO orderUidDTO = null;
        try{
            orderUidDTO = ModelUtil.parseOrderUid(orderUidStr);
            if(orderUidDTO == null){
            	return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "order_uid's format is invalid");
            }
        }catch(Exception e){
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "order_uid's format is invalid");
        }

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.CANCEL_ORDER.getActionName());
        tradeReq.setParam("orderId", orderUidDTO.getOrderId());
        tradeReq.setParam("userId", orderUidDTO.getUserId());
        tradeReq.setParam("appKey", appKey);

        Response tradeResp = getTradeService().execute(tradeReq);

        return MopApiUtil.transferResp(tradeResp);
    }

    public String getName() {
        return "/trade/order/cancel";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}

/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
 * Qualified Name:     com.mockuai.tradecenter.mop.api.action.CancelOrder
 * JD-Core Version:    0.6.2
 */