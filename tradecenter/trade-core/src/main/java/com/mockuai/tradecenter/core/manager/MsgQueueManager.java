package com.mockuai.tradecenter.core.manager;

import com.mockuai.distributioncenter.common.domain.dto.SellerOrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundItemLogDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.RefundItemLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;

import java.util.List;

public interface MsgQueueManager {
	/**
	 * 发送申请退款消息
	 * @param refundOrderItemDTO
	 * @throws TradeException
	 */
	public void sendApplyRefundMsg(RefundOrderItemDTO refundOrderItemDTO) throws TradeException;

	/**
	 * 发送退款成功消息
	 * @param orderItemDO
	 * @throws TradeException
	 */
	public void sendRefundSuccessMsg(OrderItemDO orderItemDO) throws TradeException;

	/**
	 * 发送支付成功消息；
	 * 如果指定的订单是主订单，则针对其下的所有子订单逐一发送支付成功消息
	 * @param orderDO
	 * @throws TradeException
	 */
	public void sendPaySuccessMsg(OrderDO orderDO) throws TradeException;

	/**
	 * 针对指定的子订单列表逐一发送支付成功消息
	 * @param subOrderList
	 * @throws TradeException
	 */
	public void sendPaySuccessMsg(List<OrderDO> subOrderList) throws TradeException;

	/**
	 * 发送订单相关消息
	 * @param tag
	 * @param orderDTO
	 * @throws TradeException
	 */
	public void sendOrderMessage(String tag,OrderDTO orderDTO) throws TradeException;
	
}
