package com.mockuai.tradecenter.core.service.action.order;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DeliveryManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.MsgQueueManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

/**
 * 批量发货
 * @author wangyingni
 *
 */
public class BatchDeliveryGoods implements Action{
	private static final Logger log = LoggerFactory.getLogger(BatchDeliveryGoods.class);
	
	@Resource
	private DeliveryManager deliveryManager;
	
	@Resource
	private OrderItemManager orderItemManager;
	
	@Resource
    private TransactionTemplate transactionTemplate;
	
	@Resource
	private OrderManager orderManager;	
	
	@Resource
	private MsgQueueManager msgQueueManager;
	
	public List<OrderDeliveryInfoDTO> orderDeliveryInfoDTOs;
	
	@Override
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		
		log.info(" BatchDeliveryGoods start ");
		
		Request request = context.getRequest();
		orderDeliveryInfoDTOs = (List<OrderDeliveryInfoDTO>)request.getParam("deliveryInfoList");
		//检查入参是否为空
		if(orderDeliveryInfoDTOs == null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "deliveryInfoList is null");
		}
		final String appKey = (String) context.get("appKey");
		final String bizCode = (String) context.get("bizCode");
		//检查订单是否存在,订单状态是否为已付款或已经发货
		OrderDO order;
		TradeResponse tradeResponse = null;
		for(  OrderDeliveryInfoDTO  dto: orderDeliveryInfoDTOs ){
			if(null==dto.getOrderId()||null==dto.getUserId()){
				return new TradeResponse(ResponseCode.PARAM_E_PARAM_INVALID.getCode(),"orderId or userId is null");
			}
			order = this.orderManager.getOrder(dto.getOrderId(), dto.getUserId());
			if(order==null){
				tradeResponse = new TradeResponse(ResponseCode.BIZ_E_ITEM_NOT_EXIST.getCode(),"订单"+dto.getOrder_sn()+ResponseCode.BIZ_E_ITEM_NOT_EXIST.getComment());
				return tradeResponse;
			}
			/*if(order.getOrderStatus() != TradeConstants.Order_Status.PAID){
				tradeResponse = new TradeResponse(ResponseCode.BZI_E_ORDER_DELIVERYED_CANNOT_DELIVERY.getCode(),"订单"+order.getOrderSn()+ResponseCode.BZI_E_ORDER_DELIVERYED_CANNOT_DELIVERY.getComment());
				return tradeResponse;
			}*/
			if( !EnumOrderStatus.UN_DELIVER.getCode().equals(order.getOrderStatus()+"") ){
				tradeResponse = new TradeResponse(ResponseCode.BIZ_E_ORDER_NO_UN_DELIVERYED_CANNOT_DELIVERY.getCode(),"订单["+order.getOrderSn()+"]"+ResponseCode.BIZ_E_ORDER_NO_UN_DELIVERYED_CANNOT_DELIVERY.getComment());
				return tradeResponse;
			}
		}
		//将操作传入事务中
	     TradeResponse transResult = transactionTemplate.execute(new TransactionCallback<TradeResponse>() {
	            public TradeResponse doInTransaction(TransactionStatus transactionStatus) {
	            	try {	            		
	            		
	            		//添加订单发货信息
	            		boolean batchDeliveryResult = BatchDeliveryGoods.this.deliveryManager.
	            				batchAddDeliveryInfo(orderDeliveryInfoDTOs, appKey);
	            		
	            		OrderDTO orderDTO = new OrderDTO();
	            		orderDTO.setBizCode(bizCode);
	            		
//	            		if(batchDeliveryResult){
            			for(OrderDeliveryInfoDTO dto:BatchDeliveryGoods.this.orderDeliveryInfoDTOs){
            				orderItemManager.updateOrderItemDeliveryMarkByOrderId(dto.getOrderId());
							int orderResult = BatchDeliveryGoods.this.orderManager.deliveryGoods(dto.getOrderId(), dto.getUserId());
							
							// TODO 发送mq消息
							orderDTO.setId(dto.getOrderId());
							orderDTO.setOrderSn(dto.getOrder_sn());
							
							msgQueueManager.sendOrderMessage("orderdelivered", orderDTO);
							
							
							if(orderResult<=0){
								transactionStatus.setRollbackOnly();
								TradeResponse tradeResponse = new TradeResponse(ResponseCode.BZI_E_ORDER_ADD_DELIVERY_IMFO_FAILED.getCode(),"订单"+dto.getOrder_sn()+ResponseCode.BZI_E_ORDER_ADD_DELIVERY_IMFO_FAILED.getComment());
								return tradeResponse;
							}
						}
//	            		}
	            		
						
					} catch (TradeException e) {
						 //回滚事务
	                    transactionStatus.setRollbackOnly();
	                    log.error("BatchDeliveryGoods error", e);
	                    return ResponseUtils.getFailResponse(e.getResponseCode());
					}

            		
            		
	            	return ResponseUtils.getSuccessResponse(true);
	            }});
	     

		log.info(" BatchDeliveryGoods end ");
	     
		return transResult;
	}
	


	@Override
	public String getName() {
		return ActionEnum.BATCH_DELIVERY.getActionName();
	}

}
