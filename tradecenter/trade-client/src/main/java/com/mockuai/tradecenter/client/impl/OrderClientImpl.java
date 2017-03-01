package com.mockuai.tradecenter.client.impl;

import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.api.TradeService;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.domain.*;
import com.mockuai.tradecenter.common.domain.dataCenter.SalesVolumeDTO;
import com.mockuai.tradecenter.common.domain.message.OrderMessageDTO;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderClientImpl implements OrderClient{

	@Resource
	private TradeService tradeService;



	public void setTradeService(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	
	

	public TradeService getTradeService() {
		return tradeService;
	}


	public Response<String> callbackRecover(AlipaymentDTO orderQTO, String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.CALL_BACK_RECOVER.getActionName());
		request.setParam("orderQTO",orderQTO);
		request.setParam("appKey", appKey);
		Response<String> response = tradeService.execute(request);
		return response;
	}



	public Response<OrderDTO> addOrder(OrderDTO orderDTO, String appKey) {
		System.out.println(" 【addOrder client】 ");
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.ADD_ORDER.getActionName());
		request.setParam("orderDTO", orderDTO);
		request.setParam("appKey", appKey);
		Response<OrderDTO> response = tradeService.execute(request);
		return response;
	}

	public Response<PaymentUrlDTO> getPaymentUrlForWap(long orderId, long userId, String wxAuthCode, String appKey) {

		com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
		tradeReq.setCommand(ActionEnum.GET_PAYMENT_URL_FOR_WAP.getActionName());
		tradeReq.setParam("orderId", orderId);
		tradeReq.setParam("userId", userId);
		tradeReq.setParam("wxAuthCode", wxAuthCode);
		tradeReq.setParam("appKey", appKey);

		Response<PaymentUrlDTO> tradeResp = tradeService.execute(tradeReq);

		return tradeResp;
	}

	@Override
	public Response<List<OrderDTO>> queryOrder(OrderQTO query, String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		// 原有逻辑
//		request.setCommand(ActionEnum.QUERY_SELLER_ORDER.getActionName());
		request.setCommand(ActionEnum.QUERY_INNER_USER_ORDER.getActionName());
		request.setParam("orderQTO",query);
		request.setParam("appKey", appKey);
		Response<List<OrderDTO>> response = tradeService.execute(request);
		return response;
	}

    @Override
    public Response<List<OrderDTO>> queryAllOrderBg(OrderQTO query, String appKey) {
        com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_ALL_ORDER_BG.getActionName());
        request.setParam("orderQTO",query);
        request.setParam("appKey", appKey);
        Response<List<OrderDTO>> response = tradeService.execute(request);
        return response;
    }

	@Override
	public Response<List<OrderDTO>> querySellerOrder(OrderQTO orderQTO,
			String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_SELLER_ORDER.getActionName());
		request.setParam("orderQTO",orderQTO);
		request.setParam("appKey", appKey);
		Response<List<OrderDTO>> response = tradeService.execute(request);
		return response;
	}



	@Override
	public Response<Boolean> pushOrders(List<OrderQTO> orderQTOList,
			String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_PUSH_ORDER_STATUS.getActionName());
		request.setParam("orderQTOList",orderQTOList);
		request.setParam("appKey", appKey);
		Response<Boolean> response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<Boolean> updateDeliveryMarkById(OrderQTO orderQTO,
			String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_DELIVERY_MARK.getActionName());
		request.setParam("orderQTO",orderQTO);
		request.setParam("appKey", appKey);
		Response<Boolean> response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<Boolean> updateOrderStatusById(OrderQTO orderQTO,
			String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.ROLLBACK_ORDER.getActionName());
		request.setParam("orderQTO",orderQTO);
		request.setParam("appKey", appKey);
		Response<Boolean> response = tradeService.execute(request);
		return response;
	}
	
	@Override
	public Response<Boolean> updateOrderSupplier(OrderQTO orderQTO,
			String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_ORDER_SUPPLIER.getActionName());
		request.setParam("orderQTO",orderQTO);
		request.setParam("appKey", appKey);
		Response<Boolean> response = tradeService.execute(request);
		return response;
	}



	@Override
	public Response<Boolean> updateOrderPrice(long orderId, long userId, long freight,long floatingPrice,String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_ORDER_PRICE.getActionName());
		request.setParam("appKey", appKey);
		request.setParam("orderId",orderId);
		request.setParam("userId", userId);
		request.setParam("floatingPrice", floatingPrice);
		request.setParam("freight", freight);
		Response<Boolean> response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<Boolean> cancelOrder(long orderId, long userId, String cancelReason,String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.CANCEL_ORDER.getActionName());
		request.setParam("appKey", appKey);
		request.setParam("orderId",orderId);
		request.setParam("userId", userId);
		request.setParam("sellerCancelMark", "Y");
		request.setParam("cancelReason", cancelReason);
		Response<Boolean> response = tradeService.execute(request);
		return response;
	}

	@Override
	public Response<Boolean> deliveryGoods(long orderId, long userId, List<OrderDeliveryInfoDTO> list, String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.DELIVERY_GOODS.getActionName());
		request.setParam("appKey", appKey);
		request.setParam("orderId",orderId);
		request.setParam("userId", userId);
		request.setParam("deliveryInfoList", list);
		Response<Boolean> response = tradeService.execute(request);
		return response;
	}



	@Override
	public Response<Boolean> addOrderComment(long orderId, long userId, long itemId, long sellerId,int score, String title,
			String content,String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.COMMENT_ORDER.getActionName());
		
		
		ItemCommentDTO comment = new ItemCommentDTO();
		
		comment.setItemId(itemId);
		comment.setScore(score);
		comment.setTitle(title);
		comment.setContent(content);
		comment.setOrderId(orderId);
		comment.setUserId(userId);
		comment.setSellerId(sellerId);
		
		List<ItemCommentDTO> list = new ArrayList<ItemCommentDTO>();
		list.add(comment);
		
		request.setParam("itemCommentList", list);
		
		request.setParam("userId", userId);
		
		request.setParam("orderId", orderId);
		
		request.setParam("appKey", appKey);
		
		
		Response<Boolean> response = tradeService.execute(request);
		return response;
	}



	@Override
	public Response<List<ItemCommentDTO>> queryComment(Long sellerId, Integer score,Integer offSet,Integer pageSize,String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_COMMENT.getActionName());
		System.out.println("begin queryOrderComment");

		
		request.setParam("sellerId", sellerId);
		request.setParam("score", score);
		request.setParam("appKey", appKey);
		request.setParam("offset", offSet);
		request.setParam("pageSize", pageSize);
		return tradeService.execute(request);
	}
		
	public Response<Boolean> batchDeliveryGoods(List<OrderDeliveryInfoDTO> orderDeliveryInfoDTOs, String appKey) {
		com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
		tradeReq.setCommand(ActionEnum.BATCH_DELIVERY.getActionName());
		tradeReq.setParam("deliveryInfoList", orderDeliveryInfoDTOs);
		tradeReq.setParam("appKey", appKey);
		if(tradeReq==null)
			System.out.println("really null");
		Response<Boolean> tradeResp = tradeService.execute(tradeReq);
		return tradeResp;
	}
	



	@Override
	public Response<Boolean> updateOrderSellerMemo(long orderId, long userId,String memo,String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_ORDER_MEMO.getActionName());
		request.setParam("appKey", appKey);
		request.setParam("orderId",orderId);
		request.setParam("userId", userId);
		request.setParam("newMemo", memo);
		request.setParam("memoType", 2);
		Response<Boolean> response = tradeService.execute(request);
		return response;
	}



	@Override
	public Response<Boolean> updateOrderAsteriskMark(long orderId, long userId, String asteriskMark, String appkey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.UPDATE_ORDER_ASTERISKS_MARK.getActionName());
		request.setParam("appKey", appkey);
		request.setParam("orderId",orderId);
		request.setParam("userId", userId);
		request.setParam("isAsteriskMark", asteriskMark);//Y or N
		Response<Boolean> response = tradeService.execute(request);
		return response;
	}



	@Override
	public Response<OrderDTO> getOrder(Long orderId, Long userId, String appkey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_ORDER.getActionName());
		request.setParam("appKey", appkey);
		request.setParam("orderId",orderId);
		request.setParam("userId", userId);
		Response<OrderDTO> response = tradeService.execute(request);
		return response;
	}



	@Override
	public Response<Boolean> replyComment(long userId, long orderId, long sellerId, long itemId, long commentId,long replyUserId,
			String content,String appkey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.REPLY_COMMENT.getActionName());
		request.setParam("appKey", appkey);
		request.setParam("userId", userId);
		
		request.setParam("orderId", orderId);
		
		request.setParam("itemId", itemId);
		
		request.setParam("sellerId", sellerId);
		
		request.setParam("replyUserId", replyUserId);
		
		request.setParam("content",content);
		
		request.setParam("commentId", commentId);
		
		Response<Boolean> response = tradeService.execute(request);
		return response;
	}
	
	@Override
	public Response<DataDTO> queryData(DataQTO orderQTO,String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_DATA.getActionName());
		System.out.println("begin queryData");
		request.setParam("dataQTO", orderQTO);
		request.setParam("appKey", appKey);
		Response response = tradeService.execute(request);
		return response;
	}



	@Override
	public Response<Boolean> markRefund(Long userId,Long orderId, String appkey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.MARK_REFUND.getActionName());
	
		request.setParam("orderId", orderId);
		request.setParam("userId", userId);
		request.setParam("appKey", appkey);
		
		Response response = tradeService.execute(request);
		return response;
	}
	
