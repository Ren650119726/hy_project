package com.mockuai.tradecenter.core.service.action.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO.OrderSku;
import com.mockuai.tradecenter.common.enums.EnumOrderType;
import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;
import com.mockuai.tradecenter.core.manager.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.TradeUtil.TradeNotifyLogStatus;
import com.mockuai.tradecenter.core.util.TradeUtil.TradeNotifyLogType;

/**
* 取消订单处理类
*/
public class CancelOrder implements Action {
	private static final Logger log = LoggerFactory.getLogger(CancelOrder.class);

	@Resource
	private OrderManager orderManager;

	@Resource
	private MarketingManager marketingManager;

	@Resource
	private VirtualWealthManager virtualWealthManager;

	@Resource
	private ItemManager itemManager;

	@Resource
	private SupplierManager supplierManager;

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private OrderItemManager orderItemManager;

	@Resource
	private DataManager dataManager;

	@Resource
    ExecutorService       executor;

	@Resource
	DozerBeanService  dozerBeanService;

	@Resource
	MsgQueueManager msgQueueManager;

	@Autowired
    TradeNotifyLogManager tradeNotifyLogMng;

	public com.mockuai.tradecenter.common.api.TradeResponse<Boolean> execute(RequestContext context)
			throws TradeException {
		Request request = context.getRequest();
		final String appKey = (String) context.get("appKey");

		if (request.getParam("userId") == null) {
			log.error("userId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
		} else if (request.getParam("orderId") == null) {
			log.error("orderId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderId is null");
		}

		final long orderId = (Long) request.getParam("orderId");
		final long userId = (Long) request.getParam("userId");
		final String cancelReason = (String) request.getParam("cancelReason");
		String sellerCancelMark = (String) request.getParam("sellerCancelMark");
		boolean isSellerCancel = false;
		if (StringUtils.isNotBlank(sellerCancelMark) && sellerCancelMark.equalsIgnoreCase("y")) {
			isSellerCancel = true;
		}
		final boolean finalIsSellerCancel = isSellerCancel;
		final OrderDO order = this.orderManager.getActiveOrder(orderId, userId);
		if (order == null) {
			log.error("order doesn't exist orderId:" + orderId + " userId:" + userId);
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order doesn't exist");
		}

		// 订单状态校验，只有未支付的订单才可以取消
		if (order.getOrderStatus() != TradeConstants.Order_Status.UNPAID) {
			log.error("only unpaid order can be canceled");
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_STATUS_ERROR,
					" only unpaid order can be cancel ");
		}

		TradeNotifyLogDO  tradeNotifyLogDO = tradeNotifyLogMng.getTradeNotifyLogByOrderId(orderId, TradeNotifyLogType.PAYMENT);
		if(tradeNotifyLogDO!=null&&tradeNotifyLogDO.getStatus()==TradeNotifyLogStatus.SUCCESS){
			log.error(" order_id:{} third party order has paid ",orderId);
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_STATUS_ERROR,
					"only unpaid order can be canceled");
		}


