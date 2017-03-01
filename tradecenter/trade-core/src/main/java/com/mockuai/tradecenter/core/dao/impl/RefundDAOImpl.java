package com.mockuai.tradecenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumRefundStatus;
import com.mockuai.tradecenter.core.dao.RefundDAO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.RefundItemLogDO;

public class RefundDAOImpl extends SqlMapClientDaoSupport implements RefundDAO{
	
	@Override
	public Long updateOrderItemRefundInfo(RefundOrderItemDTO dto) {
		return (long) this.getSqlMapClientTemplate().update("refund.updateOrderItemRefundInfo", dto);
	}

	@Override
	public Long updateOrderRefundStatus(Long orderId, Integer status) {
		if(null==orderId || null== status)
			return 0L;
		OrderDO order = new OrderDO();
		order.setId(orderId);
		order.setOrderStatus(status);
		return (long) this.getSqlMapClientTemplate().update("refund.updateOrderRefundStatus", order);
	}
	
	@Override
	public Long updateOrderRefundMark(Long orderId, Integer refundMark) {
		if(null==orderId || null== refundMark)
			return 0L;
		OrderDO order = new OrderDO();
		order.setId(orderId);
		order.setRefundMark(refundMark);
		return (long) this.getSqlMapClientTemplate().update("refund.updateOrderRefundStatus", order);
	}

	@Override
	public OrderItemDO getOrderItem(Long userId,Long orderId, Long skuId) {
		OrderItemQTO query = new OrderItemQTO();
		query.setOrderId(orderId);
		query.setItemSkuId(skuId);
		query.setUserId(userId);
		return (OrderItemDO) this.getSqlMapClientTemplate().queryForObject("order_item.getOrderItem",query);
	}

	@Override
	public OrderItemDO getOrderItemByOutTradeNo(String outTradeNo, Long amount) {
		OrderItemQTO query = new OrderItemQTO();
		query.setOutTradeNo(outTradeNo);
		query.setPaymentAmount(amount);
		return (OrderItemDO) this.getSqlMapClientTemplate().queryForObject("order_item.queryOrderItemByOutTradeNo",query);
	}

	@Override
	public Long addRefundItemLog(RefundItemLogDO refundOrderItemDO) {
		return (Long) this.getSqlMapClientTemplate().insert("refund_item_log.add", refundOrderItemDO);
	}

	@Override
	public Long updateRefundItemLogDO(RefundItemLogDO refundOrderItemDO) {
		return (long) this.getSqlMapClientTemplate().update("refund_item_log.update", refundOrderItemDO);
	}

	@Override
	public RefundItemLogDO getApplyStatusRefundOrderItem(Long orderId,Long skuId) {
		RefundItemLogDO query = new RefundItemLogDO();
		query.setItemSkuId(skuId);
		query.setOrderId(orderId);
		query.setRefundStatus(Integer.parseInt(EnumRefundStatus.APPLY.getCode()));
		return (RefundItemLogDO)this.getSqlMapClientTemplate().queryForObject("refund_item_log.query",query);
	}

	@Override
	public RefundItemLogDO getProcessingStatusRefundOrderItem(Long orderId, Long skuId) {
		RefundItemLogDO query = new RefundItemLogDO();
		query.setItemSkuId(skuId);
		query.setOrderId(orderId);
		query.setRefundStatus(Integer.parseInt(EnumRefundStatus.REFUNDING.getCode()));
		return (RefundItemLogDO)this.getSqlMapClientTemplate().queryForObject("refund_item_log.query",query);
	}

	@Override
	public List<RefundItemLogDO> queryRefundItemLogDOList(RefundItemLogDO refundOrderItemDO) {
		return this.getSqlMapClientTemplate().queryForList("refund_item_log.query",refundOrderItemDO);
	}

	@Override
	public List<OrderDO> queryRefundStatusOrderList(OrderQTO query) {
		return this.getSqlMapClientTemplate().queryForList("user_order.queryRefundStatusOrders",query);
//		if(null==query.getRefundStatus()){
//			return this.getSqlMapClientTemplate().queryForList("user_order.queryRefundStatusOrders",query);
//		}else{
//			return this.getSqlMapClientTemplate().queryForList("user_order.queryRefundStatusOrderItems",query);
//		}
		
	}

	@Override
	public Long getRefundStatusOrderCount(OrderQTO query) {
		return (Long) this.getSqlMapClientTemplate().queryForObject("user_order.getRefundStatusOrderCount",query);
//		if(null==query.getRefundStatus()){
//			return (Long) this.getSqlMapClientTemplate().queryForObject("user_order.getRefundStatusOrderCount",query);
//		}else{
//			return (Long) this.getSqlMapClientTemplate().queryForObject("user_order.getRefundStatusOrderItemCount",query);
//		}
		
	}

	@Override
	public OrderItemDO getOrderItemByRefundBatchNo(String refundBatchNo) {
		// TODO Auto-generated method stub
		OrderItemQTO query = new OrderItemQTO();
		query.setRefundBatchNo(refundBatchNo);
		return (OrderItemDO) this.getSqlMapClientTemplate().queryForObject("order_item.getOrderItem", query);
	}

	@Override
	public Long updateSuitSubItemRefundInfo(RefundOrderItemDTO dto) {
		return (long) this.getSqlMapClientTemplate().update("refund.updateSuitSubItemRefundInfo", dto);
	}

	@Override
	public Long getRefundingOrderNum(Long userId, String bizCode) {
		com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO query = 
				new com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO();
		query.setBizCode(bizCode);
		query.setUserId(userId);
		return (Long) this.getSqlMapClientTemplate().queryForObject("refund.getRefundingOrderTotalAmount", query);
	}


}
