package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.domain.dto.CommissionUnitDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO.OrderSku;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.UsedCouponDTO;
import com.mockuai.tradecenter.common.domain.UsedWealthDTO;
import com.mockuai.tradecenter.core.domain.*;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.*;
import com.mockuai.tradecenter.core.manager.impl.OrderDiscountInfoManagerImpl;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.util.ModelUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/19/16.
 */
public class HandleOrderTransactionStep extends TradeBaseStep {

    private static final Logger log = LoggerFactory.getLogger(HandleOrderTransactionStep.class);
	
    @Override
    public StepName getName() {
        return StepName.HANDLE_ORDER_TRANSACTION_STEP;
    }

    @Override
    public TradeResponse execute() {
        final OrderDTO orderDTO = (OrderDTO) this.getParam("orderDTO");
        final OrderDTO orderDTOComb = (OrderDTO) this.getAttr("orderDTOComb");
        final OrderDTO orderDTONor = (OrderDTO) this.getAttr("orderDTONor");
        final String appKey = (String) this.getAttr("appKey");
        final UserConsigneeDTO consigneeDTO = (UserConsigneeDTO) this.getAttr("consigneeDTO");
        final List<OrderDiscountInfoDO> orderDiscountInfoDOs = (List<OrderDiscountInfoDO>) this.getAttr("orderDiscountInfoDOs");
        final List<OrderDiscountInfoDO> orderCombDiscountInfoDOs = (List<OrderDiscountInfoDO>) this.getAttr("orderCombDiscountInfoDOs");
        final Map<String,List<OrderDiscountInfoDO>> orderDiscountListMap = (Map<String,List<OrderDiscountInfoDO>>)this.getAttr("orderDiscountListMap");
        final Map<String,List<OrderDiscountInfoDO>> orderCombDiscountListMap = (Map<String,List<OrderDiscountInfoDO>>)this.getAttr("orderCombDiscountListMap");
        
        
        final List<OrderItemDO> orderItemDOList = (List<OrderItemDO>) this.getAttr("orderItemList");
        final Map<String,List<OrderItemDO>> orderItemListMap = (Map<String,List<OrderItemDO>>)this.getAttr("orderItemListMap");
        final List<OrderDO> subOrderList = (List<OrderDO>) this.getAttr("subOrderList");
        final OrderDO mainOrder = (OrderDO) this.getAttr("mainOrder");
//        final Map<Long, ItemSkuDTO> itemSkuMap = (Map<Long, ItemSkuDTO>) this.getAttr("itemSkuMap");
        final Boolean needSplitOrder = (Boolean)this.getAttr("needSplitOrder");
        
        final OrderDO rootOrder = (OrderDO) this.getAttr("rootOrder");

        final List<OrderItemDO> orderCombItemList = (List<OrderItemDO>) this.getAttr("orderCombItemList");
        final Map<String,List<OrderItemDO>> orderCombItemListMap = (Map<String,List<OrderItemDO>>)this.getAttr("orderCombItemListMap");
        final List<OrderDO> subCombOrderList = (List<OrderDO>) this.getAttr("subCombOrderList");
        final OrderDO combOrder = (OrderDO) this.getAttr("combOrder");
        final Boolean needSplitCombOrder = (Boolean)this.getAttr("needSplitCombOrder");

        TransactionTemplate transactionTemplate = (TransactionTemplate) this.getBean("transactionTemplate");

        //下单事务
        TradeResponse transResult = transactionTemplate.execute(new TransactionCallback<TradeResponse>() {

            public TradeResponse doInTransaction(TransactionStatus transactionStatus) {

            	OrderDTO responseOrderDTO = new OrderDTO();
            	
                try {
                    //移除购物车中对应商品
//                    removeCartItem(orderDTO, itemSkuMap);
                    removeCartItem(orderDTO);
                    
                    // 根订单信息不为空
                    if(rootOrder != null){
                    	//添加主订单数据库记录
                        long orderId = addOrderRecord(rootOrder, orderDTO);
                        rootOrder.setId(orderId);
                        
                        //添加订单收货地址信息
                        addOrderConsigneeDO(TradeUtil.genOrderConsignee(consigneeDTO), rootOrder);
                        
                    }

                    if(CollectionUtils.isNotEmpty(orderItemDOList)){
                    	//如果是秒杀订单，删除预单数据 TODO 秒杀逻辑优化
                        deletePreOrder(mainOrder,orderItemDOList);
                        
                        //添加主订单数据库记录
                        if(rootOrder!=null){
                        	 mainOrder.setOriginalOrder(rootOrder.getId());
                        }                       
                        long orderId = addOrderRecord(mainOrder, orderDTONor);
                        mainOrder.setId(orderId);

                        //添加订单商品列表
                        if(needSplitOrder == false){
                            addOrderItemDO(mainOrder, orderItemDOList, consigneeDTO.getConsignee(), appKey);
                        }

                        //添加订单支付信息
                        addOrderPaymentDO(mainOrder, appKey);

                        //添加订单收货地址信息
                        addOrderConsigneeDO(TradeUtil.genOrderConsignee(consigneeDTO), mainOrder);

//                        log.info(" orderDiscountInfoDOs: "+JSONObject.toJSONString(orderDiscountInfoDOs));
                        
                        //添加订单优惠列表信息
                        addOrderDiscountDO(orderDiscountInfoDOs, mainOrder);

                        //处理无需额外支付的订单（即全额使用余额或积分抵现的订单）
                        if (mainOrder.getTotalAmount() <= 0) {
                            processNoNeedPayOrder(mainOrder,null);
                        }

                        //拆单处理逻辑
                        if(needSplitOrder == true){
                            for (OrderDO orderDO : subOrderList) {
                                //设置子订单的父订单id
                                orderDO.setOriginalOrder(mainOrder.getId());

                                //添加订单数据库记录
                                long subOrderId = addOrderRecord(orderDO, orderDTONor);
                                orderDO.setId(subOrderId);

                                //添加订单商品列表
                                List<OrderItemDO> subOrderItemList = orderItemListMap.get(orderDO.getOrderSn());
                                addOrderItemDO(orderDO, subOrderItemList, consigneeDTO.getConsignee(), appKey);

                                //添加订单支付信息
                                addOrderPaymentDO(orderDO, appKey);

                                //添加订单收货地址信息
                                addOrderConsigneeDO(TradeUtil.genOrderConsignee(consigneeDTO), orderDO);

                                //添加订单优惠列表信息
                                List<OrderDiscountInfoDO> orderDiscountList = orderDiscountListMap.get(orderDO.getOrderSn());
                                addOrderDiscountDO(orderDiscountList, orderDO);

                                //处理无需额外支付的订单（即全额使用余额或积分抵现的订单）
                                if (mainOrder.getTotalAmount() <= 0) {
                                    processNoNeedPayOrder(orderDO,null);
                                }
                            }
                        }

                        //预使用用户优惠券
//                        preUseUserCoupon(orderDTONor, mainOrder.getId());

                        //预使用用户虚拟财富
                        preUseUserWealth(orderDTONor, mainOrder.getId());

                        /**
                         *发送交易消息：
                         * 如果不用拆单则发送主单消息，如果需要拆单则只发送子单消息。
                         * FIXME 发送交易消息的逻辑需要放在订单处理逻辑的最后，否则会出现consumer接收到消息的时候查不到对应订单的情况
                         */
                        if (needSplitOrder == false) {
                            notifyTradeMsg(mainOrder);
                        }else{
                            for (OrderDO orderDO : subOrderList) {
                                notifyTradeMsg(orderDO);
                            }
                        }
                        
                        responseOrderDTO = ModelUtil.convert2OrderDTO(mainOrder);
                    }

                    if(CollectionUtils.isNotEmpty(orderCombItemList)){

                        //添加组合订单数据库记录
                        if(rootOrder!=null){
                        	combOrder.setOriginalOrder(rootOrder.getId());
                        }    
                        long orderId = addOrderRecord(combOrder, orderDTOComb);
                        combOrder.setId(orderId);

                        //添加订单商品列表
                        if(needSplitCombOrder == false){
                            addOrderCombItemDO(combOrder, orderCombItemList, consigneeDTO.getConsignee(), appKey);
                        }

                        //添加订单支付信息
                        addOrderPaymentDO(combOrder, appKey);

                        //添加订单收货地址信息
                        addOrderConsigneeDO(TradeUtil.genOrderConsignee(consigneeDTO), combOrder);

//                        log.info(" orderCombDiscountInfoDOs: "+JSONObject.toJSONString(orderCombDiscountInfoDOs));
                        
                        //添加订单优惠列表信息
                        addOrderDiscountDO(orderCombDiscountInfoDOs, combOrder);

                        //处理无需额外支付的订单（即全额使用余额或积分抵现的订单）
                        if (combOrder.getTotalAmount() <= 0) {
                            processNoNeedPayOrder(combOrder,null);
                        }

                        //拆单处理逻辑
                        if(needSplitCombOrder == true){
                            for (OrderDO orderDO : subCombOrderList) {
                                //设置子订单的父订单id
                                orderDO.setOriginalOrder(combOrder.getId());

                                //添加订单数据库记录
                                long subOrderId = addOrderRecord(orderDO, orderDTOComb);
                                orderDO.setId(subOrderId);

                                //添加订单商品列表
                                List<OrderItemDO> subOrderItemList = orderCombItemListMap.get(orderDO.getOrderSn());
                                addOrderCombItemDO(orderDO, subOrderItemList, consigneeDTO.getConsignee(), appKey);

                                //添加订单支付信息
                                addOrderPaymentDO(orderDO, appKey);

                                //添加订单收货地址信息
                                addOrderConsigneeDO(TradeUtil.genOrderConsignee(consigneeDTO), orderDO);

                                //添加订单优惠列表信息
                                List<OrderDiscountInfoDO> orderDiscountList = orderCombDiscountListMap.get(orderDO.getOrderSn());
                                addOrderDiscountDO(orderDiscountList, orderDO);

                                //处理无需额外支付的订单（即全额使用余额或积分抵现的订单）
                                if (combOrder.getTotalAmount() <= 0) {
                                    processNoNeedPayOrder(orderDO,null);
                                }
                            }
                        }

                        //预使用用户优惠券
//                        preUseUserCoupon(orderDTOComb, combOrder.getId());

                        //预使用用户虚拟财富
                        preUseUserWealth(orderDTOComb, combOrder.getId());

                        /**
                         *发送交易消息：
                         * 如果不用拆单则发送主单消息，如果需要拆单则只发送子单消息。
                         * FIXME 发送交易消息的逻辑需要放在订单处理逻辑的最后，否则会出现consumer接收到消息的时候查不到对应订单的情况
                         */
//                    	log.info(" --------- needSplitCombOrder : "+needSplitCombOrder.toString());
                        if (needSplitCombOrder == false) {
//                        	log.info(" >>>>>>>>combOrder "+JSONObject.toJSONString(combOrder));
                            notifyTradeMsg(combOrder);
                        }else{
//                        	log.info(" >>>>>>>>subCombOrderList "+JSONObject.toJSONString(subCombOrderList));
                            for (OrderDO orderDO : subCombOrderList) {
                                notifyTradeMsg(orderDO);
                            }
                        }
                        
                        responseOrderDTO = ModelUtil.convert2OrderDTO(combOrder);
                    }
                    
                    long orderId=0l;
                    
                    if(combOrder!=null && combOrder.getId()!=null){
                    	orderId= combOrder.getId();
                    }
                    if(mainOrder!=null && mainOrder.getId()!=null){
                    	orderId= mainOrder.getId();
                    }

                    //预使用用户优惠券
                    preUseUserCoupon(orderDTO, orderId);
                    
                    
                } catch (Exception e) {
                	if(CollectionUtils.isNotEmpty(orderItemDOList)){
                        //回滚之前冻结的商品库存
                    	thawStockNew(mainOrder, orderItemDOList, appKey);
                        //释放冻结的虚拟财富
                        releaseUsedWealth(mainOrder);
                        //释放冻结的优惠券
                        releaseUsedCoupon(mainOrder);
                	}
                	if(CollectionUtils.isNotEmpty(orderCombItemList)){
                        //回滚之前冻结的商品库存
                    	thawStockNew(combOrder, orderCombItemList, appKey);
                        //释放冻结的虚拟财富
                        releaseUsedWealth(combOrder);
                        //释放冻结的优惠券
                        releaseUsedCoupon(combOrder);
                	}
                    
                    
                    //回滚事务
                    transactionStatus.setRollbackOnly();
                    logger.error("add order error", e);
                    return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
                }

                if(CollectionUtils.isNotEmpty(orderItemDOList) && CollectionUtils.isNotEmpty(orderCombItemList)){
                	responseOrderDTO = ModelUtil.convert2OrderDTO(rootOrder);
                }
//                log.info(" responseOrderDTO : "+JSONObject.toJSONString(responseOrderDTO));
//                OrderDTO responseOrderDTO = ModelUtil.convert2OrderDTO(mainOrder);
                return ResponseUtils.getSuccessResponse(responseOrderDTO);
            }
        });

        if (transResult.isSuccess()) {
            this.setAttr("returnOrder", transResult.getModule());
        }
        return transResult;
    }

