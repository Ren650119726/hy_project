package com.mockuai.tradecenter.core.service.action.order.add.step;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.UsedCouponDTO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.MarketingManager;
import com.mockuai.tradecenter.core.manager.OrderDiscountInfoManager;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.util.JsonUtil;

/**
 * Created by zengzhangqiang on 5/19/16.
 */
public class HandleOrderSettlementStep extends TradeBaseStep {
	
	private static final Logger log = LoggerFactory.getLogger(HandleOrderSettlementStep.class);
	
    @Override
    public StepName getName() {
        return StepName.HANDLE_ORDER_SETTLEMENT_STEP;
    }

    @Override
    public TradeResponse execute() {
        OrderDTO orderDTO = (OrderDTO) this.getParam("orderDTO");
        OrderDTO orderDTONor = (OrderDTO) this.getAttr("orderDTONor");
        OrderDTO orderDTOComb = (OrderDTO) this.getAttr("orderDTOComb");
        String appKey = (String) this.getAttr("appKey");
//        String bizCode = (String) this.getAttr("bizCode");
//        Map<Long, ItemSkuDTO> itemSkuMap = (Map<Long, ItemSkuDTO>) this.getAttr("itemSkuMap");
//        Map<Long, ItemDTO> itemMap = (Map<Long, ItemDTO>) this.getAttr("itemMap");
        List<OrderItemDO> orderItemDOList = (List<OrderItemDO>) this.getAttr("orderItemList");
        List<OrderItemDO> orderCombItemList = (List<OrderItemDO>) this.getAttr("orderCombItemList");
        

        List<OrderItemDO> orderTotalItemList = new ArrayList<OrderItemDO>();
    	if(CollectionUtils.isNotEmpty(orderItemDOList)){
    		orderTotalItemList.addAll(orderItemDOList);
    	}
    	if(CollectionUtils.isNotEmpty(orderCombItemList)){
    		orderTotalItemList.addAll(orderCombItemList);
    	}
    	
    	OrderDTO orderTotalDTO = new OrderDTO();
 
		try {
			BeanUtils.copyProperties(orderTotalDTO, orderDTO);
		} catch (Exception e) {			
			e.printStackTrace();
			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_DEFAULT_ERROR, e.getMessage());
		} 
		// 初始清空商品信息
		/*orderTotalDTO.setOrderItems(null);
//		log.info(" Settlement orderDTONor : "+JSONObject.toJSONString(orderDTONor));
		if(orderDTONor!=null && CollectionUtils.isNotEmpty(orderDTONor.getOrderItems())){			
			orderTotalDTO.setOrderItems(orderDTONor.getOrderItems());						
		}
//		log.info(" Settlement orderDTOComb : "+JSONObject.toJSONString(orderDTOComb));
		if(orderDTOComb!=null && CollectionUtils.isNotEmpty(orderDTOComb.getOrderItems())){
			if(CollectionUtils.isNotEmpty(orderTotalDTO.getOrderItems())){
				orderTotalDTO.getOrderItems().addAll(orderDTOComb.getOrderItems());
			}else{
				orderTotalDTO.setOrderItems(orderDTOComb.getOrderItems());
			}
			
		}*/
        
//		log.info(" settlement orderTotalDTO : "+JSONObject.toJSONString(orderTotalDTO));        
//		log.info(" settlement orderTotalItemList : "+JSONObject.toJSONString(orderTotalItemList));
		
        try {
        	
            Long consigneeId = null;

            if (null != orderTotalDTO.getOrderConsigneeDTO()
                    && null != orderTotalDTO.getOrderConsigneeDTO().getConsigneeId()) {
                consigneeId = orderTotalDTO.getOrderConsigneeDTO().getConsigneeId();
            }

            SettlementInfo settlement = getSettlementInfo(orderTotalDTO.getUserId(), orderTotalDTO.getOrderItems(),
                    consigneeId, appKey);
            
//            log.info(" settlement : "+JSONObject.toJSONString(settlement));
            //商品总价
            orderTotalDTO.setTotalPrice(getItemTotalPrice(orderTotalItemList));
            
            List<OrderDiscountInfoDO> resultOrderDiscounts = new ArrayList<OrderDiscountInfoDO>();
            //满减送，限时购优惠处理
            List<OrderDiscountInfoDO> directOrderDiscounts = processDirectDiscount(orderTotalDTO, settlement,appKey,orderDTOComb,orderDTONor,orderItemDOList);
//            log.info(" settlement directOrderDiscounts :"+JSONObject.toJSONString(directOrderDiscounts));
            if(directOrderDiscounts!=null && !directOrderDiscounts.isEmpty()){
            	resultOrderDiscounts.addAll(directOrderDiscounts);
            }
            //优惠券优惠处理
            List<OrderDiscountInfoDO> orderDiscountInfoDOs = processDiscount(orderTotalDTO, settlement,orderDTOComb,orderDTONor);
//            log.info(" settlement orderDiscountInfoDOs :"+JSONObject.toJSONString(orderDiscountInfoDOs));
            if(orderDiscountInfoDOs!=null && !orderDiscountInfoDOs.isEmpty()){
            	resultOrderDiscounts.addAll(orderDiscountInfoDOs);
            }
            
            //首单
            List<OrderDiscountInfoDO> firstOrderDiscountInfoDOs = processDirectFirstDiscount(orderTotalDTO, settlement,resultOrderDiscounts,orderDTOComb,orderDTONor);
//            log.info(" settlement firstOrderDiscountInfoDOs :"+JSONObject.toJSONString(firstOrderDiscountInfoDOs));
            if(firstOrderDiscountInfoDOs!=null && !firstOrderDiscountInfoDOs.isEmpty()){
            	resultOrderDiscounts.addAll(firstOrderDiscountInfoDOs);
            }
            
            //虚拟财富优惠处理（即余额和积分的抵现处理）
            /*List<OrderDiscountInfoDO> virtualWealthDiscounts = processVirtualWealthDiscount(orderTotalDTO, settlement);
            if(virtualWealthDiscounts != null && !virtualWealthDiscounts.isEmpty()){
            	resultOrderDiscounts.addAll(virtualWealthDiscounts);
            }*/
            
//            log.info(" @@@@@@@@@@@ end resultOrderDiscounts: "+JSONObject.toJSONString(resultOrderDiscounts));
            List<OrderDiscountInfoDO> resultNorOrderDiscounts = new ArrayList<OrderDiscountInfoDO>();
            List<OrderDiscountInfoDO> resultCombOrderDiscounts = new ArrayList<OrderDiscountInfoDO>();
            for(OrderDiscountInfoDO  orderDiscountInfoDO:resultOrderDiscounts){
            	if(orderDiscountInfoDO.getCombOrderItemId()!=null){
            		resultCombOrderDiscounts.add(orderDiscountInfoDO);
            	}else{
            		resultNorOrderDiscounts.add(orderDiscountInfoDO);
            	}
            }
            
            //将订单结算相关信息放入管道上下文中
            this.setAttr("settlement", settlement);
//            this.setAttr("orderDiscountInfoDOs", resultOrderDiscounts);
//            this.setAttr("orderDiscountListMap", getOrderDiscountListMap(resultOrderDiscounts));
            this.setAttr("orderDiscountInfoDOs", resultNorOrderDiscounts);
            this.setAttr("orderDiscountListMap", getOrderDiscountListMap(resultNorOrderDiscounts));
            this.setAttr("orderCombDiscountInfoDOs", resultCombOrderDiscounts);
            this.setAttr("orderCombDiscountListMap", getOrderDiscountListMap(resultCombOrderDiscounts));

        } catch (TradeException e) {
            logger.error("get discountInfo error" + e.getResponseCode().getComment(), e);
            return ResponseUtils.getFailResponse(e.getResponseCode(), e.getMessage());
        }


