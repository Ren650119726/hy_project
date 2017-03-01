package com.mockuai.tradecenter.core.service.action.order.add.step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumOrderType;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.util.ModelUtil;

/**
 * Created by zengzhangqiang on 5/21/16.
 */
public class HandleOrderAssemblingStep extends TradeBaseStep {

	private static final Logger log = LoggerFactory.getLogger(HandleOrderAssemblingStep.class);
	
    @Override
    public StepName getName() {
        return StepName.HANDLE_ORDER_ASSEMBLING_STEP;
    }

    @Override
    public TradeResponse execute() {
        OrderDTO orderDTO = (OrderDTO) this.getParam("orderDTO");
        OrderDTO orderDTONor = (OrderDTO) this.getAttr("orderDTONor");
        OrderDTO orderDTOComb = (OrderDTO) this.getAttr("orderDTOComb");

        Integer appType = (Integer) this.getAttr("appType");
        String bizCode = (String) this.getAttr("bizCode");

        List<OrderDiscountInfoDO> orderDiscountInfoDOs = (List<OrderDiscountInfoDO>) this.getAttr("orderDiscountInfoDOs");
        List<OrderDiscountInfoDO> orderCombDiscountInfoDOs = (List<OrderDiscountInfoDO>) this.getAttr("orderCombDiscountInfoDOs");
        SettlementInfo settlement = (SettlementInfo) this.getAttr("settlement");

        Map<Long, ItemDTO> itemMap = (Map<Long, ItemDTO>) this.getAttr("itemMap");
        List<OrderItemDO> orderItemDOList = (List<OrderItemDO>) this.getAttr("orderItemList");
        Boolean needSplitOrder = (Boolean)this.getAttr("needSplitOrder");
        Map<Long, List<OrderItemDO>> storeOrderItemMap = (Map<Long, List<OrderItemDO>>)this.getAttr("storeOrderItemMap");
        Map<Long, Long> storeSupplierMap = (Map<Long, Long>) this.getAttr("storeSupplierMap");

//        Map<Long, ItemDTO> combItemMap = (Map<Long, ItemDTO>) this.getAttr("combItemMap");
        List<OrderItemDO> orderCombItemList = (List<OrderItemDO>) this.getAttr("orderCombItemList");
        Boolean needSplitCombOrder = (Boolean)this.getAttr("needSplitCombOrder");
        Map<Long, List<OrderItemDO>> storeCombOrderItemMap = (Map<Long, List<OrderItemDO>>)this.getAttr("storeCombOrderItemMap");
        Map<Long, Long> storeCombSupplierMap = (Map<Long, Long>) this.getAttr("storeCombSupplierMap");
                
        String rootOrderSn = (String) this.getAttr("rootOrderSn");//根订单的订单流水号        
        
//        log.info(" orderItemDOList : "+JSONObject.toJSONString(orderItemDOList));
//        log.info(" itemMap :  "+JSONObject.toJSONString(itemMap));
        
        if(!StringUtils.isBlank(rootOrderSn)){
        	List<OrderDiscountInfoDO> orderRootDiscountInfo =new ArrayList<OrderDiscountInfoDO>();
        	orderRootDiscountInfo.addAll(orderDiscountInfoDOs);
        	orderRootDiscountInfo.addAll(orderCombDiscountInfoDOs);
        	log.info(" ######### orderItemDOList : "+JSONObject.toJSONString(orderItemDOList));
        	log.info(" ######### orderRootDiscountInfo : "+JSONObject.toJSONString(orderRootDiscountInfo));
        	OrderDO rootOrder = generateRootOrderDO(orderDTO, orderItemDOList, orderRootDiscountInfo,
                     settlement.getDeliveryFee(), 0L, appType, bizCode);
        	//设置主订单的parentMark为2，代表是根订单
        	rootOrder.setParentMark(2);
            //将rootOrder放入管道上下文中，便于后续节点的处理
//        	log.info(" rootOrder : "+JSONObject.toJSONString(rootOrder));
            this.setAttr("rootOrder", rootOrder);
        }
        
        if(CollectionUtils.isNotEmpty(orderItemDOList)){
        	
//        	log.info(" ================== orderItemDOList ");
        	
        	//生成主订单
            //TODO 这里的税费先写死为0，等底层确定了税费拆单逻辑，这里需要改掉。上线前务必改掉
            OrderDO mainOrder = generateOrderDO(orderDTONor, orderItemDOList, orderDiscountInfoDOs,
                    itemMap, settlement.getDeliveryFee(), 0L, appType, bizCode);

            //TODO 秒杀逻辑另外处理
            if(storeOrderItemMap!=null){
            	//设置主订单仓库id和供应商id
                for (Map.Entry<Long, List<OrderItemDO>> entry : storeOrderItemMap.entrySet()) {
                    mainOrder.setStoreId(entry.getKey());
                    mainOrder.setSupplierId(storeSupplierMap.get(entry.getKey()));
                }
            }        

            //需要拆单
            if(needSplitOrder == true) {
                List<OrderDTO> subOrderDTOList = (List<OrderDTO>) this.getAttr("subOrderDTOList");
                Map<String, List<OrderDiscountInfoDO>> orderDiscountListMap =
                        (Map<String, List<OrderDiscountInfoDO>>) this.getAttr("orderDiscountListMap");
                Map<String, List<OrderItemDO>> orderItemListMap =
                        (Map<String, List<OrderItemDO>>) this.getAttr("orderItemListMap");
                List<OrderDO> subOrderList = generateSubOrderList(mainOrder, subOrderDTOList,
                        orderDiscountListMap, orderItemListMap, bizCode);
                this.setAttr("subOrderList", subOrderList);

                //设置主订单的parentMark为1，代表是主订单
                mainOrder.setParentMark(1);
            }

            //将orderDO放入管道上下文中，便于后续节点的处理
            this.setAttr("mainOrder", mainOrder);
        }
        
        if(CollectionUtils.isNotEmpty(orderCombItemList)){
        	//生成组合订单
            //TODO 这里的税费先写死为0，等底层确定了税费拆单逻辑，这里需要改掉。上线前务必改掉
            OrderDO combOrder = generateCombOrderDO(orderDTOComb, orderCombItemList, orderCombDiscountInfoDOs,
            		settlement.getDeliveryFee(), 0L, appType, bizCode);

            //TODO 秒杀逻辑另外处理
            if(storeCombOrderItemMap!=null){
            	//设置主订单仓库id和供应商id
                for (Map.Entry<Long, List<OrderItemDO>> entry : storeCombOrderItemMap.entrySet()) {
                	combOrder.setStoreId(entry.getKey());
                	combOrder.setSupplierId(storeCombSupplierMap.get(entry.getKey()));
                }
            }        

            //需要拆单
            if(needSplitCombOrder == true) {
                List<OrderDTO> subCombOrderDTOList = (List<OrderDTO>) this.getAttr("subCombOrderDTOList");
                Map<String, List<OrderDiscountInfoDO>> orderCombDiscountListMap =
                        (Map<String, List<OrderDiscountInfoDO>>) this.getAttr("orderCombDiscountListMap");
                Map<String, List<OrderItemDO>> orderCombItemListMap =
                        (Map<String, List<OrderItemDO>>) this.getAttr("orderCombItemListMap");
//                List<OrderDO> subOrderList = generateSubOrderList(combOrder, subCombOrderDTOList,
//                        orderDiscountListMap, orderCombItemListMap, bizCode);
                List<OrderDO> subOrderList = generateSubCombOrderList(combOrder, subCombOrderDTOList,
                		orderCombDiscountListMap, orderCombItemListMap, bizCode);
                this.setAttr("subCombOrderList", subOrderList);

                //设置主订单的parentMark为1，代表是主订单
                combOrder.setParentMark(1);
            }

            //将orderDO放入管道上下文中，便于后续节点的处理
            this.setAttr("combOrder", combOrder);
        }

        return ResponseUtils.getSuccessResponse();
    }

