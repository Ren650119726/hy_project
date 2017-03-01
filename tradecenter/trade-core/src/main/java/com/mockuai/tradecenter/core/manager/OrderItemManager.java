package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface OrderItemManager {
	
	/**
	 * 新增订单明细
	 * @param orderItem
	 * @return
	 * @throws tradeException
	 */
	public long addOrderItem(OrderItemDO orderItem)throws TradeException;

	/**
	 * 根据userId和userId获取订单明细
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws TradeException
	 */
	public List<OrderItemDO> queryOrderItem(OrderItemQTO orderItemQTO)throws TradeException;

    List<OrderItemDO> queryOrderItemBg(OrderItemQTO orderItemQTO)throws TradeException;

    /**
	 * 
	 * @param orderItemQTO
	 * @return
	 * @throws TradeException
	 */
	public OrderItemDO  getOrderItem(OrderItemQTO orderItemQTO)throws TradeException;
	
	/**
	 * 获取支付链接时检查商品信息
	 * @param orderList
	 * @param appkey
	 * @throws TradeException
	 */
	public void checkItemForGetPaymentUrl(List<OrderItemDO> orderList,String appkey)throws TradeException;
	
	/**
	 * 
	 * @param orderId
	 * @param userId
	 * @param skuIds
	 * @return
	 * @throws TradeException
	 */
	public Boolean updateOrderItemDeliveryMark(Long orderId,Long userId,Long deliveryInfoId,Integer deliveryMark,List<Long> orderItemIds)throws TradeException;

	public Boolean updateOrderItemDeliveryMarkByOrderId(Long orderId)throws TradeException;



	public List<OrderItemDO> queryTimeoutUntreatedOrderItemList(OrderItemQTO orderItemQTO)throws TradeException;
	
	public Integer getItemSalesVolumes(OrderItemQTO query)throws TradeException;
	
	public int deleteOrderItem(Long orderId)throws TradeException;
	
	public int falseDeleteOrderItem(Long orderId)throws TradeException;

	/**
	 * 更新指定订单商品所使用的虚拟财富数量
	 * @param orderItemId
	 * @param userId
	 * @param virtualWealthAmount
	 * @return
	 * @throws TradeException
	 */
	public int updateOrderItemVirtualWealthAmount(long orderItemId, long userId, long virtualWealthAmount) throws TradeException;

}