		TradeResponse transResult = transactionTemplate.execute(new TransactionCallback<TradeResponse>() {
			public TradeResponse doInTransaction(TransactionStatus transactionStatus) {
				try {

					// 判断订单是否有使用优惠券和虚拟财富，如果有使用，则需要解除优惠券和虚拟财富的预授权信息
					/*Integer discountMark = order.getDiscountMark();*/
					// 取消订单暂时不退优惠券
					/*if ((discountMark.intValue() & 1) == 1) {// 从左往右，第一个二进制位代表优惠活动标志
						boolean releaseResult = marketingManager.releaseUsedCoupon(userId, orderId, appKey);
						if (releaseResult == false) {
							throw new TradeException(ResponseCode.BIZ_E_RELEASE_COUPON_ERROR);
						}
					}*/

					/*discount_mark暂时只涉及优惠券的部分,余额支付其他地方退款*/
					/*if ((discountMark.intValue() & 2) == 2) {// 从左往右，第二个二进制位代表虚拟财富标志
						boolean releaseResult = virtualWealthManager.releaseUsedWealth(userId, orderId, appKey);
						if (releaseResult == false) {
							throw new TradeException(ResponseCode.BIZ_E_RELEASE_WEALTH_ERROR);
						}
					}*/

					int result = orderManager.cancelOrder(orderId, userId, cancelReason, finalIsSellerCancel);
					if (result == 0) {
						log.error("fail to cancel order, orderId:{}, userId:{}", orderId, userId);
						return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,
								"order doesn't exist");
					}

					// 查询订单明细
					OrderItemQTO orderItemQTO = new OrderItemQTO();
					orderItemQTO.setOrderId(orderId);
					orderItemQTO.setUserId(userId);
					List<OrderItemDO> orderItems = Collections.EMPTY_LIST;
					orderItems = orderItemManager.queryOrderItem(orderItemQTO);

					if(orderItems==null || orderItems.isEmpty()){
						//TODO error handle
					}else{
						List<Long> skuIds = new ArrayList<Long>();
						Map<Long,OrderItemDO> skuOrderItemMap = new HashMap<Long,OrderItemDO>();
						
						/*List<OrderSku> orderSkuList = new ArrayList<OrderSku>();
						
						for(OrderItemDO orderItemDO :orderItems){
							skuIds.add(orderItemDO.getItemSkuId());
						
							OrderSku orderSku = new OrderSku();
							orderSku.setSkuId(orderItemDO.getItemSkuId());
							orderSku.setNumber(orderItemDO.getNumber());
							orderSku.setStoreId(order.getStoreId());
							orderSku.setSupplierId(order.getSupplierId());
							orderSkuList.add(orderSku);
							
							没用到
							skuOrderItemMap.put(orderItemDO.getItemSkuId(), orderItemDO);
						}*/
						
						
		    			Map<Long,OrderSku> orderSkuMap = new HashMap<Long, OrderStockDTO.OrderSku>();
		    			for(OrderItemDO orderItemDO :orderItems){
		    				if(orderSkuMap.get(orderItemDO.getItemSkuId())==null){
		    					OrderSku orderSku = new OrderSku();	
		    					orderSku.setSkuId(orderItemDO.getItemSkuId());
		        				orderSku.setNumber(orderItemDO.getNumber());
								orderSku.setStoreId(order.getStoreId());
								orderSku.setSupplierId(order.getSupplierId());
		    					orderSkuMap.put(orderItemDO.getItemSkuId(), orderSku);
		    				}else{
		    					orderSkuMap.get(orderItemDO.getItemSkuId()).setNumber(orderSkuMap.get(orderItemDO.getItemSkuId()).getNumber()+orderItemDO.getNumber());
		    				}
		    				
		    			}		    			
//		    			log.info(" >>>>>>>>> orderSkuMap "+JSONObject.toJSONString(orderSkuMap));
		    			List<OrderSku> orderSkus = new ArrayList<OrderSku>();	
		    			for(Map.Entry<Long,OrderSku> entry : orderSkuMap.entrySet()){
		    				orderSkus.add(entry.getValue());
		    			}
						
						
						List<ItemSkuDTO> itemSkus = itemManager.queryItemSku(skuIds,orderItems.get(0).getSellerId(),1, appKey);
						
	    				 if (itemSkus != null && itemSkus.size() > 0 ) {
	    					 // TODO 已下单的订单，秒杀商品订单无需特殊处理
							 //如果是秒杀和竞拍的订单，则解冻库存。普通订单则补回库存
//							 if(order.getType().intValue() == EnumOrderType.SECKILL.getCode()
//									 || order.getType().intValue()==EnumOrderType.AUCTION.getCode()){
								 //解冻库存
//								 itemManager.thawStock(order.getOrderSn(), order.getUserId(), appKey);
//							 }else{
								 //补回库存

							 OrderStockDTO orderStockDTO = new OrderStockDTO();
							 if(order.getOriginalOrder()==null || order.getOriginalOrder().longValue()==0L){
								 /*正常订单*/
//								 itemManager.increaseOrderSkuStock(order.getOrderSn(), order.getUserId(), appKey);
								 
								 orderStockDTO.setOrderSn(order.getOrderSn());
								 orderStockDTO.setSellerId(order.getSellerId());
								 orderStockDTO.setOrderSkuList(orderSkus);
								
							 }else{
								 /*分仓分单*/
								 OrderDO originalOrder = CancelOrder.this.orderManager.getActiveOrder(order.getOriginalOrder(), userId);
								 if( ( order.getType() == EnumOrderType.NORMAL.getCode() || order.getType() == EnumOrderType.COMB.getCode() ) && originalOrder.getParentMark() != 1){
									 orderStockDTO.setOrderSn(order.getOrderSn());
									 orderStockDTO.setSellerId(order.getSellerId());
									 orderStockDTO.setOrderSkuList(orderSkus);
								 }else{
									 orderStockDTO.setOrderSn(originalOrder.getOrderSn());
									 orderStockDTO.setSellerId(originalOrder.getSellerId());
									 orderStockDTO.setOrderSkuList(orderSkus);
								 }
								 
							 }

							 supplierManager.thawOrderSkuStock(orderStockDTO, appKey);
							 
	    					 /*if(order.getOriginalOrder()==null || order.getOriginalOrder().longValue()==0L){
								 正常订单
								 itemManager.thawStock(order.getOrderSn(), order.getUserId(), appKey);
							 }else{
								 分仓分单
								 OrderStockDTO orderStockDTO = new OrderStockDTO();
								 OrderDO originalOrder = CancelOrder.this.orderManager.getActiveOrder(order.getOriginalOrder(), userId);
								 orderStockDTO.setOrderSn(originalOrder.getOrderSn());
								 orderStockDTO.setSellerId(originalOrder.getSellerId());
								 orderStockDTO.setOrderSkuList(orderSkuList);
								 itemManager.thawOrderSkuStockPartially(orderStockDTO,  appKey);
							 }*/
	    					 
//							 }

						 }
					}
					final List<OrderItemDO> finalOrderItems  = orderItems;
					executor.submit(new Thread(){
						public void run(){
//							dataManager.doCancelOrderBuriedPoint(order, appKey);

							OrderDTO orderDTO = dozerBeanService.cover(order, OrderDTO.class);
							List<OrderItemDTO> orderItemDTOList = dozerBeanService.coverList(finalOrderItems, OrderItemDTO.class);
							orderDTO.setOrderItems(orderItemDTOList);

							try {
								/*嗨币支付不发消息*/
								if(Integer.parseInt(EnumPaymentMethod.HI_COIN_PAY.getCode()) != order.getPaymentId()){
									msgQueueManager.sendOrderMessage("orderCancel", orderDTO);
								}
							} catch (TradeException e) {
								log.error("mq error",e);
							}catch(Throwable e){
								log.info("mq error",e);
							}
						}
					});


					return ResponseUtils.getSuccessResponse(true);
				} catch (TradeException e) {
					// 回滚事务
					transactionStatus.setRollbackOnly();
					log.error("["+e.getMessage()+"]");
					log.error("order_id" + orderId + ",cancelOrder.execute error", e);
					return ResponseUtils.getFailResponse(e.getResponseCode(),e.getMessage());
				} 
				/*catch (Exception e) {
					// 回滚事务
					transactionStatus.setRollbackOnly();
					log.error("order_id" + orderId + ",cancelOrder.execute error", e);
					return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
				}*/
			}
		});

		return transResult;
	}

	@Override
	public String getName() {
		return ActionEnum.CANCEL_ORDER.getActionName();
	}

	private boolean checkIsSuitSubOrderItem(OrderItemDO oItem){
		if(oItem.getActivityId()==null&&oItem.getOriginalSkuId()!=null){
			return true;
		}
		return false;
	}
}