        return ResponseUtils.getSuccessResponse();
    }

    /**
     * 获取指定订单商品列表的总价
     * @param orderItemDOList
     * @return
     */
    private long getItemTotalPrice(List<OrderItemDO> orderItemDOList) {
        long orderTotalPrice = 0L;
        for(OrderItemDO orderItemDO:orderItemDOList){
            if(null==orderItemDO.getServiceUnitPrice())
                orderItemDO.setServiceUnitPrice(0L);
            Long unitPrice = orderItemDO.getUnitPrice();
            if(orderItemDO.isSuitSubItem()){
                unitPrice = 0L;
            }
            orderTotalPrice += (unitPrice * orderItemDO.getNumber());
            Long orderServicePrice = orderItemDO.getServiceUnitPrice()*orderItemDO.getNumber();
            orderTotalPrice += orderServicePrice;
        }

        return orderTotalPrice;
    }
    
    private Map<String, List<OrderDiscountInfoDO>> getOrderDiscountListMap(List<OrderDiscountInfoDO> orderDiscountInfoDOs) {
        Map<String, List<OrderDiscountInfoDO>> orderDiscountListMap = new HashMap<String, List<OrderDiscountInfoDO>>();
        for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
            if (orderDiscountListMap.containsKey(orderDiscountInfoDO.getOrderSn()) == false) {
                orderDiscountListMap.put(orderDiscountInfoDO.getOrderSn(), new ArrayList<OrderDiscountInfoDO>());
            }

            orderDiscountListMap.get(orderDiscountInfoDO.getOrderSn()).add(orderDiscountInfoDO);
        }

