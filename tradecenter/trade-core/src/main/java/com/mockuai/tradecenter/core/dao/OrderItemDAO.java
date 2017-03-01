package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;

public interface OrderItemDAO {
	
	/**
	 * 写入订单明细
	 * @param orderItem
	 * @return
	 */
	public long addOrderItem(OrderItemDO orderItem);
	
	/**
	 * 根据orderId和userId查询订单明细
	 * @param orderQTO
	 * @return
	 */
	public List<OrderItemDO> queryOrderItem(OrderItemQTO orderItemQTO);
	
	public List<OrderItemDO> queryOrderItemByItemId(OrderItemQTO orderItemQTO);

    @SuppressWarnings("unchecked")
    List<OrderItemDO> queryOrderItemBg(OrderItemQTO orderItemQTO);

    public OrderItemDO getOrderItem(OrderItemQTO orderItemQTO);
	
	public List<OrderItemDO> queryOrderItemNoRefund(OrderItemQTO orderItemQTO); 
	
	public Integer getProcessingRefundOrderItemCount(OrderItemQTO query);
	
	/**
	 * 退货中
	 * @param query
	 * @return
	 */
	public List<?> queryProcessingReturnOrderItemDOList(OrderItemQTO query);
	
	/**
	 * 退款中
	 * @param query
	 * @return
	 */
	public List<?> queryWxPayProcessingRefundItemList(OrderItemQTO query);
	
	public Long updateOrderItemDeliveryMark(OrderItemDO query);
	
	public Integer getUnDeliveryOrderItemCount(OrderItemQTO query);
	
	public List<?> queryTimeoutUntreatedOrderItemList(OrderItemQTO query);
	
	public Long getUnRefundOrderItemTotalPayamount(OrderItemQTO query);
	
	/**
	 * 得到商品的销售数量
	 * @param itemId
	 * @return
	 */
	public Integer getItemSalesVolumes(OrderItemQTO query);
	
	public int updateOrderItemDOById(OrderItemDO query);
	
	public int deleteOrderItem(OrderItemQTO query);
	
	public int falseDeleteOrderItem(OrderItemQTO query);
	
	public OrderItemDO getOrderItemById(Long id);
	
}
