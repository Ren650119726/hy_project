package com.mockuai.tradecenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderUidDTO;
import com.mockuai.tradecenter.common.util.ModelUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;

/**
 * Created by zengzhangqiang on 4/27/15.
 */
public class GetOrder extends BaseAction {
    @Override
    public MopResponse execute(Request request) {
    	/*参数校验*/
    	if(StringUtils.isBlank((String)request.getParam("order_uid"))){
    		return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "参数order_uid["+(String)request.getParam("order_uid")+"]为空");
    	}
    	if(StringUtils.isBlank((String)request.getParam("app_key"))){
    		return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "app_key["+(String)request.getParam("app_key")+"]为空");
    	}
        String orderUidStr = (String)request.getParam("order_uid");
        String appKey = (String)request.getParam("app_key");

        OrderUidDTO orderUidDTO = null;
        try{
            orderUidDTO = ModelUtil.parseOrderUid(orderUidStr);
            if(orderUidDTO == null){
            	return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "参数order_uid["+(String)request.getParam("order_uid")+"]格式有误");
            }
        }catch(Exception e){
        	return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "参数order_uid["+(String)request.getParam("order_uid")+"]格式有误");
        }

        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand("getOrder");
        tradeReq.setParam("orderId", orderUidDTO.getOrderId());
        tradeReq.setParam("userId", orderUidDTO.getUserId());
        tradeReq.setParam("appKey", appKey);


        //TODO refactor the response data's type of cancalOrder service to void
        Response<OrderDTO> tradeResp = getTradeService().execute(tradeReq);

        return MopApiUtil.transferResp(tradeResp);
    }

    @Override
    public String getName() {
        return "/trade/order/get";
    }
    
    public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_GET;
	}
}
