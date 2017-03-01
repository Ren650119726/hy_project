//package com.mockuai.tradecenter.core.manager.impl;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.mockuai.tradecenter.common.constant.ResponseCode;
//import com.mockuai.tradecenter.core.dao.OrderLogDao;
//import com.mockuai.tradecenter.core.domain.OrderLogDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.OrderLogManager;
//
//public class OrderLogManagerImpl implements OrderLogManager{
//	private static final Logger log = LoggerFactory.getLogger(OrderLogManagerImpl.class);
//
//	@Resource
//	private OrderLogDao orderLogDao;
//
//	@Override
//	public long addOrderLog(OrderLogDO orderLog) throws TradeException {
//		log.info("enter addOrderLog ");
//		long result = 0L;
//		try{
//			result = this.orderLogDao.addOrderLog(orderLog);
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e.getMessage());
//		}
//		return result;
//	}
//
//	@Override
//	public long addOrderLog(String logInfo, Date timeLog, long orderId,long userId,
//			String operator)throws TradeException {
//		log.info("enter addOrderLog : " + logInfo + " " + timeLog + " "+orderId + " " + userId + " " + operator );
//
//		OrderLogDO orderLog = new OrderLogDO();
//		orderLog.setLogInfo(logInfo);
//		orderLog.setOrderId(orderId);
//		orderLog.setTimeLog(timeLog);
//		orderLog.setOperator(operator);
//
//		long result = 0L;
//		try {
//			result = this.orderLogDao.addOrderLog(orderLog);
//		} catch (Exception e) {
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e.getMessage());
//		}
//		log.info("exit addOrderLog result : " + result);
//		return result;
//	}
//
//	@Override
//	public List<OrderLogDO> getUserOrderLog(Long orderId ,Long userId)throws TradeException{
//		log.info("enter getUserOrderLog: " + orderId +userId);
//		List<OrderLogDO> result ;
//		OrderLogDO orderLog = new OrderLogDO();
//		orderLog.setOrderId(orderId);
//		orderLog.setUserId(userId);
//		try{
//			result =  this.orderLogDao.getUserOrderLog(orderLog);
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		log.info("exit getUserOrderLog: " + result.size());
//		return result;
//	}
//
//
//}
