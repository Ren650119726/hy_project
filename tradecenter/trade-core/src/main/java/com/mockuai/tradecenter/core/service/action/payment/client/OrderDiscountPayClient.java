package com.mockuai.tradecenter.core.service.action.payment.client;

import javax.annotation.Resource;

import com.mockuai.tradecenter.core.manager.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.util.TradeCoreConfig;


@Service
public class OrderDiscountPayClient  extends ClientExecutorAbstract {

	private static final Logger log = LoggerFactory.getLogger(OrderDiscountPayClient.class);
	@Resource
	TradeCoreConfig tradeCoreConfig;

	@Resource
	MarketingManager marketingManager;

	@Resource
	private VirtualWealthManager virtualWealthManager;

	@Resource
	OrderManager orderManager;

	@Autowired
	private MsgQueueManager msgQueueManager;
	
	@Resource
	TransactionTemplate transactionTemplate;
	
	@Resource
	OrderDiscountInfoManager orderDiscountInfoManager;

	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {
		final String appKey = (String) context.get("appKey");
		PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();
		final OrderDO orderDO = (OrderDO) context.get("orderDO");
		if (null == orderDO) {
			throw new TradeException("orderDO is null");
		}
		paymentUrlDTO.setRequestMethod(2);
		paymentUrlDTO.setPayType(9);
		paymentUrlDTO.setPayAmount(0L);
		if (orderDO.getOrderStatus() >= 30) {
			return paymentUrlDTO;
		}
		
		Boolean discountPayResult = transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try{
					// 预使用虚拟财富
					boolean preUseResult = virtualWealthManager.preUseUserWealth(orderDO.getUserId(), 1, orderDO.getTotalAmount(),
							orderDO.getId(), appKey);
					if (preUseResult == false) {
						throw new TradeException(ResponseCode.BIZ_E_PRE_USE_COUPON_ERROR);
					}
					boolean useUserWealthResult = virtualWealthManager.useUserWealth(orderDO.getUserId(), orderDO.getId(), appKey);
					if (useUserWealthResult == false) {
						virtualWealthManager.releaseUsedWealth(orderDO.getUserId(), orderDO.getId(), appKey);
						throw new TradeException(ResponseCode.BIZ_E_USE_WEALTH_ERROR);
					}
					
					
					orderDO.setDiscountAmount(orderDO.getTotalAmount());
					orderDO.setTotalAmount(0L);
					orderDO.setDiscountMark(2);
					orderDO.setOrderStatus(30);
					orderManager.updateOrderDiscountInfo(orderDO);
					
					
					OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
					orderDiscountInfoDO.setDiscountAmount(orderDO.getDiscountAmount());
					orderDiscountInfoDO.setDiscountType(2);
					orderDiscountInfoDO.setDiscountCode("1");
					orderDiscountInfoDO.setDiscountDesc("虚拟账户");
					orderDiscountInfoDO.setBizCode(orderDO.getBizCode());
					orderDiscountInfoDO.setOrderId(orderDO.getId());
					orderDiscountInfoDO.setUserId(orderDO.getUserId());
					orderDiscountInfoManager.addOrderDiscountInfo(orderDiscountInfoDO);
					
					return true;
				} catch (Exception e) {
					try {
						virtualWealthManager.releaseUsedWealth(orderDO.getUserId(), orderDO.getId(), appKey);
					} catch (TradeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					status.setRollbackOnly();
					log.error(" order discount pay error", e);
					return false;
				}
			}
			
		});
		
		if(discountPayResult==false)
			throw new TradeException("discount balance pay error");

		try{
			//发送支付成功内部mq消息
			msgQueueManager.sendPaySuccessMsg(orderDO);
		} catch (Exception e) {
			log.error("pay success notify error");
		}
		return paymentUrlDTO;
		
		

	}

}
