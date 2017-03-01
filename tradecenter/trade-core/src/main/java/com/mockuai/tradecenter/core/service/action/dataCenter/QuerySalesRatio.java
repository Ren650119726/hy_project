package com.mockuai.tradecenter.core.service.action.dataCenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.SalesTotalDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DataManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

/**
 * 查询销售比率
 * @author hzmk
 *
 */
public class QuerySalesRatio implements Action {
	 private static final Logger log = LoggerFactory.getLogger(QuerySalesRatio.class);
	@Autowired
	private DataManager dataManager;
	
	@Autowired
	private OrderItemManager orderItemManager;
	
	@Override
	public TradeResponse execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		DataQTO query = (DataQTO) request.getParam("dataQTO");
		if(query==null){
			log.error("query is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"dataQTO is null");
		}
		String bizCode = (String) context.get("bizCode");
		query.setBizCode(bizCode);
		
		SalesTotalDTO salesTotalDTO = null;
		if(null!=query.getItemIds()){
			OrderItemQTO orderItemQTO = new OrderItemQTO();
			orderItemQTO.setUserId(query.getUserId());
//			orderItemQTO.setItemId(query.getItemId());
			orderItemQTO.setItemIds(query.getItemIds());
			Integer salesVolumes = orderItemManager.getItemSalesVolumes(orderItemQTO);
			salesTotalDTO = new SalesTotalDTO();
			salesTotalDTO.setSalesVolumes(salesVolumes);
			return ResponseUtils.getSuccessResponse(salesTotalDTO);
		}
		if(query.getGroupType()!=null&&query.getGroupType().equals("category")){
			salesTotalDTO =  dataManager.querySalesRatioByCategory(query);
		}
		if(query.getGroupType()!=null&&query.getGroupType().equals("brand")){
			salesTotalDTO =  dataManager.querySalesRatioByBrand(query);
		}
		return ResponseUtils.getSuccessResponse(salesTotalDTO);
		
		
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_SALES_RATIO.getActionName();
	}

}