    /**
     * 生成子订单列表
     * @param mainOrder
     * @param subOrderDTOList
     * @param orderDiscountListMap
     * @param orderItemListMap
     * @return
     */
    private List<OrderDO> generateSubOrderList(OrderDO mainOrder, List<OrderDTO> subOrderDTOList,
                                                Map<String,List<OrderDiscountInfoDO>> orderDiscountListMap,
                                                Map<String, List<OrderItemDO>> orderItemListMap, String bizCode) {
        Map<Long, ItemDTO> itemDTOMap = (Map<Long, ItemDTO>) this.getAttr("itemMap");
        Map<String, OrderDTO> skuOrderMap = new HashMap<String, OrderDTO>();
        for (OrderDTO orderDTO : subOrderDTOList) {
            skuOrderMap.put(orderDTO.getOrderSn(), orderDTO);
        }

        List<OrderDO> subOrderList = new ArrayList<OrderDO>();
        for (OrderDTO subOrderDTO : subOrderDTOList) {
            //根据主订单相关信息，填充自订单的部分属性
            subOrderDTO.setDeliveryId(mainOrder.getDeliveryId());
            subOrderDTO.setPaymentId(mainOrder.getPaymentId());
            subOrderDTO.setUserId(mainOrder.getUserId());
            subOrderDTO.setSellerId(mainOrder.getSellerId());
            subOrderDTO.setUserMemo(mainOrder.getUserMemo());
            subOrderDTO.setBizCode(mainOrder.getBizCode());
            subOrderDTO.setConsignee(mainOrder.getConsignee());
            List<OrderItemDO> subOrderItemList = orderItemListMap.get(subOrderDTO.getOrderSn());
            List<OrderDiscountInfoDO> subOrderDiscountList = orderDiscountListMap.get(subOrderDTO.getOrderSn());
            //TODO 这里运费、税费和appType先写死，等底层接口能支持之后要立即改掉（上线前要改掉）
            OrderDO subOrder = generateOrderDO(subOrderDTO, subOrderItemList, subOrderDiscountList,
                    itemDTOMap, 0, 0, 3, bizCode);
            subOrderList.add(subOrder);
        }

        return subOrderList;

    }

