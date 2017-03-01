//package com.mockuai.tradecenter.core.service.action.order.add.step;
//
//import com.mockuai.itemcenter.common.constant.DBConst;
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
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by zengzhangqiang on 5/21/16.
// */
//public class HandleSuitItemStep extends TradeBaseStep {
//    @Override
//    public String getName() {
//        return null;
//    }
//
//    @Override
//    public TradeResponse execute() {
//        return null;
//    }
//
//
//
//
//
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
//            add2OrderItemListMap(orderItemListMap,orderItemDO);
//            // TODO ....增加套装子商品的查询
//
//
//            if (itemDTO.getItemType().intValue() == DBConst.SUIT_ITEM.getCode()) {
//
//                ItemDTO suitItemDTO = itemManager.getSuitItem(itemDTO.getSellerId(),
//                        itemDTO.getId(), appKey);
//
//                if (null != suitItemDTO && suitItemDTO.getSubItemList().size() > 0) {
//                    for (ItemDTO subItemDTO : suitItemDTO.getSubItemList()) {
//                        itemMap.put(subItemDTO.getId(), subItemDTO);
//
//                        List<Long> subSkuIds = new ArrayList<Long>();
//                        for (ItemSkuDTO subItemSkuDTO : subItemDTO.getItemSkuDTOList()) {
//                            subSkuIds.add(subItemSkuDTO.getId());
//                        }
//                        List<ItemSkuDTO> subItemSkuDTOList = itemManager.queryItemSku(subSkuIds,
//                                subItemDTO.getSellerId(), appKey);
//
//                        if(null==subItemSkuDTOList){
//                            throw new TradeException("subItemSkuDTOList is null");
//                        }
//                        ItemSkuDTO subItemSkuDTO = subItemSkuDTOList.get(0);
//
//                        itemSkuMap.put(subItemSkuDTO.getId(), subItemSkuDTO);
//
//                        if( null != subItemSkuDTOList && subItemSkuDTOList.size()>0 ){
//                            OrderItemDO subOrderItemDO = this.getSubOrderItemDO(orderDTO,  subItemSkuDTOList.get(0), subItemDTO,
//                                    appKey);
//
//                            subOrderItemDO.setOriginalSkuId(itemSkuDTO.getId());
//
//                            add2OrderItemListMap(orderItemListMap,subOrderItemDO);
//                        }
//
//                    }
//                }
//            }
//
//
//
//            if(orderItemDTO.getActivityId()!=null){
//                List<OrderItemDO> subOrderItemDOList = getSubOrderItemList(orderDTO,orderItemDTO.getItemSkuId(),orderItemDTO.getItemList(),orderItemDTO.getSellerId(),appKey);
//                if(null!=subOrderItemDOList&&!subOrderItemDOList.isEmpty()){
//                    for(OrderItemDO subOrderItemDO:subOrderItemDOList){
//                        if(null==orderItemListMap.get(subOrderItemDO.getItemSkuId())){
//                            add2OrderItemListMap(orderItemListMap,subOrderItemDO);
//                        }
//                        ActivityItemDTO activityItemDTO = activityItemMap.get(orderItemDO.getItemSkuId());
//                        if(null!=activityItemDTO){
//                            orderItemDO.setUnitPrice(activityItemDTO.getUnitPrice());
//                        }
//                    }
//                }
//            }
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
//
//    private OrderItemDO getSubOrderItemDO(OrderDTO orderDTO, ItemSkuDTO itemSkuDTO,
//                                          ItemDTO itemDTO, String appKey) {
//
//        OrderItemDO orderItemDO = new OrderItemDO();
//        orderItemDO.setUserId(orderDTO.getUserId());
//        orderItemDO.setSellerId(itemDTO.getSellerId());
//
//        // 查询买家名称
//        try {
//            orderItemDO.setUserName(userManager.getUserName(orderDTO.getUserId(), appKey));
//        } catch (TradeException e) {
//            // TODO error handle
//        }
//
//        orderItemDO.setNumber(1);
//        orderItemDO.setItemType(itemDTO.getItemType());
//        orderItemDO.setItemName(itemDTO.getItemName());
//        orderItemDO.setItemSkuDesc(itemSkuDTO.getSkuCode());
//        if(itemSkuDTO.getSkuCode()==null){
//            orderItemDO.setItemSkuDesc(itemDTO.getItemName());
//        }
//        orderItemDO.setUnitPrice(itemSkuDTO.getPromotionPrice());
////		orderItemDO.setUnitPrice(0L);
//        orderItemDO.setSuitSubItem(true);
//        orderItemDO.setItemSkuId(itemSkuDTO.getId());
//        orderItemDO.setItemId(itemSkuDTO.getItemId());
//        orderItemDO.setItemImageUrl(itemDTO.getIconUrl());
//        orderItemDO.setDeliveryType(itemDTO.getDeliveryType());
//        orderItemDO.setCategoryId(itemDTO.getCategoryId());
//        orderItemDO.setItemBrandId(itemDTO.getItemBrandId());
//
//        return orderItemDO;
//    }
//
//    private List<OrderItemDO> getSubOrderItemList(OrderDTO orderDTO,Long parentSkuId,List<OrderItemDTO> orderItemList,Long sellerId,String appKey)throws TradeException{
//
//        if(null==orderItemList){
//            return null;
//        }
//
//        Map<Long, ItemSkuDTO> itemSkuMap = new HashMap<Long, ItemSkuDTO>();
//        Map<Long, ItemDTO> itemMap = new HashMap<Long, ItemDTO>();
//
//
//        List<Long> skuIdList = new ArrayList<Long>();
//        List<Long> itemIdList = new ArrayList<Long>();
//        for(OrderItemDTO orderItemDTO:orderItemList){
//            skuIdList.add(orderItemDTO.getItemSkuId());
//        }
//
//        // 根据卖家ID和商品SKU ID列表来查询商品SKU信息
//        List<ItemSkuDTO> itemSkus = this.itemManager.queryItemSku(skuIdList, sellerId, appKey);
//        if (itemSkus == null | itemSkus.size() == 0) {
//            log.error("itemSku is null : " + skuIdList + "," + sellerId);
//            throw new TradeException(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "itemSku doesn't exist");
//        }
//        for(ItemSkuDTO itemSkuDTO:itemSkus){
//            itemIdList.add(itemSkuDTO.getItemId());
//        }
//
//
//        // 根据卖家ID和商品ID列表查询商品信息
//        List<ItemDTO> items = this.itemManager.queryItem(itemIdList, sellerId, appKey);
//        if (items == null | items.size() == 0) {
//            log.error("Item is null" + itemIdList + " " + sellerId);
//            throw new TradeException(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "item doesn't exist");
//        }
//
//
//        // 将商品平台查询返回的商品信息封装为 Map 方便处理
//        for (ItemSkuDTO itemSku : itemSkus) {
//            itemSkuMap.put(itemSku.getId(), itemSku);
//
//        }
//
//        for (ItemDTO item : items) {
//            itemMap.put(item.getId(), item);
//        }
//        List<OrderItemDO> returnList = new ArrayList<OrderItemDO>();
//        for(OrderItemDTO orderItemDTO:orderItemList){
//            ItemSkuDTO itemSkuDTO = null;
//            itemSkuDTO = itemSkuMap.get(orderItemDTO.getItemSkuId());
//            ItemDTO itemDTO = null;
//            if(null!=itemSkuDTO)
//                itemDTO = itemMap.get(itemSkuDTO.getItemId());
//            if( null!=itemSkuDTO&& null!=itemDTO ){
//                OrderItemDO orderItemDO = this.getOrderItemDO(orderDTO, orderItemDTO, itemSkuDTO, itemDTO, appKey);
//                orderItemDO.setOriginalSkuId(parentSkuId);
////				orderItemDO.setUnitPrice(orderItemDTO.getUnitPrice());
//                returnList.add(orderItemDO);
//            }
//        }
//        return returnList;
//
//
//    }
//}