    /**
     * TODO 这段代码隐藏很多bug，嗨云上线前，需要认真review几遍。add by caishen on 2016-05-23
     * @param orderDO
     * @param consignee
     * @param appKey
     * @return
     */
    public void addOrderItemDO(OrderDO orderDO, List<OrderItemDO> orderItemList, String consignee, String appKey) throws TradeException{

        DistributionManager distributionManager = (DistributionManager) this.getBean("distributionManager");
    	List<OrderItemDTO> orderGiftList = (List<OrderItemDTO>) this.getAttr("giftList");
        
        List<OrderDiscountInfoDO> orderDiscountInfoDOs =
                (List<OrderDiscountInfoDO>) this.getAttr("orderDiscountInfoDOs");


        OrderItemManager orderItemManager = (OrderItemManager) this.getBean("orderItemManager");
        try {
            for (OrderItemDO orderItemDO: orderItemList) {
                orderItemDO.setOrderId(orderDO.getId());
                orderItemDO.setBizCode(orderDO.getBizCode());
                orderItemDO.setUserName(consignee);
                orderItemDO.setDeliveryType(orderDO.getDeliveryId());
            }
            // TODO 添加订单赠品
            if (orderGiftList != null && orderGiftList.isEmpty()==false) {
                List<OrderItemDO> giftDOs = TradeUtil.genOrderItemDOList(orderGiftList);
                for (OrderItemDO gift : giftDOs) {
                    gift.setOrderId(orderDO.getId());
                    gift.setBizCode(orderDO.getBizCode());
                    gift.setUserName(consignee);
                    // TODO ...需要修改
                    // 设置分销商id
//                    gift.setDistributorId(orderItemList.get(0).getDistributorId());
                    /*DistShopDTO distShopDTO  = distributionManager.getDistShop(orderItemList.get(0).getDistributorId(), appKey);
                    gift.setDistributorName(distShopDTO.getShopName());*/
//                    gift.setDistributorName(orderItemList.get(0).getDistributorId()+"");
                    
                    gift.setDeliveryType(1);
                    gift.setUserId(orderDO.getUserId());
                    gift.setNumber(1);
                }
                if (null != giftDOs && giftDOs.size() > 0) {
                	// 一场满减送活动的判断,符合满减送的商品才有赠品
                	for(OrderDiscountInfoDO orderDiscountInfoDO:orderDiscountInfoDOs){
                		for(OrderItemDO orderItemDO:orderItemList){
                			if(orderDiscountInfoDO.getItemSkuId().equals(orderItemDO.getItemSkuId())){
                        		orderItemList.addAll(giftDOs);                				
                			}
                		}
                	}
//                    orderItemList.addAll(giftDOs);
                }
            }

            // 添加订单商品列表(TODO 性能优化)
            for (OrderItemDO oItem : orderItemList) {
                oItem.setOrderId(orderDO.getId());
                oItem.setBizCode(orderDO.getBizCode());
                oItem.setUserName(consignee);
                
                // TODO ...需要修改
                oItem.setDeliveryType(1);
                oItem.setUserId(orderDO.getUserId());
                if (null == oItem.getServiceUnitPrice()) {
                    oItem.setServiceUnitPrice(0L);
                }

                boolean isSuitSubOrderItem = TradeUtil.checkIsSuitSubOrderItem(oItem);

                //商品总价＝商品总价格＋商品服务总价
                Long itemServicePrice = oItem.getServiceUnitPrice() * oItem.getNumber();
                Long itemTotalPrice = oItem.getUnitPrice() * oItem.getNumber();
                itemTotalPrice += itemServicePrice;
                //单个商品需要退的运费
                Long orderItemDeliveryFee = TradeUtil.getOrderItemDeliveryFee(TradeUtil.getOrderItemListCount(orderItemList),
                        orderDO.getDeliveryFee(), itemTotalPrice, orderDO.getTotalPrice());


                if (null == orderDO.getPointDiscountAmount()) {
                    orderDO.setPointDiscountAmount(0l);
                }

                //单个商品的实付款金额
                /*Long orderItemPaymentAmount = TradeUtil.getPaymentAmount(TradeUtil.getOrderItemListCount(orderItemList),
                        //TODO 只退余额+实际付款金额+积分金额
//						orderDO.getTotalAmount()+orderDO.getDiscountAmount()-wealthDiscountAmount-orderDO.getPointDiscountAmount(),
                        orderDO.getTotalAmount(),
                        orderDO.getTotalPrice(),
                        itemTotalPrice);*/
                Long orderItemPaymentAmount = 0l;
                Long orderItemDiscountAmount = 0l;
                for(OrderDiscountInfoDO discountDO:orderDiscountInfoDOs){
                	if(oItem.getItemSkuId().equals(discountDO.getItemSkuId())){
                		if(ToolType.SIMPLE_TOOL.getCode().equals(discountDO.getDiscountCode())){
                			orderItemDiscountAmount+=discountDO.getDiscountAmount();
                		}
                		if(ToolType.FIRST_ORDER_DISCOUNT.getCode().equals(discountDO.getDiscountCode())){
                			orderItemDiscountAmount+=discountDO.getDiscountAmount();
                		}
                		if(ToolType.COMPOSITE_TOOL.getCode().equals(discountDO.getDiscountCode())){
                			orderItemDiscountAmount+=discountDO.getDiscountAmount();
                		}
                	}
                }
                orderItemPaymentAmount = itemTotalPrice - orderItemDiscountAmount;
                if(orderItemPaymentAmount<=0){
                	orderItemPaymentAmount=0l;
                }
                		TradeUtil.getPaymentAmount(TradeUtil.getOrderItemListCount(orderItemList),
                        //TODO 只退余额+实际付款金额+积分金额
//						orderDO.getTotalAmount()+orderDO.getDiscountAmount()-wealthDiscountAmount-orderDO.getPointDiscountAmount(),
                        orderDO.getTotalAmount(),
                        orderDO.getTotalPrice(),
                        itemTotalPrice);

                //单个商品的折扣金额
                /*Long orderItemDiscountAmount = TradeUtil.getDiscountAmount(TradeUtil.getOrderItemListCount(orderItemList),
                        TradeUtil.getCommonDiscountAmount(orderDiscountInfoDOs),
                        orderDO.getTotalPrice(),
                        itemTotalPrice);*/

                //单个商品的积分金额
                Long orderItemPointAmount = TradeUtil.getPointAmount(TradeUtil.getOrderItemListCount(orderItemList),
                        orderDO.getPointDiscountAmount(),
                        orderDO.getTotalPrice(),
                        itemTotalPrice);

                if (isSuitSubOrderItem) {
                    orderItemDeliveryFee = 0L;
                    orderItemPaymentAmount = 0L;
                    orderItemDiscountAmount = 0L;
                    orderItemPointAmount = 0L;
                }

//                logger.info("orderItemDeliveryFee:" + orderItemDeliveryFee);
//                logger.info("orderItemPaymentAmount:" + orderItemPaymentAmount);
//                logger.info("orderItemDiscountAmount:" + orderItemDiscountAmount);
//                logger.info("orderItemPointAmount:" + orderItemPointAmount);

                TradeUtil.RemoveDeliveryFeeAmount removeDeliveryFeePostPointAmount =
                        TradeUtil.getOrderRealPointAmount(orderItemPointAmount,
                                orderItemDiscountAmount,
                                orderItemPaymentAmount,
                                orderItemDeliveryFee);

                TradeUtil.RemoveDeliveryFeeAmount removeDeliveryFeePostPaymentAmount =
                        TradeUtil.getOrderRealPaymentAmount(orderItemPaymentAmount,
                                orderItemDeliveryFee);

                TradeUtil.RemoveDeliveryFeeAmount removeDeliveryFeePostDiscountAmount =
                        TradeUtil.getOrderRealDiscountAmount(orderItemPaymentAmount, orderItemDiscountAmount,
                                orderItemDeliveryFee);

                oItem.setPaymentAmount(removeDeliveryFeePostPaymentAmount.getAmount());

                Long orderAllDiscountAmount = TradeUtil.getDiscountAmount(TradeUtil.getOrderItemListCount(orderItemList),
                        orderDO.getDiscountAmount(),
                        orderDO.getTotalPrice(),
                        itemTotalPrice);


                oItem.setPaymentAmount(removeDeliveryFeePostPaymentAmount.getAmount());

                oItem.setDiscountAmount(removeDeliveryFeePostDiscountAmount.getAmount());

                oItem.setPointAmount(removeDeliveryFeePostPointAmount.getAmount());

                Long orderItemPoint = TradeUtil.getPoint(orderItemList.size(), orderDO.getPoint(), orderDO.getPointDiscountAmount(),
                        removeDeliveryFeePostPointAmount.getAmount());


                oItem.setPoint(orderItemPoint);

                if (TradeUtil.checkIsAllPaymentAmount(orderItemList, orderDO.getTotalAmount(), orderDO.getDeliveryFee())) {
                    oItem.setPaymentAmount((oItem.getUnitPrice() + oItem.getServiceUnitPrice()) * oItem.getNumber());
                }
                if (TradeUtil.checkIsAllPointAmount(orderItemList, orderDO.getPointDiscountAmount(), orderDO.getDeliveryFee())) {
                    oItem.setPointAmount((oItem.getUnitPrice() + oItem.getServiceUnitPrice()) * oItem.getNumber());
                }


                //TODO  以后增加商品类别判断是否赠品、赠品不减库存
                if (oItem.getPaymentAmount() > itemTotalPrice) {
                    oItem.setPaymentAmount(itemTotalPrice);
                }

                Long orderItemId = orderItemManager.addOrderItem(oItem);

                if (oItem.getOrderServiceDOList() != null) {
                    for (OrderServiceDO orderServiceDO : oItem.getOrderServiceDOList()) {
                        orderServiceDO.setOrderItemId(orderItemId);
                        orderServiceDO.setBizCode(orderDO.getBizCode());
                        orderServiceDO.setOrderId(orderDO.getId());
                        orderServiceDO.setSellerId(orderDO.getSellerId());
                    }
                    addOrderServiceDO(oItem.getOrderServiceDOList());
                }

            }

            return;
        } catch (TradeException e) {
            logger.error("addOrderDO error", e);
            throw e;
        } catch (Exception e){
            logger.error("addOrderDO error", e);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }
    


    /**
     * TODO 这段代码隐藏很多bug，嗨云上线前，需要认真review几遍。add by caishen on 2016-05-23
     * @param orderDO
     * @param consignee
     * @param appKey
     * @return
     */
    public void addOrderCombItemDO(OrderDO orderDO, List<OrderItemDO> orderItemList, String consignee, String appKey) throws TradeException{

        DistributionManager distributionManager = (DistributionManager) this.getBean("distributionManager");
    	List<OrderItemDTO> orderGiftList = (List<OrderItemDTO>) this.getAttr("giftList");
        
        List<OrderDiscountInfoDO> orderCombDiscountInfoDOs =
                (List<OrderDiscountInfoDO>) this.getAttr("orderCombDiscountInfoDOs");


        OrderItemManager orderItemManager = (OrderItemManager) this.getBean("orderItemManager");
        try {
            for (OrderItemDO orderItemDO: orderItemList) {
                orderItemDO.setOrderId(orderDO.getId());
                orderItemDO.setBizCode(orderDO.getBizCode());
                orderItemDO.setUserName(consignee);
                orderItemDO.setDeliveryType(orderDO.getDeliveryId());
            }
            // TODO 添加订单赠品
            if (orderGiftList != null && orderGiftList.isEmpty()==false) {
                List<OrderItemDO> giftDOs = TradeUtil.genOrderItemDOList(orderGiftList);
                for (OrderItemDO gift : giftDOs) {
                    gift.setOrderId(orderDO.getId());
                    gift.setBizCode(orderDO.getBizCode());
                    gift.setUserName(consignee);
                    // TODO ...需要修改
                    // 设置分销商id
//                    gift.setDistributorId(orderItemList.get(0).getDistributorId());
                    /*DistShopDTO distShopDTO  = distributionManager.getDistShop(orderItemList.get(0).getDistributorId(), appKey);
                    gift.setDistributorName(distShopDTO.getShopName());*/
//                    gift.setDistributorName(orderItemList.get(0).getDistributorId()+"");
                    
                    gift.setDeliveryType(1);
                    gift.setUserId(orderDO.getUserId());
                    gift.setNumber(1);
                }
                if (null != giftDOs && giftDOs.size() > 0) {
                	// 一场满减送活动的判断,符合满减送的商品才有赠品
                	for(OrderDiscountInfoDO orderDiscountInfoDO:orderCombDiscountInfoDOs){
                		for(OrderItemDO orderItemDO:orderItemList){
                			if(orderDiscountInfoDO.getItemSkuId().equals(orderItemDO.getItemSkuId())){
                        		orderItemList.addAll(giftDOs);                				
                			}
                		}
                	}
//                    orderItemList.addAll(giftDOs);
                }
            }

            // 添加订单商品列表(TODO 性能优化)
            for (OrderItemDO oItem : orderItemList) {
                oItem.setOrderId(orderDO.getId());
                oItem.setBizCode(orderDO.getBizCode());
                oItem.setUserName(consignee);
                
                // TODO ...需要修改
                oItem.setDeliveryType(1);
                oItem.setUserId(orderDO.getUserId());
                if (null == oItem.getServiceUnitPrice()) {
                    oItem.setServiceUnitPrice(0L);
                }

                boolean isSuitSubOrderItem = TradeUtil.checkIsSuitSubOrderItem(oItem);

                //商品总价＝商品总价格＋商品服务总价
                Long itemServicePrice = oItem.getServiceUnitPrice() * oItem.getNumber();
                Long itemTotalPrice = oItem.getUnitPrice() * oItem.getNumber();
                itemTotalPrice += itemServicePrice;
                //单个商品需要退的运费
                Long orderItemDeliveryFee = TradeUtil.getOrderItemDeliveryFee(TradeUtil.getOrderItemListCount(orderItemList),
                        orderDO.getDeliveryFee(), itemTotalPrice, orderDO.getTotalPrice());


                if (null == orderDO.getPointDiscountAmount()) {
                    orderDO.setPointDiscountAmount(0l);
                }

                //单个商品的实付款金额
                /*Long orderItemPaymentAmount = TradeUtil.getPaymentAmount(TradeUtil.getOrderItemListCount(orderItemList),
                        //TODO 只退余额+实际付款金额+积分金额
//						orderDO.getTotalAmount()+orderDO.getDiscountAmount()-wealthDiscountAmount-orderDO.getPointDiscountAmount(),
                        orderDO.getTotalAmount(),
                        orderDO.getTotalPrice(),
                        itemTotalPrice);*/
                Long orderItemPaymentAmount = 0l;
                Long orderItemDiscountAmount = 0l;
                for(OrderDiscountInfoDO discountDO:orderCombDiscountInfoDOs){
                	if(oItem.getItemSkuId().equals(discountDO.getItemSkuId())){
                		if(ToolType.SIMPLE_TOOL.getCode().equals(discountDO.getDiscountCode())){
                			orderItemDiscountAmount+=discountDO.getDiscountAmount();
                		}
                		if(ToolType.FIRST_ORDER_DISCOUNT.getCode().equals(discountDO.getDiscountCode())){
                			orderItemDiscountAmount+=discountDO.getDiscountAmount();
                		}
                		if(ToolType.COMPOSITE_TOOL.getCode().equals(discountDO.getDiscountCode())){
                			orderItemDiscountAmount+=discountDO.getDiscountAmount();
                		}
                	}
                }
                orderItemPaymentAmount = itemTotalPrice - orderItemDiscountAmount;
                if(orderItemPaymentAmount<=0){
                	orderItemPaymentAmount=0l;
                }
                		TradeUtil.getPaymentAmount(TradeUtil.getOrderItemListCount(orderItemList),
                        //TODO 只退余额+实际付款金额+积分金额
//						orderDO.getTotalAmount()+orderDO.getDiscountAmount()-wealthDiscountAmount-orderDO.getPointDiscountAmount(),
                        orderDO.getTotalAmount(),
                        orderDO.getTotalPrice(),
                        itemTotalPrice);

                //单个商品的折扣金额
                /*Long orderItemDiscountAmount = TradeUtil.getDiscountAmount(TradeUtil.getOrderItemListCount(orderItemList),
                        TradeUtil.getCommonDiscountAmount(orderDiscountInfoDOs),
                        orderDO.getTotalPrice(),
                        itemTotalPrice);*/

                //单个商品的积分金额
                Long orderItemPointAmount = TradeUtil.getPointAmount(TradeUtil.getOrderItemListCount(orderItemList),
                        orderDO.getPointDiscountAmount(),
                        orderDO.getTotalPrice(),
                        itemTotalPrice);

                if (isSuitSubOrderItem) {
                    orderItemDeliveryFee = 0L;
                    orderItemPaymentAmount = 0L;
                    orderItemDiscountAmount = 0L;
                    orderItemPointAmount = 0L;
                }

//                logger.info("orderItemDeliveryFee:" + orderItemDeliveryFee);
//                logger.info("orderItemPaymentAmount:" + orderItemPaymentAmount);
//                logger.info("orderItemDiscountAmount:" + orderItemDiscountAmount);
//                logger.info("orderItemPointAmount:" + orderItemPointAmount);

                TradeUtil.RemoveDeliveryFeeAmount removeDeliveryFeePostPointAmount =
                        TradeUtil.getOrderRealPointAmount(orderItemPointAmount,
                                orderItemDiscountAmount,
                                orderItemPaymentAmount,
                                orderItemDeliveryFee);

                TradeUtil.RemoveDeliveryFeeAmount removeDeliveryFeePostPaymentAmount =
                        TradeUtil.getOrderRealPaymentAmount(orderItemPaymentAmount,
                                orderItemDeliveryFee);

                TradeUtil.RemoveDeliveryFeeAmount removeDeliveryFeePostDiscountAmount =
                        TradeUtil.getOrderRealDiscountAmount(orderItemPaymentAmount, orderItemDiscountAmount,
                                orderItemDeliveryFee);

                oItem.setPaymentAmount(removeDeliveryFeePostPaymentAmount.getAmount());

                Long orderAllDiscountAmount = TradeUtil.getDiscountAmount(TradeUtil.getOrderItemListCount(orderItemList),
                        orderDO.getDiscountAmount(),
                        orderDO.getTotalPrice(),
                        itemTotalPrice);


                oItem.setPaymentAmount(removeDeliveryFeePostPaymentAmount.getAmount());

                oItem.setDiscountAmount(removeDeliveryFeePostDiscountAmount.getAmount());

                oItem.setPointAmount(removeDeliveryFeePostPointAmount.getAmount());

                Long orderItemPoint = TradeUtil.getPoint(orderItemList.size(), orderDO.getPoint(), orderDO.getPointDiscountAmount(),
                        removeDeliveryFeePostPointAmount.getAmount());


                oItem.setPoint(orderItemPoint);

                if (TradeUtil.checkIsAllPaymentAmount(orderItemList, orderDO.getTotalAmount(), orderDO.getDeliveryFee())) {
                    oItem.setPaymentAmount((oItem.getUnitPrice() + oItem.getServiceUnitPrice()) * oItem.getNumber());
                }
                if (TradeUtil.checkIsAllPointAmount(orderItemList, orderDO.getPointDiscountAmount(), orderDO.getDeliveryFee())) {
                    oItem.setPointAmount((oItem.getUnitPrice() + oItem.getServiceUnitPrice()) * oItem.getNumber());
                }


                //TODO  以后增加商品类别判断是否赠品、赠品不减库存
                if (oItem.getPaymentAmount() > itemTotalPrice) {
                    oItem.setPaymentAmount(itemTotalPrice);
                }

                Long orderItemId = orderItemManager.addOrderItem(oItem);

                if (oItem.getOrderServiceDOList() != null) {
                    for (OrderServiceDO orderServiceDO : oItem.getOrderServiceDOList()) {
                        orderServiceDO.setOrderItemId(orderItemId);
                        orderServiceDO.setBizCode(orderDO.getBizCode());
                        orderServiceDO.setOrderId(orderDO.getId());
                        orderServiceDO.setSellerId(orderDO.getSellerId());
                    }
                    addOrderServiceDO(oItem.getOrderServiceDOList());
                }

            }

            return;
        } catch (TradeException e) {
            logger.error("addOrderDO error", e);
            throw e;
        } catch (Exception e){
            logger.error("addOrderDO error", e);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }

    /**
     * 新增订单收货地址信息
     * @param orderConsigneeDO
     * @param orderDO
     * @throws TradeException
     */
    public void addOrderConsigneeDO(OrderConsigneeDO orderConsigneeDO, OrderDO orderDO) throws TradeException{
        OrderConsigneeManager orderConsigneeManager = (OrderConsigneeManager) this.getBean("orderConsigneeManager");

        try {
            // 添加订单收获地址
            orderConsigneeDO.setOrderId(orderDO.getId());
            orderConsigneeDO.setUserId(orderDO.getUserId());
            orderConsigneeDO.setBizCode(orderDO.getBizCode());
            orderConsigneeManager.addOrderConsignee(orderConsigneeDO);

            return;
        } catch (TradeException e) {
            logger.error("addOrderConsigneeDO error", e);
            throw e;
        } catch (Exception e){
            logger.error("addOrderConsigneeDO error", e);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }

    /**
     * 新增订单门店信息
     * @param orderStoreDO
     * @param bizCode
     * @param orderDO
     * @throws TradeException
     */
    public void addOrderStoreDO(OrderStoreDO orderStoreDO,String bizCode,OrderDO orderDO) throws TradeException{
        OrderStoreManager orderStoreManager = (OrderStoreManager) this.getBean("orderStoreManager");

        try {
            orderStoreDO.setOrderId(orderDO.getId());
            orderStoreDO.setBizCode(bizCode);

            orderStoreManager.addOrderStore(orderStoreDO);
            return;
        } catch (TradeException e) {
            logger.error("addOrderStoreDO error", e);
            throw e;
        } catch (Exception e){
            logger.error("addOrderConsigneeDO error", e);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }

    /**
     * 新增订单服务信息
     * @param orderServiceList
     * @return
     * @throws TradeException
     */
    public Boolean addOrderServiceDO(List<OrderServiceDO> orderServiceList) throws TradeException{
        OrderServiceManager orderServiceManager = (OrderServiceManager) this.getBean("orderServiceManager");

        try {
            if (null != orderServiceList && orderServiceList.size() != 0) {
                for (OrderServiceDO orderServiceDO : orderServiceList) {
                    orderServiceManager.addOrderService(orderServiceDO);
                }
            }
            return true;
        } catch (TradeException e) {
            logger.error("addOrderServiceDO error", e);
            throw e;
        } catch (Exception e){
            logger.error("addOrderServiceDO error", e);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }

    /**
     * 新增订单折扣信息
     * @param list
     * @param orderDO
     * @throws TradeException
     */
    private void addOrderDiscountDO(List<OrderDiscountInfoDO> list, OrderDO orderDO) throws TradeException{
        OrderDiscountInfoManager orderDiscountInfoManager =
                (OrderDiscountInfoManager) this.getBean("orderDiscountInfoManager");

        try {
            if (null != list && list.size() > 0) {
                for (OrderDiscountInfoDO orderDiscountInfoDO : list) {
                    orderDiscountInfoDO.setOrderId(orderDO.getId());
                    orderDiscountInfoDO.setUserId(orderDO.getUserId());
                    orderDiscountInfoDO.setBizCode(orderDO.getBizCode());
                    //TODO orderItemId的填充逻辑需要处理
                    orderDiscountInfoManager.addOrderDiscountInfo(orderDiscountInfoDO);
                }
            }
        } catch (TradeException e) {
            logger.error("addOrderDiscountDO error", e);
            throw e;
        } catch (Exception e){
            logger.error("addOrderDiscountDO error", e);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }

    /**
     * 新增订单支付信息
     * @param orderDO
     * @param appKey
     * @throws TradeException
     */
    private void addOrderPaymentDO(OrderDO orderDO, String appKey) throws TradeException{
        OrderPaymentManager orderPaymentManager = (OrderPaymentManager) this.getBean("orderPaymentManager");
        UserManager userManager = (UserManager) this.getBean("userManager");
        OrderManager orderManager = (OrderManager) this.getBean("orderManager");

        OrderPaymentDO orderPaymentDO = new OrderPaymentDO();

        // TODO 这里的paymentSn先用orderSn,后续如果支持一单多次支付的话，则需要区分开来
        orderPaymentDO.setPaymentSn(orderDO.getOrderSn());
        orderPaymentDO.setOrderId(orderDO.getId());
        orderPaymentDO.setPayStatus(TradeConstants.PaymentStatus.UNPAID);
        orderPaymentDO.setUserId(orderDO.getUserId());
        orderPaymentDO.setTotalAmount(orderDO.getTotalAmount());
        orderPaymentDO.setPayAmount(0L);
        orderPaymentDO.setPaymentId(orderDO.getPaymentId());
        orderPaymentDO.setBizCode(orderDO.getBizCode());
        orderPaymentDO.setDeleteMark(0);

        try {
            orderPaymentManager.addOrderPayment(orderPaymentDO);
            boolean needPay = true;
            if (orderDO.getTotalAmount() <= 0) {
                needPay = false;// 该订单无需再支付了
            }
            // 无需支付订单处理
            if (needPay == false) {
                // 将订单状态置为已支付
                orderManager.orderPaySuccess(orderDO.getId(),null, orderDO.getUserId());
                orderDO.setOrderStatus(TradeConstants.Order_Status.PAID);
            }
            return;
        } catch (TradeException e) {
            logger.error("OrderManagerImpl.addOrderPaymentDO error", e);
            throw e;
        } catch (Exception e){
            logger.error("addOrderDiscountDO error", e);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }


    /**
     * 移除购物车对应商品
     * @param orderDTO
     * @param itemSkuMap
     * @throws TradeException
     */
    private void removeCartItem(OrderDTO orderDTO, Map<Long,ItemSkuDTO> itemSkuMap) throws TradeException {
        UserCartItemManager userCartItemManager = (UserCartItemManager) this.getBean("userCartItemManager");

        // 如果订单来源来自购物车下单，则移除购物车中已下单商品(sourceType==null或者sourceType==1都代表购物车下单)
        if (orderDTO.getSourceType() == null || orderDTO.getSourceType().intValue() == 1) {
            List<Long> itemSkuIdList = new ArrayList<Long>();
            itemSkuIdList.addAll(itemSkuMap.keySet());
            int removeNum = userCartItemManager.removeCartItem(orderDTO.getUserId(), itemSkuIdList);
            if (removeNum != itemSkuIdList.size()) {
                // TODO error handle
            }
        }
    }
    
    /**
     * 移除购物车对应商品
     * @param orderDTO
     * @param itemSkuMap
     * @throws TradeException
     */
    private void removeCartItem(OrderDTO orderDTO) throws TradeException {
        UserCartItemManager userCartItemManager = (UserCartItemManager) this.getBean("userCartItemManager");

        // 如果订单来源来自购物车下单，则移除购物车中已下单商品(sourceType==null或者sourceType==1都代表购物车下单)
        if (orderDTO.getSourceType() == null || orderDTO.getSourceType().intValue() == 1) {
            List<Long> itemSkuIdList = new ArrayList<Long>();
            for(OrderItemDTO orderItemDTO:orderDTO.getOrderItems()){
            	itemSkuIdList.add(orderItemDTO.getItemSkuId());
            }
            
            int removeNum = userCartItemManager.removeCartItem(orderDTO.getUserId(), itemSkuIdList);
            if (removeNum != itemSkuIdList.size()) {
                // TODO error handle
            }
        }
    }

    /**
     * 添加订单记录
     * @param orderDO
     * @param orderDTO
     * @throws TradeException
     */
    private long addOrderRecord(OrderDO orderDO, OrderDTO orderDTO) throws TradeException {
        OrderManager orderManager = (OrderManager) this.getBean("orderManager");

        long orderId = orderManager.addOrder(orderDO);
        orderDO.setId(orderId);
        orderDTO.setId(orderId);

        return orderId;
    }
    
    private void deletePreOrder(OrderDO orderDO, List<OrderItemDO> orderItemDOs) throws TradeException {
        OrderManager orderManager = (OrderManager) this.getBean("orderManager");

        orderManager.phyDeletePreOrder(orderDO,orderItemDOs.get(0).getItemSkuId());
               
    }    

    /**
     * 预使用用户优惠券（临时冻结优惠券）
     * @param orderDTO
     * @param orderId
     * @throws TradeException
     */
    private void preUseUserCoupon(OrderDTO orderDTO, long orderId) throws TradeException {
        String appKey = (String) this.getAttr("appKey");
        MarketingManager marketingManager = (MarketingManager) this.getBean("marketingManager");

        // 预使用优惠券逻辑；预使用优惠券失败，则回滚订单
        List<UsedCouponDTO> usedCouponDTOs = orderDTO.getUsedCouponDTOs();
        if (usedCouponDTOs != null) {
            List<Long> couponIdList = new ArrayList<Long>();
            for (UsedCouponDTO usedCouponDTO : usedCouponDTOs) {
                couponIdList.add(usedCouponDTO.getCouponId());
            }

            boolean preUseResult = marketingManager.preUseUserCoupon(orderDTO.getUserId(),
                    couponIdList, orderId, appKey);
            if (preUseResult == false) {
                throw new TradeException(ResponseCode.BIZ_E_PRE_USE_COUPON_ERROR);
            }
        }
    }

    /**
     * 预使用用户虚拟财富（临时冻结虚拟财富）
     * @param orderDTO
     * @param orderId
     * @throws TradeException
     */
    private void preUseUserWealth(OrderDTO orderDTO, long orderId) throws TradeException {
        String appKey = (String) this.getAttr("appKey");
        VirtualWealthManager virtualWealthManager = (VirtualWealthManager) this.getBean("virtualWealthManager");

        // 预使用虚拟财富逻辑；预使用虚拟财富失败，则回滚订单
        List<UsedWealthDTO> usedWealthDTOs = orderDTO.getUsedWealthDTOs();
        if (usedWealthDTOs != null) {
            for (UsedWealthDTO usedWealthDTO : usedWealthDTOs) {

                logger.info("preUsedWealth request:" + JSONObject.toJSONString(usedWealthDTO));

                boolean preUseResult = virtualWealthManager.preUseUserWealth(orderDTO.getUserId(),
                        usedWealthDTO.getWealthType(), usedWealthDTO.getAmount(), orderId, appKey);
                if (preUseResult == false) {
                    throw new TradeException(ResponseCode.BIZ_E_PRE_USE_COUPON_ERROR);
                }
            }
        }
    }

    /**
     * 释放预使用的优惠券（即临时冻结的优惠券）
     * @param orderDO
     */
    private void releaseUsedCoupon(OrderDO orderDO) {
        String appKey = (String) this.getAttr("appKey");
        MarketingManager marketingManager = (MarketingManager) this.getBean("marketingManager");
        try {
            Integer discountMark = orderDO.getDiscountMark();
            if ((discountMark.intValue() & 1) == 1) {// 从左往右，第一个二进制位代表优惠活动标志
                marketingManager.releaseUsedCoupon(orderDO.getUserId(), orderDO.getId(), appKey);
            }
        } catch (Exception e) {
            logger.error("releaseSettlement error", e);
            //TODO 错误处理补偿机制
        }

    }

    /**
     * 释放预使用的虚拟财富（即临时冻结的虚拟财富）
     * @param orderDO
     */
    private void releaseUsedWealth(OrderDO orderDO) {
        String appKey = (String) this.getAttr("appKey");
        VirtualWealthManager virtualWealthManager = (VirtualWealthManager) this.getBean("virtualWealthManager");

        try {
            Integer discountMark = orderDO.getDiscountMark();
            if ((discountMark.intValue() & 2) == 2) {// 从左往右，第二个二进制位代表虚拟财富标志
                virtualWealthManager.releaseUsedWealth(orderDO.getUserId(), orderDO.getId(), appKey);
            }
        } catch (Exception e) {
            logger.error("releaseSettlement error", e);
            //TODO 错误处理补偿机制
        }

    }

    /**
     * 商品库存回补
     */
    private void thawStockNew(OrderDO orderDO,List<OrderItemDO> orderItemList, String appKey) {
        SupplierManager supplierManager = (SupplierManager) this.getBean("supplierManager");
                
        try {
            OrderStockDTO orderStockDTO = new OrderStockDTO();
            orderStockDTO.setOrderSn(orderDO.getOrderSn());
            orderStockDTO.setSellerId(orderDO.getSellerId());
            
            List<OrderSku> orderSkus = new ArrayList<OrderSku>();
    		
    		for(OrderItemDO orderItemDO :orderItemList){
    			OrderSku orderSku = new OrderSku();						
    			orderSku.setSkuId(orderItemDO.getItemSkuId());
    			orderSku.setNumber(orderItemDO.getNumber());
    			orderSku.setStoreId(orderDO.getStoreId());
    			orderSku.setSupplierId(orderDO.getSupplierId());
    			orderSkus.add(orderSku);
    		}
    		orderStockDTO.setOrderSkuList(orderSkus);
            supplierManager.thawOrderSkuStock(orderStockDTO, appKey);
        	
        } catch (TradeException e) {
            logger.error("thawStock error orderSn:{}", orderDO.getOrderSn(), e);
            //TODO 错误处理补偿机制
        }
    }

    /**
     * 商品库存回补
     */
    private void thawStock(OrderDO orderDO, String appKey) {
        ItemManager itemManager = (ItemManager) this.getBean("itemManager");
        try {
            itemManager.thawStock(orderDO.getOrderSn(), orderDO.getUserId(), appKey);
        } catch (TradeException e) {
            logger.error("thawStock error orderSn:{}", orderDO.getOrderSn(), e);
            //TODO 错误处理补偿机制
        }
    }


    /**
     * 处理无需第三方支付的订单（即使用余额或积分进行全额支付的订单）
     * @param orderDO
     * @throws TradeException
     */
    private void processNoNeedPayOrder(OrderDO orderDO,List<OrderDO> subOrderList) throws TradeException {
        boolean finalNeedPay = true;
        if (orderDO.getTotalAmount() <= 0) {
            finalNeedPay = false;// 该订单无需再支付了
        }

        if (finalNeedPay == true) {
            return;
        }

        String appKey = (String) this.getAttr("appKey");
        OrderManager orderManager = (OrderManager) this.getBean("orderManager");
        MarketingManager marketingManager = (MarketingManager) this.getBean("marketingManager");
        VirtualWealthManager virtualWealthManager = (VirtualWealthManager) this.getBean("virtualWealthManager");

        // 无需支付订单处理
        //将订单状态置为已支付
        orderManager.orderPaySuccess(orderDO.getId(),subOrderList, orderDO.getUserId());
        orderDO.setOrderStatus(TradeConstants.Order_Status.PAID);
        Integer discountMark = orderDO.getDiscountMark();

        //已支付订单，则直接将使用到的优惠券设置为已使用状态，将使用的虚拟财富设置为已使用状态
        if ((discountMark.intValue() & 1) == 1) {// 从左往右，第一个二进制位代表优惠活动标志
            boolean releaseResult = marketingManager.useUserCoupon(orderDO.getUserId(), orderDO.getId(),
                    appKey);
            if (releaseResult == false) {
                throw new TradeException(ResponseCode.BIZ_E_USE_COUPON_ERROR);
            }
        }

        if ((discountMark.intValue() & 2) == 2) {// 从左往右，第二个二进制位代表虚拟财富标志
            boolean releaseResult = virtualWealthManager.useUserWealth(orderDO.getUserId(), orderDO.getId(),
                    appKey);
            if (releaseResult == false) {
                throw new TradeException(ResponseCode.BIZ_E_USE_WEALTH_ERROR);
            }
        }
    }

    private OrderConsigneeDO genOrderConsignee(UserConsigneeDTO consigneeDTO) {
        OrderConsigneeDO orderConsigneeDO = new OrderConsigneeDO();
        orderConsigneeDO.setConsigneeId(consigneeDTO.getId());
        orderConsigneeDO.setConsignee(consigneeDTO.getConsignee());
        orderConsigneeDO.setAddress(consigneeDTO.getAddress());
        orderConsigneeDO.setMobile(consigneeDTO.getMobile());
        orderConsigneeDO.setPhone(consigneeDTO.getPhone());
        orderConsigneeDO.setCountryCode(consigneeDTO.getCountryCode());
        orderConsigneeDO.setProvinceCode(consigneeDTO.getProvinceCode());
        orderConsigneeDO.setCityCode(consigneeDTO.getCityCode());
        orderConsigneeDO.setAreaCode(consigneeDTO.getAreaCode());
        orderConsigneeDO.setTownCode(consigneeDTO.getTownCode());
        orderConsigneeDO.setZip(consigneeDTO.getZip());

        return orderConsigneeDO;
    }

    private void notifyTradeMsg(OrderDO orderDO) throws TradeException {
        Boolean needSplitOrder = (Boolean) this.getAttr("needSplitOrder");
        MsgQueueManager msgQueueManager = (MsgQueueManager) this.getBean("msgQueueManager");

        //如果订单待支付金额大于0，则订单为待支付状态。否则，订单直接为已支付状态
        msgQueueManager.sendOrderMessage("orderUnpaid", ModelUtil.convert2OrderDTO(orderDO));
        if (orderDO.getTotalAmount() <= 0) {//订单已支付（这种情况一般发生在优惠或者余额抵现的情况）
            //发送支付成功内部mq消息
            msgQueueManager.sendPaySuccessMsg(orderDO);
        }
    }


    /**
     * 处理商城的订单佣金（适用于多店铺商城的场景）
     * @throws TradeException
     */
    private void processMallOrderCommission() throws TradeException {
        String appKey = (String) this.getAttr("appKey");
        BizInfoDTO bizInfoDTO = (BizInfoDTO) this.getAttr("bizInfo");
        OrderDO orderDO = (OrderDO) this.getAttr("orderDO");
        List<OrderItemDO> orderItemList = (List<OrderItemDO>) this.getAttr("orderItemList");
        List<OrderDiscountInfoDO> orderDiscountInfoDOs =
                (List<OrderDiscountInfoDO>) this.getAttr("orderDiscountInfoDOs");
        OrderManager orderManager = (OrderManager) this.getBean("orderManager");
        ShopManager shopManager = (ShopManager) this.getBean("shopManager");

        //如果不是多店铺商城，则不需要计算商城佣金，直接返回即可
        if(bizInfoDTO.getBizPropertyMap()==null || bizInfoDTO.getBizPropertyMap().isEmpty()
                || bizInfoDTO.getBizPropertyMap().containsKey(BizPropertyKey.IS_MULTI_MALL)==false
                || bizInfoDTO.getBizPropertyMap().get(BizPropertyKey.IS_MULTI_MALL).getValue().equals("1")==false){
            return;
        }

        List<CommissionUnitDTO> commissionUnitDTOList = new ArrayList<CommissionUnitDTO>();
        if (orderItemList == null || orderItemList.isEmpty()) {
            //TODO error handle
        } else {
            for (OrderItemDO orderItemDO : orderItemList) {
                if (null != orderItemDO.getPaymentAmount() && orderItemDO.getPaymentAmount() > 0) {
                    CommissionUnitDTO commissionUnitDTO = new CommissionUnitDTO();
                    commissionUnitDTO.setItemId(orderItemDO.getItemId());
                    commissionUnitDTO.setSkuId(orderItemDO.getItemSkuId());
                    commissionUnitDTO.setPrice(orderItemDO.getUnitPrice());
                    commissionUnitDTO.setNum(orderItemDO.getNumber());
                    commissionUnitDTO.setSellerId(orderDO.getSellerId());
                    commissionUnitDTOList.add(commissionUnitDTO);
                }

            }
        }


        //修改订单商城佣金值
        Long commission = 0L;
        if (commissionUnitDTOList.isEmpty() == false) {
            commission = shopManager.getOrderCommission(commissionUnitDTOList, appKey);
        }

        long mallAllDiscountAmt = TradeUtil.getMallAllDiscountAmt(orderDiscountInfoDOs);

        commission -= mallAllDiscountAmt;

        if (commission <= 0) {
            commission = 0L;
        }

        OrderDO updateOrderDO = new OrderDO();
        updateOrderDO.setId(orderDO.getId());
        updateOrderDO.setUserId(orderDO.getUserId());
        updateOrderDO.setMallCommission(commission);
        orderManager.updateOrderMallCommission(updateOrderDO);
    }
}