    /**
     * 生成子订单列表
     * @param mainOrder
     * @param subOrderDTOList
     * @param orderDiscountListMap
     * @param orderItemListMap
     * @return
     */
    private List<OrderDO> generateSubCombOrderList(OrderDO mainOrder, List<OrderDTO> subOrderDTOList,
                                                Map<String,List<OrderDiscountInfoDO>> orderDiscountListMap,
                                                Map<String, List<OrderItemDO>> orderItemListMap, String bizCode) {

        Map<String, OrderDTO> skuOrderMap = new HashMap<String, OrderDTO>();
        for (OrderDTO orderDTO : subOrderDTOList) {
            skuOrderMap.put(orderDTO.getOrderSn(), orderDTO);
        }

        List<OrderDO> subOrderList = new ArrayList<OrderDO>();
        for (OrderDTO subOrderDTO : subOrderDTOList) {
            //根据主订单相关信息，填充自订单的部分属性
            subOrderDTO.setDeliveryId(mainOrder.getDeliveryId());
            subOrderDTO.setPaymentId(mainOrder.getPaymentId());
            subOrderDTO.setUserId(mainOrder.getUserId());
            subOrderDTO.setSellerId(mainOrder.getSellerId());
            subOrderDTO.setUserMemo(mainOrder.getUserMemo());
            subOrderDTO.setBizCode(mainOrder.getBizCode());
            subOrderDTO.setConsignee(mainOrder.getConsignee());
            List<OrderItemDO> subOrderItemList = orderItemListMap.get(subOrderDTO.getOrderSn());
            List<OrderDiscountInfoDO> subOrderDiscountList = orderDiscountListMap.get(subOrderDTO.getOrderSn());
            //TODO 这里运费、税费和appType先写死，等底层接口能支持之后要立即改掉（上线前要改掉）
//            OrderDO subOrder = generateCombOrderDO(subOrderDTO, subOrderItemList, subOrderDiscountList,
//                    0, 0, 3, bizCode);
            OrderDO subOrder = generateSubCombOrderDO(subOrderDTO, subOrderItemList, subOrderDiscountList,
                    0, 0, 3, bizCode);
            subOrderList.add(subOrder);
        }

        return subOrderList;

    }

