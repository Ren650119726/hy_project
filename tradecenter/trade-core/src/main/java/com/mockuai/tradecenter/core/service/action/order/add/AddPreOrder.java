package com.mockuai.tradecenter.core.service.action.order.add;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO.OrderSku;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.core.domain.BizLockDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.BizLockManager;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.OrderSeqManager;
import com.mockuai.tradecenter.core.manager.SupplierManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.ModelUtil;
import com.mockuai.tradecenter.core.util.PaymentUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;

@Service
public class AddPreOrder  implements Action {

    private static final Logger log = LoggerFactory.getLogger(AddPreOrder.class);


  
    @Autowired
    OrderManager orderManager;
    
    @Resource
    ItemManager itemManager;
    
    @Resource
    SupplierManager supplierManager;
    
	@Resource
	OrderSeqManager orderSeqManager;
	
	@Resource
	OrderItemManager orderItemManager;
	
	@Autowired
	BizLockManager bizLockManager;

    @Resource
    private TransactionTemplate transactionTemplate;


    public String getName() {
        return ActionEnum.ADD_PRE_ORDER.getActionName();
    }
    
    
    
    @Override
    public TradeResponse<OrderDTO> execute(final RequestContext context) {    	
    	
    	log.info(" AddPreOrder start ");
    	
        Request request = context.getRequest();

		final String appKey = (String) context.get("appKey");
		final String bizCode = (String) context.get("bizCode");
		
        if (request.getParam("orderDTO") == null) {
            log.error("orderDTO is null");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderDTO is null");
        }
        
        
        final OrderDTO orderDTO = (OrderDTO) request.getParam("orderDTO");
        if(null==orderDTO.getUserId()){
        	return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
        }
        if(null==orderDTO.getSellerId()){
        	return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "sellerId is null");
        }
        
        if(null==orderDTO.getActivityId()){
        	return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "activityId is null");
        }
        
        if(null==orderDTO.getTimeoutCancelSeconds()){
        	return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "timeoutCancelSecoundNumber is null");
        }
        
        if (orderDTO.getOrderItems() == null) {
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderItems is null");
		} else if (orderDTO.getOrderItems().size() == 0) {
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderItems is empty");
		}

        List<OrderItemDTO> orderItemList = orderDTO.getOrderItems();
        
        Long skuId = orderItemList.get(0).getItemSkuId();
        
