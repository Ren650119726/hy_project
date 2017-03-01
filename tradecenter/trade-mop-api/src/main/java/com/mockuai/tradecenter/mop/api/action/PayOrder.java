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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PayOrder extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(PayOrder.class);

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String orderUidStr = (String) request.getParam("order_uid");
        String payTypeStr = (String) request.getParam("pay_type");
        String payPassword = (String) request.getParam("pay_password");

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

        Integer payType = null;
        try {
            payType = Integer.valueOf(payTypeStr);
        } catch (Exception e) {
            log.error("error to parse pay_type", e);
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "pay_type's format is invalid");
        }

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.PAY_ORDER.getActionName());
        tradeReq.setParam("orderId", orderUidDTO.getOrderId());
        tradeReq.setParam("userId", orderUidDTO.getUserId());
        tradeReq.setParam("payType", payType);
        tradeReq.setParam("payPassword", payPassword);
        tradeReq.setParam("appKey", appKey);

        Response tradeResp = getTradeService().execute(tradeReq);

        return MopApiUtil.transferResp(tradeResp);
    }

    public String getName() {
        return "/trade/order/pay";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