    /**
     * 生成订单对象
     * @param orderDTO
     * @param orderItemDOList
     * @param orderDiscountInfoDOs
     * @param itemMap
     * @param deliveryFee
     * @param taxFee
     * @param appType
     * @param bizCode
     * @return
     */
    private OrderDO generateOrderDO(OrderDTO orderDTO, List<OrderItemDO> orderItemDOList,
                                    List<OrderDiscountInfoDO> orderDiscountInfoDOs,
                                    Map<Long,ItemDTO> itemMap,long deliveryFee,
                                    long taxFee, int appType, String bizCode) {
        // 创建订单
        long itemTotalPrice = getItemTotalPrice(orderItemDOList);// 订单商品总价
        Long sellerId = orderDTO.getOrderItems().get(0).getSellerId();

        OrderDO orderDO = ModelUtil.convert2OrderDO(orderDTO); // 将DTO转为DO

        //设置bizCode
        orderDO.setBizCode(bizCode);

        //设置卖家id
        orderDO.setSellerId(sellerId);

        //设置优惠总价
        long totalDiscountAmount = getTotalDiscountAmount(orderDiscountInfoDOs);
        orderDO.setDiscountAmount(totalDiscountAmount);

        //设置运费
        orderDO.setDeliveryFee(deliveryFee);

        //设置税费
        orderDO.setTaxFee(taxFee);

        //设置订单总支付金额
        // 订单总价等于（商品总价＋总运费＋海关税费（针对跨境商品））－总优惠金额（优惠券＋满减送＋会员折扣＋余额抵现＋积分抵现）
        long totalAmount = (itemTotalPrice + deliveryFee + taxFee) - totalDiscountAmount;

        //如果可使用优惠金额超过订单待付款金额，则将订单总金额设为0。并提示调用方
        boolean needPay = true;
        if (totalAmount <= 0) {
            totalAmount = 0;
            needPay = false;// 该订单无需再支付了
        }
        orderDO.setTotalAmount(totalAmount);
        orderDO.setTotalPrice(itemTotalPrice);//订单商品总价

        //设置订单配送方式
//        int deliveryId = 0;// 0代表卖家包邮，由卖家决定配送方式
        int deliveryId = 1;// 0代表卖家包邮，默认为快递邮寄
        if (orderDTO.getDeliveryId() != null) {
            deliveryId = orderDTO.getDeliveryId();
        }
        orderDO.setDeliveryId(deliveryId);

        //设置订单类型
        orderDO.setType(getOrderType(orderItemDOList, itemMap).getCode());

        //设置支付方式
        int paymentId = orderDTO.getPaymentId(); // 支付方式
        if (needPay == true) {
            orderDO.setPaymentId(paymentId);
        } else {
            // 无需支付的情况,paymentId置为0
            orderDO.setPaymentId(0);
        }

        //设置订单报销信息（如果订单报销信息不为空的话，则设置订单报销标记）
        if (orderDTO.getOrderInvoiceDTO() != null) {
            orderDO.setInvoiceMark(1);
        } else {
            orderDO.setInvoiceMark(0);
        }

        //设置订单优惠信息
        orderDO.setDiscountMark(getDiscountMark(orderDiscountInfoDOs));
        orderDO.setPoint(0L);//FIXME 嗨云平台只有在支付时才会使用虚拟财富
        orderDO.setPointDiscountAmount(0L);//FIXME 嗨云平台只有在支付时才会使用虚拟财富

        //设置下单的终端类型
        orderDO.setAppType(appType);

        //设置仓库和供应商id
        orderDO.setStoreId(orderDTO.getStoreId());
        orderDO.setSupplierId(orderDTO.getSupplierId());

        //设置订单跨境信息。TODO 订单跨境扩展信息要等税费结算逻辑好了之后再填充
        if (isHigoOrder(orderItemDOList)) {
            orderDO.setHigoMark(1);
        }else{
            orderDO.setHigoMark(0);
        }

        return orderDO;
    }

