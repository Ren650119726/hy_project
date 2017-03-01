//package com.mockuai.tradecenter.core.manager;
//
//import java.util.Date;
//import java.util.List;
//
//import com.mockuai.tradecenter.common.domain.OrderQTO;
//import com.mockuai.tradecenter.core.domain.OrderDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//
//public interface SellerOrderManager {
//
//	/**
//	 * 添加订单接口示例
//	 * @param orderDO
//	 * @return
//	 * @throws TradeException
//	 */
//	public long addOrder(OrderDO orderDO) throws TradeException;
//
//	/**
//	 * 取消订单
//	 * @param orderDO
//	 * @return
//	 * @throws TradeException
//	 */
//	public int cancelOrder(Long orderId,Long supplierId)throws TradeException;
//
//	/**
//	 * 删除订单
//	 * @param orderId
//	 * @param userId
//	 * @return
//	 * @throws TradeException
//	 */
//	public int deleteOrder(Long orderId,Long supplierId)throws TradeException;
//
//	/**
//	 * 根据订单id和用户id查询订单
//	 * @param orderId
//	 * @param userId
//	 * @return
//	 * @throws TradeExcetion
//	 */
//	public OrderDO getOrder(Long orderId ,Long supplierId)throws TradeException;
//
//	/**
//	 * 关闭订单 -- 结单
//	 * @param orderId
//	 * @param supplierId
//	 * @return
//	 * @throws TradeException
//	 */
//	public int closeOrder(Long orderId,Long supplierId,int orderStatus)throws TradeException;
//
//	/**
//	 * 根据查询条件获取总记录数
//	 * @param orderQTO
//	 * @return
//	 * @throws TradeException
//	 */
//	public int getTotalCount(OrderQTO orderQTO)throws TradeException;
//
//	/**
//	 * 复合条件查询卖家订单
//	 * @param orderQTO
//	 * @return
//	 * @throws TradeException
//	 */
//	public List<OrderDO> querySellerOrder(OrderQTO orderQTO)throws TradeException;
//
//	/**
//	 * 修改订单备注
//	 * @param orderId
//	 * @param userId
//	 * @param newMemo
//	 * @param memoType
//	 * @return
//	 * @throws TradeException
//	 */
//	public int updateOrderMemo(Long orderId,Long supplierId,String newMemo,Integer memoType)throws TradeException;
//
//
//	/**
//	 * 发货
//	 * @param date
//	 * @param orderId
//	 * @param userId
//	 * @param deliveryStatus
//	 * @return
//	 * @throws TradeException
//	 */
//	public int deliveryGoods(long orderId, long userId) throws TradeException;
//
//	/**
//	 * 修改订单的支付状态
//	 * @param orderId
//	 * @param supplierId
//	 * @return
//	 * @throws TradeException
//	 */
//	public int orderPaySuccess(long orderId, long supplierId)throws TradeException;
//}
