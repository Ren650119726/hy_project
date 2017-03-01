package com.mockuai.tradecenter.core.service.action.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.noggit.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.DeliveryDetailDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderTrackDTO;
import com.mockuai.tradecenter.common.domain.OrderTrackQTO;
import com.mockuai.tradecenter.core.domain.OrderTrackDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DeliveryManager;
import com.mockuai.tradecenter.core.manager.OrderTrackManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.DozerBeanService;

public class QueryOrderTrack implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryOrderTrack.class);
	
	@Autowired
	OrderTrackManager orderTrackMng;
	
	@Resource
	DozerBeanService  dozerBeanService;
	
	@Resource
	DeliveryManager deliveryManager;

	@Override
	public TradeResponse execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<List<OrderTrackDTO>> response = null;
		String appKey = (String) context.get("appKey");
		if (request.getParam("userId") == null) {
			log.error("userId is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
		} else if (request.getParam("orderId") == null) {
			log.error("userId is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderId is null");
		}

		long orderId = (Long) request.getParam("orderId");
		long userId = (Long) request.getParam("userId");
		
		OrderTrackQTO query = new OrderTrackQTO();
		query.setOrderId(orderId);
		
		
		List<OrderTrackDO> orderTrackDOs = orderTrackMng.queryOrderTrack(query);
		if(orderTrackDOs.isEmpty()){
			response = new TradeResponse(ResponseCode.RESPONSE_SUCCESS);
			return response;
		}
		
		List<OrderDeliveryInfoDTO> deliveryInfoDTOs = deliveryManager.queryDeliveryInfo(orderId, userId, appKey);
		
		List<OrderTrackDTO> orderTrackDTOs = dozerBeanService.coverList(orderTrackDOs, OrderTrackDTO.class);
		
		int index = orderTrackDTOs.size();
		for(int i=0;i<orderTrackDTOs.size();i++){
			OrderTrackDTO orderTrackDTO = orderTrackDTOs.get(i);
			if(orderTrackDTO.getOrderStatus()==50){
				index = i;
			}
		}
		
		orderTrackDTOs.addAll(index,genOrderTrackDTOs(deliveryInfoDTOs) );
		response = ResponseUtils.getSuccessResponse(orderTrackDTOs);
		response.setTotalCount(orderTrackDTOs.size());
		log.info("queryOrderTrack response:"+JSONUtil.toJSON(orderTrackDTOs));
		
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ORDER_TRACK.getActionName();
	}
	
	private List<OrderTrackDTO> genOrderTrackDTOs(List<OrderDeliveryInfoDTO> deliveryInfoDTOs){
		List<OrderTrackDTO> orderTrackDTOs = Collections.EMPTY_LIST;
		if(deliveryInfoDTOs!=null&&deliveryInfoDTOs.isEmpty()==false){
			orderTrackDTOs = new ArrayList<OrderTrackDTO>();
			for(OrderDeliveryInfoDTO deliveryInfoDTO:deliveryInfoDTOs){
				if(deliveryInfoDTO.getDeliveryDetailDTOs()!=null&&
						deliveryInfoDTO.getDeliveryDetailDTOs().isEmpty()==false){
					
					for(DeliveryDetailDTO deliveryDetailDTO:deliveryInfoDTO.getDeliveryDetailDTOs()){
						OrderTrackDTO orderTrack = new OrderTrackDTO();
						orderTrack.setOperateTime(deliveryDetailDTO.getOpTime());
						orderTrack.setTrackInfo(deliveryDetailDTO.getContent());
					
						orderTrackDTOs.add(orderTrack);
					}
					
					
				}
			}
		}
		
		
		return orderTrackDTOs;
	}

}