    /**
     * 生成订单对象
     * @param orderDTO
     * @param orderItemDOList
     * @param orderDiscountInfoDOs
     * @param itemMap
     * @param deliveryFee
     * @param taxFee
     * @param appType
     * @param bizCode
     * @return
     */
    private OrderDO generateSubCombOrderDO(OrderDTO orderDTO, List<OrderItemDO> orderItemDOList,
                                    List<OrderDiscountInfoDO> orderDiscountInfoDOs,long deliveryFee,
                                    long taxFee, int appType, String bizCode) {
        // 创建订单
        long itemTotalPrice = getItemTotalPrice(orderItemDOList);// 订单商品总价
//        long itemTotalPrice = getCombItemTotalPrice(orderDTO);// 订单商品总价
        Long sellerId = orderDTO.getOrderItems().get(0).getSellerId();

        OrderDO orderDO = ModelUtil.convert2OrderDO(orderDTO); // 将DTO转为DO

        //设置bizCode
        orderDO.setBizCode(bizCode);

        //设置卖家id
        orderDO.setSellerId(sellerId);

        //设置优惠总价
        long totalDiscountAmount = getTotalDiscountAmount(orderDiscountInfoDOs);
        orderDO.setDiscountAmount(totalDiscountAmount);

        //设置运费
        orderDO.setDeliveryFee(deliveryFee);

        //设置税费
        orderDO.setTaxFee(taxFee);

        //设置订单总支付金额
        // 订单总价等于（商品总价＋总运费＋海关税费（针对跨境商品））－总优惠金额（优惠券＋满减送＋会员折扣＋余额抵现＋积分抵现）
//        long totalAmount = (itemTotalPrice + deliveryFee + taxFee) - totalDiscountAmount;
        long totalAmount = (itemTotalPrice + deliveryFee + taxFee) - totalDiscountAmount;

        //如果可使用优惠金额超过订单待付款金额，则将订单总金额设为0。并提示调用方
        boolean needPay = true;
        if (totalAmount <= 0) {
            totalAmount = 0;
            needPay = false;// 该订单无需再支付了
        }
        orderDO.setTotalAmount(totalAmount);
        orderDO.setTotalPrice(itemTotalPrice);//订单商品总价

        //设置订单配送方式
//        int deliveryId = 0;// 0代表卖家包邮，由卖家决定配送方式
        int deliveryId = 1;// 0代表卖家包邮，默认为快递邮寄
        if (orderDTO.getDeliveryId() != null) {
            deliveryId = orderDTO.getDeliveryId();
        }
        orderDO.setDeliveryId(deliveryId);

        //设置订单类型
        orderDO.setType(orderItemDOList.get(0).getItemType());

        //设置支付方式
        int paymentId = orderDTO.getPaymentId(); // 支付方式
        if (needPay == true) {
            orderDO.setPaymentId(paymentId);
        } else {
            // 无需支付的情况,paymentId置为0
            orderDO.setPaymentId(0);
        }

        //设置订单报销信息（如果订单报销信息不为空的话，则设置订单报销标记）
        if (orderDTO.getOrderInvoiceDTO() != null) {
            orderDO.setInvoiceMark(1);
        } else {
            orderDO.setInvoiceMark(0);
        }

        //设置订单优惠信息
        orderDO.setDiscountMark(getDiscountMark(orderDiscountInfoDOs));
        orderDO.setPoint(0L);//FIXME 嗨云平台只有在支付时才会使用虚拟财富
        orderDO.setPointDiscountAmount(0L);//FIXME 嗨云平台只有在支付时才会使用虚拟财富

        //设置下单的终端类型
        orderDO.setAppType(appType);

        //设置仓库和供应商id
        orderDO.setStoreId(orderDTO.getStoreId());
        orderDO.setSupplierId(orderDTO.getSupplierId());

        //设置订单跨境信息。TODO 订单跨境扩展信息要等税费结算逻辑好了之后再填充
        if (isHigoOrder(orderItemDOList)) {
            orderDO.setHigoMark(1);
        }else{
            orderDO.setHigoMark(0);
        }

        return orderDO;
    }

