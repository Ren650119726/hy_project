package com.mockuai.tradecenter.core.dao;

import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.TradeNotifyLogQTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.Map;

public interface OrderDAO {
	
	/**
	 * 根据order_sn查询Order信息，用于批量发货
	 * @param orderDO
	 * @return
	 */
	public OrderDO getOrderByOrder_sn(OrderDO orderDO);
	
	/**
	 * 推送订单
	 * @param orderQTOList
	 * @return
	 */
	public int updatePushOrderStatus(List<OrderQTO> orderQTOList);
	
	/**
	 * 根据订单id更新订单状态
	 * @param paramMap
	 * @return
	 */
	public int updateOrderStatusById(Map paramMap);	
	
	/**
	 * 根据订单id更新商品发货状态
	 * @param paramMap
	 * @return
	 */
	public Boolean updateDeliveryMarkById(Map paramMap);
	
	public int phyDeleteOrder(OrderQTO query) ;
	
	public Boolean UpdateOrderSupplier(OrderQTO orderQTO);
	
	public List<OrderDO> queryDeliveriedOrders(OrderQTO orderQTO);
	
	/**
	 * 查询超时收货订单
	 */
	public List<OrderDO> queryOverTimeOrder(OrderQTO orderQTO);
	 
	/**
	 * 添加订单
	 * @param orderDO
	 * @return
	 */
	public long addOrder(OrderDO orderDO);
	
	/**
	 * 取消订单
	 * @param orderQTO
	 * @return
	 */
	public int cancelOrder(OrderDO orderDO);
	
	/**
	 * 删除订单 （逻辑删除）
	 * 只有取消的订单才可以删除
	 */
	public int deleteOrder(OrderDO orderDO); 
	
	/**
	 * 根据订单ID和用户ID查询订单
	 * @param orderQTO
	 * @return
	 */
	public OrderDO getOrder(OrderDO orderDO);
	
	/**
	 * 更新订单的收货人信息
	 * @param orderDO
	 * @return
	 */
	public int updateConsigneeInfo(OrderDO orderDO);
	
	/**
	 * 更新订单备注
	 * @param orderDO
	 * @return
	 */
	public int updateOrderMemo(OrderDO orderDO);

	/**
	 * 修改订单支付方式
	 * @param orderDO
	 * @return
	 */
	public int updateOrderPayType(OrderDO orderDO);
	
	/**
	 * 顾客签收更改订单状态
	 * @param orderDO
	 * @return
	 */
	public int confirmArrival(OrderDO orderDO);
	
	/**
	 * 整单发货
	 * @param order
	 * @return
	 */
	public int deliveryGoods(OrderDO order);
	
	/**
	 * 查询有效订单
	 * @return
	 */
	public OrderDO getActiveOrder(OrderDO orderDO);
	
	/**
	 * 标志订单的支付状态为已成功
	 * @return
	 */
	public int orderPaySuccess(OrderDO orderDO);
	
	/**
	 * test
	 * @param orderDO
	 * @return
	 */
	public int orderPaySuccessTest(OrderDO orderDO);
	
	/**
	 * 结单 ，表示订单的生命周期结束不能再对订单做其他状态相关的操作
	 * @param order
	 * @return
	 */
	public int closeOrder(OrderDO orderDO);

	/**
	 * 评价订单
	 * @param orderDO
	 * @return
	 */
	public int commentOrder(OrderDO orderDO);
	
	/**
	 * 顾客退货申请 更改售后状态
	 * @param orderDO
	 * @return
	 */
	public int returnApply(OrderDO orderDO);
	
	/**
	 * 审核用户退货申请 更改对应的状态标志
	 * @param orderDO
	 * @return
	 */
	public int auditReturnApply(OrderDO orderDO);
	
	/**
	 * 退款操作标记售后状态为--已退款
	 * @param orderDO
	 * @return
	 */
	public int refund(OrderDO orderDO);
	
	/**
	 * 复合条件查询订单
	 * @param orderQTO
	 * @return
	 */
	public List<OrderDO> queryUserOrders(OrderQTO orderQTO) throws TradeException;
	
	public List<String> getCallBackOrderXX(OrderQTO orderQTO) throws TradeException;
	
	public int deleteCallBackOrderXX(OrderQTO orderQTO) throws TradeException;
	
	/**
	 * 前台订单列表接口（优化）
	 * @param orderQTO
	 * @return
	 */
	public List<OrderDO> queryUserOrdersUpgrade(OrderQTO orderQTO) throws TradeException;
	