//        log.info(" @@@@@@@@@@@ end orderDiscountListMap: "+JSONObject.toJSONString(orderDiscountListMap));
        
        return orderDiscountListMap;
    }

    /**
     * 处理订单使用优惠券的优惠场景，并生成对应的订单优惠明细列表
     * @param orderDTO
     * @param settlementInfo
     * @return
     */
    private List<OrderDiscountInfoDO> processDiscount(OrderDTO orderDTO,
                                                      SettlementInfo settlementInfo,OrderDTO orderDTOComb,OrderDTO orderDTONor) throws TradeException {
        // 营销活动优惠信息处理
        List<DiscountInfo> discountInfoList = getDiscountInfoList(settlementInfo);

        if(discountInfoList.isEmpty()){
            return Collections.emptyList();
        }
        
        //可用优惠券集合，以优惠券id为key，优惠券对象为value
        Map<Long, GrantedCouponDTO> availableCouponMap = new HashMap<Long, GrantedCouponDTO>();
        //优惠券与discountInfo的反向映射关系集
        Map<Long, DiscountInfo> couponDiscountMap = new HashMap<Long, DiscountInfo>();
        for (DiscountInfo discountInfo : discountInfoList) {
            MarketActivityDTO marketActivityDTO = discountInfo.getActivity();
            
            // 优惠券不可用
            if(marketActivityDTO.getCouponMark()==0){
            	return Collections.emptyList();
            }
            //需要优惠券的活动
            if (marketActivityDTO.getToolCode().equals(ToolType.SIMPLE_TOOL.getCode())) {

                List<GrantedCouponDTO> availableCouponList = discountInfo.getAvailableCoupons();
                if (availableCouponList != null) {
                    for (GrantedCouponDTO grantedCouponDTO : availableCouponList) {
                        availableCouponMap.put(grantedCouponDTO.getId(), grantedCouponDTO);
                        couponDiscountMap.put(grantedCouponDTO.getId(), discountInfo);
                    }
                }
            }

        }

        logger.info("[ORDER_SETTLEMENT_TRACE] availableCouponMap:{},couponDiscountMap:{}",
                JsonUtil.toJson(availableCouponMap), JsonUtil.toJson(couponDiscountMap));

        // 优惠券合法性校验
        List<UsedCouponDTO> usedCouponDTOs = orderDTO.getUsedCouponDTOs();
        if (usedCouponDTOs != null) {
            for (UsedCouponDTO usedCouponDTO : usedCouponDTOs) {// 用户希望使用的优惠券不在可用优惠券列表中
                if (availableCouponMap.containsKey(usedCouponDTO.getCouponId()) == false) {
                    throw new TradeException(ResponseCode.BIZ_E_SPECIFIED_COUPON_UNAVAILABLE);
                }
            }
        }

        /**
         * 订单商品优惠明细处理。由于订单里头不同的商品能享受到的优惠可能不一样，
         * 所以需要为订单商品列表中能享受到优惠的每个商品生成对应的优惠记录。
         * 如果后续支持一个订单可使用多个优惠券进行叠加优惠的话，可能一个订单商品就会对应多条优惠记录。
         */
        List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();
        if (usedCouponDTOs != null) {
            for (UsedCouponDTO usedCouponDTO : usedCouponDTOs) {
                if (availableCouponMap.containsKey(usedCouponDTO.getCouponId())) {
                    GrantedCouponDTO grantedCouponDTO = availableCouponMap.get(usedCouponDTO.getCouponId());
                    //获取商品分摊的优惠明细
//                    Map<MarketItemDTO, Long> itemDiscountMap =
//                            getItemDiscountMap(couponDiscountMap.get(usedCouponDTO.getCouponId()), orderDTO.getOrderItems());
                    
                    Map<MarketItemDTO, Long> itemDiscountMap = new HashMap<MarketItemDTO, Long>();
                	if(orderDTOComb!=null && CollectionUtils.isNotEmpty(orderDTOComb.getOrderItems())){
                		itemDiscountMap = getCombItemDiscountMap(couponDiscountMap.get(usedCouponDTO.getCouponId()), orderDTO.getOrderItems(),orderDTOComb,orderDTONor,settlementInfo);                		
                	}else{
                		itemDiscountMap = getItemDiscountMap(couponDiscountMap.get(usedCouponDTO.getCouponId()), orderDTO.getOrderItems(),settlementInfo);
                	}
                    
//                    Map<MarketItemDTO, Long> itemDiscountMap =
//                    		getAssignItemDiscountMap(couponDiscountMap.get(usedCouponDTO.getCouponId()));

                    if(itemDiscountMap!= null && !itemDiscountMap.isEmpty()){
                    	//为每个命中优惠的商品生成一条商品优惠记录
                        for (Map.Entry<MarketItemDTO,Long> entry : itemDiscountMap.entrySet()) {
                            OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();

                            //TODO discountType、discountCode、discountDesc先写死，需重构成常量引用
                            orderDiscountInfoDO.setDiscountType(1);
                            orderDiscountInfoDO.setDiscountCode(ToolType.SIMPLE_TOOL.getCode());// TODO
                            orderDiscountInfoDO.setDiscountDesc("优惠券");
                            //该商品所分摊到的优惠金额
                            orderDiscountInfoDO.setDiscountAmount(entry.getValue());
                            //营销活动的总优惠金额
                            orderDiscountInfoDO.setActivityDiscountAmount(grantedCouponDTO.getDiscountAmount());
                            orderDiscountInfoDO.setUserId(orderDTO.getUserId());
                            orderDiscountInfoDO.setItemSkuId(entry.getKey().getItemSkuId());
                            orderDiscountInfoDO.setItemId(entry.getKey().getItemId());
//                            orderDiscountInfoDO.setDistributorId(entry.getKey().getDistributorId());
                            orderDiscountInfoDO.setMarketActivityId(grantedCouponDTO.getActivityId());
                            orderDiscountInfoDO.setUserCouponId(usedCouponDTO.getCouponId());
                            orderDiscountInfoDO.setCombOrderItemId(entry.getKey().getCombineItemSkuId());
                            //设置sku对应的订单的流水号
//                            orderDiscountInfoDO.setOrderSn(getOrderSn(entry.getKey().getItemSkuId()));
                            if(entry.getKey().getCombineItemSkuId()!=null){
	                        	orderDiscountInfoDO.setOrderSn(getCombOrderSn(entry.getKey().getItemSkuId(),entry.getKey().getCombineItemSkuId()));
	                        }else{
	                        	orderDiscountInfoDO.setOrderSn(getOrderSn(entry.getKey().getItemSkuId()));
	                        }
                            orderDiscountInfoDOs.add(orderDiscountInfoDO);
                        }
                    }

                    logger.info("[ORDER_SETTLEMENT_TRACE] itemDiscountMap:{}", JSONObject.toJSONString(itemDiscountMap));
                }
            }
        }
        logger.info("[ORDER_SETTLEMENT_TRACE] orderDiscountInfoDOs:{}", JSONObject.toJSONString(orderDiscountInfoDOs));
        return orderDiscountInfoDOs;
    }

    private String getOrderSn(Long skuId) {
        Boolean needSplitOrder = (Boolean)this.getAttr("needSplitOrder");
        if (needSplitOrder == true) {
            Map<Long, OrderDTO> skuOrderMap = (Map<Long, OrderDTO>) this.getAttr("skuOrderMap");
            OrderDTO orderDTO = skuOrderMap.get(skuId);

            if (orderDTO == null) {
                logger.error("the order is null, which is corresponding to the sku, skuId:{}", skuId);
                throw new IllegalStateException("the order is null, which is corresponding to the sku, skuId="+skuId);
            }

            return orderDTO.getOrderSn();
        }else{
            String mainOrderSn = (String)this.getAttr("mainOrderSn");//主订单的订单流水号
            return mainOrderSn;
        }
    }

    private String getCombOrderSn(Long skuId,Long combSkuId) {
        Boolean needSplitCombOrder = (Boolean)this.getAttr("needSplitCombOrder");
        if (needSplitCombOrder == true) {
//            Map<Long, OrderDTO> skuCombOrderMap = (Map<Long, OrderDTO>) this.getAttr("skuCombOrderMap");
        	List<OrderDTO> subCombOrderDTOList = (List<OrderDTO>) this.getAttr("subCombOrderDTOList");
//          OrderDTO orderDTO = skuCombOrderMap.get(skuId);
        	OrderDTO orderDTO = null;
//        	log.info(" >>>>>>>>>>>>>>subCombOrderDTOList: "+JSONObject.toJSONString(subCombOrderDTOList));
//        	log.info(" skuId,"+skuId+"combSkuId,"+combSkuId);
        	for(OrderDTO order : subCombOrderDTOList){
        		for(OrderItemDTO orderItemDTO :order.getOrderItems()){
        			if(orderItemDTO.getItemSkuId().equals(skuId) && orderItemDTO.getCombineItemSkuId().equals(combSkuId)){
        				orderDTO = order;
        				break;
        			}
        		}
        	}

            if (orderDTO == null) {
                logger.error("the order is null, which is corresponding to the sku, skuId:{}", skuId);
                throw new IllegalStateException("the order is null, which is corresponding to the sku, skuId="+skuId);
            }

            return orderDTO.getOrderSn();
        }else{
            String combOrderSn = (String)this.getAttr("combOrderSn");//主订单的订单流水号
            return combOrderSn;
        }
    }

    /**
     * 处理订单虚拟财富抵现场景，并生成对应的优惠明细列表
     * （1）如果本次下单需要拆单，则需要为每个订单生成对应的虚拟财富抵现优惠记录
     * @param orderDTO
     * @param settlementInfo
     * @return
     * @throws TradeException
     */
    private List<OrderDiscountInfoDO> processVirtualWealthDiscount(OrderDTO orderDTO,
                                                                   SettlementInfo settlementInfo) throws TradeException{
        List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();
//        // 虚拟账户使用信息处理
//        List<UsedWealthDTO> usedWealthDTOs = orderDTO.getUsedWealthDTOs();
//        List<WealthAccountDTO> wealthAccountDTOs = settlementInfo.getWealthAccountList();
//        Map<Long, WealthAccountDTO> availableWealthAccountMap = new HashMap<Long, WealthAccountDTO>();
//        if (wealthAccountDTOs != null) {
//            for (WealthAccountDTO wealthAccountDTO : wealthAccountDTOs) {
//                availableWealthAccountMap.put(wealthAccountDTO.getId(), wealthAccountDTO);
//            }
//        }
//
//        if (usedWealthDTOs != null) {
//            //由于虚拟财富是针对订单所有商品使用的，所以为了节省开销，不需要为每个订单商品记录一条orderDiscount记录
//            for (UsedWealthDTO usedWealthDTO : usedWealthDTOs) {
//                if (availableWealthAccountMap.containsKey(usedWealthDTO.getWealthAccountId())) {
//                    //获取用户的对应虚拟财富账户信息
//                    WealthAccountDTO wealthAccountDTO = availableWealthAccountMap.get(usedWealthDTO.getWealthAccountId());
//
//                    //账户余额小于用户希望使用的额度，则提示余额不足
//                    if (wealthAccountDTO.getAmount().longValue() < usedWealthDTO.getAmount().longValue()) {
//                        throw new TradeException(ResponseCode.BIZ_E_SPECIFIED_WEALTH_BALANCE_NOT_ENOUGH);
//                    }
//
//                    OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
//                    //使用积分抵现
//                    if (wealthAccountDTO.getWealthType() == 2 && null != usedWealthDTO.getAmount()) {
//                        usedWealthDTO.setPoint(usedWealthDTO.getAmount());
//                        Double exchangeRate = wealthAccountDTO.getExchangeRate();
//                        String exchangeAmount = MoneyUtil.getFormatMoney(
//                                MoneyUtil.mul(1d, MoneyUtil.mul(usedWealthDTO.getAmount() + "", exchangeRate + "")),
//                                "##0");
//                        usedWealthDTO.setWealthType(wealthAccountDTO.getWealthType());
//
//                        orderDiscountInfoDO.setDiscountAmount(Long.parseLong(exchangeAmount));
//                        //TODO 这里后续需要验证下客户端针对余额和积分的使用都传递amount
//                        orderDiscountInfoDO.setWealthAmount(usedWealthDTO.getAmount());
//                    } else if (wealthAccountDTO.getWealthType() == 1) {//使用余额抵现
//                        orderDiscountInfoDO.setDiscountAmount(usedWealthDTO.getAmount());
//                        orderDiscountInfoDO.setWealthAmount(usedWealthDTO.getAmount());
//                    }
//
//                    //TODO 这里的discountType、discountDesc等信息先写死，后续需要重构成常量引用
//                    orderDiscountInfoDO.setDiscountType(2);
//                    orderDiscountInfoDO.setDiscountCode("" + wealthAccountDTO.getWealthType());
//                    orderDiscountInfoDO.setDiscountDesc("虚拟账户");
//
//                    //处理拆单优惠结算相关逻辑
//                    Boolean needSplitOrder = (Boolean) this.getAttr("needSplitOrder");
//                    //如果不用拆单，则直接将生成的订单优惠信息添加到优惠记录集合中；如果需要拆单，则需要为每个订单生成一条订单优惠信息
//                    if (needSplitOrder == false) {
//                        //填入主订单的流水号
//                        String mainOrderSn = (String) this.getAttr("mainOrderSn");
//                        orderDiscountInfoDO.setOrderSn(mainOrderSn);
//                        orderDiscountInfoDOs.add(orderDiscountInfoDO);
//                    }else {//拆单优惠逻辑处理
//                        List<OrderDTO> subOrderList = (List<OrderDTO>) this.getAttr("subOrderDTOList");
//                        orderDiscountInfoDOs.addAll(splitOrderWealthDiscount(orderDiscountInfoDO, subOrderList));
//                    }
//
//
//                } else {// 用户希望使用的虚拟账户不在可用虚拟账户列表中
//                    throw new TradeException(ResponseCode.BIZ_E_SPECIFIED_WEALTH_UNAVAILABLE);
//                }
//            }
//        }

        return orderDiscountInfoDOs;
    }

    /**
     * 处理订单满减送优惠场景，并生成订单优惠明细列表
     * @return
     */
    private List<OrderDiscountInfoDO> processDirectDiscount(OrderDTO orderDTO,
                                                            SettlementInfo settlementInfo,String appKey,OrderDTO orderDTOComb,OrderDTO orderDTONor,List<OrderItemDO> orderItemDOList) throws TradeException {
    	
        List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();
        //营销活动优惠信息处理（这里尤指满减送）
        List<DiscountInfo> directDiscountInfoList = getDirectDiscountInfoList(settlementInfo);
        
        // 订单总金额 TODO 税费暂时为0
        Long orderAmount = orderDTO.getTotalPrice()+settlementInfo.getDeliveryFee()+0;
        
//        log.info(" processDirectDiscount directDiscountInfoList : "+JSONObject.toJSONString(directDiscountInfoList));
        
        if (directDiscountInfoList.isEmpty() == false) {
            for (DiscountInfo discountInfo : directDiscountInfoList) {
                MarketActivityDTO marketActivityDTO = discountInfo.getActivity();

                if (marketActivityDTO.getToolCode().equals(ToolType.COMPOSITE_TOOL.getCode())) { //满减送
                	// 满减送金额是否达标判断 TODO
                	if(orderAmount>=discountInfo.getConsume()){
                		
	                	Long subActivityId = 0l;
	                	List<MarketActivityDTO> subMarketActivityList = marketActivityDTO.getSubMarketActivityList();
	                	if(subMarketActivityList!=null && !subMarketActivityList.isEmpty()){
	                		if(subMarketActivityList.get(0) != null && subMarketActivityList.get(0).getId()!=null){
	                			subActivityId = subMarketActivityList.get(0).getId();
	                		}
	                	}
	                	
	                    /**
	                     * 订单商品优惠明细处理。由于订单里头不同的商品能享受到的优惠可能不一样，
	                     * 所以需要为订单商品列表中能享受到优惠的每个商品生成对应的优惠记录。
	                     */
//	                    Map<MarketItemDTO, Long> itemDiscountMap = getItemDiscountMap(discountInfo, orderDTO.getOrderItems());
	                	Map<MarketItemDTO, Long> itemDiscountMap = new HashMap<MarketItemDTO, Long>();
	                	if(orderDTOComb!= null && CollectionUtils.isNotEmpty(orderDTOComb.getOrderItems())){
	                		itemDiscountMap = getCombItemDiscountMap(discountInfo, orderDTO.getOrderItems(),orderDTOComb,orderDTONor,settlementInfo);
	                	}else{
	                		itemDiscountMap = getItemDiscountMap(discountInfo, orderDTO.getOrderItems(),settlementInfo);
	                	}
//	                    log.info("  >>>>>>>>>> itemDiscountMap : "+JSONObject.toJSONString(itemDiscountMap));
	                    // 赠品跟主商品同订单
//	                    String orderSnFGf = "";
//	                    List<StoreItemSkuDTO> originalStores = new ArrayList<StoreItemSkuDTO>();
//	                    List<StoreItemSkuDTO> giftsStores = new ArrayList<StoreItemSkuDTO>();
	                    
	                    //为每个命中优惠的商品生成一条商品优惠记录
	                    for (Map.Entry<MarketItemDTO,Long> entry : itemDiscountMap.entrySet()) {
	                        OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
	
	                        //TODO discountType、discountCode、discountDesc先写死，需重构成常量引用
	                        orderDiscountInfoDO.setDiscountType(1);
	                        orderDiscountInfoDO.setDiscountCode(marketActivityDTO.getToolCode());
	                        orderDiscountInfoDO.setDiscountDesc("活动优惠");
	                        //该商品分摊到的优惠金额
	                        orderDiscountInfoDO.setDiscountAmount(entry.getValue());
	                        //营销活动的优惠总额
	                        orderDiscountInfoDO.setActivityDiscountAmount(discountInfo.getDiscountAmount());
	                        orderDiscountInfoDO.setUserId(orderDTO.getUserId());
	                        orderDiscountInfoDO.setItemSkuId(entry.getKey().getItemSkuId());
//	                        orderDiscountInfoDO.setDistributorId(entry.getKey().getDistributorId());
	                        orderDiscountInfoDO.setMarketActivityId(discountInfo.getActivity().getId());
	                        orderDiscountInfoDO.setCombOrderItemId(entry.getKey().getCombineItemSkuId());
	                        // 记录子活动id
	                        orderDiscountInfoDO.setSubMarketActivityId(subActivityId);
	                        // 优惠券id默认为0 
	                        orderDiscountInfoDO.setUserCouponId(0L);
	                        //设置sku对应的订单的流水号
                            if(entry.getKey().getCombineItemSkuId()!=null){
	                        	orderDiscountInfoDO.setOrderSn(getCombOrderSn(entry.getKey().getItemSkuId(),entry.getKey().getCombineItemSkuId()));
	                        }else{
	                        	orderDiscountInfoDO.setOrderSn(getOrderSn(entry.getKey().getItemSkuId()));
	                        }
//	                        orderSnFGf=getOrderSn(entry.getKey().getItemSkuId());
	                        orderDiscountInfoDOs.add(orderDiscountInfoDO);
	                        
	                        //主商品的仓库信息
//	                        originalStores.add(itemManager.getStoreInfo(entry.getKey().getItemSkuId(), new Long(entry.getKey().getNumber()), appKey));
	                    }

	                	//order_discount中添加赠品信息
                		/*if(discountInfo.getGiftList()!=null && !discountInfo.getGiftList().isEmpty()){
                			for(MarketItemDTO marketItemDTO:discountInfo.getGiftList()){
                				OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
                				
    	                        //TODO discountType、discountCode、discountDesc先写死，需重构成常量引用
    	                        orderDiscountInfoDO.setDiscountType(1);
    	                        orderDiscountInfoDO.setDiscountCode(marketActivityDTO.getToolCode());
    	                        orderDiscountInfoDO.setDiscountDesc("活动优惠");
    	                        //该商品分摊到的优惠金额
    	                        orderDiscountInfoDO.setDiscountAmount(0l);
    	                        //营销活动的优惠总额
    	                        orderDiscountInfoDO.setActivityDiscountAmount(discountInfo.getDiscountAmount());
    	                        orderDiscountInfoDO.setUserId(orderDTO.getUserId());
    	                        orderDiscountInfoDO.setItemSkuId(marketItemDTO.getItemSkuId());
//    	                        orderDiscountInfoDO.setDistributorId(orderDTO.getOrderItems().get(0).getDistributorId());
    	                        orderDiscountInfoDO.setMarketActivityId(discountInfo.getActivity().getId());
    	                        // 记录子活动id
    	                        orderDiscountInfoDO.setSubMarketActivityId(subActivityId);
    	                        // 优惠券id默认为0 
    	                        orderDiscountInfoDO.setUserCouponId(0L);
    	                        //设置sku对应的订单的流水号
    	                        orderDiscountInfoDO.setOrderSn(orderSnFGf);
    	                        orderDiscountInfoDOs.add(orderDiscountInfoDO);
    	                        
    	                        //赠品的仓库信息
    	                        giftsStores.add(itemManager.getStoreInfo(marketItemDTO.getItemSkuId(), new Long(marketItemDTO.getNumber()), appKey));
                			}
                			
                			// 同一满减送活动判断赠品和主商品是否同仓库 TODO
                			if(giftsStores!=null && !giftsStores.isEmpty()){
                				if(originalStores!=null && !originalStores.isEmpty()){
                					for(StoreItemSkuDTO storeItemSkuDTO:giftsStores){
                						for(int i=0;i< originalStores.size();i++){
                							if(originalStores.get(i).getStoreId() == storeItemSkuDTO.getStoreId()){
                								break;
                							}else{
                								if(i==originalStores.size()-1){
                                        			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION,"同一活动优惠赠品和主商品不同仓库，无法生成订单");
                                        		}
                							}
                						}
                						
                					}
                				}
                			}
                    		
                    		
                		}*/
                		
                		
                	}
                    
                }

                if (marketActivityDTO.getToolCode().equals(ToolType.TIME_RANGE_DISCOUNT.getCode())) { //限时购

                    /**
                     * 订单商品优惠明细处理。由于订单里头不同的商品能享受到的优惠可能不一样，
                     * 所以需要为订单商品列表中能享受到优惠的每个商品生成对应的优惠记录。
                     */
                    log.info(" >>>>>>>>>> discountInfo : "+JSONObject.toJSONString(discountInfo));
                    for(MarketItemDTO marketItemDTO:discountInfo.getItemList()){
                    	OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();

                        //TODO discountType、discountCode、discountDesc先写死，需重构成常量引用
                        orderDiscountInfoDO.setDiscountType(4);
                        orderDiscountInfoDO.setDiscountCode(marketActivityDTO.getToolCode());
                        orderDiscountInfoDO.setDiscountDesc("限时购");
                        //该商品分摊到的优惠金额
                        // 计算限时购的优惠金额
//                        ItemSkuDTO itemSkuDTO = this.getSkuInfo(marketItemDTO.getItemSkuId(),appKey);
                        if(CollectionUtils.isNotEmpty(orderItemDOList)){
                        	for(OrderItemDO orderItemDO:orderItemDOList){
                        		if(orderItemDO.getItemSkuId().equals(marketItemDTO.getItemSkuId())){
                        			orderItemDO.setUnitPrice(marketItemDTO.getUnitPrice());
                        			// 限购数量处理
                        			log.info(" orderItemDO.getNumber() : "+orderItemDO.getNumber()+" marketItemDTO.getLimitNumber(): "+marketItemDTO.getLimitNumber());
                        			if(orderItemDO.getNumber() > marketItemDTO.getLimitNumber()){
                        				log.error(" skuid:"+orderItemDO.getItemSkuId()+" 购买数量大于限购数量 ");
                        				throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT,"购买数量大于限购数量");
                            		}
                        		}
                        		
                        	}
                        }
                        orderDiscountInfoDO.setDiscountAmount(0l);
                        //营销活动的优惠总额
                        orderDiscountInfoDO.setActivityDiscountAmount(discountInfo.getDiscountAmount());
                        orderDiscountInfoDO.setUserId(orderDTO.getUserId());
                        orderDiscountInfoDO.setItemSkuId(marketItemDTO.getItemSkuId());
//                        orderDiscountInfoDO.setDistributorId(marketItemDTO.getDistributorId());
                        orderDiscountInfoDO.setMarketActivityId(discountInfo.getActivity().getId());
                        // 优惠券id默认为0 
                        orderDiscountInfoDO.setUserCouponId(0L);
                        //设置sku对应的订单的流水号
                        orderDiscountInfoDO.setOrderSn(getOrderSn(marketItemDTO.getItemSkuId()));
                        orderDiscountInfoDOs.add(orderDiscountInfoDO);
                    }

                }
            }

        }
        // TODO 本次订单所满足的优惠信息校验
        return orderDiscountInfoDOs;
    }
    
    private ItemSkuDTO getSkuInfo(Long skuId,String appKey) throws TradeException{

    	ItemManager itemManager = (ItemManager) this.getBean("itemManager");
    	try {
    		List<ItemSkuDTO> itemSkuDTOs = itemManager.queryItemSku(skuId, appKey);
    		if(CollectionUtils.isEmpty(itemSkuDTOs)){
    			throw new TradeException(ResponseCode.SYS_E_DEFAULT_ERROR," skuId:"+skuId+" ,itemSkuClient.queryItemSku sku is null");
    		}
    		return itemSkuDTOs.get(0);
		} catch (TradeException e) {
			throw new TradeException(e);
		}
    	
    }
    
    /**
     * 处理订单满减送优惠场景，并生成订单优惠明细列表
     * @return
     */
    private List<OrderDiscountInfoDO> processDirectFirstDiscount(OrderDTO orderDTO,
                                                            SettlementInfo settlementInfo,List<OrderDiscountInfoDO> resultOrderDiscounts,OrderDTO orderDTOComb,OrderDTO orderDTONor) throws TradeException {

        List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();
        //营销活动优惠信息处理（这里尤指满减送）
        List<DiscountInfo> directDiscountInfoList = getDirectDiscountInfoList(settlementInfo);
        
        // 订单总金额 TODO 税费暂时为0
        Long orderAmount = orderDTO.getTotalPrice()+settlementInfo.getDeliveryFee()+0;
        
        Long currentDiscountAmount = 0l;
        
        for(OrderDiscountInfoDO orderDiscountInfoDO:resultOrderDiscounts){
        	currentDiscountAmount  = currentDiscountAmount + orderDiscountInfoDO.getDiscountAmount();
        }
        
        Long limitAmount = orderAmount - currentDiscountAmount;
        

//        log.info(" limitAmount : "+limitAmount+" orderAmount : "+orderAmount+" currentDiscountAmount : "+currentDiscountAmount);
        
//        log.info(" processDirectDiscount directDiscountInfoList : "+JSONObject.toJSONString(directDiscountInfoList));
        
        if (directDiscountInfoList.isEmpty() == false) {
            for (DiscountInfo discountInfo : directDiscountInfoList) {
                MarketActivityDTO marketActivityDTO = discountInfo.getActivity();

                if (marketActivityDTO.getToolCode().equals(ToolType.FIRST_ORDER_DISCOUNT.getCode())) { //首单立减
                	// 首单金额是否达标判断 TODO
                	if(limitAmount>=discountInfo.getConsume()){
                	
	                    /**
	                     * 订单商品优惠明细处理。由于订单里头不同的商品能享受到的优惠可能不一样，
	                     * 所以需要为订单商品列表中能享受到优惠的每个商品生成对应的优惠记录。
	                     */
//	                    Map<MarketItemDTO, Long> itemDiscountMap = getItemDiscountMap(discountInfo, orderDTO.getOrderItems());
	                    
	                    Map<MarketItemDTO, Long> itemDiscountMap = new HashMap<MarketItemDTO, Long>();
	                	if(orderDTOComb!=null && CollectionUtils.isNotEmpty(orderDTOComb.getOrderItems())){
	                		itemDiscountMap = getCombItemDiscountMap(discountInfo, orderDTO.getOrderItems(),orderDTOComb,orderDTONor,settlementInfo);	                		
	                	}else{
	                		itemDiscountMap = getItemDiscountMap(discountInfo, orderDTO.getOrderItems(),settlementInfo);
	                	}
	
	                    //为每个命中优惠的商品生成一条商品优惠记录
	                    for (Map.Entry<MarketItemDTO,Long> entry : itemDiscountMap.entrySet()) {
	                        OrderDiscountInfoDO orderDiscountInfoDO = new OrderDiscountInfoDO();
	
	                        //TODO discountType、discountCode、discountDesc先写死，需重构成常量引用
	                        orderDiscountInfoDO.setDiscountType(5);
	                        orderDiscountInfoDO.setDiscountCode(marketActivityDTO.getToolCode());
	                        orderDiscountInfoDO.setDiscountDesc("首单立减");
	                        //该商品分摊到的优惠金额
	                        orderDiscountInfoDO.setDiscountAmount(entry.getValue());
	                        //营销活动的优惠总额
	                        orderDiscountInfoDO.setActivityDiscountAmount(discountInfo.getDiscountAmount());
	                        orderDiscountInfoDO.setUserId(orderDTO.getUserId());
	                        orderDiscountInfoDO.setItemSkuId(entry.getKey().getItemSkuId());
//	                        orderDiscountInfoDO.setDistributorId(entry.getKey().getDistributorId());
	                        orderDiscountInfoDO.setMarketActivityId(discountInfo.getActivity().getId());
	                        orderDiscountInfoDO.setCombOrderItemId(entry.getKey().getCombineItemSkuId());
	                        // 优惠券id默认为0 
	                        orderDiscountInfoDO.setUserCouponId(0L);
	                        //设置sku对应的订单的流水号
//	                        orderDiscountInfoDO.setOrderSn(getOrderSn(entry.getKey().getItemSkuId()));
                            if(entry.getKey().getCombineItemSkuId()!=null){
	                        	orderDiscountInfoDO.setOrderSn(getCombOrderSn(entry.getKey().getItemSkuId(),entry.getKey().getCombineItemSkuId()));
	                        }else{
	                        	orderDiscountInfoDO.setOrderSn(getOrderSn(entry.getKey().getItemSkuId()));
	                        }
	                        orderDiscountInfoDOs.add(orderDiscountInfoDO);
	                    }
	                    
                	}
                }
            }

        }
        // TODO 本次订单所满足的优惠信息校验
        return orderDiscountInfoDOs;
    }
    
    /**
     * TODO 处理会员优惠明细（由于嗨云不涉及会员优惠，所以可以暂时不处理）
     * （1）如果会员优惠是订单级别，且本次下单需要拆单，则需要为每个订单生成一条优惠记录
     * （2）如果会员优惠是商品级别，则需要为每个涉及到优惠的订单商品都生成一条优惠记录
     * @param orderDTO
     * @param settlementInfo
     * @return
     * @throws TradeException
     */
    private List<OrderDiscountInfoDO> processMemberDiscount(OrderDTO orderDTO,
                                                            SettlementInfo settlementInfo) throws TradeException {
        String bizCode = (String) this.getAttr("biz_code");
        //会员优惠处理
        OrderDiscountInfoManager orderDiscountInfoManager =
                (OrderDiscountInfoManager) this.getBean("orderDiscountInfoManager");
        OrderDiscountInfoDO memberDiscount = orderDiscountInfoManager.genMemberDiscount(settlementInfo,
                orderDTO.getUserId(), bizCode);

        List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();
        if (null != memberDiscount) {
            orderDiscountInfoDOs.add(memberDiscount);
        }

        return orderDiscountInfoDOs;
    }

    /**
     * 获取订单商品所分摊到的优惠的映射关系集
     * @param discountInfo
     * @return
     */
    private Map<MarketItemDTO, Long> getCombItemDiscountMap(DiscountInfo discountInfo, List<OrderItemDTO> orderItemList,OrderDTO orderDTOComb,OrderDTO orderDTONor,SettlementInfo settlementInfo) {

        long totalItemPrice = 0L;
        long totalDiscountAmount = 0L;       

//    	log.info(" -----------null pointer discountInfo : "+JSONObject.toJSONString(discountInfo));
//    	log.info(" null pointer orderItemList : "+JSONObject.toJSONString(orderItemList));
        //FIXME 这里为了兼容营销结算时如果所有商品都满足优惠条件的话，直接返回空的商品列表的情况。通过伪造填充discountInfo的itemList属性来处理
        if (discountInfo.getItemList() != null && !discountInfo.getItemList().isEmpty()) {
//        	log.info(" ============================== ");
        	
        	Boolean isComb = false;
        	List<Long> skuids= new ArrayList<Long>();
        	// 判断享受满减的组合商品
        	for (int i=0;i<discountInfo.getItemList().size();i++) {
            	for(OrderItemDTO orderItemDTO:orderItemList){
                	if(discountInfo.getItemList().get(i).getItemSkuId().equals(orderItemDTO.getItemSkuId()) && orderItemDTO.getCombineItemSkuId()!=null){
                		skuids.add(orderItemDTO.getItemSkuId());
                		isComb=true;
                		discountInfo.getItemList().remove(i);
                		break;
                	}
                }
            }
        	if(isComb){
//        		log.info(" %%%%%%%%% skuids "+JSONObject.toJSONString(skuids));
        		List<OrderItemDTO> orderItemDTODis = new ArrayList<OrderItemDTO>();
        		
        		if(CollectionUtils.isNotEmpty(skuids)){
        			for(int i=0;i<skuids.size();i++){
        				for(int j=0;j<orderDTOComb.getOrderItems().size();j++){
        					if(skuids.get(i).equals(orderDTOComb.getOrderItems().get(j).getCombineItemSkuId())){
        						orderItemDTODis.add(orderDTOComb.getOrderItems().get(j));
        					}
        				}
        			}
        		}
//        		log.info(" %%%%%%%%% orderItemDTODis "+JSONObject.toJSONString(orderItemDTODis));
        		if(CollectionUtils.isNotEmpty(discountInfo.getItemList())){
            		discountInfo.getItemList().addAll(genMarketItemList(orderItemDTODis)); 
//            		log.info(" 11111 discountInfo : "+JSONObject.toJSONString(discountInfo));
            	}else{
            		discountInfo.setItemList((genMarketItemList(orderItemDTODis)));
//            		log.info(" 22222 pointer discountInfo : "+JSONObject.toJSONString(discountInfo));
            	}
        	}
        	
        	
        }else{
//        	log.info(" $$$$$$$$$$$$$$$$$$$$$$$$$$$$ ");
        	discountInfo.setItemList(genMarketItemList(orderDTOComb.getOrderItems()));
        	if(orderDTONor!=null && CollectionUtils.isNotEmpty(orderDTONor.getOrderItems())){
        		discountInfo.getItemList().addAll(genMarketItemList(orderDTONor.getOrderItems()));
        	}
        	
            
        }
//    	log.info(" >>>>>>>>>null pointer discountInfo : "+JSONObject.toJSONString(discountInfo));
                
        // 针对优惠券场景的判断
        if(ToolType.SIMPLE_TOOL.getCode().equals(discountInfo.getActivity().getToolCode()) ){
        	// (限时购活动单品不允许使用优惠券在优惠券商品列表去除)
            List<DiscountInfo> discountInfoList = settlementInfo.getDiscountInfoList();
            log.info(" ##### coupon discountInfoList ："+JsonUtil.toJson(discountInfoList));
            log.info(" ##### coupon before discountInfo ："+JsonUtil.toJson(discountInfo));
            if(CollectionUtils.isNotEmpty(discountInfoList)){
            	for (int i=0;i< discountInfo.getItemList().size();i++) {
            		for(DiscountInfo discountInfoTmp : discountInfoList){
            			// 限时购商品不允许使用优惠券
            			if(discountInfoTmp.getActivity().getCouponMark() == 0){
            				for(MarketItemDTO marketItemDTO :discountInfoTmp.getItemList()){
            					if(marketItemDTO.getItemSkuId().equals(discountInfo.getItemList().get(i).getItemSkuId())){
            						discountInfo.getItemList().remove(i);
            					}
            				}
            			}
                	}
            	}        	
            }
            log.info(" ##### coupon after discountInfo ："+JsonUtil.toJson(discountInfo));
        }
        
        
        //计算满足营销条件的所有商品的总价
        for (MarketItemDTO marketItemDTO : discountInfo.getItemList()) {
//        	log.info(" null pointer marketItemDTO : "+JSONObject.toJSONString(marketItemDTO));
            totalItemPrice += marketItemDTO.getUnitPrice()*marketItemDTO.getNumber();
        }
//        log.info(" ########## totalItemPrice : "+totalItemPrice);
        Map<MarketItemDTO, Long> itemDiscountMap = new HashMap<MarketItemDTO, Long>();
        for (int i=0; i<discountInfo.getItemList().size(); i++) {
            MarketItemDTO marketItemDTO = discountInfo.getItemList().get(i);
            long itemPrice = marketItemDTO.getUnitPrice() * marketItemDTO.getNumber();
            long itemDiscountAmount = (itemPrice*discountInfo.getDiscountAmount())/totalItemPrice;
            totalDiscountAmount += itemDiscountAmount;

            //如果所有商品分摊完，还有一些零头分摊不尽的话，将零头优惠额补到最后一个遍历的商品上去
            if((i==discountInfo.getItemList().size()-1) && discountInfo.getDiscountAmount()>totalDiscountAmount) {
                itemDiscountAmount += (discountInfo.getDiscountAmount() - totalDiscountAmount);
            }

            itemDiscountMap.put(marketItemDTO, itemDiscountAmount);
        }
        
//        log.info(" ########## itemDiscountMap : "+JSONObject.toJSONString(itemDiscountMap));

//        logger.info("[ORDER_SETTLEMENT_TRACE] discountInfo:{}, orderItemList:{}, itemDiscountMap:{}",
//                JsonUtil.toJson(discountInfo), JsonUtil.toJson(orderItemList), JsonUtil.toJson(itemDiscountMap));

        return itemDiscountMap;
    }

    /**
     * 获取订单商品所分摊到的优惠的映射关系集
     * @param discountInfo
     * @return
     */
    private Map<MarketItemDTO, Long> getItemDiscountMap(DiscountInfo discountInfo, List<OrderItemDTO> orderItemList,SettlementInfo settlementInfo) {

        long totalItemPrice = 0L;
        long totalDiscountAmount = 0L;
        
        

//    	log.info(" -----------null pointer discountInfo : "+JSONObject.toJSONString(discountInfo));
//    	log.info(" null pointer orderItemList : "+JSONObject.toJSONString(orderItemList));
        //FIXME 这里为了兼容营销结算时如果所有商品都满足优惠条件的话，直接返回空的商品列表的情况。通过伪造填充discountInfo的itemList属性来处理
        if (discountInfo.getItemList() == null || discountInfo.getItemList().isEmpty()) {
            discountInfo.setItemList(genMarketItemList(orderItemList));
        }
//    	log.info(" >>>>>>>>>null pointer discountInfo : "+JSONObject.toJSONString(discountInfo));
        // 针对优惠券场景的判断
        if(ToolType.SIMPLE_TOOL.getCode().equals(discountInfo.getActivity().getToolCode()) ){
        	// (限时购活动单品不允许使用优惠券在优惠券商品列表去除)
            List<DiscountInfo> discountInfoList = settlementInfo.getDiscountInfoList();
            log.info(" ##### coupon discountInfoList ："+JsonUtil.toJson(discountInfoList));
            log.info(" ##### coupon before discountInfo ："+JsonUtil.toJson(discountInfo));
            if(CollectionUtils.isNotEmpty(discountInfoList)){
            	for (int i=0;i< discountInfo.getItemList().size();i++) {
            		for(DiscountInfo discountInfoTmp : discountInfoList){
            			// 限时购商品不允许使用优惠券
            			if(discountInfoTmp.getActivity().getCouponMark() == 0){
            				for(MarketItemDTO marketItemDTO :discountInfoTmp.getItemList()){
            					if(marketItemDTO.getItemSkuId().equals(discountInfo.getItemList().get(i).getItemSkuId())){
            						discountInfo.getItemList().remove(i);
            					}
            				}
            			}
                	}
            	}        	
            }
            log.info(" ##### coupon after discountInfo ："+JsonUtil.toJson(discountInfo));
        }

        //计算满足营销条件的所有商品的总价
        for (MarketItemDTO marketItemDTO : discountInfo.getItemList()) {
//        	log.info(" null pointer marketItemDTO : "+JSONObject.toJSONString(marketItemDTO));
            totalItemPrice += marketItemDTO.getUnitPrice()*marketItemDTO.getNumber();
        }

        Map<MarketItemDTO, Long> itemDiscountMap = new HashMap<MarketItemDTO, Long>();
        for (int i=0; i<discountInfo.getItemList().size(); i++) {
            MarketItemDTO marketItemDTO = discountInfo.getItemList().get(i);
            long itemPrice = marketItemDTO.getUnitPrice() * marketItemDTO.getNumber();
            long itemDiscountAmount = (itemPrice*discountInfo.getDiscountAmount())/totalItemPrice;
            totalDiscountAmount += itemDiscountAmount;

            //如果所有商品分摊完，还有一些零头分摊不尽的话，将零头优惠额补到最后一个遍历的商品上去
            if((i==discountInfo.getItemList().size()-1) && discountInfo.getDiscountAmount()>totalDiscountAmount) {
                itemDiscountAmount += (discountInfo.getDiscountAmount() - totalDiscountAmount);
            }

            itemDiscountMap.put(marketItemDTO, itemDiscountAmount);
        }

//        logger.info("[ORDER_SETTLEMENT_TRACE] discountInfo:{}, orderItemList:{}, itemDiscountMap:{}",
//                JsonUtil.toJson(discountInfo), JsonUtil.toJson(orderItemList), JsonUtil.toJson(itemDiscountMap));

        return itemDiscountMap;
    }

    /**
     * 获取指定优惠券商品所分摊到的优惠的映射关系集
     * @param discountInfo
     * @return
     */
    private Map<MarketItemDTO, Long> getAssignItemDiscountMap(DiscountInfo discountInfo) {

        long totalItemPrice = 0L;
        long totalDiscountAmount = 0L;

        //FIXME 这里为了兼容营销结算时如果所有商品都满足优惠条件的话，直接返回空的商品列表的情况。通过伪造填充discountInfo的itemList属性来处理
        if (discountInfo.getItemList() == null || discountInfo.getItemList().isEmpty()) {
           return null;
        }

        //计算满足营销条件的所有商品的总价
        for (MarketItemDTO marketItemDTO : discountInfo.getItemList()) {
            totalItemPrice += marketItemDTO.getUnitPrice()*marketItemDTO.getNumber();
        }

        Map<MarketItemDTO, Long> itemDiscountMap = new HashMap<MarketItemDTO, Long>();
        for (int i=0; i<discountInfo.getItemList().size(); i++) {
            MarketItemDTO marketItemDTO = discountInfo.getItemList().get(i);
            long itemPrice = marketItemDTO.getUnitPrice() * marketItemDTO.getNumber();
            long itemDiscountAmount = (itemPrice*discountInfo.getDiscountAmount())/totalItemPrice;
            totalDiscountAmount += itemDiscountAmount;

            //如果所有商品分摊完，还有一些零头分摊不尽的话，将零头优惠额补到最后一个遍历的商品上去
            if((i==discountInfo.getItemList().size()-1) && discountInfo.getDiscountAmount()>totalDiscountAmount) {
                itemDiscountAmount += (discountInfo.getDiscountAmount() - totalDiscountAmount);
            }

            itemDiscountMap.put(marketItemDTO, itemDiscountAmount);
        }

        logger.info("[ORDER_SETTLEMENT_TRACE] discountInfo:{}, itemDiscountMap:{}",
                JsonUtil.toJson(discountInfo), JsonUtil.toJson(itemDiscountMap));

        return itemDiscountMap;
    }

    /**
     * 拆解订单虚拟财富抵现记录
     * @param orderDiscountInfoDO
     * @param subOrderList
     * @return
     */
    private List<OrderDiscountInfoDO> splitOrderWealthDiscount(OrderDiscountInfoDO orderDiscountInfoDO,
                                                         List<OrderDTO> subOrderList) {
        long totalItemPrice = 0L;
        long totalDiscountAmount = 0L;
        long totalWealthAmount = 0L;

        for (OrderDTO orderDTO: subOrderList) {
            totalItemPrice += orderDTO.getTotalPrice();
        }

        List<OrderDiscountInfoDO> orderDiscountInfoDOs = new ArrayList<OrderDiscountInfoDO>();
//        for (int i = 0; i < subOrderList.size(); i++) {
//            OrderDTO subOrder = subOrderList.get(i);
//            OrderDiscountInfoDO subOrderDiscountInfo = new OrderDiscountInfoDO();
//
//            try {
//                BeanUtils.copyProperties(subOrderDiscountInfo, orderDiscountInfoDO);
//                long orderDiscountAmount =
//                        (subOrder.getTotalPrice() * orderDiscountInfoDO.getDiscountAmount()) / totalItemPrice;
//                long orderWealthAmount =
//                        (subOrder.getTotalPrice() * orderDiscountInfoDO.getWealthAmount()) / totalItemPrice;
//
//                totalDiscountAmount += orderDiscountAmount;
//                totalWealthAmount += orderWealthAmount;
//
//                if (i == subOrderList.size() - 1 && orderDiscountInfoDO.getDiscountAmount() > totalDiscountAmount) {
//                    orderDiscountAmount += (orderDiscountInfoDO.getDiscountAmount() - totalDiscountAmount);
//                }
//
//                if (i == subOrderList.size() - 1 && orderDiscountInfoDO.getWealthAmount() > totalWealthAmount) {
//                    orderWealthAmount += (orderDiscountInfoDO.getWealthAmount() - totalWealthAmount);
//                }
//
//                subOrderDiscountInfo.setOrderSn(subOrder.getOrderSn());
//                subOrderDiscountInfo.setDiscountAmount(orderDiscountAmount);
//                subOrderDiscountInfo.setWealthAmount(orderWealthAmount);
//                orderDiscountInfoDOs.add(subOrderDiscountInfo);
//            } catch (Exception ignore) {
//            }
//        }

        return orderDiscountInfoDOs;
    }

    private SettlementInfo getSettlementInfo(Long userId, List<OrderItemDTO> orderItems, Long consigneeId,
                                             String appkey) throws TradeException {
        MarketingManager marketingManager = (MarketingManager) this.getBean("marketingManager");
        SettlementInfo settlementInfo = null;
        if (null == consigneeId) {
            settlementInfo = marketingManager.getSettlementInfo(userId, orderItems, null, appkey);
        } else {
            settlementInfo = marketingManager.getSettlementInfo(userId, orderItems, consigneeId, appkey);
        }

        return settlementInfo;
    }

    private List<DiscountInfo> getDiscountInfoList(SettlementInfo settlementInfo) throws TradeException {
        if (settlementInfo == null) {
            return Collections.EMPTY_LIST;
        }
        // 营销活动优惠信息处理
        List<DiscountInfo> discountInfoList = settlementInfo.getDiscountInfoList();
        return discountInfoList;
    }

    private List<DiscountInfo> getDirectDiscountInfoList(SettlementInfo settlementInfo) throws TradeException {
        if (settlementInfo == null) {
            return Collections.EMPTY_LIST;
        }
        // 营销活动优惠信息处理
        List<DiscountInfo> discountInfoList = settlementInfo.getDirectDiscountList();
        return discountInfoList;
    }
    
    private List<MarketItemDTO> genMarketItemList(List<OrderItemDTO> orderItemDTOs) {
        if (orderItemDTOs == null || orderItemDTOs.isEmpty()) {
            return Collections.emptyList();
        }

        List<MarketItemDTO> marketItemDTOs = new ArrayList<MarketItemDTO>();
        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            MarketItemDTO marketItemDTO = genMarketItem(orderItemDTO);
            marketItemDTOs.add(marketItemDTO);
        }

        return marketItemDTOs;
    }

    private MarketItemDTO genMarketItem(OrderItemDTO orderItemDTO) {
        MarketItemDTO marketItemDTO = new MarketItemDTO();
        marketItemDTO.setItemId(orderItemDTO.getItemId());
        marketItemDTO.setItemSkuId(orderItemDTO.getItemSkuId());
        marketItemDTO.setNumber(orderItemDTO.getNumber());
        marketItemDTO.setSellerId(orderItemDTO.getSellerId());
        marketItemDTO.setUnitPrice(orderItemDTO.getUnitPrice());
        marketItemDTO.setCombineItemSkuId(orderItemDTO.getCombineItemSkuId());
//        marketItemDTO.setDistributorId(orderItemDTO.getDistributorId());

        return marketItemDTO;
    }
}
