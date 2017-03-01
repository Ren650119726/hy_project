package com.mockuai.tradecenter.client;

import com.aliyun.oss.ServiceException;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.*;
import com.mockuai.tradecenter.common.domain.dataCenter.SalesVolumeDTO;
import com.mockuai.tradecenter.common.domain.message.OrderMessageDTO;

import java.util.List;
import java.util.Map;

public interface OrderClient {
	
	/**
	 * TODO
	 * 20160713晚支付无法正常回调，模拟回调处理支付成功订单
	 * @param orderQTO
	 * @param appKey
	 * @return
	 */
    Response<String> callbackRecover(AlipaymentDTO orderQTO,String appKey);
	
	/**
	 * 新增订单
	 * @param orderDTO
	 * @return
	 */
	Response<OrderDTO> addOrder(OrderDTO orderDTO, String appKey);
	Response<OrderDTO> getOrder(Long orderId,Long userId,String appkey);

	/**
	 * 获取支付链接对象（包含支付链接和支付请求参数）
	 * @param orderId
	 * @param userId
	 * @return
	 */
	Response<PaymentUrlDTO> getPaymentUrlForWap(long orderId, long userId, String wxAuthCode, String appKey);
	
	/**
	 * 订单查询（买家）
	 */
	Response<List<OrderDTO>> queryOrder(OrderQTO orderQTO, String appKey);

    Response<List<OrderDTO>> queryAllOrderBg(OrderQTO orderQTO, String appKey);
	
	/**
	 * 订单查询（卖家）
	 */
	Response<List<OrderDTO>> querySellerOrder(OrderQTO orderQTO, String appKey);
	
	/**
     * 批量推送订单
     * @param orderQTOList
     * @param appKey
     * @return
     * @throws ServiceException
     */
    Response<Boolean> pushOrders(List<OrderQTO> orderQTOList,String appKey);  
    
    Response<Boolean> updateDeliveryMarkById(OrderQTO orderQTO,String appKey);
    
    Response<Boolean> updateOrderStatusById(OrderQTO orderQTO,String appKey);
    
	Response<Boolean> updateOrderSupplier(OrderQTO orderQTO,String appKey);
	
	/**
	 * 修改订单价格
	 * @param orderId
	 * @param userId
	 * @param freight
	 * @param floatingPrice
	 * @return
	 */
	Response<Boolean> updateOrderPrice(long orderId,long userId,long freight,long floatingPrice,String appKey);
	
	/**
	 * 取消订单
	 * @param orderId
	 * @param userId
	 * @param appKey
	 * @return
	 */
	Response<Boolean> cancelOrder(long orderId,long userId,String cancelReason,String appKey);
	
	/**
	 * 发货
	 * @param orderId
	 * @param userId
	 * @param list
	 * @param appkEY
	 * @return
	 */
	Response<Boolean> deliveryGoods(long orderId,long userId,List<OrderDeliveryInfoDTO> list,String appkeycd);
	
	/**
	 * 添加评论 
	 * @param orderId
	 * @param userId
	 * @param itemId
	 * @param score
	 * @param title
	 * @param content
	 * @return
	 */
	Response<Boolean> addOrderComment(long orderId,long userId,long itemId,long sellerId,int score,String title,String content,String appKey);
	Response<Boolean> batchDeliveryGoods(List<OrderDeliveryInfoDTO> orderDeliveryInfoDTOs, String appKey);
	
	/**
	 * 商家查询评论
	 * @param sellerId
	 * @param appKey
	 * @return
	 */
	Response<List<ItemCommentDTO>> queryComment(Long sellerId,Integer socre,Integer currentPage,Integer pageSize,String appKey);
	
	/**
	 * 修改备注
	 * @param orderId
	 * @param memo
	 * @return
	 */
	Response<Boolean> updateOrderSellerMemo(long orderId,long userId,String memo,String appKey);
	
	/**
	 * 修改加星
	 * @param orderId
	 * @param userId
	 * @param asteriskMark
	 * @param appkey
	 * @return
	 */
	Response<Boolean> updateOrderAsteriskMark(long orderId,long userId,String asteriskMark,String appkey);
	
	Response<Boolean> replyComment(long userId, long orderId, long sellerId, long itemId, long commentId,long replyUserId,
			String content,String appkey);
	
	/**
	 * 商家查询数据中心数据
	 * @param sellerID
	 * @param orderQTO
	 * @return
	 */
	Response<DataDTO> queryData(DataQTO orderQTO,String appKey);
	
	
	/**
	 * 标记为退款
	 * @param appkey
	 * @return
	 */
	Response<Boolean> markRefund(Long userId,Long orderId, String appkey);
	
	
//	/**
//	 * 商家查询数据中心数据
//	 * @param sellerID
//	 * @param orderQTO
//	 * @return
//	 */
//	Response<DataDTO> queryData(DataQTO dataQTO,String appKey);
	
	/**
	 * 获取销量前10的商品
	 * @param orderQTO
	 * @param appKey
	 * @return
	 */
	Response<List<SalesVolumeDTO>> queryTOP10Item(DataQTO dataQTO,String appKey);
	
	/**
	 * 得到数据的总数
	 * @param dataQTO
	 * @return
	 */
	Response<DataDTO> getData(DataQTO dataQTO,String appKey);
	
	/**
	 * 得到每天的数据
	 * @param dataQTO
	 * @return
	 */
	Response<Map<String,DataDTO>> getDataDaily(DataQTO dataQTO,String appKey);
	
	Response<String> getPickupCode(OrderMessageDTO orderMessageDTO,String appKeys);
	
	Response<OrderDTO> getAloneOrder(Long orderId,Long userId,String appkey);

	/**
	 * 查询订单轨迹信息，以轨迹列表的信息返回，列表中的每个元素代表订单生命周期的一个节点
	 * @param orderId
	 * @param userId
	 * @param appKey
	 * @return
	 */
	Response<List<OrderTrackDTO>> queryOrderTrack(Long orderId, Long userId, String appKey);

    Response<StatisticsActivityInfoDTO> queryStatisticsActivityInfo(long activityId,String  appKey);

    Response<List<SaleRankDTO>> querySaleRank(long activityId,String  appKey);

	Response<OrderDTO> getUserTotalCost(Long userId, String  appKey);

	// 查询返回用户条件的订单总数
	Response<Integer> queryUserOrderCount(OrderQTO orderQTO, String  appKey);

	/**
	 * huangsiqian 0902
	 * 根据订单用户手机查询分享人id
	 */
//	Response<List<Long>> getShareId(Long userId,String appKey);
	
	/**
	 * 获取已支付过订单的用户数据
	 * @return
	 */
	Response<List<OrderDTO>> getUsers(OrderQTO orderQTO,String appKey);


}
