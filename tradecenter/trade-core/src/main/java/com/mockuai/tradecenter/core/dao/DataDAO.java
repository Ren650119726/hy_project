package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.dataCenter.CategoryDateQTO;
import com.mockuai.tradecenter.core.domain.DailyDataDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.SalesRatioDO;
import com.mockuai.tradecenter.core.domain.TOPItemDO;

public interface DataDAO {
	
	/**
	 * 查询TOP10的商品
	 * @param orderQTO
	 * @return
	 */
	public List<TOPItemDO> getTOP10Item(DataQTO orderQTO);
	
	/**
	 * 查询成交额 
	 * @param orderQTO
	 * @return
	 */
	public Long getTotalAmount(DataQTO dataQTO);
	
	/**
	 * 查询成交额 
	 * @param orderQTO
	 * @return
	 */
	public Long getAllTotalAmount(DataQTO dataQTO);
	
	/**
	 * 查询订单总数
	 * @param orderQTO
	 * @return
	 */
	public long getTotalOrderCount(DataQTO dataQTO);
	
	/**
	 * 查询付款订数
	 * @param orderQTO
	 * @return
	 */
	public long getPaidOrderCount(DataQTO dataQTO);
	
	/**
	 * 查询下单人数
	 * @param orderQTO
	 * @return
	 */
	public long getTotalUserCount(DataQTO dataQTO);
	
	/**
	 * 查询付款人数
	 * @param orderQTO
	 * @return
	 */
	public long getPaidUserCount(DataQTO dataQTO);
	
	
	
	
	/**
	 * 查询老用户数
	 * @param dataQTO
	 * @return
	 */
	public long getOldUserCount(DataQTO dataQTO);
	
	/**
	 * 按天查询成交额
	 * @param dataQTO
	 * @return
	 */
	public List<DailyDataDO> getTotalAmountDaily(DataQTO dataQTO);
	
	/**
	 * 按天查询订单总数
	 * @param dataQTO
	 * @return
	 */
	public List<DailyDataDO> getTotalOrderCountDaily(DataQTO dataQTO);
	public List<SalesRatioDO> querySalesRatioByCategory(DataQTO query);
	
	/**
	 * 按天查询已支付订单总数
	 * @param dataQTO
	 * @return
	 */
	public List<DailyDataDO> getPaidOrderCountDaily(DataQTO dataQTO);
	
	/**
	 * 按天查询用户总数
	 * @param dataQTO
	 * @return
	 */
	public List<DailyDataDO> getTotalUserCountDaily(DataQTO dataQTO);
	
	/**
	 * 按天查询已支付用户数
	 * @param dataQTO
	 * @return
	 */
	public List<DailyDataDO> getPaidUserCountDaily(DataQTO dataQTO);
	public List<SalesRatioDO> querySalesRatioByBrand(DataQTO query);
	
	public Long getItemCount(DataQTO query);
	
	public Long getPaidItemCount(DataQTO query);
	
	public Long getPaidCountBySkuId(DataQTO query);
	
	public List<OrderItemDO>  queryCategoryTopSaleItems(CategoryDateQTO query);
	
	public Long getSumOrderTotalPrice(DataQTO dataQTO);
}
