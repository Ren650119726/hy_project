package com.mockuai.tradecenter.core.manager;


import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface OrderPaymentManager {
	
	/**
	 * 添加支付单号
	 * @return
	 * @throws TradeException
	 */
	public Long addOrderPayment(OrderPaymentDO orderPaymentDO)throws TradeException;
	
	/**
	 * 查询付款单
	 * @param id
	 * @return
	 * @throws TradeException
	 */
	/*public PaymentNoticeDO getOrderPayment(Long id)throws TradeException;*/
	
	/**
	 * 根据订单号查询对应支付单信息
	 * @param orderSn
	 * @return
	 * @throws TradeException
	 */
	public OrderPaymentDO getOrderPayment(long orderId, Long userId)throws TradeException;

	/**
	 * 更新订单支付表的支付方式
	 * @param orderId
	 * @param userId
	 * @param paymentId
	 * @return
	 * @throws TradeException
	 */
	public int updatePaymentType(long orderId, long userId, int paymentId) throws TradeException;


	/**
	 * 标记支付单状态为  支付成功
	 * @param payStatus
	 * @param tradeNo
	 * @param orderPaymentId
	 *@param userId @return
	 * @throws TradeExcetion
	 */
	public int paySuccess(long orderPaymentId,long userId,long
			totalFee,String outerTradeNo)throws TradeException;
	
}
