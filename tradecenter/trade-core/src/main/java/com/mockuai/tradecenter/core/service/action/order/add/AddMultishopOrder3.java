//package com.mockuai.tradecenter.core.service.action.order.add;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.alibaba.fastjson.JSONObject;
//import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
//import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
//import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
//import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;
//import com.mockuai.marketingcenter.common.constant.MarketLevel;
//import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
//import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
//import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
//import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
//import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
//import com.mockuai.marketingcenter.common.domain.dto.MultiSettlementInfo;
//import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
//import com.mockuai.marketingcenter.common.domain.dto.ShopSettlementInfo;
//import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
//import com.mockuai.shopcenter.domain.dto.StoreDTO;
//import com.mockuai.tradecenter.common.api.Request;
//import com.mockuai.tradecenter.common.api.TradeResponse;
//import com.mockuai.tradecenter.common.constant.ActionEnum;
//import com.mockuai.tradecenter.common.constant.ResponseCode;
//import com.mockuai.tradecenter.common.domain.ItemServiceDTO;
//import com.mockuai.tradecenter.common.domain.OrderDTO;
//import com.mockuai.tradecenter.common.domain.OrderItemDTO;
//import com.mockuai.tradecenter.common.domain.OrderServiceDTO;
//import com.mockuai.tradecenter.common.domain.UsedCouponDTO;
//import com.mockuai.tradecenter.common.domain.UsedWealthDTO;
//import com.mockuai.tradecenter.common.enums.EnumSubTransCode;
//import com.mockuai.tradecenter.common.util.MoneyUtil;
//import com.mockuai.tradecenter.core.base.TradeInnerOper;
//import com.mockuai.tradecenter.core.base.factory.TradeInnerFactory;
//import com.mockuai.tradecenter.core.base.request.InnerRequest;
//import com.mockuai.tradecenter.core.base.request.ItemRequest;
//import com.mockuai.tradecenter.core.base.result.TradeOperResult;
//import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
//import com.mockuai.tradecenter.core.domain.OrderDO;
//import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
//import com.mockuai.tradecenter.core.domain.OrderItemDO;
//import com.mockuai.tradecenter.core.domain.OrderStoreDO;
//import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.ItemManager;
//import com.mockuai.tradecenter.core.manager.MarketingManager;
//import com.mockuai.tradecenter.core.manager.OrderManager;
//import com.mockuai.tradecenter.core.manager.OrderSeqManager;
//import com.mockuai.tradecenter.core.manager.StoreManager;
//import com.mockuai.tradecenter.core.manager.UserManager;
//import com.mockuai.tradecenter.core.service.OrderService;
//import com.mockuai.tradecenter.core.service.RequestContext;
//import com.mockuai.tradecenter.core.service.ResponseUtils;
//import com.mockuai.tradecenter.core.service.action.Action;
//import com.mockuai.tradecenter.core.util.DozerBeanService;
//import com.mockuai.tradecenter.core.util.ModelUtil;
//import com.mockuai.tradecenter.core.util.TradeUtil;
//import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
//import com.mockuai.usercenter.common.dto.UserDTO;
//
//
//public class AddMultishopOrder3 implements Action {
//
//    private static final Logger log = LoggerFactory.getLogger(AddMultishopOrder3.class);
//
//
//
//    @Autowired
//    OrderManager orderManager;
//
//
//    @Autowired
//    TradeInnerFactory tradeInnerFactory;
//
//    @Resource
//	UserManager userManager;
//
//    @Resource
//	StoreManager storeManager;
//
//    @Resource
//	ItemManager itemManager;
//
//    @Resource
//	OrderSeqManager orderSeqManager;
//
//    @Autowired
//    OrderService  orderService;
//
//    @Autowired
//    MarketingManager marketingManager;
//
//    @Resource
//	DozerBeanService dozerBeanService;
//
//	@Override
//    public String getName() {
//        return ActionEnum.ADD_MULTISHOP_ORDER.getActionName();
//    }
//    @Override
//    public TradeResponse<?> execute(final RequestContext context) throws TradeException {
//        Request request = context.getRequest();
//        if (request.getParam("orderDTOList") == null) {
//            log.error("orderDTOList is null");
//            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderDTOList is null");
//        }
//        if(request.getParam("userId") == null){
//        	  log.error("userId is null");
//              return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
//        }
//        if(request.getParam("paymentId") == null){
//      	  log.error("paymentId is null");
//            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "paymentId is null");
//        }
//        Long userId = (Long)request.getParam("userId");
//        List<OrderDTO> orderDTOList = (List<OrderDTO>) request.getParam("orderDTOList");
//        if(	orderDTOList.size()==0 ){
//        	return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderDTOList is empty");
//        }
//        Long usePointAmount = 0L;
//        if(null!=request.getParam("usePointAmount"))
//        	usePointAmount = (Long)request.getParam("usePointAmount");
//        Long useBalanceAmount = 0L;
//        if(null!=request.getParam("useBalanceAmount"))
//        	useBalanceAmount = (Long)request.getParam("useBalanceAmount");
//
//        TradeInnerOper checkItemInnerOper =  tradeInnerFactory.getTrans(EnumSubTransCode.CHECK_ITEM);
//        String bizCode = (String) context.get("bizCode");
//		String appKey = (String) context.get("appKey");
//		Integer appType = (Integer) context.get("appType");
//        Integer paymentId = (Integer)request.getParam("paymentId");
//
//
//
//		InnerRequest innerRequest = new InnerRequest();
//		innerRequest.setAppKey(appKey);
//		innerRequest.setBizCode(bizCode);
//		innerRequest.setAppType(appType);
//		innerRequest.setUserId(userId);
//		Map<Long,Integer> sellerDeliveryIdMap = new HashMap<Long,Integer>();
//		List<OrderTogetherDTO> orderTogetherDTOList = new ArrayList<OrderTogetherDTO>();
//		List<OrderTogetherDTO> needSplitOrderTogetherList = new ArrayList<OrderTogetherDTO>();
//		Map<Long,OrderTogetherDTO> sellerOrderTogetherMap = new HashMap<Long,OrderTogetherDTO>();
//
//
//		Long consigneeId = null;
//		 Map<Long,List<OrderItemDTO>> sellerItemsMap = new HashMap<Long,List<OrderItemDTO>>();
//
//		 log.info("orderDTOs:"+orderDTOList);
//
//        for(OrderDTO orderDTO:orderDTOList){
//        	if(null==orderDTO)
//        			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderDTO is empty");
//        	if(null==orderDTO.getOrderItems()||orderDTO.getOrderItems().isEmpty())
//        			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderItem is empty");
//
//        		orderDTO.setUserId(userId);
//        		orderDTO.setPaymentId(paymentId);
//        		OrderTogetherDTO orderTogetherDTO = new OrderTogetherDTO();
//        		ItemRequest itemRequest = new ItemRequest();
//        		for(OrderItemDTO orderItemDTO:orderDTO.getOrderItems()){
//        			if(null==orderItemDTO.getSellerId())
//        				orderItemDTO.setSellerId(orderDTO.getSellerId());
//        		}
//        		itemRequest.setOrderItemList(orderDTO.getOrderItems());
//        		innerRequest.setItemRequest(itemRequest);
//        		TradeOperResult<?> checkItemOperResult = checkItemInnerOper.doTransaction(innerRequest);
//
//        		//增值服务放到orderItemDTO上
//        		try {
//        			genItemServiceListMap(orderDTO, checkItemOperResult.getItemResponse().getItemSkuMap(), appKey);
//        		} catch (TradeException e) {
//        			log.error(e.getResponseCode().getComment(), e);
//        			return ResponseUtils.getFailResponse(e.getResponseCode());
//        		}
//
//        		Long sellerId = checkItemOperResult.getItemResponse().getItemSkuMap().get(orderDTO.getOrderItems().get(0).getItemSkuId()).getSellerId();
//        		orderDTO.setSellerId(sellerId);
//
//        		//商品信息
//        		orderTogetherDTO.setOrderItemDTOList(orderDTO.getOrderItems());
//
//
//        		 sellerItemsMap.put(orderDTO.getSellerId(), orderDTO.getOrderItems());
//
//        		if (null == orderDTO.getDeliveryId()) {
//        			orderDTO.setDeliveryId(1);
//        		}
//        		UserConsigneeDTO consigneeDTO = null;
//        		OrderConsigneeDO orderConsigneeDO = null;
//        		String consignee = "";
//        		// TODO 配送方式验证
//        		if (orderDTO.getDeliveryId() == 0 || orderDTO.getDeliveryId() == 1) {
//        			// TODO 收货地址信息验证
//        			try {
//        				consigneeId = orderDTO.getOrderConsigneeDTO().getConsigneeId(); // 用户地址id
//        				consigneeDTO = userManager.getUserConsignee(userId, consigneeId, appKey);
//        				if (consigneeDTO == null) {
//        					return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_CONSIGNEE_INVALID);
//        				}
//        				consignee = consigneeDTO.getConsignee();
//
//        				orderConsigneeDO = TradeUtil.genOrderConsignee(consigneeDTO);
//
//        			} catch (TradeException e) {
//        				log.error(e.getResponseCode().getComment(), e);
//        				return ResponseUtils.getFailResponse(e.getResponseCode(), "getUserInfo error");
//        			}
//        		}
//
//        		if ((orderDTO.getDeliveryId() == 2 || orderDTO.getDeliveryId() == 3)
//        				&& (null == orderDTO.getOrderStoreDTO() || null == orderDTO.getOrderStoreDTO().getStoreId())) {
//        			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "storeId is null");
//        		}
//
//        		StoreDTO store = null;
//        		OrderStoreDO orderStoreDO = null;
//        		if (null != orderDTO.getOrderStoreDTO() && null != orderDTO.getOrderStoreDTO().getStoreId()) {
//        			try {
//        				store = storeManager.getStore(orderDTO.getOrderStoreDTO().getStoreId(),
//        						orderDTO.getOrderItems().get(0).getSellerId(), appKey);
//        				// 0 不支付 1 支持
//        				if (orderDTO.getDeliveryId() == 2 && store.getSupportPickUp() == 0) {
//        					throw new TradeException("当前门店不支持到店自提");
//        				}
//
//        				if (orderDTO.getDeliveryId() == 3 && store.getSupportDelivery() == 0) {
//        					throw new TradeException("当前门店不支持门店配送");
//        				}
//
//        				UserDTO storeUserDTO = userManager.getUser(store.getOwnerId(), appKey);
//        				consignee = orderDTO.getOrderConsigneeDTO().getConsignee();
//
//        				orderConsigneeDO = TradeUtil.genOrderConsignee(orderDTO, store, storeUserDTO);
//
//        				orderStoreDO = TradeUtil.getOrderStoreDO(orderDTO, store, storeUserDTO);
//
//        			} catch (TradeException e) {
//        				log.error(e.getResponseCode().getComment(), e);
//        				return ResponseUtils.getFailResponse(e.getResponseCode(), e.getMessage());
//        			}
//        		}
//
//        		//收货地址
//        		orderTogetherDTO.setOrderConsigneeDO(orderConsigneeDO);
//
//        		//门店
//        		orderTogetherDTO.setOrderStoreDO(orderStoreDO);
//
//
//        		orderTogetherDTO.setItemResponse(checkItemOperResult.getItemResponse());
//
//
//        		orderTogetherDTO.setOrderDiscountInfoDOs(new ArrayList<OrderDiscountInfoDO>());
//
//        		sellerOrderTogetherMap.put(orderDTO.getSellerId(), orderTogetherDTO);
//
//        		sellerDeliveryIdMap.put(orderDTO.getSellerId(),orderDTO.getDeliveryId());
//        }
//
//        Map<Long,ShopSettlementInfo> sellerSettleMap = new HashMap<Long,ShopSettlementInfo>();
//
//        MultiSettlementInfo multiSettlementInfo = marketingManager.getMultiSettlementInfo(userId, consigneeId, sellerItemsMap,
//        		sellerDeliveryIdMap,appKey);
//
//        if(multiSettlementInfo.getShopSettlementInfos().isEmpty()==false){
//        	for(ShopSettlementInfo shopSettlementInfo:multiSettlementInfo.getShopSettlementInfos()){
//        		sellerSettleMap.put(shopSettlementInfo.getShopInfo().getSellerId(), shopSettlementInfo);
//        	}
//        }
//
//        for(OrderDTO orderDTO:orderDTOList){
//
//        	OrderTogetherDTO orderTogetherDTO = sellerOrderTogetherMap.get(orderDTO.getSellerId());
//        	ShopSettlementInfo shopSettlement =  sellerSettleMap.get(orderDTO.getSellerId());
//        	if(null!=shopSettlement){
//        		SettlementInfo settlement = shopSettlement.getSettlementInfo();
//            	Map<Long,ActivityItemDTO> activityItemMap = new HashMap<Long,ActivityItemDTO>();
//            	if(null!=orderTogetherDTO&&null!=settlement){
//            		List<DiscountInfo> discountInfos = getDiscountInfos(settlement);
//            		List<DiscountInfo> directDiscountList = settlement.getDirectDiscountList();
//            		// 代表满减送
//            		List<OrderDiscountInfoDO> directOrderDiscountDOs = null;
//            		directOrderDiscountDOs = processDirectDiscount(directDiscountList,activityItemMap);
//
//
//            		Map<Long, GrantedCouponDTO> availableCouponMap = new HashMap<Long, GrantedCouponDTO>();
//            		List<OrderDiscountInfoDO> orderDiscountInfoDOs = null;
//            		orderDiscountInfoDOs = processDiscount(orderDTO.getUsedCouponDTOs(),
//            				discountInfos, settlement);
//
//            		orderDiscountInfoDOs.addAll(directOrderDiscountDOs);
//
//
//
//
//        			// 组装orderItemMap,便于后续使用, 其中key是itemSkuId
//            		Map<Long, List<OrderItemDO>> orderItemListMap = null;
//
//            		try {
//            			log.info("orderDTO:"+ JSONObject.toJSONString(orderDTO));
//
//            			orderItemListMap = orderService.genOrderItemDOsMap(orderDTO, orderTogetherDTO.getItemResponse().getItemMap(),
//            					orderTogetherDTO.getItemResponse().getItemSkuMap(),
//            					appKey,
//            					activityItemMap);
//
//            			log.info("orderItemListMap:"+ JSONObject.toJSONString(orderItemListMap));
//
//            		} catch (TradeException e) {
//            			log.error(e.getResponseCode().getComment(), e);
//            			return ResponseUtils.getFailResponse(e.getResponseCode());
//            		}
//
//            		List<OrderItemDO> orderItemDOList = new ArrayList<OrderItemDO>();
//            		for (Entry<Long, List<OrderItemDO>> entry : orderItemListMap.entrySet()) {
//    					List<OrderItemDO> orderItemList = entry.getValue();
//    					for(OrderItemDO orderItemDO:orderItemList){
//    						orderItemDO.setBizCode(bizCode);
//    						orderItemDO.setUserName(orderTogetherDTO.getOrderConsigneeDO().getConsignee());
//    						// TODO ...需要修改
//    						orderItemDO.setDeliveryType(1);
//    						orderItemDOList.add(orderItemDO);
//    					}
//    				}
//
//            		List<OrderItemDTO> giftlist = getGiftToOrderItems(settlement.getGiftList(), appKey);
//        			if (null != giftlist && giftlist.size() > 0) {
//        				List<OrderItemDO> giftDOs = dozerBeanService.coverList(giftlist, OrderItemDO.class);
//
//    					for (OrderItemDO gift : giftDOs) {
//    						gift.setBizCode(bizCode);
//    						gift.setUserName(orderTogetherDTO.getOrderConsigneeDO().getConsignee());
//    						gift.setDeliveryType(1);
//    						gift.setUserId(orderDTO.getUserId());
//    						gift.setNumber(1);
//    					}
//    					if (null != giftDOs && giftDOs.size() > 0) {
//    						orderItemDOList.addAll(giftDOs);
//    					}
//        			}
//
//        			orderTogetherDTO.setItemList(orderItemDOList);
//
//            		OrderDO orderDO = convert2TotalOrderDO(orderDTO,orderItemListMap,
//            				orderDiscountInfoDOs,
//           				 settlement.getDeliveryFee(),bizCode);
//
//            		orderDO.setAppType(appType);
//            		orderTogetherDTO.setOrderDO(orderDO);
//
//            		orderTogetherDTO.getOrderDiscountInfoDOs().addAll(orderDiscountInfoDOs);
//
//            		orderTogetherDTOList.add(orderTogetherDTO);
//            		if(orderDO.getTotalAmount()>0){
//            			needSplitOrderTogetherList.add(orderTogetherDTO);
//            		}
//
//
//
//            	}
//        	}
//
//
//        }
//
//        //主订单的积分折扣金额
//   	 	long parentOrderPointAmount = 0l;
//
//       Long allOrderTotalAmount = TradeUtil.getAllOrderTotalAmount(needSplitOrderTogetherList);
//
//
//
//        //分拆point_amount, balance_amount
//        if(needSplitOrderTogetherList.size()>0&&(usePointAmount>0||useBalanceAmount>0)){
//
//
//        	 List<WealthAccountDTO> wealthAccountDTOs = multiSettlementInfo.getWealthAccountDTOs();
//
//
//        	 long totalSurplusPointAmount = 0l;
//
//        	for(OrderTogetherDTO orderTogether:needSplitOrderTogetherList){
//        		List<UsedWealthDTO> usedWealthDTOs = new ArrayList<UsedWealthDTO>();
//        		Long subOrderSplitPointAmount = 0l;
//        		Long subSplitPoint = 0L;
//
//        		Long splitBalanceAmount = 0L;
//        		if(useBalanceAmount>0){
//
//        			splitBalanceAmount = TradeUtil.getOrderSplitAmount(needSplitOrderTogetherList.size(),
//        					useBalanceAmount,
//            				orderTogether.getOrderDO().getTotalAmount(),
//            				allOrderTotalAmount
//            			).longValue();
//
//        			if(splitBalanceAmount>0){
//        				OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
//            			orderDiscountInfoDO.setDiscountType(2);
//    					orderDiscountInfoDO.setDiscountCode("1");
//    					orderDiscountInfoDO.setBizCode(bizCode);
//    					orderDiscountInfoDO.setUserId(userId);
//    					orderDiscountInfoDO.setDiscountDesc("虚拟财富");
//    					orderDiscountInfoDO.setDiscountAmount(splitBalanceAmount);
//    					orderTogether.getOrderDiscountInfoDOs().add(orderDiscountInfoDO);
//    					//TODO ...
//    					UsedWealthDTO usedWealthDTO = new UsedWealthDTO();
//    					usedWealthDTO.setUserId(userId);
//    					usedWealthDTO.setAmount(splitBalanceAmount);
//    					usedWealthDTO.setWealthType(1);
//    					usedWealthDTOs.add(usedWealthDTO);
//
//    					orderTogether.setWealthDiscountAmount(splitBalanceAmount);
//
//        			}
//        			parentOrderPointAmount+=(splitBalanceAmount.doubleValue()-splitBalanceAmount.longValue());
//        		}
//
//
//        		if(usePointAmount>0){
//        			Double splitPoint = TradeUtil.getOrderSplitAmount(needSplitOrderTogetherList.size(),
//            				usePointAmount,
//            				orderTogether.getOrderDO().getTotalAmount(),
//            				allOrderTotalAmount);
//            		if(splitPoint>0){
//            			//TODO ...
//            			WealthAccountDTO wealthAccount = TradeUtil.getWealthAccountDTO(wealthAccountDTOs,2);
//            			if(null==wealthAccount){
//            				throw new TradeException("结算信息为空");
//            			}
//
//
//
//            			Double exchangeAmount = MoneyUtil.mul(splitPoint.doubleValue()+ "", wealthAccount.getExchangeRate() + "");
//
//            			subOrderSplitPointAmount = exchangeAmount.longValue();
//
//            			parentOrderPointAmount+=(exchangeAmount.doubleValue()-subOrderSplitPointAmount);
//
//            			String refundPointAmount = MoneyUtil.getFormatMoney(
//								MoneyUtil.mul(1d, MoneyUtil.mul(splitPoint.longValue()+ "", wealthAccount.getExchangeRate() + "")),
//								"##0");
//
//            			OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
//            			orderDiscountInfoDO.setDiscountType(2);
//    					orderDiscountInfoDO.setDiscountCode("2");
//    					orderDiscountInfoDO.setBizCode(bizCode);
//    					orderDiscountInfoDO.setUserId(userId);
//    					orderDiscountInfoDO.setDiscountDesc("积分");
//    					orderDiscountInfoDO.setDiscountAmount(subOrderSplitPointAmount);
//
//
//
//    					//TODO .....
//    					UsedWealthDTO usedWealthDTO = new UsedWealthDTO();
//    					usedWealthDTO.setUserId(userId);
//    					usedWealthDTO.setAmount(subOrderSplitPointAmount);
//    					usedWealthDTO.setWealthType(2);
//
//
//
//    					orderTogether.getOrderDO().setPoint(splitPoint.longValue());
//
//    					orderTogether.getOrderDO().setPointDiscountAmount(Long.parseLong(refundPointAmount));
//    					orderTogether.getOrderDO().setPoint(subSplitPoint);
//
//    					if((splitBalanceAmount+subOrderSplitPointAmount)-orderTogether.getOrderDO().getTotalAmount()>=0){
//    						long surplusPointAmount=(splitBalanceAmount+subOrderSplitPointAmount)-orderTogether.getOrderDO().getTotalAmount();
//
//        					if(surplusPointAmount>=0){
//
//        						usedWealthDTO.setAmount(subOrderSplitPointAmount-surplusPointAmount);
//
//        						orderDiscountInfoDO.setDiscountAmount(subOrderSplitPointAmount-surplusPointAmount);
//
//        						totalSurplusPointAmount+=surplusPointAmount;
//
//        						orderTogether.setNeedPay(false);
//        					}
//    					}
//    					orderTogether.getOrderDiscountInfoDOs().add(orderDiscountInfoDO);
//
//
//    					usedWealthDTOs.add(usedWealthDTO);
//            		}
//        		}
//
//
//        		orderTogether.getOrderDO().setDiscountAmount(orderTogether.getOrderDO().getDiscountAmount()+splitBalanceAmount+subOrderSplitPointAmount);
//        		Long orderTotalAmount = orderTogether.getOrderDO().getTotalAmount()-splitBalanceAmount-subOrderSplitPointAmount;
//        		if(orderTotalAmount<=0){
//        			orderTotalAmount = 0L;
//        		}
//
//
//        		orderTogether.getOrderDO().setTotalAmount(orderTotalAmount);
//        		orderTogether.setUsedWealthDTOs(usedWealthDTOs);
//        	}
//
//        	if(totalSurplusPointAmount>0){
//        		for(OrderTogetherDTO orderTogether:needSplitOrderTogetherList){
//        			//如果折扣金额大于支付金额
//            		if(orderTogether.isNeedPay()){
//            			List<OrderDiscountInfoDO> orderDiscountInfoDOs = orderTogether.getOrderDiscountInfoDOs();
//            			if(orderDiscountInfoDOs.isEmpty()==false){
//            				for(OrderDiscountInfoDO orderDiscountInfoDO: orderDiscountInfoDOs){
//
//            		        	if(orderDiscountInfoDO.getDiscountType()==2&&
//            		        			orderDiscountInfoDO.getDiscountCode().equals("2")){
//            		        		long needPointAmt = (orderTogether.getOrderDO().getTotalPrice()+orderTogether.getOrderDO().getDeliveryFee())-orderTogether.getOrderDO().getDiscountAmount();
//            		        		if(needPointAmt<=totalSurplusPointAmount){
//            		        			orderDiscountInfoDO.setDiscountAmount(orderDiscountInfoDO.getDiscountAmount()+needPointAmt);
//            		        			totalSurplusPointAmount-=needPointAmt;
//            		        		}
//
//            		        	}
//            		        }
//            			}
//            		}
//        		}
//        	}
//
//        }
//
//
//        //组装oriOrderDO
//        OrderDO oriOrderDO = new OrderDO();
//        oriOrderDO.setUserId(userId);
//        oriOrderDO.setSellerId(0L);
//        oriOrderDO.setBizCode(bizCode);
//        oriOrderDO.setOrderSn(this.orderSeqManager.getOrderSn(userId));
//        oriOrderDO.setType(0);
//        oriOrderDO.setPaymentId(paymentId);
//        oriOrderDO.setDeliveryId(0);
//        oriOrderDO.setTotalPrice(0l);
//        oriOrderDO.setTotalAmount(TradeUtil.getAllOrderTotalAmount(needSplitOrderTogetherList));
//        oriOrderDO.setDeliveryFee(0l);
//        oriOrderDO.setDiscountAmount(parentOrderPointAmount);
//        oriOrderDO.setDiscountMark(0);
//        oriOrderDO.setInvoiceMark(0);
//        OrderDTO responseOrderDTO =  orderService.addMultishopOrder(oriOrderDO, orderTogetherDTOList, appKey);
//        TradeResponse result  = new TradeResponse(responseOrderDTO);
//        return result;
//
//    }
//
//
//
//
//
//
//    public List<OrderItemDO> genOrderItemList(OrderDTO orderDTO, Map<Long, ItemDTO> itemMap,
//			Map<Long, ItemSkuDTO> itemSkuMap, String appKey) throws TradeException {
//		List<OrderItemDO> orderItemDOList = new ArrayList<OrderItemDO>();
//		for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
//			ItemSkuDTO itemSkuDTO = itemSkuMap.get(orderItemDTO.getItemSkuId());
//
//			if (itemSkuDTO == null) { // 如果该商品在不存在
//				log.error("itemSku doesn't exist : " + orderItemDTO.getItemSkuId());
//				throw new TradeException(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "itemSku doesn't exist");
//			}
//
//			ItemDTO itemDTO = itemMap.get(itemSkuDTO.getItemId());
//			if (itemDTO == null) {
//				log.error("item doesn't exist" + itemSkuDTO.getItemId());
//				throw new TradeException(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "item doesn't exist");
//			}
//
//			OrderItemDO orderItemDO = this.getOrderItemDO(orderDTO, orderItemDTO, itemSkuDTO, itemDTO, appKey);
//			orderItemDOList.add(orderItemDO);
//		}
//
//		return orderItemDOList;
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
//
//		return orderItemDO;
//	}
//
//
//
//	private Map<Long, List<ValueAddedServiceDTO>> genItemServiceListMap(OrderDTO orderDTO,
//			Map<Long, ItemSkuDTO> itemSkuMap, String appKey) throws TradeException {
//		Map<Long, List<ValueAddedServiceDTO>> itemServiceList = new HashMap<Long, List<ValueAddedServiceDTO>>();
//		for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
//
//			if (orderItemDTO.getServiceList() != null && orderItemDTO.getServiceList().size() > 0) {
//				List<Long> serviceIds = new ArrayList<Long>();
//				for (ItemServiceDTO itemServiceDTO : orderItemDTO.getServiceList()) {
//					serviceIds.add(itemServiceDTO.getServiceId());
//				}
//				List<ItemPriceDTO> serviceList = itemManager.queryItemServiceList(orderItemDTO.getItemSkuId(),
//						serviceIds, orderDTO.getUserId(),orderItemDTO.getSellerId(), appKey);
//				if (serviceList != null) {
//					itemServiceList.put(orderItemDTO.getItemSkuId(), serviceList.get(0).getValueAddedServiceDTOList());
//				}
//				if(null!=serviceList.get(0).getValueAddedServiceDTOList()){
//					List<OrderServiceDTO> orderServiceDTOList = new ArrayList<OrderServiceDTO>();
//					for(ValueAddedServiceDTO valueAddedService:serviceList.get(0).getValueAddedServiceDTOList()){
//						OrderServiceDTO orderServiceDTO = new OrderServiceDTO();
//						orderServiceDTO.setItemSkuId(orderItemDTO.getItemSkuId());
//						orderServiceDTO.setPrice(valueAddedService.getServicePrice());
//						orderServiceDTO.setServiceName(valueAddedService.getServiceName());
//						orderServiceDTO.setServiceImageUrl(valueAddedService.getIconUrl());
//						orderServiceDTO.setServiceId(valueAddedService.getId());
//						orderServiceDTOList.add(orderServiceDTO);
//					}
//					orderItemDTO.setOrderServiceList(orderServiceDTOList);
//				}
//
//			}
//
//		}
//		return itemServiceList;
//	}
//
//
//	private List<DiscountInfo> getDiscountInfos(SettlementInfo settlementInfo) throws TradeException {
//		if (settlementInfo == null) {
//			return Collections.EMPTY_LIST;
//		}
//		// 营销活动优惠信息处理
//		List<DiscountInfo> discountInfos = settlementInfo.getDiscountInfoList();
//		return discountInfos;
//	}
//
//	private OrderDO convert2TotalOrderDO(OrderDTO orderDTO,
//			Map<Long, List<OrderItemDO>> orderItemDOListMap, List<OrderDiscountInfoDO> orderDiscountInfoDOs, Long deliveryFee,
//			String bizCode) throws TradeException {
//		// 创建订单
//		long itemTotalPrice = TradeUtil.getOrderTotalPrice(orderDTO, orderItemDOListMap);// 订单商品总价
//
//		long suitTotalPrice = TradeUtil.getSuitOrderItemTotalPrice(orderDTO, orderItemDOListMap);//套装的总价格
//
//
//		String orderSn = null;
//		long userId = orderDTO.getUserId(); // 用户id
//		Long sellerId = orderDTO.getOrderItems().get(0).getSellerId();
//
//		orderSn = this.orderSeqManager.getOrderSn(userId); // 按照订单号生成规则生成订单编号
//		OrderDO orderDO = ModelUtil.convert2OrderDO(orderDTO); // 将DTO转为DO
//		orderDO.setUserId(userId); // 买家ID
//		orderDO.setSellerId(sellerId);
//		orderDO.setOrderSn(orderSn);
//
////		long totalDiscountAmount = TradeUtil.getTotalDiscountAmount(orderDiscountInfoDOs);
//
//		long directDiscountAmount = TradeUtil.getCommonDiscountAmount(orderDiscountInfoDOs);
//
//		orderDO.setDiscountAmount(directDiscountAmount);
//
//		orderDO.setDeliveryFee(deliveryFee);
//
//		long singleItemPrice =  ((itemTotalPrice-suitTotalPrice) + deliveryFee) - directDiscountAmount;
//		if(singleItemPrice<=0)
//			singleItemPrice = 0L;
//
//		// 订单总价等于（商品总价＋总运费）－总优惠金额
//		long totalAmount = singleItemPrice + suitTotalPrice;
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
//		final int paymentId = orderDTO.getPaymentId(); // 支付方式
//		if (needPay == true) {
//			orderDO.setPaymentId(paymentId);
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
//
//	private List<OrderItemDTO> getGiftToOrderItems(
//			List<com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO> giftList, String appKey)
//					throws TradeException {
//
//		if (null != giftList && giftList.size() > 0) {
//
//			List<OrderItemDTO> giftlist = new ArrayList<OrderItemDTO>();
//
//			for (MarketItemDTO marketitemDTO : giftList) {
//				List<Long> skuIds = new ArrayList<Long>();
//				skuIds.add(marketitemDTO.getItemSkuId());
//
//				List<ItemSkuDTO> itemSkus = this.itemManager.queryItemSku(skuIds, marketitemDTO.getSellerId(),
//						appKey);
//				if (itemSkus == null | itemSkus.size() == 0) {
//					log.error("itemSku is null : " + skuIds + "," + marketitemDTO.getSellerId());
//					throw new TradeException(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "itemSku doesn't exist");
//				}
//				ItemSkuDTO sku = itemSkus.get(0);
//				OrderItemDTO orderItemDTO = new OrderItemDTO();
//				orderItemDTO.setItemId(marketitemDTO.getItemId());
//				orderItemDTO.setSellerId(marketitemDTO.getSellerId());
//				orderItemDTO.setItemSkuId(marketitemDTO.getItemSkuId());
//				orderItemDTO.setItemName(marketitemDTO.getItemName());
//				orderItemDTO.setItemImageUrl(marketitemDTO.getIconUrl());
//				orderItemDTO.setItemSkuDesc(sku.getSkuCode());
//				orderItemDTO.setUnitPrice(0L);
//				if (null != marketitemDTO.getNumber()) {
//					orderItemDTO.setNumber(marketitemDTO.getNumber());
//				} else {
//					orderItemDTO.setNumber(1);
//				}
//
//				giftlist.add(orderItemDTO);
//			}
//
//			return giftlist;
//		}
//		return null;
//	}
//
//
//	/**
//	 * 处理订单优惠信息，并生成订单对象(代表满减送)
//	 *
//	 * @param orderDTO
//	 * @param itemMap
//	 * @param itemSkuMap
//	 * @return
//	 */
//	private List<OrderDiscountInfoDO> processDirectDiscount(
//			List<DiscountInfo> discountInfos,
//			Map<Long,ActivityItemDTO> activityItemMap) throws TradeException {
//
//		List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();
//
//			// 营销活动优惠信息处理
//			if (discountInfos != null && discountInfos.isEmpty() == false) {
//				for (DiscountInfo discountInfo : discountInfos) {
//					MarketActivityDTO marketActivityDTO = discountInfo.getActivity();
//
//					if (marketActivityDTO.getToolCode().equals("ReachMultipleReduceTool")) { // 代表满减
//						// if(marketActivityDTO.getCouponMark() ==
//						// 0){//不需要优惠券的活动
//						OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
//						orderDiscountInfoDO.setDiscountType(1);
//						orderDiscountInfoDO.setDiscountCode(marketActivityDTO.getToolCode());
//						orderDiscountInfoDO.setDiscountAmount(discountInfo.getDiscountAmount());
//						orderDiscountInfoDO.setDiscountDesc("满减送");
//						orderDiscountInfoDOs.add(orderDiscountInfoDO);
//					}
//
//					if(marketActivityDTO.getToolCode().equals("BarterTool")){
//						List<ActivityItemDTO> activityItemDTOList = marketActivityDTO.getTargetItemList();
//						if(null!=activityItemDTOList){
//							for(ActivityItemDTO activityItemDTO:activityItemDTOList){
//								activityItemDTO.setUnitPrice(discountInfo.getDiscountAmount());
//								activityItemMap.put(activityItemDTO.getItemSkuId(), activityItemDTO);
//							}
//						}
//					}
//
//				}
//
//			}
//
//
//		return orderDiscountInfoDOs;
//	}
//
//
//	/**
//	 * 处理订单优惠信息，并生成订单对象
//	 *
//	 * @param orderDTO
//	 * @param itemMap
//	 * @param itemSkuMap
//	 * @return
//	 */
//	private List<OrderDiscountInfoDO> processDiscount(
//			List<UsedCouponDTO> usedCouponDTOs,
//			List<DiscountInfo> discountInfos,
//			SettlementInfo settlementInfo) throws TradeException {
//
//		List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();
//
//			// 营销活动优惠信息处理
//			if (discountInfos != null && discountInfos.isEmpty() == false) {
//				Map<Long, GrantedCouponDTO> availableCouponMap = new HashMap<Long, GrantedCouponDTO>();
//				Map<Long,Integer> couponMallMarkMap = new HashMap<Long, Integer>();
//				for (DiscountInfo discountInfo : discountInfos) {
//					MarketActivityDTO marketActivityDTO = discountInfo.getActivity();
//
//					if (marketActivityDTO.getToolCode().equals("SYS_MARKET_TOOL_000001")) {
//
//						List<GrantedCouponDTO> availableCouponList = discountInfo.getAvailableCoupons();
//						if (availableCouponList != null) {
//							for (GrantedCouponDTO grantedCouponDTO : availableCouponList) {
//								availableCouponMap.put(grantedCouponDTO.getId(), grantedCouponDTO);
//
//								Integer mallMark = 0;
//								if(marketActivityDTO.getLevel().intValue()==MarketLevel.BIZ_LEVEL.getValue()){
//									mallMark = 1;
//								}
//
//								couponMallMarkMap.put(grantedCouponDTO.getId(), mallMark);
//
//							}
//						}
//					}
//
//				}
//
//				// 优惠券合法性校验
//				if (usedCouponDTOs != null) {
//					for (UsedCouponDTO usedCouponDTO : usedCouponDTOs) {// 用户希望使用的优惠券不在可用优惠券列表中
//						if (availableCouponMap.containsKey(usedCouponDTO.getCouponId()) == false) {
//							throw new TradeException(ResponseCode.BIZ_E_SPECIFIED_COUPON_UNAVAILABLE);
//						}
//					}
//				}
//
//				// 来放优惠券  只能用一张
//				if (usedCouponDTOs != null) {
//
//					long totalSave = 0L;
//					int matchCount = 0;
//					int mallDiscountMark = 0 ;
//					for (UsedCouponDTO usedCouponDTO : usedCouponDTOs) {
//						if (availableCouponMap.containsKey(usedCouponDTO.getCouponId())) {
//							GrantedCouponDTO grantedCouponDTO = availableCouponMap.get(usedCouponDTO.getCouponId());
//							totalSave += grantedCouponDTO.getDiscountAmount();
//							matchCount++;
//						}
//
//						mallDiscountMark = couponMallMarkMap.get(usedCouponDTO.getCouponId());
//
//					}
//
//					if (matchCount > 0) {// 如果匹配优惠券数大于1，则添加一条优惠记录
//						OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
//						orderDiscountInfoDO.setDiscountType(1);
//						orderDiscountInfoDO.setDiscountCode("SYS_MARKET_TOOL_000001");// TODO
//																						// 先写死
//						orderDiscountInfoDO.setDiscountAmount(totalSave);
//						orderDiscountInfoDO.setDiscountDesc("优惠券");
//
//						orderDiscountInfoDO.setMallMark(mallDiscountMark);
//
//						orderDiscountInfoDOs.add(orderDiscountInfoDO);
//					}
//				}
//
//			}
//
//
//		return orderDiscountInfoDOs;
//	}
//
//}
