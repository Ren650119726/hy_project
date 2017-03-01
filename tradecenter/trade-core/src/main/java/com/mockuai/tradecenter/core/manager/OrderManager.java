package com.mockuai.tradecenter.core.manager;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.tradecenter.common.domain.AlipaymentDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;

	
public interface OrderManager {
	
	/**
	 * 根据order_sn得到订单，用于批量发货
	 * @param order_sn
	 * @return
	 * @throws TradeException
	 */
	public OrderDO getOrderByOrderSn(String order_sn)throws TradeException; 
	
	/**
	 * 推送订单
	 * @param orderQTOList
	 * @return
	 * @throws TradeException
	 */
	public Boolean pushOrders(List<OrderQTO> orderQTOList)throws TradeException; 
	
	/**
	 * 回滚订单
	 * @return
	 * @throws TradeException
	 */
	public Boolean rollBackOrder(OrderQTO orderQTO) throws TradeException;
	
	public Boolean updateDeliveryMarkById(OrderQTO orderQTO) throws TradeException;
	
	public Boolean UpdateOrderSupplier(OrderQTO orderQTO) throws TradeException;
	
	public List<OrderDO> queryDeliveriedOrders(Integer orderStatus) throws TradeException;
	
	/**
	 * 收货过期订单自动确认收货
	 * @param int
	 * @return
	 * @throws TradeException
	 */
	public List<OrderDO> queryOverTimeOrder(int count)throws TradeException, ParseException ;
	
	/**
	 * 添加订单接口示例
	 * @param orderDO
	 * @return
	 * @throws TradeException
	 */
	public long addOrder(OrderDO orderDO) throws TradeException;
	
	public long addPreOrder(OrderDO orderDO)throws TradeException;
	
	/**
	 * 取消订单
	 * @param orderDO
	 * @return
	 * @throws TradeException
	 */
	public int cancelOrder(Long orderId,Long userId,String cancelReason,boolean isSellerCancel)throws TradeException;
	
	/**
	 * 删除订单
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws TradeException
	 */
	public Integer deleteOrder(Long orderId, Long userId)throws TradeException;
	
	/**
	 * 根据订单id和用户id查询订单
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws TradeExcetion
	 */
	public OrderDO getOrder(Long orderId ,Long userId)throws TradeException;
	
	/**
	 * 获取用户订单数据 1.2.2
	 * @param orderQTO
	 * @return
	 * @throws TradeException
	 */
	public Integer queryUserOrderCount(OrderQTO orderQTO) throws TradeException;
	
	/**
	 * 跟新订单备注
	 * @param orderId
	 * @param userId
	 * @param newMemo
	 * @param memoType
	 * @return
	 * @throws TradeException
	 */
	public int updateOrderMemo(Long orderId,Long userId,String newMemo,Integer memoType)throws TradeException;
	
	/**
	 * 查询买家订单
	 * @param orderQTO
	 * @return
	 * @throws TradeException
	 */
	public List<OrderDO> queryUserOrders(OrderQTO orderQTO)throws TradeException;
	
	/**
	 * 获取订单记录数
	 * @param orderQTO
	 * @return
	 * @throws TradeException
	 */
	public int queryUserOrdersCount(OrderQTO orderQTO)throws TradeException;
	
	/**
	 * 订单列表mop接口（优化）
	 * @param orderQTO
	 * @return
	 * @throws TradeException
	 */
	public List<OrderDO> queryUserOrdersUpgrade(OrderQTO orderQTO) throws TradeException;

	/**
	 * 查询卖家订单
	 * TODO 该接口临时为洋东西查询订单报表提供了，后续需要去掉
	 * @param orderQTO
	 * @return
	 * @throws TradeException
	 */
	public List<OrderDO> querySellerOrders(OrderQTO orderQTO)throws TradeException;
	
	/**
	 * 复合查询总的订单条数
	 * @param orderQTO
	 * @return
	 * @throws TradeException
	 */
	public int getTotalCount(OrderQTO orderQTO)throws TradeException;
	
	/**
	 * 顾客确认签收，  更改订单的配送状态
	 * @param orderDO
	 * @return
	 * @throws TradeException
	 */
	public int confirmReceival(long orderId, long userId, int deliveryStatus)throws TradeException;
	
	/**
	 * 订单发货
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public int deliveryGoods(long orderId, long userId) throws TradeException;
	
	/**
	 * 查询有效订单
	 * @param orderQTO
	 * @return
	 * @throws TradeException
	 */
	public OrderDO getActiveOrder(Long orderId ,Long userId)throws TradeException;
	
	/**
	 * 标记订单的支付状态为已成功，如果存在子订单，则将子订单也标记为已支付
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws TradeException
	 */
	public int orderPaySuccess(Long orderId,List<OrderDO> subOrderList,Long userId)throws TradeException;
	
	/**
	 * test
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws TradeException
	 */
	public int orderPaySuccessTest(Long orderId,Long userId)throws TradeException;
	
	/**
	 * 结单 ，表示订单的生命周期结束不能再对订单做其他状态相关的操作
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws TradeException
	 */
	public int closeOrder(Long orderId, Long userId)throws TradeException;

	/**
	 * 评价订单
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws TradeException
	 */
	public int commentOrder(Long orderId, Long userId) throws TradeException;

