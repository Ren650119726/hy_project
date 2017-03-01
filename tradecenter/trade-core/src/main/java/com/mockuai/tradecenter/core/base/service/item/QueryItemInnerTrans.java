package com.mockuai.tradecenter.core.base.service.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.base.TradeInnerOper;
import com.mockuai.tradecenter.core.base.request.InnerRequest;
import com.mockuai.tradecenter.core.base.result.ItemResponse;
import com.mockuai.tradecenter.core.base.result.TradeOperResult;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;

@Service
public class QueryItemInnerTrans implements TradeInnerOper {
	 private static final Logger log = LoggerFactory.getLogger(QueryItemInnerTrans.class);
	@Resource
	ItemManager itemManager;
	@Resource
	OrderManager 		orderManager;
	
	@Override
	public TradeOperResult doTransaction(InnerRequest req)throws TradeException {
		// TODO Auto-generated method stub
		  //将商品平台查询返回的商品信息封装为 Map 方便处理
        final Map<Long, ItemSkuDTO> itemSkuMap = new HashMap<Long, ItemSkuDTO>();
        Map<Long, ItemDTO> itemMap = new HashMap<Long, ItemDTO>();
        //查询订单商品以及SKU信息
        queryOrderItems(req.getItemRequest().getOrderItemList(), itemMap, itemSkuMap, req.getAppKey());
        
        System.out.println("itemSkuMap:"+itemSkuMap);
        System.out.println("itemMap:"+itemMap);
        
        for(OrderItemDTO orderItemDTO:req.getItemRequest().getOrderItemList()){
        	ItemSkuDTO itemSkuDTO = itemSkuMap.get(orderItemDTO.getItemSkuId());
        	ItemDTO itemDTO = itemMap.get(itemSkuDTO.getItemId());
        }
        
    	checkOrderItem(req.getItemRequest().getOrderItemList(), itemMap, itemSkuMap, req.getUserId(),req.getAppKey());
        
        TradeOperResult tradeOperResult = new TradeOperResult();
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setItemMap(itemMap);
        itemResponse.setItemSkuMap(itemSkuMap);
        tradeOperResult.setItemResponse(itemResponse);
        
        tradeOperResult.setSuccess(true);
        
		return tradeOperResult;
	}
	
	/**
     * 查询订单中的商品和SKU列表
     *
     * @param itemMap
     * @param itemSkuMap
     * @throws Exception
     */
    private void queryOrderItems(List<OrderItemDTO> orderItemList, Map<Long, ItemDTO> itemMap,
                                 Map<Long, ItemSkuDTO> itemSkuMap, String appKey) throws TradeException {
        //按照供应商将商品分组 用于商品平台查询商品信息
        Map<Long, List<Long>> sellerItemSkuMap = this.genSellerItemSkuMap(orderItemList);

        List<ItemSkuDTO> itemSkuList = new ArrayList<ItemSkuDTO>();
        List<ItemDTO> itemList = new ArrayList<ItemDTO>();
        // TODO 后续考虑使用并发的方式
        //根据卖家ID分批查询商品sku信息和商品信息（由于商品sku表和商品表是基于卖家ID做分表的，所以这里需要分批查询）
        for (Entry<Long, List<Long>> entry : sellerItemSkuMap.entrySet()) {
            Long sellerId = entry.getKey();
            List<Long> skuIdList = entry.getValue();
            // 根据卖家ID和商品SKU ID列表来查询商品SKU信息
            List<ItemSkuDTO> itemSkus = this.itemManager.queryItemSku(skuIdList, sellerId, appKey);
            if (itemSkus == null| itemSkus.size() == 0) {
                log.error("itemSku is null : " + skuIdList + "," + sellerId);
                throw new TradeException(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"itemSku doesn't exist");
            }

            //将查询到的商品SKU信息添加到SKU总列表中
            itemSkuList.addAll(itemSkus);
            List<Long> itemIdList = new ArrayList<Long>();
            for (ItemSkuDTO itemSku : itemSkuList) {
                itemIdList.add(itemSku.getItemId());
            }

            //根据卖家ID和商品ID列表查询商品信息
            List<ItemDTO> items = this.itemManager.queryItem(itemIdList, sellerId, appKey);
            if (items == null| items.size() == 0) {
                log.error("Item is null" + itemIdList + " " + sellerId);
				throw new TradeException(ResponseCode.BIZ_E_ITEM_NOT_EXIST,"item doesn't exist");
            }

            //将查询到的商品信息添加到商品总列表中
            itemList.addAll(items);
        }

        // 将商品平台查询返回的商品信息封装为 Map 方便处理
        for (ItemSkuDTO itemSku : itemSkuList) {
            itemSkuMap.put(itemSku.getId(), itemSku);

        }

        for (ItemDTO item : itemList) {
            itemMap.put(item.getId(), item);
        }

    }
    