    /**
     * 生成订单对象
     * @param orderDTO
     * @param orderItemDOList
     * @param orderDiscountInfoDOs
     * @param itemMap
     * @param deliveryFee
     * @param taxFee
     * @param appType
     * @param bizCode
     * @return
     */
    private OrderDO generateCombOrderDO(OrderDTO orderDTO, List<OrderItemDO> orderItemDOList,
                                    List<OrderDiscountInfoDO> orderDiscountInfoDOs,long deliveryFee,
                                    long taxFee, int appType, String bizCode) {
        // 创建订单
//        long itemTotalPrice = getItemTotalPrice(orderItemDOList);// 订单商品总价
        long itemTotalPrice = getCombItemTotalPrice(orderDTO);// 订单商品总价
        Long sellerId = orderDTO.getOrderItems().get(0).getSellerId();

        OrderDO orderDO = ModelUtil.convert2OrderDO(orderDTO); // 将DTO转为DO

        //设置bizCode
        orderDO.setBizCode(bizCode);

        //设置卖家id
        orderDO.setSellerId(sellerId);

        //设置优惠总价
        long totalDiscountAmount = getTotalDiscountAmount(orderDiscountInfoDOs);
        orderDO.setDiscountAmount(totalDiscountAmount);

        //设置运费
        orderDO.setDeliveryFee(deliveryFee);

        //设置税费
        orderDO.setTaxFee(taxFee);

        //设置订单总支付金额
        // 订单总价等于（商品总价＋总运费＋海关税费（针对跨境商品））－总优惠金额（优惠券＋满减送＋会员折扣＋余额抵现＋积分抵现）
//        long totalAmount = (itemTotalPrice + deliveryFee + taxFee) - totalDiscountAmount;
        long totalAmount = (itemTotalPrice + deliveryFee + taxFee) - totalDiscountAmount;

        //如果可使用优惠金额超过订单待付款金额，则将订单总金额设为0。并提示调用方
        boolean needPay = true;
        if (totalAmount <= 0) {
            totalAmount = 0;
            needPay = false;// 该订单无需再支付了
        }
        orderDO.setTotalAmount(totalAmount);
        orderDO.setTotalPrice(itemTotalPrice);//订单商品总价

        //设置订单配送方式
//        int deliveryId = 0;// 0代表卖家包邮，由卖家决定配送方式
        int deliveryId = 1;// 0代表卖家包邮，默认为快递邮寄
        if (orderDTO.getDeliveryId() != null) {
            deliveryId = orderDTO.getDeliveryId();
        }
        orderDO.setDeliveryId(deliveryId);

        //设置订单类型
        orderDO.setType(orderItemDOList.get(0).getItemType());

        //设置支付方式
        int paymentId = orderDTO.getPaymentId(); // 支付方式
        if (needPay == true) {
            orderDO.setPaymentId(paymentId);
        } else {
            // 无需支付的情况,paymentId置为0
            orderDO.setPaymentId(0);
        }

        //设置订单报销信息（如果订单报销信息不为空的话，则设置订单报销标记）
        if (orderDTO.getOrderInvoiceDTO() != null) {
            orderDO.setInvoiceMark(1);
        } else {
            orderDO.setInvoiceMark(0);
        }

        //设置订单优惠信息
        orderDO.setDiscountMark(getDiscountMark(orderDiscountInfoDOs));
        orderDO.setPoint(0L);//FIXME 嗨云平台只有在支付时才会使用虚拟财富
        orderDO.setPointDiscountAmount(0L);//FIXME 嗨云平台只有在支付时才会使用虚拟财富

        //设置下单的终端类型
        orderDO.setAppType(appType);

        //设置仓库和供应商id
        orderDO.setStoreId(orderDTO.getStoreId());
        orderDO.setSupplierId(orderDTO.getSupplierId());

        //设置订单跨境信息。TODO 订单跨境扩展信息要等税费结算逻辑好了之后再填充
        if (isHigoOrder(orderItemDOList)) {
            orderDO.setHigoMark(1);
        }else{
            orderDO.setHigoMark(0);
        }

        return orderDO;
    }

