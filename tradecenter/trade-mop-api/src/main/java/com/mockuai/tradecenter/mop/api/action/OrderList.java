package com.mockuai.tradecenter.mop.api.action;


import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


public class OrderList extends BaseAction {
    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        /*订单状态判断*/
    	Integer orderStatus = null;
        String orderStatusStr = null;
		if(!StringUtils.isBlank((String) request.getParam("order_status"))){
		    orderStatusStr = (String) request.getParam("order_status");
		    try {        	
	            orderStatus = Integer.valueOf(Integer.parseInt(orderStatusStr));
	            /* TODO 前端app待发货状态特殊处理，前端传30，后台返回30,35的数据 */
	            if(EnumOrderStatus.PAID.getCode().equals(orderStatusStr)){
	            	orderStatusStr = " user_order.order_status = " + EnumOrderStatus.PAID.getCode()+" or user_order.order_status = "+ EnumOrderStatus.UN_DELIVER.getCode();
	            }else{
	            	orderStatusStr = " user_order.order_status = "+ orderStatusStr;
	            }
			} catch (Exception e) {
				return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "参数order_status["+orderStatusStr+"]格式有误，必须数字类型");
			}
		}
        
		if(StringUtils.isBlank((String) request.getParam("offset"))){
			return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "参数offset["+(String) request.getParam("offset")+"]不能为空");
		}
		if(StringUtils.isBlank((String) request.getParam("count"))){
			return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "参数count["+(String) request.getParam("count")+"]不能为空");
		}
		if(StringUtils.isBlank((String) request.getParam("app_key"))){
			return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "app_key["+(String) request.getParam("app_key")+"]不能为空");
		}
        String offsetStr = (String) request.getParam("offset");
        String countStr = (String) request.getParam("count");
        String appKey = (String)request.getParam("app_key");
        
        Long userId = (Long) request.getAttribute("user_id");
        if(userId==null){
        	return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "用户id：user_id["+userId+"]不能为空");
        }
        
        Integer offset = 0 ;
        Integer count = 20;
		try {
			offset = Integer.valueOf(Integer.parseInt(offsetStr));
			if(offset<0){
				return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "offset["+offsetStr+"]格式有误，必须>=0");
			}
		} catch (Exception e) {
			return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "offset["+offsetStr+"]格式有误，必须数字类型");
		}
		try {
			count = Integer.valueOf(Integer.parseInt(countStr));
			if(count<=0){
				return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "offset["+offsetStr+"]格式有误，必须数>0");
			}
		} catch (Exception e) {
			return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "count["+countStr+"]格式有误，必须数字类型");
		} 
		
        OrderQTO orderQTO = new OrderQTO();
        orderQTO.setOrderStatus(orderStatus);
        orderQTO.setOrderStatusStr(orderStatusStr);
        orderQTO.setUserId(userId);
		orderQTO.setOffset(offset);
		orderQTO.setCount(count);
		
       
        Integer  allRefundingOrderMark = 0;
        if(!StringUtils.isBlank((String)request.getParam("refund_mark"))){
        	try {
            	allRefundingOrderMark = Integer.parseInt((String)request.getParam("refund_mark"));
			} catch (Exception e) {
				return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "refund_mark["+(String)request.getParam("refund_mark")+"]格式有误，必须数字类型");
			}
        	orderQTO.setAllRefundingMark(allRefundingOrderMark);
        }
        
        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand("queryUserOrder");
        tradeReq.setParam("orderQTO", orderQTO);
        tradeReq.setParam("appKey", appKey);


        Response tradeResp = getTradeService().execute(tradeReq);

        if (tradeResp.getCode() == ResponseCode.RESPONSE_SUCCESS.getCode()) {
            List<OrderDTO> orderDTOs = (List<OrderDTO>) tradeResp.getModule();
            List mopOrderDTOs = new ArrayList();
            if (orderDTOs != null) {
                for (OrderDTO orderDTO : orderDTOs) {
                    mopOrderDTOs.add(MopApiUtil.genMopOrder(orderDTO));
                }
            }

            Map data = new HashMap();
            data.put("order_list", mopOrderDTOs);
            /*data.put("total_count", Long.valueOf(tradeResp.getTotalCount()));*/
            /*data.put("current_time", new Date());*/
            
            return new MopResponse(data);
        }
        return MopApiUtil.transferResp(tradeResp);
    }

    public String getName() {
        return "/trade/order/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}