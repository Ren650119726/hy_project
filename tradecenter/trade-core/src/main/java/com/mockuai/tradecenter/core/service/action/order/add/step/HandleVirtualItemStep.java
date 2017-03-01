//package com.mockuai.tradecenter.core.service.action.order.add.step;
//
//import com.mockuai.itemcenter.common.constant.VirtualMark;
//import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
//import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
//import com.mockuai.marketingcenter.common.domain.dto.ActivityItemDTO;
//import com.mockuai.tradecenter.common.api.TradeResponse;
//import com.mockuai.tradecenter.common.constant.ResponseCode;
//import com.mockuai.tradecenter.common.domain.OrderDTO;
//import com.mockuai.tradecenter.common.domain.OrderItemDTO;
//import com.mockuai.tradecenter.core.domain.OrderItemDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.service.RequestContext;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by zengzhangqiang on 5/21/16.
// */
//public class HandleVirtualItemStep extends TradeBaseStep {
//    @Override
//    public StepName getName() {
//        return null;
//    }
//
//    @Override
//    public TradeResponse execute() {
//        return null;
//    }
//
//    private Map<Long, List<OrderItemDO>> genOrderItemListMap(OrderDTO orderDTO, Map<Long, ItemDTO> itemMap,
//                                                             Map<Long, ItemSkuDTO> itemSkuMap, String appKey,Map<Long,ActivityItemDTO> activityItemMap) throws TradeException {
//        Map<Long, List<OrderItemDO>> orderItemListMap = new HashMap<Long, List<OrderItemDO>>();
//        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
//            ItemSkuDTO itemSkuDTO = itemSkuMap.get(orderItemDTO.getItemSkuId());
//
////			if(null!=orderItemListMap.get(orderItemDTO.getItemSkuId())){
////				continue;
////			}
//
//            if (itemSkuDTO == null) { // 如果该商品在不存在
//                logger.error("itemSku doesn't exist : " + orderItemDTO.getItemSkuId());
//                throw new TradeException(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "itemSku doesn't exist");
//            }
//
//            ItemDTO itemDTO = itemMap.get(itemSkuDTO.getItemId());
//            if (itemDTO == null) {
//                logger.error("item doesn't exist" + itemSkuDTO.getItemId());
//                throw new TradeException(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "item doesn't exist");
//            }
//
//            OrderItemDO orderItemDO = this.getOrderItemDO(orderDTO, orderItemDTO, itemSkuDTO, itemDTO, appKey);
//
//
//            //增加虚拟商品标识
//            if(null!=itemDTO.getVirtualMark()&&itemDTO.getVirtualMark()== VirtualMark.VIRTUAL.getCode()){
//                orderItemDO.setVirtualMark(1);
//            }
//
//
//        }
//
//        return orderItemListMap;
//    }
//}
