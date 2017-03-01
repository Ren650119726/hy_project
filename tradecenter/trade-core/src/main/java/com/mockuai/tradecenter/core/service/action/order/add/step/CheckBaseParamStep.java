package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.order.GetOrder;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 下单接口基本信息校验
 * Created by zengzhangqiang on 5/19/16.
 */
public class CheckBaseParamStep extends TradeBaseStep {
	private static final Logger log = LoggerFactory.getLogger(CheckBaseParamStep.class);
	
    @Override
    public StepName getName() {
        return StepName.CHECK_BASE_PARAM_STEP;
    }

    @Override
    public TradeResponse execute() {
        OrderDTO orderDTO = (OrderDTO) this.getParam("orderDTO");

        //订单信息校验
        if(orderDTO == null) {
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderDTO is null");
        }
        
        log.info(" addorder orderDTO : "+JSONObject.toJSONString(orderDTO));
        
        //买家id校验
        if (orderDTO.getUserId() == null) {
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
        }

        //订单收货地址信息校验
        if (orderDTO.getOrderConsigneeDTO() == null
                || orderDTO.getOrderConsigneeDTO().getConsigneeId() == null) {
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "consigneeId is null");
        }

        //订单支付方式校验
        if (orderDTO.getPaymentId() == null) {
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "paymentId is null");
        }

        //订单收货方式校验
        if (orderDTO.getDeliveryId() == null) {
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "deliveryId is null");
        }

        //订单商品列表校验
        if (orderDTO.getOrderItems() == null || orderDTO.getOrderItems().size() == 0) {
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,
                    "orderItemList is null or empty");
        }
        
        // 订单商品数量必须为正整数
        for(OrderItemDTO orderItemDTO:orderDTO.getOrderItems()){
        	if(orderItemDTO.getNumber()<0){
        		return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_INVALID,
                        "item number < 0 ");
        	}
        }

        // TODO 订单备注校验：长度、内容等信息的校验
//        String memo = orderDTO.getUserMemo();

        return ResponseUtils.getSuccessResponse();
    }
}