    /**
     * 生成订单对象
     * @param orderDTO
     * @param orderItemDOList
     * @param orderDiscountInfoDOs
     * @param itemMap
     * @param deliveryFee
     * @param taxFee
     * @param appType
     * @param bizCode
     * @return
     */
    private OrderDO generateRootOrderDO(OrderDTO orderDTO, List<OrderItemDO> orderItemDOList,
                                    List<OrderDiscountInfoDO> orderDiscountInfoDOs,long deliveryFee,
                                    long taxFee, int appType, String bizCode) {
        // 创建订单
//        long itemTotalPrice = getItemTotalPrice(orderItemDOList);// 订单商品总价
    	for(OrderItemDO orderItemDO:orderItemDOList){
    		for(OrderItemDTO orderItemDTO : orderDTO.getOrderItems()){
    			if(orderItemDTO.getItemSkuId().equals(orderItemDO.getItemSkuId())){
    				orderItemDTO.setUnitPrice(orderItemDO.getUnitPrice());    				
    			}
    		}
    	}
        long itemTotalPrice = getRootItemTotalPrice(orderDTO);// 订单商品总价
        Long sellerId = orderDTO.getOrderItems().get(0).getSellerId();

        OrderDO orderDO = ModelUtil.convert2OrderDO(orderDTO); // 将DTO转为DO

        //设置bizCode
        orderDO.setBizCode(bizCode);

        //设置卖家id
        orderDO.setSellerId(sellerId);

        //设置优惠总价
        long totalDiscountAmount = getTotalDiscountAmount(orderDiscountInfoDOs);
        orderDO.setDiscountAmount(totalDiscountAmount);

        //设置运费
        orderDO.setDeliveryFee(deliveryFee);

        //设置税费
        orderDO.setTaxFee(taxFee);

        //设置订单总支付金额
        // 订单总价等于（商品总价＋总运费＋海关税费（针对跨境商品））－总优惠金额（优惠券＋满减送＋会员折扣＋余额抵现＋积分抵现）
//        long totalAmount = (itemTotalPrice + deliveryFee + taxFee) - totalDiscountAmount;
        long totalAmount = (itemTotalPrice + deliveryFee + taxFee) - totalDiscountAmount;

        //如果可使用优惠金额超过订单待付款金额，则将订单总金额设为0。并提示调用方
        boolean needPay = true;
        if (totalAmount <= 0) {
            totalAmount = 0;
            needPay = false;// 该订单无需再支付了
        }
        orderDO.setTotalAmount(totalAmount);
        orderDO.setTotalPrice(itemTotalPrice);//订单商品总价

        //设置订单配送方式
//        int deliveryId = 0;// 0代表卖家包邮，由卖家决定配送方式
        int deliveryId = 1;// 0代表卖家包邮，默认为快递邮寄
        if (orderDTO.getDeliveryId() != null) {
            deliveryId = orderDTO.getDeliveryId();
        }
        orderDO.setDeliveryId(deliveryId);

        //设置订单类型
        orderDO.setType(orderItemDOList.get(0).getItemType());

        //设置支付方式
        int paymentId = orderDTO.getPaymentId(); // 支付方式
        if (needPay == true) {
            orderDO.setPaymentId(paymentId);
        } else {
            // 无需支付的情况,paymentId置为0
            orderDO.setPaymentId(0);
        }

        //设置订单报销信息（如果订单报销信息不为空的话，则设置订单报销标记）
        if (orderDTO.getOrderInvoiceDTO() != null) {
            orderDO.setInvoiceMark(1);
        } else {
            orderDO.setInvoiceMark(0);
        }

        //设置订单优惠信息
        orderDO.setDiscountMark(getDiscountMark(orderDiscountInfoDOs));
        orderDO.setPoint(0L);//FIXME 嗨云平台只有在支付时才会使用虚拟财富
        orderDO.setPointDiscountAmount(0L);//FIXME 嗨云平台只有在支付时才会使用虚拟财富

        //设置下单的终端类型
        orderDO.setAppType(appType);

        //设置仓库和供应商id
        orderDO.setStoreId(orderDTO.getStoreId());
        orderDO.setSupplierId(orderDTO.getSupplierId());

        //设置订单跨境信息。TODO 订单跨境扩展信息要等税费结算逻辑好了之后再填充
        if (isHigoOrder(orderItemDOList)) {
            orderDO.setHigoMark(1);
        }else{
            orderDO.setHigoMark(0);
        }

        return orderDO;
    }

    /**
     * 获取订单类型
     * @param orderItemDOs
     * @param itemMap
     * @return
     */
    private EnumOrderType getOrderType(List<OrderItemDO> orderItemDOs, Map<Long, ItemDTO> itemMap){
        if(orderItemDOs.size()==1){
            Long itemId = orderItemDOs.get(0).getItemId();
            int itemType = itemMap.get(itemId).getItemType();
            if(itemType == ItemType.GROUP_BUY.getType()) {
                return EnumOrderType.GROUP_BUYING;
            }else if (itemType == ItemType.AUCTION.getType()) {
                return EnumOrderType.AUCTION;
            }else if (itemType == ItemType.SECKILL.getType()) {
                return EnumOrderType.SECKILL;
            }else if (itemType == ItemType.GIFT_PACKS.getType()) {
                return EnumOrderType.GIFT_PACK;
            }
        }
        return EnumOrderType.NORMAL;
    }

    /**
     * 获取订单优惠标志（二进制标签）
     * @param orderDiscountInfoDOs
     * @return
     */
    private int getDiscountMark(List<OrderDiscountInfoDO> orderDiscountInfoDOs) {
        // 如果订单有可用优惠实体的话，则给订单设置优惠标志
        int discountMark = 0;
        if (orderDiscountInfoDOs != null && orderDiscountInfoDOs.isEmpty() == false) {
            for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
                if (orderDiscountInfoDO.getDiscountType() == 1) {//优惠券和满减送
                    discountMark = (discountMark | 1);//从左往右，第一个二进制位置为1
                } else if (orderDiscountInfoDO.getDiscountType() == 2) {//积分和余额抵现
                    discountMark = (discountMark | 2);//从左往右，第二个二进制位设置为1
                }else if(orderDiscountInfoDO.getDiscountType() == 3){ //会员折扣
                    discountMark = (discountMark | 4);//从左往右，第三个二进制位置为1
                }
                // TODO 新增限时购和首单立减
                else if(orderDiscountInfoDO.getDiscountType() == 4){ //限时购
                    discountMark = (discountMark | 8);//从左往右，第四个二进制位置为1
                }
                else if(orderDiscountInfoDO.getDiscountType() == 5){ //首单立减
                    discountMark = (discountMark | 16);//从左往右，第五个二进制位置为1
                }
            }
        }
        return discountMark;
    }

    /**
     * 获取订单使用的总积分数
     * @param orderDiscountInfoDOs
     * @return
     */
