package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.OrderStockDTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.OrderSeqManager;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.util.TradeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 冻结指定商品库存，并对商品进行分组，按照商品所属仓库进行分组
 * Created by zengzhangqiang on 5/19/16.
 */
public class HandleOrderSplitStep extends TradeBaseStep {
    @Override
    public StepName getName() {
        return StepName.HANDLE_ORDER_SPLIT_STEP;
    }

    @Override
    public TradeResponse execute() {
        OrderDTO orderDTO = (OrderDTO) this.getParam("orderDTO");
        OrderDTO orderDTONor = (OrderDTO) this.getAttr("orderDTONor");
        OrderDTO orderDTOComb = (OrderDTO) this.getAttr("orderDTOComb");
        String appKey = (String) this.getAttr("appKey");
        String bizCode = (String) this.getAttr("bizCode");
//        Map<Long, ItemSkuDTO> itemSkuMap = (Map<Long, ItemSkuDTO>) this.getAttr("itemSkuMap");
//        Map<Long, ItemDTO> itemMap = (Map<Long, ItemDTO>) this.getAttr("itemMap");
        List<OrderItemDO> orderItemList = (List<OrderItemDO>) this.getAttr("orderItemList");
        Boolean needSplitOrder = (Boolean)this.getAttr("needSplitOrder");
        Map<Long, List<OrderItemDTO>> storeOrderItemMap = (Map<Long, List<OrderItemDTO>>)this.getAttr("storeOrderItemMap");
        Map<Long, Long> storeSupplierMap = (Map<Long, Long>) this.getAttr("storeSupplierMap");
        String mainOrderSn = (String) this.getAttr("mainOrderSn");//主订单的订单流水号
        
        String rootOrderSn = (String) this.getAttr("rootOrderSn");//根订单的订单流水号        

        List<OrderItemDO> orderCombItemList = (List<OrderItemDO>) this.getAttr("orderCombItemList");
        Boolean needSplitCombOrder = (Boolean)this.getAttr("needSplitCombOrder");
        Map<Long, List<OrderItemDTO>> storeCombOrderItemMap = (Map<Long, List<OrderItemDTO>>)this.getAttr("storeCombOrderItemMap");
        Map<Long, Long> storeCombSupplierMap = (Map<Long, Long>) this.getAttr("storeCombSupplierMap");
        String combOrderSn = (String) this.getAttr("combOrderSn");//组合订单的订单流水号

        try {
            OrderSeqManager orderSeqManager = (OrderSeqManager) this.getBean("orderSeqManager");
            //如果需要拆单，则生成子单列表
            if (needSplitOrder!=null && needSplitOrder == true) {
                //获取子订单流水号
                List<String> subOrderSnList = orderSeqManager.getSubOrderSnList(mainOrderSn, storeOrderItemMap.size());
                //生成子订单列表
                int i=0;
                List<OrderDTO> subOrderList = new ArrayList<OrderDTO>();
                for (Map.Entry<Long, List<OrderItemDTO>> entry : storeOrderItemMap.entrySet()) {
                    OrderDTO subOrder = new OrderDTO();
                    subOrder.setOrderSn(subOrderSnList.get(i++));
                    subOrder.setStoreId(entry.getKey());
                    subOrder.setSupplierId(storeSupplierMap.get(entry.getKey()));
                    subOrder.setOrderItems(entry.getValue());
                    subOrder.setTotalPrice(getSumOfItemPrice(entry.getValue()));
                    subOrderList.add(subOrder);
                }

                //将子单列表放入管道上下文中
                this.setAttr("subOrderDTOList", subOrderList);
                //将skuId与订单映射集放入管道上下文中，便于后续处理
                Map<Long,OrderDTO> skuOrderMap = getSkuOrderMap(subOrderList);
                this.setAttr("skuOrderMap", skuOrderMap);
                this.setAttr("orderItemListMap", getOrderItemListMap(skuOrderMap, orderItemList));
            }
            

            //如果需要拆单，则生成子单列表
            if (needSplitCombOrder != null &&needSplitCombOrder == true) {
            	Long orderTotalPrice = 0l;
            	Map<Long,Long> combItemTpriceMap = new HashMap<Long, Long>(); 
                for(OrderItemDTO orderItemDTO :orderDTOComb.getOrderItems()){
                	if(combItemTpriceMap.get(orderItemDTO.getCombineItemSkuId())==null){
                		combItemTpriceMap.put(orderItemDTO.getCombineItemSkuId(), orderItemDTO.getCombineItemPrice());
                	}
                }
                for(Map.Entry<Long, Long> entry:combItemTpriceMap.entrySet()){
                	orderTotalPrice += entry.getValue();
                }
            	
                //获取子订单流水号
                List<String> subOrderSnList = orderSeqManager.getSubOrderSnList(combOrderSn, storeCombOrderItemMap.size());
                //生成子订单列表
                int i=0;
                Long mPrice=0l;
                List<OrderDTO> subOrderList = new ArrayList<OrderDTO>();
                for (Map.Entry<Long, List<OrderItemDTO>> entry : storeCombOrderItemMap.entrySet()) {
                	
                    OrderDTO subOrder = new OrderDTO();
                    subOrder.setOrderSn(subOrderSnList.get(i++));
                    subOrder.setStoreId(entry.getKey());
                    subOrder.setSupplierId(storeCombSupplierMap.get(entry.getKey()));
                    subOrder.setOrderItems(entry.getValue());
                    if(i==storeCombOrderItemMap.size()-1){
                    	subOrder.setTotalPrice(orderTotalPrice-mPrice);
                	}else{
                		subOrder.setTotalPrice(getSumOfItemPrice(entry.getValue()));
                        mPrice+=getSumOfItemPrice(entry.getValue());
                	}                    
                    subOrderList.add(subOrder);
                }

                //将子单列表放入管道上下文中
                this.setAttr("subCombOrderDTOList", subOrderList);
                //将skuId与订单映射集放入管道上下文中，便于后续处理
                Map<Long,OrderDTO> skuOrderMap = getSkuOrderMap(subOrderList);
                this.setAttr("skuCombOrderMap", skuOrderMap);
                this.setAttr("orderCombItemListMap", getOrderItemListMap(skuOrderMap, orderCombItemList));
            }
        } catch (TradeException e) {
            return ResponseUtils.getFailResponse(e.getResponseCode(), e.getMessage());
        }

        return ResponseUtils.getSuccessResponse();
    }

