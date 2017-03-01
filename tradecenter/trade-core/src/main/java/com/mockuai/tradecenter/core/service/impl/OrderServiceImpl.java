package com.mockuai.tradecenter.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import com.mockuai.tradecenter.core.manager.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.domain.OrderServiceDO;
import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.listener.OrderListener;
import com.mockuai.tradecenter.core.service.OrderService;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.TradeUtil;

public class OrderServiceImpl extends BaseService implements OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Resource
	private DozerBeanService dozerBeanService;

	@Resource
	private ItemManager itemManager;

	@Resource
	private UserManager userManager;

	@Resource
	private OrderManager orderManager;

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private MarketingManager marketingManager;

	@Resource
	private VirtualWealthManager virtualWealthManager;

	@Resource
	private OrderListener commisionOrderListener;

	@Resource
	private UserCartItemManager userCartItemManager;

	@Resource
	ExecutorService       executor;

   @Resource
    private OrderPaymentManager orderPaymentManager;

   @Resource
   private OrderItemManager orderItemManager;

   @Resource
   DataManager dataManager;

   @Resource
   MsgQueueManager msgQueueManager;

   @Resource
   OrderDiscountInfoManager orderDiscountInfoMng;


	@Override
	public Map<Long, List<OrderItemDO>> genOrderItemDOsMap(OrderDTO orderDTO, Map<Long, ItemDTO> itemMap,
			Map<Long, ItemSkuDTO> itemSkuMap, String appKey, Map<Long, ActivityItemDTO> activityItemMap) throws TradeException {

		Map<Long, List<OrderItemDO>> orderItemListMap = new HashMap<Long, List<OrderItemDO>>();
		for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
			ItemSkuDTO itemSkuDTO = itemSkuMap.get(orderItemDTO.getItemSkuId());

			if (itemSkuDTO == null) { // 如果该商品在不存在
				throw new TradeException(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "itemSku doesn't exist");
			}

			ItemDTO itemDTO = itemMap.get(itemSkuDTO.getItemId());
			if (itemDTO == null) {
				throw new TradeException(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "item doesn't exist");
			}

			OrderItemDO orderItemDO = this.getOrderItemDO(orderDTO, orderItemDTO, itemSkuDTO, itemDTO, appKey);

			ActivityItemDTO activityItemDTO = activityItemMap.get(orderItemDO.getItemSkuId());
			if (null != activityItemDTO) {
				orderItemDO.setUnitPrice(activityItemDTO.getUnitPrice());
			}

			add2OrderItemListMap(orderItemListMap, orderItemDO);
			// TODO ....增加套装子商品的查询

			if (itemDTO.getItemType().intValue() == DBConst.SUIT_ITEM.getCode()) {
				orderItemDTO.setItemType(DBConst.SUIT_ITEM.getCode());
				ItemDTO suitItemDTO = itemManager.getSuitItem(itemDTO.getSellerId(), itemDTO.getId(), appKey);

				if (null != suitItemDTO && suitItemDTO.getSubItemList().size() > 0) {
					for (ItemDTO subItemDTO : suitItemDTO.getSubItemList()) {
						itemMap.put(subItemDTO.getId(), subItemDTO);

						List<Long> subSkuIds = new ArrayList<Long>();
						for (ItemSkuDTO subItemSkuDTO : subItemDTO.getItemSkuDTOList()) {
							subSkuIds.add(subItemSkuDTO.getId());
						}
						List<ItemSkuDTO> subItemSkuDTOList = itemManager.queryItemSku(subSkuIds,
								subItemDTO.getSellerId(), appKey);

						if (null == subItemSkuDTOList) {
							throw new TradeException("subItemSkuDTOList is null");
						}
						ItemSkuDTO subItemSkuDTO = subItemSkuDTOList.get(0);

						itemSkuMap.put(subItemSkuDTO.getId(), subItemSkuDTO);

						if (null != subItemSkuDTOList && subItemSkuDTOList.size() > 0) {
							OrderItemDO subOrderItemDO = this.getSubOrderItemDO(orderDTO, subItemSkuDTOList.get(0),
									subItemDTO, appKey);

							subOrderItemDO.setOriginalSkuId(itemSkuDTO.getId());

							add2OrderItemListMap(orderItemListMap, subOrderItemDO);
						}

					}
				}
			}

			if (orderItemDTO.getActivityId() != null) {
				List<OrderItemDO> subOrderItemDOList = getSubOrderItemList(orderDTO, orderItemDTO.getItemSkuId(),
						orderItemDTO.getItemList(), orderItemDTO.getSellerId(), appKey);
				if (null != subOrderItemDOList && !subOrderItemDOList.isEmpty()) {
					for (OrderItemDO subOrderItemDO : subOrderItemDOList) {
						if (null == orderItemListMap.get(subOrderItemDO.getItemSkuId())) {
							add2OrderItemListMap(orderItemListMap, subOrderItemDO);
						}

					}
				}
			}

		}

		return orderItemListMap;

	}

	private OrderItemDO getOrderItemDO(OrderDTO orderDTO, OrderItemDTO orderItemDTO, ItemSkuDTO itemSkuDTO,
			ItemDTO itemDTO, String appKey) {

		OrderItemDO orderItemDO = new OrderItemDO();
		orderItemDO.setUserId(orderDTO.getUserId());
		orderItemDO.setSellerId(itemDTO.getSellerId());

		// 查询买家名称
		try {
			orderItemDO.setUserName(userManager.getUserName(orderDTO.getUserId(), appKey));
		} catch (TradeException e) {
			// TODO error handle
		}

		Long itemServicePrice = TradeUtil.getItemServicePrice(orderItemDTO);
		if (null == itemServicePrice) {
			itemServicePrice = 0L;
		}

		int number = orderItemDTO.getNumber();
		orderItemDO.setNumber(number);
		orderItemDO.setItemType(itemDTO.getItemType());
		orderItemDO.setItemName(itemDTO.getItemName());
		orderItemDO.setItemSkuDesc(itemSkuDTO.getSkuCode());
		orderItemDO.setUnitPrice(itemSkuDTO.getPromotionPrice());
		orderItemDO.setServiceUnitPrice(itemServicePrice);
		orderItemDO.setItemSkuId(itemSkuDTO.getId());
		orderItemDO.setItemId(itemSkuDTO.getItemId());
		orderItemDO.setItemImageUrl(itemDTO.getIconUrl());
		orderItemDO.setDeliveryType(itemDTO.getDeliveryType());
		orderItemDO.setCategoryId(itemDTO.getCategoryId());
		orderItemDO.setItemBrandId(itemDTO.getItemBrandId());
		orderItemDO.setActivityId(orderItemDTO.getActivityId());
		if (null != orderItemDTO.getOrderServiceList() && orderItemDTO.getOrderServiceList().size() != 0) {
			orderItemDO.setOrderServiceDOList(
					dozerBeanService.coverList(orderItemDTO.getOrderServiceList(), OrderServiceDO.class));
		}

		return orderItemDO;
	}

	private void add2OrderItemListMap(Map<Long, List<OrderItemDO>> orderItemListMap, OrderItemDO orderItemDO) {
		List<OrderItemDO> orderItemDOList = orderItemListMap.get(orderItemDO.getItemSkuId());
		if (null == orderItemDOList) {
			orderItemDOList = new ArrayList<OrderItemDO>();
			orderItemDOList.add(orderItemDO);
			orderItemListMap.put(orderItemDO.getItemSkuId(), orderItemDOList);
		} else {
			orderItemDOList.add(orderItemDO);
		}
	}

	private OrderItemDO getSubOrderItemDO(OrderDTO orderDTO, ItemSkuDTO itemSkuDTO, ItemDTO itemDTO, String appKey) {

		OrderItemDO orderItemDO = new OrderItemDO();
		orderItemDO.setUserId(orderDTO.getUserId());
		orderItemDO.setSellerId(itemDTO.getSellerId());

		// 查询买家名称
		try {
			orderItemDO.setUserName(userManager.getUserName(orderDTO.getUserId(), appKey));
		} catch (TradeException e) {
			// TODO error handle
		}

		orderItemDO.setNumber(1);
		orderItemDO.setItemType(itemDTO.getItemType());
		orderItemDO.setItemName(itemDTO.getItemName());
		orderItemDO.setItemSkuDesc(itemSkuDTO.getSkuCode());
		if (itemSkuDTO.getSkuCode() == null) {
			orderItemDO.setItemSkuDesc(itemDTO.getItemName());
		}
		orderItemDO.setUnitPrice(itemSkuDTO.getPromotionPrice());
		orderItemDO.setItemSkuId(itemSkuDTO.getId());
		orderItemDO.setItemId(itemSkuDTO.getItemId());
		orderItemDO.setItemImageUrl(itemDTO.getIconUrl());
		orderItemDO.setDeliveryType(itemDTO.getDeliveryType());
		orderItemDO.setCategoryId(itemDTO.getCategoryId());
		orderItemDO.setItemBrandId(itemDTO.getItemBrandId());

		return orderItemDO;
	}

	private List<OrderItemDO> getSubOrderItemList(OrderDTO orderDTO, Long parentSkuId, List<OrderItemDTO> orderItemList,
			Long sellerId, String appKey) throws TradeException {

		if (null == orderItemList) {
			return null;
		}

		Map<Long, ItemSkuDTO> itemSkuMap = new HashMap<Long, ItemSkuDTO>();
		Map<Long, ItemDTO> itemMap = new HashMap<Long, ItemDTO>();

		List<Long> skuIdList = new ArrayList<Long>();
		List<Long> itemIdList = new ArrayList<Long>();
		for (OrderItemDTO orderItemDTO : orderItemList) {
			skuIdList.add(orderItemDTO.getItemSkuId());
		}

		// 根据卖家ID和商品SKU ID列表来查询商品SKU信息
		List<ItemSkuDTO> itemSkus = this.itemManager.queryItemSku(skuIdList, sellerId, appKey);
		if (itemSkus == null | itemSkus.size() == 0) {
			throw new TradeException(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "itemSku doesn't exist");
		}
		for (ItemSkuDTO itemSkuDTO : itemSkus) {
			itemIdList.add(itemSkuDTO.getItemId());
		}

		// 根据卖家ID和商品ID列表查询商品信息
		List<ItemDTO> items = this.itemManager.queryItem(itemIdList, sellerId, appKey);
		if (items == null | items.size() == 0) {
			throw new TradeException(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "item doesn't exist");
		}

		// 将商品平台查询返回的商品信息封装为 Map 方便处理
		for (ItemSkuDTO itemSku : itemSkus) {
			itemSkuMap.put(itemSku.getId(), itemSku);

		}

		for (ItemDTO item : items) {
			itemMap.put(item.getId(), item);
		}
		List<OrderItemDO> returnList = new ArrayList<OrderItemDO>();
		for (OrderItemDTO orderItemDTO : orderItemList) {
			ItemSkuDTO itemSkuDTO = null;
			itemSkuDTO = itemSkuMap.get(orderItemDTO.getItemSkuId());
			ItemDTO itemDTO = null;
			if (null != itemSkuDTO)
				itemDTO = itemMap.get(itemSkuDTO.getItemId());
			if (null != itemSkuDTO && null != itemDTO) {
				OrderItemDO orderItemDO = this.getOrderItemDO(orderDTO, orderItemDTO, itemSkuDTO, itemDTO, appKey);
				orderItemDO.setOriginalSkuId(parentSkuId);
				// orderItemDO.setUnitPrice(orderItemDTO.getUnitPrice());
				returnList.add(orderItemDO);
			}
		}
		return returnList;

	}

	private boolean checkNoNeedPay(OrderDO oriOrderDO,  List<OrderTogetherDTO> orderTogetherList){

		log.info("oriOrderDO:"+JSONObject.toJSONString(oriOrderDO));

		log.info("orderTogetherList:"+JSONObject.toJSONString(orderTogetherList));

		long discountAmt = 0l;
		long totalAmt = 0l;
		for(OrderTogetherDTO orderTogetherDTO:orderTogetherList){
//			discountAmt+=orderTogetherDTO.getOrderDO().getDiscountAmount();
			discountAmt+=TradeUtil.getDiscountAmount(orderTogetherDTO);
			totalAmt+=orderTogetherDTO.getOrderDO().getTotalPrice()+orderTogetherDTO.getOrderDO().getDeliveryFee();
		}
		discountAmt+=oriOrderDO.getDiscountAmount();
		if(discountAmt>=totalAmt){
			return true;
		}
		return false;
	}



	@Override
	public void paySubOrderSuccess(List<OrderDO> orders, String outTradeNo, String appKey) {
		    	try{
		    		if(null!=orders&&orders.isEmpty()==false){
		    			for(OrderDO order:orders){

		    				orderManager.orderPaySuccess(order.getId(),null, order.getUserId());


		    				//更新支付单信息
		                    OrderPaymentDO orderPaymentDO = orderPaymentManager.getOrderPayment(order.getId(),order.getUserId());
		                    if(orderPaymentDO == null){
		                        //TODO error handle
		                    	continue;
		                    }

		                    orderPaymentManager.paySuccess(orderPaymentDO.getId(), orderPaymentDO.getUserId(),
		                    		orderPaymentDO.getPayAmount(), outTradeNo);


		                    //判断订单是否有使用优惠券和虚拟财富，如果有使用，则需要调用营销接口最终确认使用优惠券和虚拟财富
		                    Integer discountMark = order.getDiscountMark();
		                    if((discountMark.intValue() & 1) == 1){//从左往右，第一个二进制位代表优惠活动标志
		                        boolean releaseResult = marketingManager.useUserCoupon(order.getUserId(), order.getId(), appKey);
		                        if(releaseResult == false){
		                            throw new TradeException(ResponseCode.BIZ_E_USE_COUPON_ERROR);
		                        }
		                    }

		                    if((discountMark.intValue() & 2) == 2){//从左往右，第二个二进制位代表虚拟财富标志
		                        boolean releaseResult = virtualWealthManager.useUserWealth(order.getUserId(), order.getId(), appKey);
		                        if(releaseResult == false){
		                            throw new TradeException(ResponseCode.BIZ_E_USE_WEALTH_ERROR);
		                        }
		                    }


		                    if(order.getType().intValue()==3||order.getType().intValue()==4){
		                    	OrderItemQTO orderItemQuery = new OrderItemQTO();
		    					orderItemQuery.setOrderId(order.getId());
		    					orderItemQuery.setUserId(order.getUserId());
		    					List<OrderItemDO> orderItemDOList = orderItemManager.queryOrderItem(orderItemQuery);
		    					if(null!=orderItemDOList){
                                    //TODO 暂时先注释掉，需要立即抽空调整逻辑 add by caishen on 2016-05-26
//		    						 itemManager.crushItemSkuStock(orderItemDOList.get(0).getItemSkuId(),
//		    								 order.getSellerId(), orderItemDOList.get(0).getNumber(), appKey);
		    					}
		                    }

		                    //
		                    userManager.addSellerUserRelate(order.getUserId(), order.getSellerId(),
		                    		order.getId(),"paid", order.getTotalAmount(), appKey);

							//发送支付成功内部mq消息
							msgQueueManager.sendPaySuccessMsg(order);
		    			}

		            }

		    	}catch(Exception e){
		    		log.error("paySubOrderSuccess error",e);
		    	}
	}

	@Override
	public TradeResponse<OrderDTO> addOrder(RequestContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