//    private long getOrderPoint(List<OrderDiscountInfoDO> orderDiscountInfoDOs){
//        long point = 0L;
//        if (orderDiscountInfoDOs != null && orderDiscountInfoDOs.isEmpty() == false) {
//            for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
//                if (orderDiscountInfoDO.getDiscountType() == 2) {//积分和余额抵现
//                    if (orderDiscountInfoDO.getDiscountCode().equals("2")) {
//                        point += orderDiscountInfoDO.getWealthAmount();
//                    }
//                }
//            }
//        }
//        return point;
//    }

    /**
     * 获取订单的总积分抵现金额
     * @param orderDiscountInfoDOs
     * @return
     */
//    private long getOrderPointAmount(List<OrderDiscountInfoDO> orderDiscountInfoDOs){
//        long pointAmount = 0L;
//        if (orderDiscountInfoDOs != null && orderDiscountInfoDOs.isEmpty() == false) {
//            for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
//                if (orderDiscountInfoDO.getDiscountType() == 2) {//积分和余额抵现
//                    if (orderDiscountInfoDO.getDiscountCode().equals("2")) {
//                        pointAmount += orderDiscountInfoDO.getDiscountAmount();
//                    }
//                }
//            }
//        }
//        return pointAmount;
//    }

    /**
     * 获取订单的总优惠金额
     * @param orderDiscountInfoDOs
     * @return
     */
    private long getTotalDiscountAmount(List<OrderDiscountInfoDO> orderDiscountInfoDOs) {
        if (orderDiscountInfoDOs == null) {
            return 0L;
        }

        long totalDiscountAmount = 0L;
        for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
            totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
        }
        return totalDiscountAmount;
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

    /**
     * 获取指定订单商品列表的总价
     * @param orderItemDOList
     * @return
     */
    private long getCombItemTotalPrice(OrderDTO orderDTO) {
        long orderTotalPrice = 0L;
        Map<Long,Long> combItemTpriceMap = new HashMap<Long, Long>(); 
        for(OrderItemDTO orderItemDTO :orderDTO.getOrderItems()){
        	if(combItemTpriceMap.get(orderItemDTO.getCombineItemSkuId())==null){
        		combItemTpriceMap.put(orderItemDTO.getCombineItemSkuId(), orderItemDTO.getCombineItemPrice());
        	}
        }
        for(Map.Entry<Long, Long> entry:combItemTpriceMap.entrySet()){
        	orderTotalPrice += entry.getValue();
        }
        /*for(OrderItemDO orderItemDO:orderItemDOList){
            if(null==orderItemDO.getServiceUnitPrice())
                orderItemDO.setServiceUnitPrice(0L);
            Long unitPrice = orderItemDO.getUnitPrice();
            if(orderItemDO.isSuitSubItem()){
                unitPrice = 0L;
            }
            orderTotalPrice += (unitPrice * orderItemDO.getNumber());
            Long orderServicePrice = orderItemDO.getServiceUnitPrice()*orderItemDO.getNumber();
            orderTotalPrice += orderServicePrice;
        }*/

        return orderTotalPrice;
    }

    /**
     * 获取指定订单商品列表的总价
     * @param orderItemDOList
     * @return
     */
    private long getRootItemTotalPrice(OrderDTO orderDTO) {
        long orderTotalPrice = 0L;
        for(OrderItemDTO orderItemDTO :orderDTO.getOrderItems()){
        	orderTotalPrice += orderItemDTO.getNumber()*orderItemDTO.getUnitPrice();
        }

        return orderTotalPrice;
    }

    /**
     * 根据订单商品列表判定订单是否为跨境订单
     * @param orderItemDOList
     * @return
     */
    private boolean isHigoOrder(List<OrderItemDO> orderItemDOList) {
        if (orderItemDOList == null || orderItemDOList.isEmpty()) {
            return false;
        }

        for (OrderItemDO orderItemDO : orderItemDOList) {
            if (orderItemDO.getHigoMark().intValue() == 1) {
                return true;
            }
        }

        return false;
    }
}
