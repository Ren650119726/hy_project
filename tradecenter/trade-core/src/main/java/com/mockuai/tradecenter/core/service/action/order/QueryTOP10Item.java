package com.mockuai.tradecenter.core.service.action.order;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.dataCenter.SalesVolumeDTO;
import com.mockuai.tradecenter.core.domain.TOPItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DataManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

public class QueryTOP10Item  implements Action{
	
	private static final Logger log = LoggerFactory.getLogger(ConfirmReceipt.class);
	
	@Resource
	private DataManager dataManager;
	
    @Resource
    private ItemClient itemClient;

    
	@Override
	public TradeResponse<List<SalesVolumeDTO>> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		String appKey = (String)context.get("appKey");
		DataQTO dataQTO = (DataQTO) request.getParam("dataQTO");
		
		if(dataQTO==null){
			log.error("orderQTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderQTO is null");
		}
		
		long seller_id = dataQTO.getSeller_id();
		
		List<TOPItemDO> simple_items = dataManager.getTOP10Item(dataQTO);
		List<SalesVolumeDTO> items = new ArrayList<SalesVolumeDTO>();
		
		for(TOPItemDO item:simple_items){
			com.mockuai.itemcenter.common.domain.dto.ItemDTO temp =  itemClient.getItem(item.getId(), seller_id, false, appKey).getModule();
			SalesVolumeDTO newTemp = new SalesVolumeDTO();
			
			if(null==temp){
				log.error("item is null");
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"item is null");
			}
			newTemp.setItem_sales_volume(item.getSales_volume());
			newTemp.setItem_id(item.getId());
			newTemp.setItem_name(temp.getItemName());
			items.add(newTemp);
		}
		
		return ResponseUtils.getSuccessResponse(items);
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_TOP10_ITEM.getActionName();
	}

}
