package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
import com.mockuai.tradecenter.common.domain.SalesTotalDTO;
import com.mockuai.tradecenter.core.domain.DailyDataDO;
import com.mockuai.tradecenter.core.domain.DailyDataDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;
import com.mockuai.tradecenter.core.domain.TOPItemDO;
import com.mockuai.tradecenter.core.domain.TOPItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
public interface DataManager {
	
	/**
	 * 做下单埋点
	 * @param deviceType
	 * @param order
	 * @param appkey
	 */
	public void doAddOrderBuriedPoint(List<OrderTogetherDTO> orders,String appkey);
	
	public void doPaySuccessBuriedPoint(List<OrderTogetherDTO> orders,String appkey);
	
	/**
	 * 支付成功后埋点
	 * @param order
	 */
	public void doPaySuccessBuriedPoint(OrderDO order);
	
	/**
	 * 取消订单数据埋点
	 * @param order
	 */
	public void doCancelOrderBuriedPoint(OrderDO order,String appkey);
	
	/**
	 * 已经发货数据埋点
	 * @param order
	 * @param appkey
	 */
	public void doHasDeliveryedBuriedPoint(OrderDO order,String appkey);
	/**
	 * 代评价数据埋点
	 * @param order
	 * @param appkey
	 */
	public void addWaitCommentStatusBuriedPoint(OrderDO order,String appkey);
	
	/**
	 * 订单评价数据埋点
	 * @param list
	 * @param appkey
	 */
	public void doOrderCommentBuriedPoint(List<ItemCommentDTO> list,String appkey);

	/**
	 * 查询TOP10的商品
	 * @param orderQTO
	 * @return
	 */
	public List<TOPItemDO> getTOP10Item(DataQTO dataQTO);
	
	/**
	 * 查询成交额 
	 * @param orderQTO
	 * @return
	 */
	public Long getTotalAmount(DataQTO dataQTO);
	
	/**
	 * 查询订单总数
	 * @param orderQTO
	 * @return
	 */
	public Long getTotalOrderCount(DataQTO dataQTO);
	
	/**
	 * 查询付款订数
	 * @param orderQTO
	 * @return
	 */
	public Long getPaidOrderCount(DataQTO dataQTO);
	
	/**
	 * 查询下单人数
	 * @param orderQTO
	 * @return
	 */
	public Long getTotalUserCount(DataQTO dataQTO);
	
	/**
	 * 查询付款人数
	 * @param orderQTO
	 * @return
	 */
	public Long getPaidUserCount(DataQTO dataQTO);
	
	
	
	/**
	 * 查询老用户数
	 * @param dataQTO
	 * @return
	 */
	public Long getOldUserCount(DataQTO dataQTO);
	
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
	
	
	public SalesTotalDTO querySalesRatioByCategory(DataQTO query)throws TradeException;
	
	public SalesTotalDTO querySalesRatioByBrand(DataQTO query)throws TradeException;
	
	public SalesTotalDTO getSalesVolumes(Long itemId)throws TradeException;
	
	public Long getItemCount(DataQTO query)throws TradeException;
	
	public Long getPaidItemCount(DataQTO query)throws TradeException;
	
	public Long getPaidOrderCountBySkuId(Long skuId)throws TradeException;
	
	public Long getSumOrderTotalPrice(DataQTO dataQTO)throws TradeException;
	
}
