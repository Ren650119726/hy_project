package com.mockuai.tradecenter.core.service.action.dataCenter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.ItemSalesVolumeDTO;
import com.mockuai.tradecenter.common.domain.dataCenter.SalesVolumeDTO;
import com.mockuai.tradecenter.core.domain.TOPItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DataManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.TradeCoreConfig;

public class QueryTOP10Item  implements Action{
	
	private static final Logger log = LoggerFactory.getLogger(QueryTOP10Item.class);
	
	@Resource
	private DataManager dataManager;
	
    @Resource
    private ItemClient itemClient;

    @Autowired
	private TradeCoreConfig tradeCoreConfig;
    
	@Override
	public TradeResponse<List<ItemSalesVolumeDTO>> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		String appKey = (String)context.get("appKey");
		DataQTO dataQTO = (DataQTO) request.getParam("dataQTO");
		
		if(dataQTO==null){
			log.error("dataQTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"dataQTO is null");
		}
		String bizCode = (String) context.get("bizCode");
		dataQTO.setBizCode(bizCode);
		List<ItemSalesVolumeDTO> items = new ArrayList<ItemSalesVolumeDTO>();
		if(dataQTO.getItemIds()==null||dataQTO.getItemIds().isEmpty()){
			return ResponseUtils.getSuccessResponse(items);
		}
		
		List<TOPItemDO> simple_items = dataManager.getTOP10Item(dataQTO);
		
		
		for(TOPItemDO item:simple_items){
//			com.mockuai.itemcenter.common.domain.dto.ItemDTO temp =  itemClient.getItem(item.getId(), seller_id, false, appKey).getModule();
			ItemSalesVolumeDTO newTemp = new ItemSalesVolumeDTO();
//			
//			if(null==temp){
//				log.error("item is null");
////				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"item is null");
//				continue;
//			}
			newTemp.setItemSalesVolume(item.getSales_volume());
			newTemp.setItemId(item.getId());
//			newTemp.setItem_name(temp.getItemName());
			items.add(newTemp);
		}
		
		return ResponseUtils.getSuccessResponse(items);
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_TOP10_ITEM.getActionName();
	}

}
