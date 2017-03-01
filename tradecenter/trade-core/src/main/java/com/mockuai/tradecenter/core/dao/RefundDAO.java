package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.RefundItemLogDO;

public interface RefundDAO {
	
	public Long updateOrderItemRefundInfo(RefundOrderItemDTO dto);
	
	public Long updateOrderRefundStatus(Long orderId,Integer status);
	
	public Long updateOrderRefundMark(Long orderId,Integer refundMark);
	
	public OrderItemDO getOrderItem(Long userId,Long orderId,Long skuId);
	
	public OrderItemDO getOrderItemByOutTradeNo(String outTradeNo,Long amount);
	
	public Long addRefundItemLog(RefundItemLogDO refundOrderItemDO);
	
	public Long updateRefundItemLogDO(RefundItemLogDO refundOrderItemDO);
	
	public RefundItemLogDO getApplyStatusRefundOrderItem(Long orderId,Long skuId);
	
	public RefundItemLogDO getProcessingStatusRefundOrderItem(Long orderId,Long skuId);
	
	public List<RefundItemLogDO> queryRefundItemLogDOList(RefundItemLogDO refundOrderItemDO);
	
	public List<OrderDO> queryRefundStatusOrderList(OrderQTO query);
	
	public Long getRefundStatusOrderCount(OrderQTO query);
	
	public OrderItemDO getOrderItemByRefundBatchNo(String refundBatchNo);
	
	public Long updateSuitSubItemRefundInfo(RefundOrderItemDTO dto);
	
	public Long getRefundingOrderNum(Long userId,String bizCode);
	
//	public 
	

}