//        log.info(" orderDTO 【"+JSONObject.toJSONString(orderDTO)+"】");
        
        OrderDO preOrderDO;
		try {
			OrderItemQTO query = new OrderItemQTO();
    		query.setUserId(orderDTO.getUserId());
    		query.setItemSkuId(skuId);       
    		query.setDeleteMark(0);              	
        	
        	preOrderDO = orderManager.getPreOrderByItemQTO(query);
//			preOrderDO = orderManager.getPreOrder(orderDTO.getUserId(), skuId);
			if(null!=preOrderDO){
				log.error(" pre order has exist ");
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "pre order has exist");
			}
		} catch (TradeException e) {
			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION, e.getMessage());
		}
		
		TradeResponse transResult = transactionTemplate.execute(new TransactionCallback<TradeResponse>() {

			@Override
			public TradeResponse doInTransaction(TransactionStatus transactionStatus) {

				try{
										
					// 按照卖家将商品分组 用于商品平台查询商品信息 (sellerId对skuId进行分组)
			        Map<Long, List<Long>> sellerItemSkuMap = genSellerItemSkuMap(orderDTO);

			        // sku集合
			        List<ItemSkuDTO> itemSkuList = new ArrayList<ItemSkuDTO>();
			        // item集合
			        List<ItemDTO> itemList = new ArrayList<ItemDTO>();
			        // TODO 后续考虑使用并发的方式
			        // 根据卖家ID分批查询商品sku信息和商品信息（由于商品sku表和商品表是基于卖家ID做分表的，所以这里需要分批查询）
			        try {
			            for (Map.Entry<Long, List<Long>> entry : sellerItemSkuMap.entrySet()) {
			                Long sellerId = entry.getKey();
			                List<Long> skuIdList = entry.getValue();
			                // 根据卖家ID和商品SKU ID列表来查询商品SKU信息
			                List<ItemSkuDTO> itemSkus = itemManager.queryItemSku(skuIdList, sellerId, appKey);
			                if (itemSkus == null | itemSkus.size() == 0) {
			                	log.error("itemSku is null : " + skuIdList + "," + sellerId);
			                    return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "itemSku doesn't exist");
			                }

			                // 将查询到的商品SKU信息添加到SKU总列表中
			                itemSkuList.addAll(itemSkus);
			                List<Long> itemIdList = new ArrayList<Long>();
			                for (ItemSkuDTO itemSku : itemSkuList) {
			                    itemIdList.add(itemSku.getItemId());
			                }

			                // 根据卖家ID和商品ID列表查询商品信息
//			                log.info(" >>>>>>> itemIdList : "+JSONObject.toJSONString(itemIdList));
			                ItemQTO itemQTO = new ItemQTO();
			                itemQTO.setIdList(itemIdList);
			                itemQTO.setSellerId(sellerId);
			                itemQTO.setItemType(13);
			                List<ItemDTO> items = itemManager.queryItem(itemQTO, appKey);
//			                List<ItemDTO> items = itemManager.queryItem(itemIdList, sellerId, appKey);
			                if (items == null | items.size() == 0) {
			                    log.error("Item is null" + itemIdList + " " + sellerId);
			                    return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "item doesn't exist");
			                }

			                // 将查询到的商品信息添加到商品总列表中
			                itemList.addAll(items);
			            }
			        } catch (TradeException e) {
			        	log.error("", e);
			            return ResponseUtils.getFailResponse(e.getResponseCode(), e.getMessage());
			        }

			        // 将商品平台查询返回的商品信息封装为 Map 方便处理 
			        // sku map<skuid,skudto> 
			        Map<Long, ItemSkuDTO> itemSkuMap = new HashMap<Long, ItemSkuDTO>();
			        for (ItemSkuDTO itemSku : itemSkuList) {
			            itemSkuMap.put(itemSku.getId(), itemSku);
			        }

			        // spu map<spuid,spudto>
			        Map<Long, ItemDTO> itemMap = new HashMap<Long, ItemDTO>();
			        for (ItemDTO item : itemList) {
			            itemMap.put(item.getId(), item);
			        }

			        //封装orderItemListMap和orderItemList，方便后续处理
			        Map<Long, List<OrderItemDO>> orderItemListMap = Collections.EMPTY_MAP;
			        List<OrderItemDO> orderItemDOList = Collections.EMPTY_LIST;
			        try {
			            orderItemListMap = genOrderItemListMap(orderDTO, itemMap, itemSkuMap);
			            orderItemDOList = genOrderItemList(orderDTO, itemMap, itemSkuMap);
			        } catch (TradeException e) {
			        	log.error("", e);
			            return ResponseUtils.getFailResponse(e.getResponseCode());
			        }

			        //过滤订单商品列表的相关信息
			        filterOrderItemDTOList(orderDTO, itemMap, itemSkuMap);
					
					log.info(" AddPreOrder orderDTO : "+ JSONObject.toJSONString(orderDTO));
			        
			        OrderDO order = null;
					order = convert2TotalOrderDO(sellerItemSkuMap, orderDTO, itemSkuMap, null, 0l, bizCode);
					
					log.info(" AddPreOrder order : "+ JSONObject.toJSONString(order));
					
					int type = 1;
					boolean isSeckill = PaymentUtil.isSeckillItem(orderItemDOList);
					if(isSeckill)
						type = 3;
					order.setType(type);
					order.setBizCode(bizCode);
					
					
					
					/*实现资源锁，防止同一个用户，在同一时间段，针对同一个商品进行多次秒杀实现资源锁*/
					BizLockDO bizLockDO = new BizLockDO();
					bizLockDO.setType(PaymentUtil.BizLockType.activity_type);
					bizLockDO.setLockSn(order.getUserId()+"_"+order.getType()+"_"+orderItemDOList.get(0).getItemSkuId());
					bizLockDO.setBizCode(order.getBizCode());
					boolean bizLock = bizLockManager.bizLock(bizLockDO);
					if(!bizLock){
						return ResponseUtils.getFailResponse(ResponseCode.SYS_E_DEFAULT_ERROR,"biz lock error");
					}
					
					
					//冻结商品库存新
					OrderStockDTO orderStockDTO = new OrderStockDTO();
					orderStockDTO.setOrderSn(order.getOrderSn());
					orderStockDTO.setSellerId(order.getSellerId());
					List<OrderSku> orderSkus = new ArrayList<OrderSku>();
					for(OrderItemDO orderItemDO :orderItemDOList){
						OrderSku orderSku = new OrderSku();						
						orderSku.setSkuId(orderItemDO.getItemSkuId());
						orderSku.setNumber(orderItemDO.getNumber());
						orderSkus.add(orderSku);
					}
					orderStockDTO.setOrderSkuList(orderSkus);
		            OrderStockDTO orderStock = supplierManager.freezeOrderSkuStock(orderStockDTO, appKey);
					
		            order.setStoreId(orderStock.getOrderSkuList().get(0).getStoreId());
		            order.setSupplierId(orderStock.getOrderSkuList().get(0).getSupplierId());
		            
		            //订单主表
					Long orderId = orderManager.addPreOrder(order);
					
					//订单商品表
					for(OrderItemDO orderItemTmp :orderItemDOList){
						
						orderItemTmp.setOrderId(orderId);
						orderItemTmp.setBizCode(bizCode);
						orderItemTmp.setUserName(orderId + "");
						orderItemTmp.setDeliveryType(1);
						orderItemTmp.setDistributorName(orderItemTmp.getDistributorId()+"");
						
						orderItemManager.addOrderItem(orderItemTmp);
						
					}
					
					//冻结商品库存
//		            OrderStockDTO orderStock = itemManager.frozenStock(order.getOrderSn(),
//		                    orderDTO.getUserId(), orderItemDOList, appKey);
					
							            
		            OrderDTO responseOrderDTO = ModelUtil.convert2OrderDTO(order);
					
			        TradeResponse response = ResponseUtils.getSuccessResponse(responseOrderDTO);
                    return response;
					
					
				}catch(TradeException e){
	                //回滚事务
	                transactionStatus.setRollbackOnly();
	                log.error("", e);
	                return ResponseUtils.getFailResponse(e.getResponseCode());
	            }
				catch(Exception e){
					transactionStatus.setRollbackOnly();
                    log.error("add order error", e);
                    return  new TradeResponse(e);
				}
				
			}


		  });
		 

    	log.info(" AddPreOrder end ");


        return transResult;

    }

	private long getOrderTotalPrice(OrderDTO orderDTO, Map<Long, ItemSkuDTO> itemSkuMap) {
		List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItems();
		long orderTotalPrice = 0L;
		for (OrderItemDTO orderItemDTO : orderItemDTOs) {
			Long skuId = orderItemDTO.getItemSkuId();
			ItemSkuDTO itemSkuDTO = itemSkuMap.get(skuId);
			if (itemSkuDTO == null) {
				// TODO error handle
			}

			orderTotalPrice += (itemSkuDTO.getPromotionPrice() * orderItemDTO.getNumber());
		}
		return orderTotalPrice;
	}
  
    private OrderDO convert2TotalOrderDO(Map<Long, List<Long>> sellerSkuMap, OrderDTO orderDTO,
			Map<Long, ItemSkuDTO> itemSkuMap, List<OrderDiscountInfoDO> orderDiscountInfoDOs, Long deliveryFee,
			String bizCode) throws TradeException {
		// 创建订单
		long itemTotalPrice = getOrderTotalPrice(orderDTO, itemSkuMap);// 订单商品总价 (促销价*数量)
		String orderSn = null;
		long userId = orderDTO.getUserId(); // 用户id
		Long sellerId = orderDTO.getOrderItems().get(0).getSellerId();
		if (sellerSkuMap.size() > 1) {
			sellerId = 0L;
		}

		orderSn = orderSeqManager.getOrderSn(userId); // 按照订单号生成规则生成订单编号
		OrderDO orderDO = ModelUtil.convert2OrderDO(orderDTO); // 将DTO转为DO
		orderDO.setUserId(userId); // 买家ID
		orderDO.setSellerId(sellerId);
		orderDO.setOrderSn(orderSn);

		Long totalDiscountAmount = 0l;

		orderDO.setDiscountAmount(totalDiscountAmount);

		orderDO.setDeliveryFee(deliveryFee);

		// 订单总价等于（商品总价＋总运费）－总优惠金额
		long totalAmount = (itemTotalPrice + deliveryFee) - totalDiscountAmount;
		// 如果可使用优惠金额超过订单待付款金额，则将订单总金额设为0。并提示调用方
		boolean needPay = true;
		if (totalAmount <= 0) {
			totalAmount = 0;
			needPay = false;// 该订单无需再支付了
		}
		orderDO.setTotalAmount(totalAmount);
		orderDO.setTotalPrice(itemTotalPrice);
		orderDO.setBizCode(bizCode);

//		int deliveryId = 0;// 0代表卖家包邮，由卖家决定配送方式
		int deliveryId = 1;// 1代表卖家包邮，默认为快递邮寄
		if (orderDTO.getDeliveryId() != null) {
			deliveryId = orderDTO.getDeliveryId();
		}
		orderDO.setDeliveryId(deliveryId);
		orderDO.setType(1);// TODO

		if (needPay == true) {
			orderDO.setPaymentId(1);
		} else {
			// 无需支付的情况,paymentId置为0
			orderDO.setPaymentId(0);
		}

		// 如果订单报销信息不为空的话，则设置订单报销标记
		if (orderDTO.getOrderInvoiceDTO() != null) {
			orderDO.setInvoiceMark(1);
		} else {
			orderDO.setInvoiceMark(0);
		}
		Long point = 0L;
		Long pointAmount = 0L;
		// 如果订单有可用优惠实体的话，则给订单设置优惠标志
		if (orderDiscountInfoDOs != null && orderDiscountInfoDOs.isEmpty() == false) {
			int discountMark = 0;
			for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
				if (orderDiscountInfoDO.getDiscountType() == 1) {// 营销活动
					discountMark = (discountMark | 1);
				}
//				else if (orderDiscountInfoDO.getDiscountType() == 2) {//
//					discountMark = (discountMark | 2);
//					if (orderDiscountInfoDO.getDiscountCode().equals("2")) {
//						point += orderDiscountInfoDO.getPoint();
//						pointAmount += orderDiscountInfoDO.getDiscountAmount();
//					}
//				}
			}
			orderDO.setDiscountMark(discountMark);// 设置优惠标志
		} else {
			orderDO.setDiscountMark(0);
		}
		orderDO.setPoint(point);
		orderDO.setPointDiscountAmount(pointAmount);

		return orderDO;
	}


    /**
     * 过滤订单商品列表的核心信息：
     * （1）根据服务端查询到的商品价格来填充orderItem里的价格属性，以防止客户端调用时作弊，设置非法价格
     * @param orderDTO
     * @param itemMap
     * @param itemSkuMap
     */
    private void filterOrderItemDTOList(OrderDTO orderDTO, Map<Long, ItemDTO> itemMap,
                                      Map<Long, ItemSkuDTO> itemSkuMap){
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            ItemSkuDTO itemSkuDTO = itemSkuMap.get(orderItemDTO.getItemSkuId());
            //FIXME 填充商品的现价
            orderItemDTO.setUnitPrice(itemSkuDTO.getPromotionPrice());

            //TODO 填充订单商品的用户名，后续有机会重构的话，可以去掉订单商品中存的userName信息，订单上面存该信息即可
            orderItemDTO.setUserName("default");
        }

    }



    private List<OrderItemDO> genOrderItemList(
            OrderDTO orderDTO, Map<Long, ItemDTO> itemMap, Map<Long, ItemSkuDTO> itemSkuMap) throws TradeException {
        List<OrderItemDO> orderItemDOList = new ArrayList<OrderItemDO>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            ItemSkuDTO itemSkuDTO = itemSkuMap.get(orderItemDTO.getItemSkuId());
            ItemDTO itemDTO = itemMap.get(itemSkuDTO.getItemId());

            //TODO 这里的用户名需要填充
            OrderItemDO orderItemDO = TradeUtil.genOrderItemDO(orderDTO, orderItemDTO, itemSkuDTO, itemDTO, "default");
            orderItemDOList.add(orderItemDO);
        }

        return orderItemDOList;
    }


    private Map<Long, List<OrderItemDO>> genOrderItemListMap(
            OrderDTO orderDTO, Map<Long, ItemDTO> itemMap, Map<Long, ItemSkuDTO> itemSkuMap) throws TradeException {
        Map<Long, List<OrderItemDO>> orderItemListMap = new HashMap<Long, List<OrderItemDO>>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            ItemSkuDTO itemSkuDTO = itemSkuMap.get(orderItemDTO.getItemSkuId());
            ItemDTO itemDTO = itemMap.get(itemSkuDTO.getItemId());

            //TODO 这里的用户名需要填充
            OrderItemDO orderItemDO = TradeUtil.genOrderItemDO(orderDTO, orderItemDTO, itemSkuDTO, itemDTO, "default");
            add2OrderItemListMap(orderItemListMap,orderItemDO);
        }

        return orderItemListMap;
    }

    private Map<Long, List<Long>> genSellerItemSkuMap(OrderDTO orderDTO) {
        // 按照供应商将商品分组 用于商品平台查询商品信息
        Map<Long, List<Long>> sellerItemSkuMap = new HashMap<Long, List<Long>>();
        // 将下单商品列表按照供应商id分组
        for (OrderItemDTO orderItem : orderDTO.getOrderItems()) {
            Long skuId = orderItem.getItemSkuId();
            if (sellerItemSkuMap.get(orderItem.getSellerId()) == null) {
                List<Long> supplierItems = new ArrayList<Long>();
                supplierItems.add(skuId);
                sellerItemSkuMap.put(orderItem.getSellerId(), supplierItems);
            } else {
                sellerItemSkuMap.get(orderItem.getSellerId()).add(skuId);
            }
        }

        return sellerItemSkuMap;
    }
    


    private void add2OrderItemListMap(Map<Long, List<OrderItemDO>> orderItemListMap,OrderItemDO orderItemDO){
        List<OrderItemDO> orderItemDOList = orderItemListMap.get(orderItemDO.getItemSkuId());
        if(null==orderItemDOList){
            orderItemDOList = new ArrayList<OrderItemDO>();
            orderItemDOList.add(orderItemDO);
            orderItemListMap.put(orderItemDO.getItemSkuId(), orderItemDOList);
        }else{
            orderItemDOList.add(orderItemDO);
        }
    }


}
