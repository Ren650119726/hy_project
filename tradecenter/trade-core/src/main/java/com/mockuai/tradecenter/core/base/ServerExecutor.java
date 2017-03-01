package com.mockuai.tradecenter.core.base;

import com.mockuai.tradecenter.core.exception.TradeException;
/**
 * 后面需要把支付回调重构下
 * @author hzmk
 *
 */
public interface ServerExecutor {
	
	/**
	 * 支付回调
	 * @param obj
	 * @throws TradeException
	 */
	public void paymentCallBack(Integer paymentId,Object obj)throws TradeException;
	
	/**
	 * 退款回调
	 * @param paymentId
	 * @param obj
	 * @throws TradeException
	 */
	public void refundCallBack(Integer paymentId,Object obj)throws TradeException;

}
