package com.mockuai.tradecenter.client;

import java.util.List;

import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.ItemSalesVolumeDTO;
import com.mockuai.tradecenter.common.domain.ItemSkuDTO;
import com.mockuai.tradecenter.common.domain.SalesRatioDTO;
import com.mockuai.tradecenter.common.domain.SalesTotalDTO;
import com.mockuai.tradecenter.common.domain.dataCenter.CategoryDateQTO;
import com.mockuai.tradecenter.common.domain.dataCenter.SalesVolumeDTO;

/**
 * 交易数据报表接口
 * @author hzmk
 *
 */
public interface TradeDataReportClient {
	
	/**
	 * 根据分类或品牌统计销售比率
	 * @param orderQTO
	 * @param appKey
	 * @return
	 */
	Response<SalesTotalDTO> querySalesRatio(DataQTO orderQTO,String appKey);
	

	Response<List<ItemSkuDTO>> queryCategoryTopSaleItem(List<CategoryDateQTO> categoryQTOs,String appKey);
	
	Response<List<ItemSalesVolumeDTO>>  queryItemSalesVolume(DataQTO orderQTO,String appKey);
	
}
