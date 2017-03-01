//package com.mockuai.tradecenter.core.dao;
//import java.util.List;
//
//import com.mockuai.tradecenter.common.domain.OrderQTO;
//import com.mockuai.tradecenter.core.domain.OrderDO;
//
//public interface SellerOrderDao {
//
//	/**
//	 * 添加订单
//	 * @param orderDO
//	 * @return
//	 */
//	public long addOrder(OrderDO orderDO);
//
//	/**
//	 * 取消订单
//	 * @param orderQTO
//	 * @return
//	 */
//	public int cancelOrder(OrderDO orderDO);
//
//	/**
//	 * 删除订单 （逻辑删除）
//	 * 只有取消的订单才可以删除
//	 */
//	public int deleteOrder(OrderDO orderDO);
//
//	/**
//	 * 根据订单ID和用户ID查询订单
//	 * @param orderQTO
//	 * @return
//	 */
//	public OrderDO getOrder(OrderDO orderDO);
//
//	/**
//	 * 获取满足条件的订单的总条数
//	 * @param orderQTO
//	 * @return
//	 */
//	public int getTotalCount(OrderQTO orderQTO);
//
//	/**
//	 * 关闭订单
//	 * @param orderDO
//	 * @return
//	 */
//	public int closeOrder(OrderDO orderDO);
//
//	/**
//	 * 复合条件查询卖家订单
//	 * @param orderDO
//	 * @return
//	 */
//	public List<OrderDO> querySellerOrders(OrderQTO orderQTO);
//
//	/**
//	 * 订单标记为已支付
//	 * @param orderDO
//	 * @return
//	 */
//	public int refund(OrderDO orderDO);
//
//	/**
//	 * 修改收货人信息
//	 * @param orderDO
//	 * @return
//	 */
//	public int updateConsigneeInfo(OrderDO orderDO);
//
//	/**
//	 * 修改定单备注
//	 * @param orderDO
//	 * @return
//	 */
//	public int updateOrderMemo(OrderDO orderDO);
//
//	/**
//	 * 修改为退货申请状态
//	 * @param orderDO
//	 * @return
//	 */
//	public int applyReturn(OrderDO orderDO);
//
//	/**
//	 * 退货申请审核
//	 * @param orderDO
//	 * @return
//	 */
//	public int auditReturnApply(OrderDO orderDO);
//
//	/**
//	 * 更新订单的发货状态
//	 * @param orderDO
//	 * @return
//	 */
//	public int deliveryGoods(OrderDO orderDO);
//
//	/**
//	 * 支付成功
//	 * @param orderDO
//	 * @return
//	 */
//	public int orderPaySuccess(OrderDO orderDO);
//
//	/**
//	 * 待发货订单
//	 * @param orderDO
//	 * @return
//	 */
//	public int pendingDelivery(OrderDO orderDO);
//}
