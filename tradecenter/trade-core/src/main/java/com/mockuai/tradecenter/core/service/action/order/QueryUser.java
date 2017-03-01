package com.mockuai.tradecenter.core.service.action.order;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.ModelUtil;

@Service
public class QueryUser implements Action{
	private static final Logger log = LoggerFactory.getLogger(QueryUser.class);


	@Resource
	private OrderManager orderManager;
	  
	public TradeResponse<List<OrderDTO>> execute(RequestContext context) throws TradeException {
		
		Request request = context.getRequest();
		TradeResponse<List<OrderDTO>> response = null;
		if(request.getParam("orderQTO") == null){
			log.error("orderQTO is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderQTO is null");
		}

		final String bizCode = (String) context.get("bizCode");
		OrderQTO orderQTO = (OrderQTO)request.getParam("orderQTO");
		
		List<OrderDO> result ;

		try{
			orderQTO.setBizCode(bizCode);
			orderQTO.setDeleteMark(0);
			log.info(" params orderQTO : " + JSONObject.toJSONString(orderQTO));
			/*获取分页的订单数据集合*/
			
			result = this.orderManager.queryUser(orderQTO);
		
			List<OrderDTO> module = new ArrayList<OrderDTO>();			
			
			for(OrderDO orderDO : result){
				OrderDTO orderDTO = ModelUtil.convert2OrderDTO(orderDO);												
				
				module.add(orderDTO);
			}	
	
			response = ResponseUtils.getSuccessResponse(module);
			
		
		}catch(TradeException e){
			log.error("db error: " ,e);
			response = ResponseUtils.getFailResponse(e.getResponseCode());
			return response;
		}
		
		return response;
		
	}
	 
	@Override
	public String getName() {
		return ActionEnum.QUERY_USER.getActionName();
	}
}
