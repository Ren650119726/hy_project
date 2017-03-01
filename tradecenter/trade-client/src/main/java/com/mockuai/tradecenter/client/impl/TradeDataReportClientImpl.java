package com.mockuai.tradecenter.client.impl;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.tradecenter.client.TradeDataReportClient;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.ItemSalesVolumeDTO;
import com.mockuai.tradecenter.common.domain.ItemSkuDTO;
import com.mockuai.tradecenter.common.domain.SalesTotalDTO;
import com.mockuai.tradecenter.common.domain.dataCenter.CategoryDateQTO;
import com.mockuai.tradecenter.common.domain.dataCenter.SalesVolumeDTO;

public class TradeDataReportClientImpl implements TradeDataReportClient {
	@Resource
	private TradeService tradeService;
	
	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	
	@Override
	public Response<SalesTotalDTO> querySalesRatio(DataQTO query, String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_SALES_RATIO.getActionName());
		request.setParam("dataQTO",query);
		request.setParam("appKey", appKey);
		Response response = tradeService.execute(request);
		
		return response;
	}

	@Override
	public Response<List<ItemSkuDTO>> queryCategoryTopSaleItem(List<CategoryDateQTO> categoryQTOs,String appKey) {
		// TODO Auto-generated method stub
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_CATEGORY_TOP_SALE_ITEMS.getActionName());
		request.setParam("categoryDateQTOList",categoryQTOs);
		request.setParam("appKey", appKey);
		Response response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<List<ItemSalesVolumeDTO>> queryItemSalesVolume(DataQTO query, String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_TOP10_ITEM.getActionName());
		if(null!=query&&query.getItemIds()!=null){
			query.setTop_number(query.getItemIds().size());
		}
		request.setParam("dataQTO",query);
		request.setParam("appKey", appKey);
		Response<List<ItemSalesVolumeDTO>> response = tradeService.execute(request);
		return response;
	}

}
