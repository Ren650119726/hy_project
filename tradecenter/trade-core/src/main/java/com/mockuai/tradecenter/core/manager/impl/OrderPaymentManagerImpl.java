package com.mockuai.tradecenter.core.manager.impl;

import javax.annotation.Resource;

import com.mockuai.tradecenter.common.constant.TradeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.dao.OrderPaymentDAO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderPaymentManager;
import com.mockuai.tradecenter.core.service.RequestDispatcher;

public class OrderPaymentManagerImpl implements OrderPaymentManager {
	private static final Logger log = LoggerFactory.getLogger(RequestDispatcher.class);
	
	@Resource 
	private OrderPaymentDAO orderPaymentDAO;

	@Override
	public Long addOrderPayment(OrderPaymentDO orderPaymentDO)throws TradeException {
		log.info("enter addOrderPayment");
		long id = 0L;
		try{
			 id = this.orderPaymentDAO.addOrderPayment(orderPaymentDO);
		}catch(Exception e){
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
		}
		log.info("exit addOrderPayment id: " + id);
		return id;
	}

	@Override
	public OrderPaymentDO getOrderPayment(long orderId, Long userId)throws TradeException{
//		log.info("enter getOrderPayment: " + orderId + ","+ userId);
		// 根据订单ID和用户Id查询交易单
		try{
//			OrderPaymentDO orderPaymentDO = this.orderPaymentDAO.getOrderPayment(orderId, userId); //TODO
			OrderPaymentDO orderPaymentDO = this.orderPaymentDAO.getOrderPaymentList(orderId, userId);
			
//			log.info("exit getOrderPayment " +orderPaymentDO );
			return orderPaymentDO;
		}catch(Exception e){
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
		}


	}

	public int updatePaymentType(long orderId, long userId, int paymentId) throws TradeException {
		try {
			int opNum = this.orderPaymentDAO.updatePaymentType(orderId, userId, paymentId);
			return opNum;
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
		}
	}

	@Override
	public int paySuccess(long orderPaymentId,long userId,
						  long payAmount,String  outerTradeNo)throws TradeException{
		log.info("enter paySuccess: " + payAmount + "," + outerTradeNo + "," + orderPaymentId + "," + userId);
		int result = 0;
		
		OrderPaymentDO orderPaymentDO = new OrderPaymentDO();
		orderPaymentDO.setPayAmount(payAmount);
		orderPaymentDO.setOutTradeNo(outerTradeNo);
		orderPaymentDO.setId(orderPaymentId);
		orderPaymentDO.setUserId(userId);
		orderPaymentDO.setPayStatus(TradeConstants.PaymentStatus.PAID);

		try {
			result = this.orderPaymentDAO.paySuccess(orderPaymentDO);
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
		}
		log.info("exit paySuccess updated: " + result);
		return result;
	}

}

