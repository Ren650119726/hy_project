package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.core.domain.OrderPaymentDO;

public interface OrderPaymentDAO {

	/**
	 * 写入支付单
	 * @return
	 */
	public long addOrderPayment(OrderPaymentDO orderPaymentDO);

	
	/**
	 * 根据订单ID查询支付单
	 * @return
	 */
	public OrderPaymentDO getOrderPayment(long orderId, Long userId);

	
	/**
	 * 根据订单ID查询支付单列表
	 * @return
	 */
	public OrderPaymentDO getOrderPaymentList(long orderId, Long userId);

	/**
	 * 更新支付方式
	 * @param orderId
	 * @param userId
	 * @param paymentId
	 * @return
	 */
	public int updatePaymentType(long orderId, long userId, int paymentId);
	
	/**
	 * 支付成功更新状态
	 * @param orderPaymentDO
	 * @return
	 */
	public int paySuccess(OrderPaymentDO orderPaymentDO);
}
