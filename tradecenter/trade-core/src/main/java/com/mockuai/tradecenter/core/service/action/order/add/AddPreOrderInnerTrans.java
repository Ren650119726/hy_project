//package com.mockuai.tradecenter.core.service.action.order.add;
//
//import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
//import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
//import com.mockuai.tradecenter.common.constant.ResponseCode;
//import com.mockuai.tradecenter.common.domain.OrderDTO;
//import com.mockuai.tradecenter.common.domain.OrderItemDTO;
//import com.mockuai.tradecenter.core.base.TradeInnerOper;
//import com.mockuai.tradecenter.core.base.request.InnerRequest;
//import com.mockuai.tradecenter.core.base.request.OrderRequest;
//import com.mockuai.tradecenter.core.base.result.TradeOperResult;
//import com.mockuai.tradecenter.core.domain.BizLockDO;
//import com.mockuai.tradecenter.core.domain.OrderDO;
//import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
//import com.mockuai.tradecenter.core.domain.OrderItemDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.*;
//import com.mockuai.tradecenter.core.util.ModelUtil;
//import com.mockuai.tradecenter.core.util.PaymentUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.TransactionCallback;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import javax.annotation.Resource;
//import java.util.*;
//import java.util.Map.Entry;
//
//@Service
//public class AddPreOrderInnerTrans implements TradeInnerOper {
//	private static final Logger log = LoggerFactory.getLogger(AddPreOrderInnerTrans.class);
//	@Resource
//	UserManager userManager;
//	@Resource
//	OrderSeqManager orderSeqManager;
//	@Resource
//	OrderManager orderManager;
//	@Resource
//	OrderItemManager orderItemManager;
//	@Resource
//	ItemPlatformManager itemPlatformManager;
//	@Autowired
//	TransactionTemplate transactionTemplate;
//	@Autowired
//	BizLockManager bizLockManager;
//
//	@Override
//	public TradeOperResult doTransaction(final InnerRequest req) throws TradeException {
//
//		TradeOperResult transResult = transactionTemplate.execute(new TransactionCallback<TradeOperResult>() {
//
//			@Override
//			public TradeOperResult doInTransaction(TransactionStatus transactionStatus) {
//				TradeOperResult result  = new TradeOperResult();
//				result.setSuccess(true);
//				try{
//
//					OrderRequest orderRequest = req.getOrderRequest();
//					// 组装orderItemMap,便于后续使用, 其中key是itemSkuId
//					Map<Long, OrderItemDO> orderItemMap = null;
//					orderItemMap = genOrderItemMap(orderRequest.getOrderDTO(), orderRequest.getItemMap(),
//							orderRequest.getItemSkuMap(), req.getAppKey());
//
//					OrderItemDTO orderItemDTO = orderRequest.getOrderDTO().getOrderItems().get(0);
//
//					OrderDO preOrderDO  = orderManager.getPreOrder(req.getUserId(), orderItemDTO.getItemSkuId());
//					if(null!=preOrderDO){
//						OrderDTO responseOrderDTO = ModelUtil.convert2OrderDTO(preOrderDO);
//						result.setModule(responseOrderDTO);
//						return result;
//					}
//
//					Map<Long, List<Long>> sellerSkuMap = genSellerItemSkuMap(orderRequest.getOrderDTO());
//					OrderDO order = null;
//					order = convert2TotalOrderDO(sellerSkuMap, orderRequest.getOrderDTO(), orderRequest.getItemSkuMap(), null, 0l,
//							req.getBizCode());
//					System.out.println("order:" + order);
//					List<OrderItemDO> orderItemDOList = new ArrayList<OrderItemDO>();
//					for (Entry<Long, OrderItemDO> entry : orderItemMap.entrySet()) {
//						orderItemDOList.add(entry.getValue());
//					}
//					int type = 1;
//					boolean isAuction = PaymentUtil.isAuctionItem(orderItemDOList);
//					if(isAuction)
//						type = 4;
//					boolean isSeckill = PaymentUtil.isSeckillItem(orderItemDOList);
//					if(isSeckill)
//						type = 3;
//					order.setType(type);
//					order.setBizCode(req.getBizCode());
//
//					BizLockDO bizLockDO = new BizLockDO();
//					bizLockDO.setType(PaymentUtil.BizLockType.activity_type);
//					bizLockDO.setLockSn(order.getUserId()+"_"+order.getType()+"_"+orderItemDOList.get(0).getItemSkuId());
//					bizLockDO.setBizCode(order.getBizCode());
//					boolean bizLock = bizLockManager.bizLock(bizLockDO);
//					if(!bizLock){
//						 result.setSuccess(false);
//		                 result.setTradeException(new TradeException("biz lock error"));
//		                 return result;
//					}
//
//					Long orderId = orderManager.addPreOrder(order);
//					List<OrderItemDO> itemList = new ArrayList<OrderItemDO>();
//					for (Entry<Long, OrderItemDO> entry : orderItemMap.entrySet()) {
//						OrderItemDO orderItem = entry.getValue();
//						orderItem.setOrderId(orderId);
//						orderItem.setBizCode(req.getBizCode());
//						orderItem.setUserName(orderId + "");
//						orderItem.setDeliveryType(1);
//						itemList.add(orderItem);
//						orderItemManager.addOrderItem(orderItem);
//						// TODO ..冻结库存
//						itemPlatformManager.frozenStock(orderItem.getItemSkuId(), orderRequest.getOrderDTO().getSellerId(), 1,
//								req.getAppKey());
//
//					}
//					OrderDTO responseOrderDTO = ModelUtil.convert2OrderDTO(order);
//					result.setModule(responseOrderDTO);
//
//				}catch(TradeException e){
//					transactionStatus.setRollbackOnly();
//                    log.error("add order error", e);
//                    result.setSuccess(false);
//                    result.setTradeException(e);
//				}catch(Exception e){
//					transactionStatus.setRollbackOnly();
//                    log.error("add order error", e);
//                    result.setSuccess(false);
//                    result.setTradeException(new TradeException(e.getMessage()));
////                    return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
//				}
//
//
//				return result;
//			}
//
//
//		  });
//
//
//		return transResult;
//	}
//
//	private List<OrderItemDTO> getOrderItemsBySellerId(Long sellerId, Map<Long, List<Long>> sellerItemSkuMap,
//			Map<Long, OrderItemDO> orderItemMap) {
//		List<Long> skuIdList = sellerItemSkuMap.get(sellerId);
//		if (null == skuIdList && skuIdList.size() == 0) {
//			return Collections.EMPTY_LIST;
//		}
//		List<OrderItemDTO> orderLists = new ArrayList<OrderItemDTO>();
//		for (Long skuId : skuIdList) {
//			OrderItemDO orderItem = orderItemMap.get(skuId);
//			OrderItemDTO orderItemDTO = ModelUtil.convert2OrderItemDTO(orderItem);
//			orderLists.add(orderItemDTO);
//		}
//		return orderLists;
//	}
//
//	public Map<Long, OrderItemDO> genOrderItemMap(OrderDTO orderDTO, Map<Long, ItemDTO> itemMap,
//			Map<Long, ItemSkuDTO> itemSkuMap, String appKey) throws TradeException {
//		Map<Long, OrderItemDO> orderItemMap = new HashMap<Long, OrderItemDO>();
//		for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
//			ItemSkuDTO itemSkuDTO = itemSkuMap.get(orderItemDTO.getItemSkuId());
//
//			if (itemSkuDTO == null) { // 如果该商品在不存在
//				log.error("itemSku doesn't exist : " + orderItemDTO.getItemSkuId());
//				// return
//				// ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_NOT_EXIST,"itemSku
//				// doesn't exist");
//				throw new TradeException(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "itemSku doesn't exist");
//			}
//
//			ItemDTO itemDTO = itemMap.get(itemSkuDTO.getItemId());
//			if (itemDTO == null) {
//				log.error("item doesn't exist" + itemSkuDTO.getItemId());
//				// return
//				// ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_NOT_EXIST,"item
//				// doesn't exist");
//				throw new TradeException(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "item doesn't exist");
//			}
//
//			OrderItemDO orderItemDO = this.getOrderItemDO(orderDTO, orderItemDTO, itemSkuDTO, itemDTO, appKey);
//			orderItemMap.put(orderItemDTO.getItemSkuId(), orderItemDO);// 便于后续的优惠处理
//		}
//
//		return orderItemMap;
//	}
//
//	private OrderItemDO getOrderItemDO(OrderDTO orderDTO, OrderItemDTO orderItemDTO, ItemSkuDTO itemSkuDTO,
//			ItemDTO itemDTO, String appKey) {
//
//		OrderItemDO orderItemDO = new OrderItemDO();
//		orderItemDO.setUserId(orderDTO.getUserId());
//		orderItemDO.setSellerId(itemDTO.getSellerId());
//
//		// 查询买家名称
//		try {
//			orderItemDO.setUserName(userManager.getUserName(orderDTO.getUserId(), appKey));
//		} catch (TradeException e) {
//			// TODO error handle
//		}
//
//		int number = orderItemDTO.getNumber();
//		orderItemDO.setNumber(number);
//		orderItemDO.setItemName(itemDTO.getItemName());
//		orderItemDO.setItemSkuDesc(itemSkuDTO.getSkuCode());
//		orderItemDO.setUnitPrice(itemSkuDTO.getPromotionPrice());
//		orderItemDO.setItemSkuId(itemSkuDTO.getId());
//		orderItemDO.setItemId(itemSkuDTO.getItemId());
//		orderItemDO.setItemImageUrl(itemDTO.getIconUrl());
//		orderItemDO.setDeliveryType(itemDTO.getDeliveryType());
//		orderItemDO.setCategoryId(itemDTO.getCategoryId());
//		orderItemDO.setItemBrandId(itemDTO.getItemBrandId());
//		orderItemDO.setItemType(itemDTO.getItemType());
//		orderItemDO.setSupplierId(itemDTO.getSupplierId());
//
//		return orderItemDO;
//	}
//
//	private Map<Long, List<Long>> genSellerItemSkuMap(OrderDTO orderDTO) {
//		// 按照供应商将商品分组 用于商品平台查询商品信息
//		Map<Long, List<Long>> sellerItemSkuMap = new HashMap<Long, List<Long>>();
//		// 将下单商品列表按照供应商id分组
//		for (OrderItemDTO orderItem : orderDTO.getOrderItems()) {
//			Long skuId = orderItem.getItemSkuId();
//			if (sellerItemSkuMap.get(orderItem.getSellerId()) == null) {
//				List<Long> supplierItems = new ArrayList<Long>();
//				supplierItems.add(skuId);
//				sellerItemSkuMap.put(orderItem.getSellerId(), supplierItems);
//			} else {
//				sellerItemSkuMap.get(orderItem.getSellerId()).add(skuId);
//			}
//		}
//		return sellerItemSkuMap;
//	}
//
//	private long getOrderTotalPrice(OrderDTO orderDTO, Map<Long, ItemSkuDTO> itemSkuMap) {
//		List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItems();
//		long orderTotalPrice = 0L;
//		for (OrderItemDTO orderItemDTO : orderItemDTOs) {
//			Long skuId = orderItemDTO.getItemSkuId();
//			ItemSkuDTO itemSkuDTO = itemSkuMap.get(skuId);
//			if (itemSkuDTO == null) {
//				// TODO error handle
//			}
//
//			orderTotalPrice += (itemSkuDTO.getPromotionPrice() * orderItemDTO.getNumber());
//		}
//		return orderTotalPrice;
//	}
//
//	private OrderDO convert2TotalOrderDO(Map<Long, List<Long>> sellerSkuMap, OrderDTO orderDTO,
//			Map<Long, ItemSkuDTO> itemSkuMap, List<OrderDiscountInfoDO> orderDiscountInfoDOs, Long deliveryFee,
//			String bizCode) throws TradeException {
//		// 创建订单
//		long itemTotalPrice = getOrderTotalPrice(orderDTO, itemSkuMap);// 订单商品总价
//		String orderSn = null;
//		long userId = orderDTO.getUserId(); // 用户id
//		Long sellerId = orderDTO.getOrderItems().get(0).getSellerId();
//		if (sellerSkuMap.size() > 1) {
//			sellerId = 0L;
//		}
//
//		orderSn = orderSeqManager.getOrderSn(userId); // 按照订单号生成规则生成订单编号
//		OrderDO orderDO = ModelUtil.convert2OrderDO(orderDTO); // 将DTO转为DO
//		orderDO.setUserId(userId); // 买家ID
//		orderDO.setSellerId(sellerId);
//		orderDO.setOrderSn(orderSn);
//
//		Long totalDiscountAmount = 0l;
//
//		orderDO.setDiscountAmount(totalDiscountAmount);
//
//		orderDO.setDeliveryFee(deliveryFee);
//
//		// 订单总价等于（商品总价＋总运费）－总优惠金额
//		long totalAmount = (itemTotalPrice + deliveryFee) - totalDiscountAmount;
//		// 如果可使用优惠金额超过订单待付款金额，则将订单总金额设为0。并提示调用方
//		boolean needPay = true;
//		if (totalAmount <= 0) {
//			totalAmount = 0;
//			needPay = false;// 该订单无需再支付了
//		}
//		orderDO.setTotalAmount(totalAmount);
//		orderDO.setTotalPrice(itemTotalPrice);
//		orderDO.setBizCode(bizCode);
//
//		int deliveryId = 0;// 0代表卖家包邮，由卖家决定配送方式
//		if (orderDTO.getDeliveryId() != null) {
//			deliveryId = orderDTO.getDeliveryId();
//		}
//		orderDO.setDeliveryId(deliveryId);
//		orderDO.setType(1);// TODO
//
//		if (needPay == true) {
//			orderDO.setPaymentId(1);
//		} else {
//			// 无需支付的情况,paymentId置为0
//			orderDO.setPaymentId(0);
//		}
//
//		// 如果订单报销信息不为空的话，则设置订单报销标记
//		if (orderDTO.getOrderInvoiceDTO() != null) {
//			orderDO.setInvoiceMark(1);
//		} else {
//			orderDO.setInvoiceMark(0);
//		}
//		Long point = 0L;
//		Long pointAmount = 0L;
//		// 如果订单有可用优惠实体的话，则给订单设置优惠标志
//		if (orderDiscountInfoDOs != null && orderDiscountInfoDOs.isEmpty() == false) {
//			int discountMark = 0;
//			for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
//				if (orderDiscountInfoDO.getDiscountType() == 1) {// 营销活动
//					discountMark = (discountMark | 1);
//				} else if (orderDiscountInfoDO.getDiscountType() == 2) {//
//					discountMark = (discountMark | 2);
//					if (orderDiscountInfoDO.getDiscountCode().equals("2")) {
//						point += orderDiscountInfoDO.getPoint();
//						pointAmount += orderDiscountInfoDO.getDiscountAmount();
//					}
//				}
//			}
//			orderDO.setDiscountMark(discountMark);// 设置优惠标志
//		} else {
//			orderDO.setDiscountMark(0);
//		}
//		orderDO.setPoint(point);
//		orderDO.setPointDiscountAmount(pointAmount);
//
//		return orderDO;
//	}
//
//}