    private long getSumOfItemPrice(List<OrderItemDTO> orderItemDTOs) {
        long totalPrice = 0L;

        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            long itemPrice = orderItemDTO.getUnitPrice() * orderItemDTO.getNumber();
            totalPrice += itemPrice;
        }

        return totalPrice;
    }

    /**
     * 获取skuId与订单对象的映射关系集
     * @param orderDTOs
     * @return
     */
    private Map<Long, OrderDTO> getSkuOrderMap(List<OrderDTO> orderDTOs) {
        Map<Long, OrderDTO> skuOrderMap = new HashMap<Long, OrderDTO>();
        for (OrderDTO orderDTO: orderDTOs) {
            for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
                skuOrderMap.put(orderItemDTO.getItemSkuId(), orderDTO);
            }
        }

        return skuOrderMap;
    }

    private Map<String, List<OrderItemDO>> getOrderItemListMap(Map<Long, OrderDTO> skuOrderMap,
                                                               List<OrderItemDO> orderItemDOList) {
        Map<String, List<OrderItemDO>> orderItemListMap = new HashMap<String, List<OrderItemDO>>();
        for (OrderItemDO orderItemDO : orderItemDOList) {
            if (skuOrderMap.containsKey(orderItemDO.getItemSkuId()) == false) {
                String errorMsg = "the order is null, which is corresponding to sku, skuId="+orderItemDO.getItemSkuId();
                logger.error(errorMsg);
                throw new IllegalStateException(errorMsg);
            }


            OrderDTO orderDTO = skuOrderMap.get(orderItemDO.getItemSkuId());
            if (orderItemListMap.containsKey(orderDTO.getOrderSn()) == false) {
                orderItemListMap.put(orderDTO.getOrderSn(), new ArrayList<OrderItemDO>());
            }

            orderItemListMap.get(orderDTO.getOrderSn()).add(orderItemDO);
        }

        return orderItemListMap;
    }
}
