package com.mockuai.tradecenter.core.service.action.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.tradecenter.common.domain.*;
import com.mockuai.tradecenter.core.domain.*;
import com.mockuai.tradecenter.core.manager.*;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderInvoiceDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.ModelUtil;

@Service
public class GetPreOrder implements Action{
	private static final Logger log = LoggerFactory.getLogger(GetPreOrder.class);
	
	@Resource 
	private OrderManager orderManager;
	
	@Resource
	private DozerBeanService  dozerBeanService;

	public TradeResponse<OrderDTO> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<OrderDTO> response = null;
		
		if (request.getParam("orderQTO") == null) {
			log.error("orderQTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderQTO is null");
		}
		
		OrderQTO orderQTO = (OrderQTO) request.getParam("orderQTO");
		
		if(null==orderQTO.getUserId()){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
		}
		if(null==orderQTO.getItemSkuId()){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "itemSkuId is null");
		}

		
		try{
			OrderDO orderDO = this.orderManager.getPreOrder(orderQTO.getUserId(), orderQTO.getItemSkuId());

			if(orderDO == null){
				return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"pre order doesn't exist");
			}

			OrderDTO orderDTO = new OrderDTO();
			orderDTO = ModelUtil.convert2OrderDTO(orderDO);

			
			response = ResponseUtils.getSuccessResponse(orderDTO);
			return response;
		}catch(TradeException e){
			log.error("getOrder error: ",e);
			return ResponseUtils.getFailResponse(e.getResponseCode());
		}
	}

	
	
	@Override
	public String getName() {
		return ActionEnum.GET_PRE_ORDER.getActionName();
	}
}
