//package com.mockuai.tradecenter.core.manager;
//
//import java.util.Date;
//import java.util.List;
//
//import com.mockuai.tradecenter.core.domain.OrderLogDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//
//public interface OrderLogManager {
//
//	/**
//	 * 添加订单操作日志
//	 * @param orderLog
//	 * @return
//	 * @throws TradeException
//	 */
//	public long addOrderLog(OrderLogDO orderLog)throws TradeException;
//
//	/**
//	 * 添加订单操作日志
//	 * @param logInfo
//	 * @param timeLog
//	 * @param orderId
//	 * @param userId
//	 * @param operator
//	 * @return
//	 * @throws TradeException
//	 */
//	public long addOrderLog(String logInfo,Date timeLog,long orderId,long userId,String operator)throws TradeException;
//
//	/**
//	 * 获取用户的订单操作明细 --供用户查看,需要过滤部分不对用户显示的信息
//	 * @return
//	 */
//	public List<OrderLogDO> getUserOrderLog(Long orderId ,Long userId)throws TradeException;
//
//}
