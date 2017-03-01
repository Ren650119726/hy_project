package com.mockuai.tradecenter.core.service.action.dataCenter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.ItemSkuDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.SalesTotalDTO;
import com.mockuai.tradecenter.common.domain.dataCenter.CategoryDateQTO;
import com.mockuai.tradecenter.core.dao.DataDAO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

public class QueryCategoryTopItems implements Action {
	 private static final Logger log = LoggerFactory.getLogger(QueryCategoryTopItems.class);
	
	@Autowired
	private DataDAO dataDAO;
	
	@Override
	public TradeResponse execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		if(request.getParam("categoryDateQTOList") == null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"categoryDateQTOList is null");
		}
		List<CategoryDateQTO> categoryDateQTOs =  (List<CategoryDateQTO>) request.getParam("categoryDateQTOList");
		if(categoryDateQTOs.isEmpty()){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"categoryDateQTOList is empty");
		}
		
		List<ItemSkuDTO> returnList = new ArrayList<ItemSkuDTO>();
		List<OrderItemDO> orderItemDOList = new ArrayList<OrderItemDO>();
		for(CategoryDateQTO categoryDateQTO:categoryDateQTOs){
			if(null==categoryDateQTO){
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"categoryDateQTO is empty");
			}
			if(categoryDateQTO.getCategoryId()==null){
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"categoryId is empty");
			}
			if(categoryDateQTO.getTopItemNumber()==null)
				categoryDateQTO.setTopItemNumber(5);
			
			orderItemDOList.addAll(dataDAO.queryCategoryTopSaleItems(categoryDateQTO))  ;
		}
	
		if(!orderItemDOList.isEmpty()){
			for(OrderItemDO orderItemDO:orderItemDOList){
				ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
				itemSkuDTO.setItemId(orderItemDO.getItemId());
				itemSkuDTO.setItemSkuId(orderItemDO.getItemSkuId());
				returnList.add(itemSkuDTO);
			}
		}
		
		
		return ResponseUtils.getSuccessResponse(returnList);
		
		
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_CATEGORY_TOP_SALE_ITEMS.getActionName();
	}

}