    private Map<Long, List<Long>> genSellerItemSkuMap(List<OrderItemDTO> orderiItemList) {
        // 按照供应商将商品分组 用于商品平台查询商品信息
        Map<Long, List<Long>> sellerItemSkuMap = new HashMap<Long, List<Long>>();
        // 将下单商品列表按照供应商id分组
        for (OrderItemDTO orderItem : orderiItemList) {
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
    
    private void checkOrderItem(List<OrderItemDTO> orderItemDTOList, Map<Long, ItemDTO> itemMap, Map<Long, ItemSkuDTO> itemSkuMap,
    		Long userId,
			String appKey) throws TradeException {
		
		
		for (OrderItemDTO orderItemDTO : orderItemDTOList) {
			
			String itemSkuKey = orderItemDTO.getItemSkuId() + "_" + orderItemDTO.getSellerId();
			ItemSkuDTO itemSkuDTO = itemSkuMap.get(orderItemDTO.getItemSkuId());
			if (itemSkuDTO == null) { // 该商品在不存在
				log.error("itemSku doesn't exist : " + itemSkuKey);
				throw new TradeException(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "itemSku doesn't exist");
			}

			ItemDTO itemDTO = itemMap.get(itemSkuDTO.getItemId());

			checkUserItemPurchase(itemDTO, userId, orderItemDTO.getSellerId(), orderItemDTO.getNumber().intValue(),
					appKey);

			// 验证商品销售时间和销售量限制
			// TODO 销售时间的验证需要重构，不能直接使用应用服务器时间
			Date now = new Date();
			if (itemDTO.getSaleMaxNum() != null && itemDTO.getSaleMaxNum() != 0
					&& orderItemDTO.getNumber().intValue() > itemDTO.getSaleMaxNum().intValue()) { // 购买最大量限制
				throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT,
						"该商品最多只能购买" + itemDTO.getSaleMaxNum().intValue() + "件");
			} else if (itemDTO.getSaleMinNum() != null && itemDTO.getSaleMinNum() != 0
					&& orderItemDTO.getNumber().intValue() < itemDTO.getSaleMinNum().intValue()) { // 购买最小量限制
				throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MIN_AMOUNT,
						"该商品最少需要购买" + itemDTO.getSaleMinNum().intValue() + "件");
			} else if (itemDTO.getItemStatus() != 4) {
				throw new TradeException(ResponseCode.BZI_E_ITEM_NOT_GROUNDING, "该商品未上架");
			}
			else if (itemSkuDTO.getStockNum() != null){
				long frozenStockNum = 0L;
				if(null!=itemSkuDTO.getFrozenStockNum()){
					frozenStockNum = itemSkuDTO.getFrozenStockNum();
				}
				long realStockNum = itemSkuDTO.getStockNum().longValue()-frozenStockNum;
				if(orderItemDTO.getNumber().longValue() > realStockNum){
					log.error("orderItem number " + orderItemDTO.getNumber().intValue() + " out of stock number"
							+ itemSkuDTO.getStockNum().intValue());
					throw new TradeException(ResponseCode.BIZ_E_ITEM_OUT_OF_STOCK);
				}
			}

			// 修改商品的单价为 商品平台的商品单价
			// orderItemDTO.setUnitPrice(itemSkuDTO.getPromotionPrice());

		}
	}
    
 // 用户购买限购
 	private void checkUserItemPurchase(ItemDTO itemDTO, Long userId, Long sellerId, Integer num, String appKey)
 			throws TradeException {

 		OrderQTO query = new OrderQTO();

 		query.setUserId(userId);

 		if (null == itemDTO.getId() || null == sellerId) {
 			throw new TradeException(ResponseCode.PARAM_E_PARAM_INVALID,
 					ResponseCode.PARAM_E_PARAM_INVALID.getComment());
 		}

 		Integer itmeBuyLimitCount = itemManager.getItemBuyLimit(itemDTO.getId(), sellerId, appKey);

 		if (null != itmeBuyLimitCount && itmeBuyLimitCount != 0) {
			Integer hasBuyCount = orderManager.getHasBuyCount(userId, itemDTO.getId());
			if (itmeBuyLimitCount > hasBuyCount) {
 				Integer canBuyCount = itmeBuyLimitCount - hasBuyCount;
 				if (num > canBuyCount) {
 					log.error("item over the purchase quantity : " + itemDTO.getId());
 					throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "item over the purchase quantity");
 				}
 			} else {
 				throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "该商品已经超过最多购买量");
 			}
 		}

 	}

}
