package com.mockuai.tradecenter.mop.api.action;


import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderUidDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.util.JsonUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class ReceiptOrder extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
    	if(StringUtils.isBlank((String)request.getParam("order_uid_list"))){
    		return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "order_uid_list["+(String)request.getParam("order_uid_list")+"]为空");			
    	}
    	if(StringUtils.isBlank((String)request.getParam("app_key"))){
    		return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "app_key["+(String)request.getParam("app_key")+"]为空");			
    	}
        String orderUidStrs = (String) request.getParam("order_uid_list");        
        String appKey = (String)request.getParam("app_key");

        List orderUidStrList = null;
        try {
        	orderUidStrList = (List) JsonUtil.parseJson(orderUidStrs, List.class);
            if(orderUidStrList == null){
            	return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "order_uid_list["+(String)request.getParam("order_uid_list")+"]格式有误，转化出错");
            }
		} catch (Exception e) {
			return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "order_uid_list["+(String)request.getParam("order_uid_list")+"]格式有误，转化出错");
		}
        
        Iterator i$ = orderUidStrList.iterator();
        if (i$.hasNext()) {
            String orderUidStr = (String) i$.next();
            OrderUidDTO orderUidDTO = null;
            try {
            	orderUidDTO = ModelUtil.parseOrderUid(orderUidStr);
                if(orderUidDTO == null){
                	return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "order_uid_list["+(String)request.getParam("order_uid_list")+"]格式有误，转化出错");
                }
			} catch (Exception e) {
				return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "order_uid_list["+(String)request.getParam("order_uid_list")+"]格式有误，转化出错");
			}
            com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
            tradeReq.setCommand(ActionEnum.CONFIRM_RECEIVAL.getActionName());
            tradeReq.setParam("orderId", orderUidDTO.getOrderId());
            tradeReq.setParam("userId", orderUidDTO.getUserId());
            tradeReq.setParam("appKey", appKey);


            Response tradeResp = getTradeService().execute(tradeReq);
            if (tradeResp.getCode() != ResponseCode.RESPONSE_SUCCESS.getCode()) {
                return MopApiUtil.transferResp(tradeResp);
            }
            return new MopResponse(tradeResp.getCode(), tradeResp.getMessage());
        }

        return MopApiUtil.getResponse(MopRespCode.REQUEST_SUCESS);
    }

    public String getName() {
        return "/trade/order/receipt";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}

/* Location:           /work/tmp/trade-mop-api-0.0.1-20150519.033139-22.jar
 * Qualified Name:     com.mockuai.tradecenter.mop.api.action.ReceiptOrder
 * JD-Core Version:    0.6.2
 */