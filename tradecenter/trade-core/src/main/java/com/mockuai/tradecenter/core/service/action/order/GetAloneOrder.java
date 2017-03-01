package com.mockuai.tradecenter.core.service.action.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.OrderServiceDTO;
import com.mockuai.tradecenter.common.domain.OrderStoreDTO;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderServiceDO;
import com.mockuai.tradecenter.core.domain.OrderStoreDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderConsigneeManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.StoreManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.ModelUtil;

@Service
public class GetAloneOrder implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetAloneOrder.class);

	@Resource
	private OrderManager orderManager;
	@Resource
	private OrderItemManager orderItemManager;
	
	@Resource
	private StoreManager storeManager;
	
	@Resource
	private DozerBeanService  dozerBeanService;
	
	@Resource
	private OrderConsigneeManager orderConsigneeManager;

	public TradeResponse<OrderDTO> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<OrderDTO> response = null;

		if (request.getParam("userId") == null) {
			log.error("userId is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
		} else if (request.getParam("orderId") == null) {
			log.error("userId is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderId is null");
		}
		String appKey = (String) context.get("appKey");
		long orderId = (Long) request.getParam("orderId");
		long userId = (Long) request.getParam("userId");
		OrderDO orderDO = null;
		try {
			orderDO = this.orderManager.getOrder(orderId, userId);

			if (orderDO == null) {
				return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order doesn't exist");
			}

			OrderDTO orderDTO = new OrderDTO();
			orderDTO = ModelUtil.convert2OrderDTO(orderDO);

			// 查询订单明细
			OrderItemQTO orderItemQTO = new OrderItemQTO();
			orderItemQTO.setOrderId(orderId);
			orderItemQTO.setUserId(userId);
			List<OrderItemDO> orderItems = Collections.EMPTY_LIST;
			orderItems = this.orderItemManager.queryOrderItem(orderItemQTO);

			if(orderItems==null || orderItems.isEmpty()){
			}else{
//				List<OrderItemDTO> orderItemDTOList = ModelUtil.convert2OrderItemDTOList(orderItems);
				
				 List<OrderItemDTO> orderItemsDTOs = new ArrayList<OrderItemDTO>();
			        for(OrderItemDO item: orderItems){
			            orderItemsDTOs.add(ModelUtil.convert2OrderItemDTO(item));
			        }
				
				orderDTO.setOrderItems(orderItemsDTOs);
			}
			OrderStoreDO orderStore = storeManager.getOrderStore(orderId);
			if( null!= orderStore ){
				OrderStoreDTO orderStoreDTO = dozerBeanService.cover(orderStore, OrderStoreDTO.class);
				orderDTO.setOrderStoreDTO(orderStoreDTO);
			}
			//查询订单收货地址信息
			OrderConsigneeDO orderConsigneeDO = orderConsigneeManager.getOrderConsignee(orderId, userId);

			if(orderConsigneeDO == null){
				//TODO error handle
			}else{
				OrderConsigneeDTO orderConsigneeDTO = ModelUtil.convert2OrderConsigneeDTO(orderConsigneeDO);
				orderDTO.setOrderConsigneeDTO(orderConsigneeDTO);
			}
			
			response = ResponseUtils.getSuccessResponse(orderDTO);
			return response;
		} catch (TradeException e) {
			log.error("getOrder error: ", e);
			return ResponseUtils.getFailResponse(e.getResponseCode());
		}
	}

	@Override
	public String getName() {
		return ActionEnum.GET_ALONE_ORDER.getActionName();
	}
}
