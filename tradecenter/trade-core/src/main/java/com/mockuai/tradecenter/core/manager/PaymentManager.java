//package com.mockuai.tradecenter.core.manager;
//
//import com.mockuai.tradecenter.core.domain.PaymentDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//
//public interface PaymentManager {
//
//	/**
//	 * 新增支付方式
//	 * @param payment
//	 * @return
//	 * @throws TradeException
//	 */
//	public Long addPayment(PaymentDO payment)throws TradeException;
//
//	/**
//	 * 删除支付方式
//	 * @param paymentId
//	 * @return
//	 * @throws TradeException
//	 */
//	public int deletePayment(Integer paymentId)throws TradeException;
//
//	/**
//	 * 修改支付方式
//	 * @param id
//	 * @return
//	 * @throws TradeException
//	 */
//	public int updatePayment(PaymentDO payment)throws TradeException;
//
//	/**
//	 * 根据id获取支付方式
//	 * @param id
//	 * @return
//	 * @throws TradeException
//	 */
//	public PaymentDO getPayment(Integer paymentId)throws TradeException;
//
//	/**
//	 * 根据支付分类来获取支付方式
//	 * @param className
//	 * @return
//	 * @throws TradeException
//	 */
//	public PaymentDO getPaymentByClass(String className)throws TradeException;
//
//	/**
//	 * 获取支付方式的签名key
//	 * @param payment
//	 * @return
//	 * @throws TradeException
//	 */
//	public String getPaymentSignKey(PaymentDO payment)throws TradeException;
//}