//	@Override
//	public Response<DataDTO> queryData(DataQTO dataQTO,String appKey) {
//		Request request = new BaseRequest();
//		request.setCommand(ActionEnum.QUERY_DATA.getActionName());
//		System.out.println("begin queryData");
//		request.setParam("dataQTO", dataQTO);
//		request.setParam("appKey", appKey);
//		Response response = tradeService.execute(request);
//		return response;
//	}
//
	@Override
	public Response<List<SalesVolumeDTO>> queryTOP10Item(DataQTO dataQTO,String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_TOP10_ITEM.getActionName());
		System.out.println("begin queryTOP10Items");
		request.setParam("dataQTO", dataQTO);
		request.setParam("appKey", appKey);
		Response response = tradeService.execute(request);
		return response;
	}

	public Response<DataDTO> getData(DataQTO dataQTO,String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_DATA.getActionName());
		System.out.println("begin getData");
		request.setParam("dataQTO", dataQTO);
		request.setParam("appKey", appKey);
		Response response = tradeService.execute(request);
		return response;
	}

	public Response<Map<String, DataDTO>> getDataDaily(DataQTO dataQTO,String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_DATA_DAILY.getActionName());
		System.out.println("begin getDataDaily");
		request.setParam("dataQTO", dataQTO);
		request.setParam("appKey", appKey);
		Response response = tradeService.execute(request);
		return response;
	}



	@Override
	public Response<String> getPickupCode(OrderMessageDTO orderMessageDTO, String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_PICKUP_CODE.getActionName());
		request.setParam("orderMessage",  orderMessageDTO);		
		request.setParam("appKey", appKey);
		Response response = tradeService.execute(request);
		return response;
	}


	@Override
	public Response<OrderDTO> getAloneOrder(Long orderId, Long userId, String appkey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_ALONE_ORDER.getActionName());
		request.setParam("appKey", appkey);
		request.setParam("orderId",orderId);
		request.setParam("userId", userId);
		Response<OrderDTO> response = tradeService.execute(request);
		return response;
	}


	@Override
	public Response<List<OrderTrackDTO>> queryOrderTrack(Long orderId, Long userId, String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_ORDER_TRACK.getActionName());
		request.setParam("appKey", appKey);
		request.setParam("orderId", orderId);
		request.setParam("userId", userId);
		Response<List<OrderTrackDTO>> response = tradeService.execute(request);
		return response;
	}
    @Override
    public Response<StatisticsActivityInfoDTO> queryStatisticsActivityInfo(long activityId,String appKey) {
        com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_ACTIVITY_STATISTICS.getActionName());
        request.setParam("appKey", appKey);
        request.setParam("activityId", activityId);
        Response<StatisticsActivityInfoDTO> response = tradeService.execute(request);
        return response;

    }

    @Override
    public Response<List<SaleRankDTO>> querySaleRank(long activityId,String appKey) {
        com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_SALE_RANK.getActionName());
        request.setParam("appKey", appKey);
        request.setParam("activityId", activityId);
        Response<List<SaleRankDTO>> response = tradeService.execute(request);
        return response;

    }

	@Override
	public Response<OrderDTO> getUserTotalCost(Long userId, String appKey) {
		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
		request.setCommand(ActionEnum.GET_USER_TOTAL_COST.getActionName());
		request.setParam("appKey",appKey);
		request.setParam("userId",userId);
		Response<OrderDTO> response = tradeService.execute(request);

		return response;
	}

	@Override
	public Response<Integer> queryUserOrderCount(OrderQTO orderQTO,
			String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_USER_ORDER_COUNT.getActionName());
		request.setParam("appKey",appKey);
		request.setParam("orderQTO",orderQTO);
		Response<Integer> response = tradeService.execute(request);

		return response;
	}



	@Override
	public Response<List<OrderDTO>> getUsers(OrderQTO orderQTO,String appKey) {
		Request request = new BaseRequest();
		request.setCommand(ActionEnum.QUERY_USER.getActionName());
		request.setParam("appKey", appKey);
		request.setParam("orderQTO", orderQTO);
		Response<List<OrderDTO>> response = tradeService.execute(request);
		
		return response;
	}

//	@Override
//	public Response<List<Long>> getShareId(Long userId, String appKey) {
//		com.mockuai.tradecenter.common.api.Request request = new BaseRequest();
//		request.setCommand(ActionEnum.GET_SHARE_ID.getActionName());
//		request.setParam("appKey",appKey);
//		request.setParam("userId",userId);
//		Response<List<Long>> response = tradeService.execute(request);
//		return response;
//	}
	
	
	
}