	/**
	 * 修改订单支付方式
	 * @param orderId
	 * @param userId
	 * @param paymentId
	 * @return
	 * @throws TradeException
	 */
	public void updateOrderPayType(OrderDO orderDO, int payType) throws TradeException;

	/**
	 * 修改指定主订单跟子订单的支付方式
	 * @param mainOrder
	 * @param subOrderList
	 * @param payType
	 * @throws TradeException
	 */
	public void updateOrderPayType(OrderDO mainOrder, List<OrderDO> subOrderList, int payType) throws TradeException;

	/**
	 * 下单时候字段验证
	 * @param orderQTO
	 * @return
	 * @throws TradeEception
	 */
	public String validateFields4Add(OrderDTO orderDTO)throws TradeException;
	
	/**
	 * 复合查询总的订单条数
	 * @param orderQTO
	 * @return
	 * @throws TradeException
	 */
	public int getSellerOrdersTotalCount(OrderQTO orderQTO)throws TradeException;
	
	public String getCallBackOrderXX(AlipaymentDTO orderQTO) throws TradeException;
	
	/**
	 * 修改价格、
	 * @param orderId
	 * @param userId
	 * @param totalAmount
	 * @param deliveryFee
	 * @return
	 */
	public int updateOrderTotalAmountAndDeliveryFee(long orderId,long userId,long floatingPrice,long deliveryFee)throws TradeException;
	
	/**
	 * 订单加星标注
	 * @param orderId
	 * @param userId
	 * @param isAsteriskMark
	 * @return
	 * @throws TradeException
	 */
	public boolean updateOrderAsteriskMark(Long orderId,Long userId,boolean isAsteriskMark)throws TradeException;
	
	
	/**
	 * 查询超时未支付订单
	 * @param orderQTO
	 * @return
	 * @throws TradeException
	 */
	public List<OrderDO> queryTimeoutUnpaidOrders(OrderQTO orderQTO)throws TradeException;
	
	/**
	 * 收货过期订单自动确认收货
	 * @param int
	 * @return
	 * @throws TradeException
	 */
	public List<OrderDO> querySignOffOverTimeOrders(int count)throws TradeException ;
	
	public Integer getHasBuyCount(Long userId,Long itemId)throws TradeException ;
	
	/**
	 * 标记订单为退款、商户已经退款、
	 * @param userId
	 * @param orderId
	 * @return
	 * @throws TradeException
	 */
	public boolean markRefund(Long userId,Long orderId)throws TradeException ;
	
	public List<OrderTogetherDTO> convert2OrderTogetherDTOList(Map<Long, List<Long>> sellerSkuMap,
    		OrderDTO orderDTO,
    		Map<Long, ItemSkuDTO> itemSkuMap,
    		String bizCode,
    		Map<Long, List<OrderItemDO>> orderItemMap,
    		UserConsigneeDTO consigneeDTO,
    		OrderDO order,
    		String appkey)throws TradeException ;
	/**
	 * 根据bizCode得到配置的支付宝公钥
	 * @param bizCode
	 * @return
	 * @throws TradeException
	 */
	public String getAlipayPubKey(String bizCode)throws TradeException ;
	
	/**
	 * 
	 * @param bizCode
	 * @param paymenId
	 * @return
	 * @throws TradeException
	 */
	public String getWxpayPartnerKey(String bizCode,int paymenId)throws TradeException ;
	
	
	public int updateOrderTotalAmount(OrderDO order);
	
	public int updatePickupCode(Long orderId,Long userId,String pickupCode);
	
	public int updateOrderDiscountInfo(OrderDO order);
	
	/**
	 * 是否已经购买过商品
	 * @param userId
	 * @param skuId
	 * @return
	 */
	public boolean checkHasPurchasedItem(Long userId,Long skuId );
	
	public OrderDO getPreOrder(Long userId,Long skuId)throws TradeException;
	
	public OrderDO getPreOrderByItemQTO(OrderItemQTO query) throws TradeException;
	
	public List<OrderDO> queryTimeoutCancelPreOrders(OrderQTO query)throws TradeException;
	
	public Boolean deletePreOrder(OrderDO orderDO,Long skuId)throws TradeException;
	
	public Boolean phyDeletePreOrder(OrderDO orderDO,Long skuId)throws TradeException;
	
	public OrderDO getActivityOrder(Long userId,Long skuId)throws TradeException;
	
	public List<OrderDO> querySubOrders(Long oid)throws TradeException;
	
	public int updateOrderMallCommission(OrderDO orderDO)throws TradeException;

    List<OrderDO> queryAllOrderBg(OrderQTO orderQTO) throws TradeException;

    int queryAllOrderCountBg(OrderQTO orderQTO) throws TradeException;
    
    public List<OrderDO> queryInnerUserOrders(OrderQTO orderQTO) throws TradeException;
    
    public List<OrderDO> queryUser(OrderQTO orderQTO) throws TradeException;
    
    public String getLianlianpayPubKey(String bizCode) throws TradeException;

	/**
	 * huangsiqian 0830
	 * @param orderQTO
	 * @return
	 * @throws TradeException
     */
	Long getUserTotalCost(OrderQTO orderQTO)throws  TradeException;


	/**
	 * huangsiqian 0902
	 *
	 */

//	List<Long> getShareId(Long userId) throws TradeException;

}