	/**
	 * 秒杀预单查询
	 * @param orderQTO
	 * @return
	 * @throws TradeException
	 */
	public List<OrderDO> queryUserOrdersUpgradeSeckill(OrderQTO orderQTO)
			throws TradeException;
	
	/**
	 * 前台维权订单接口
	 * @param orderQTO
	 * @return
	 */
	public List<OrderDO> queryUserOrdersRefund(OrderQTO orderQTO) throws TradeException;
	
	public int queryUserOrdersCount(OrderQTO orderQTO) throws TradeException;
	
	public List<OrderDO> queryStoreOrders(OrderQTO orderQTO);
	
	public int getStoreOrdersCount(OrderQTO orderQTO);

	/**
	 * 复合条件查询订单
	 * @param orderQTO
	 * @return
	 */
	public int getTotalCount(OrderQTO orderQTO);
	
	public int getSellerOrdersTotalCount(OrderQTO orderQTO);
	
	
	public int updateOrderTotalAmountAndDeliveryFee(OrderDO orderDO);
	
	
	public int updateOrderTotalAmount(OrderDO orderDO);
	/**
	 * 修改星号标记
	 * @param orderDO
	 * @return
	 */
	public int updateOrderAsteriskMark(OrderDO orderDO);
	
	public List<OrderDO> queryTimeoutUnpaidOrders(OrderQTO query);
	
	/**
	 * 查询超时收货订单
	 */
	public List<OrderDO> querySignOffOverTimeOrder(OrderQTO orderQTO);
	
	public Integer getHasBuyCount(OrderQTO orderQTO);
	
	public Integer subOrderPaySuccess(OrderDO orderDO);
	
	/**
	 * 根据原始单查询子订单
	 * @param orderQTO
	 * @return
	 */
	public List<OrderDO> querySubOrdersByOriginalOrder(OrderQTO orderQTO);
	
	/**
	 * 标记为退款
	 * @param query
	 * @return
	 */
	public int markRefund(OrderQTO query);
	
	public List<OrderDO> queryNoSettlementOrders(OrderQTO orderQTO)throws TradeException ;
	
	public int modifySettlementStatus(Long oid,String status)throws TradeException ;
	
	/**
	 * 查询未结算的订单
	 * @param orderQTO
	 * @return
	 * @throws TradeException
	 */
	public Long getUnSettlementAmount(DataQTO query);

	
	public List<OrderDO>  queryUnsettlementOrders(OrderQTO query);
	
	public Long getUnsettlementOrderCount(OrderQTO query);
	
	public int getItemHasPurchasedCount(OrderQTO query);
	
	public OrderDO getPreOrder(OrderQTO query);
	
	public int updatePreOrder(OrderDO orderDO);
	
	public List<OrderDO> queryTimeoutPreOrders(OrderQTO query);
	
	public int deletePreOrder(OrderQTO query);
	
	public OrderDO getActivityOrder(OrderQTO query);
	
	public int update2ActivityOrder(OrderDO orderDO);
	
	public int updateOrderDiscountInfo(OrderDO orderDO);
	/** 
	public int closeOrder(OrderDO orderDO);
	/*public OrderDO readOrder(OrderDO orderDO)throws DAOException;
	
	public OrderDO deleteOrder(OrderDO orderDO)throws DAOException;
	
	public OrderDO findOrder(OrderDO orderDO)throws DAOException;*/
	
	public Long getMallCommissionSumAmount(OrderQTO query);
	
	public int updateOrderCommission(OrderDO orderDO);
	
	public Long getSellerSettleTotalAmount(OrderQTO query);

    List<OrderDO> queryOrderByTradeNotify(List<TradeNotifyLogDO> param);

    public int falseDeleteOrderById(OrderQTO orderQTO);

    List<OrderDO> queryOrderByUserIdAndPaymentId(TradeNotifyLogQTO tradeNotifyLogQTO);

    List<OrderDO>  queryAllOrderBg(OrderQTO query);


    Integer   queryOrderCount(String discountCode);

    Integer queryAllOrderCountBg(OrderQTO query);

    public List<OrderDO> queryInnerUserOrders(OrderQTO orderQTO)
			throws TradeException;

    public List<OrderDO> queryUser(OrderQTO orderQTO)
			throws TradeException;

	Long getUserTotalCost(OrderQTO orderQTO);

	public Integer queryUserOrderCount(OrderQTO orderQTO);

//	List<Long> getShareId(Long userId);

}
