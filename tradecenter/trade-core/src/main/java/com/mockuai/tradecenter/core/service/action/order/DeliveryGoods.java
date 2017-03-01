package com.mockuai.tradecenter.core.service.action.order;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DeliveryManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.MsgQueueManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.TradeUtil.RefundMark;

/**
 * 
 * @author cwr
 */
public class DeliveryGoods implements Action {
	private static final Logger log = LoggerFactory.getLogger(DeliveryGoods.class);
	
	@Resource
	private DeliveryManager deliveryManager;
	
	@Resource
	private OrderManager orderManager;	
	
	@Resource
	private OrderItemManager orderItemManager;
	
	@Resource
	private MsgQueueManager msgQueueManager;
	
	@Resource
    private TransactionTemplate transactionTemplate;
	
	public TradeResponse<Boolean> execute(final RequestContext context) throws TradeException {
		Request request = context.getRequest();
		String appKey = (String) context.get("appKey");
		String bizCode = (String) context.get("bizCode");
		final Long orderId = (Long)request.getParam("orderId");
		final Long userId = (Long)request.getParam("userId");
		List<OrderDeliveryInfoDTO> orderDeliveryInfoDTOs = (List<OrderDeliveryInfoDTO>)request.getParam("deliveryInfoList");
		
		//TODO 入参校验
		if(orderDeliveryInfoDTOs == null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "deliveryInfoList is null");
		}
		
		
		final OrderDO order = this.orderManager.getOrder(orderId, userId);
		if(order == null){
			log.error("order doesn't exist");
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order doesn't exist");
		}
		long sellerId = order.getSellerId();
		
		if(order.getOrderStatus() == TradeConstants.Order_Status.DELIVERIED){
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_AHAVE_BEEN_SHIPPED);
		}

		//订单状态检查
		/*老业务调整*/
//		if(order.getOrderStatus() != TradeConstants.Order_Status.PAID){
//			return ResponseUtils.getFailResponse(ResponseCode.BZI_E_ORDER_UNPAID_CANNOT_DELIVERY);
//		}
		
		if(order.getOrderStatus() != TradeConstants.Order_Status.UN_DELIVER){
			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION, "只有待发货订单才能发货");
		}
		
		if( null!=order.getRefundMark() && order.getRefundMark() ==RefundMark.REFUNDING_MARK ){
			return ResponseUtils.getFailResponse(ResponseCode.HAS_REFUND_ITEM_CAN_NOT_OPERATE);
		}
		
		Boolean deliveryResult ;
		
		OrderDeliveryInfoDTO orderDeliveryInfoDTO = orderDeliveryInfoDTOs.get(0);
		
		//增加  
			
			Long deliveryInfoId  = null;
			if(null!=order&&order.getDeliveryId()==2){
				//到店自提
				try{
					deliveryResult =  deliveryManager.deliveryGoodsByPickup(orderId, userId, orderDeliveryInfoDTO.getPickupCode());
				}catch(TradeException e){
					return ResponseUtils.getFailResponse(ResponseCode.PICK_UP_CODE_NOT_MATCH);
				}
				
			}else{
			     if(orderDeliveryInfoDTO.getDeliveryType()!=2){
			    	 //如果是只发虚拟商品 则不能发无须物流
			    	 if(null!=orderDeliveryInfoDTO.getOrderItemIds()&&orderDeliveryInfoDTO.getOrderItemIds().size()==1){
			    		 OrderItemQTO query = new OrderItemQTO();
			    		 query.setOrderId(orderDeliveryInfoDTO.getOrderId());
			    		 query.setOrderItemId(orderDeliveryInfoDTO.getOrderItemIds().get(0));
			    		 OrderItemDO orderItemDO = orderItemManager.getOrderItem(query);
			    		 if(null!=orderItemDO&&orderItemDO.getVirtualMark()!=null&&orderItemDO.getVirtualMark()==1){
			    			 return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_INVALID,"虚拟商品只能发无须物流");
			    		 }
			    	 }
			    	//添加订单发货信息
						try{
							orderDeliveryInfoDTO.setOrderId(orderId);
							orderDeliveryInfoDTO.setUserId(userId);
							deliveryInfoId  = this.deliveryManager.addDeliveryInfo(orderDeliveryInfoDTO,appKey);
						}catch(TradeException e){
							return ResponseUtils.getFailResponse(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
						}
			     }

			}
			
			//TODO ... 判断是否是赠品
			
			final OrderDTO orderDTO = new OrderDTO();
			orderDTO.setBizCode(bizCode);
			orderDTO.setId(orderId);
			orderDTO.setOrderSn(orderDeliveryInfoDTO.getOrder_sn());
			
			//修改订单状态，将订单状态置为已发货
			if(orderDeliveryInfoDTO.getOrderItemIds()!=null&&orderDeliveryInfoDTO.getOrderItemIds().size()>0){
				// 修改单商品的发货状态
				deliveryResult = orderItemManager.updateOrderItemDeliveryMark(orderId,userId, deliveryInfoId,1, orderDeliveryInfoDTO.getOrderItemIds());
				if(deliveryResult){
					log.info(" orderDTO 1: "+JSONObject.toJSONString(orderDTO));
					// TODO 发送mq消息,嗨币支付不发送消息
					if(Integer.parseInt(EnumPaymentMethod.HI_COIN_PAY.getCode()) != order.getPaymentId()){
						msgQueueManager.sendOrderMessage("orderdelivered", orderDTO);
					}
					
					return ResponseUtils.getSuccessResponse(true);
				}else{
					return ResponseUtils.getFailResponse(ResponseCode.SYS_E_DATABASE_ERROR,"deliveryGoods error");
				}
			
			}else{
				
				TradeResponse transResult = transactionTemplate.execute(new TransactionCallback<TradeResponse>() {
					 public TradeResponse doInTransaction(TransactionStatus transactionStatus) {
						 int orderResult;
						try {
							orderResult = orderManager.deliveryGoods(orderId, userId);
							if(orderResult > 0){
								 orderItemManager.updateOrderItemDeliveryMarkByOrderId(orderId);
							 }

							// TODO 发送mq消息,嗨币支付不发送消息
							log.info(" orderDTO 2: "+JSONObject.toJSONString(orderDTO));
							if(Integer.parseInt(EnumPaymentMethod.HI_COIN_PAY.getCode()) != order.getPaymentId()){
								msgQueueManager.sendOrderMessage("orderdelivered", orderDTO);
							}
							
							context.put("order", order);
						} catch (TradeException e) {
							transactionStatus.setRollbackOnly();
							log.error("error",e);
							return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_NOT_EXIST,"deliveryGoods error");
						}
						 
						 return ResponseUtils.getSuccessResponse(true);
					 }
				});
				return	transResult ;
				
			}

	}

	@Override
	public String getName() {
		return ActionEnum.DELIVERY_GOODS.getActionName();
	}
	
}
