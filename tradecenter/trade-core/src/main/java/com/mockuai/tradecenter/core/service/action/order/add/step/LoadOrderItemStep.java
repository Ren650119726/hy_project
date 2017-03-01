package com.mockuai.tradecenter.core.service.action.order.add.step;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.itemcenter.common.constant.ItemStatus;
import com.mockuai.itemcenter.common.domain.dto.CompositeItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;

/**
 * Created by zengzhangqiang on 5/20/16.
 */
public class LoadOrderItemStep extends TradeBaseStep {
	private static final Logger log = LoggerFactory.getLogger(LoadOrderItemStep.class);
    @Override
    public StepName getName() {
        return StepName.LOAD_ORDER_ITEM_STEP;
    }

    @Override
    public TradeResponse execute() {
        ItemManager itemManager = (ItemManager) this.getBean("itemManager");
        String appKey = (String) this.getAttr("appKey");
        OrderDTO orderDTO = (OrderDTO) this.getParam("orderDTO");
        
        // 按照卖家将商品分组 用于商品平台查询商品信息
//        Map<Long, List<Long>> sellerItemSkuMap = this.genSellerItemSkuMap(orderDTO);
        //非组合商品
        Map<Long, List<Long>> sellerItemSkuMap = new HashMap<Long, List<Long>>();
        //组合商品单独生成订单
        Map<Long, List<Long>> sellerCombiItemSkuMap = new HashMap<Long, List<Long>>();
        
    	//正常商品DTO
    	OrderDTO orderDTONor = new OrderDTO();
    	//组合商品DTO
    	OrderDTO orderDTOComb = new OrderDTO();
    	
	    List<ItemSkuDTO> itemSkuList = new ArrayList<ItemSkuDTO>();
	    List<ItemDTO> itemList = new ArrayList<ItemDTO>();
        
        // TODO 后续考虑使用并发的方式
        // 
        try {
        	// 存在组合商品或非组合商品，组装相关对象
        	this.genSellerItemCombSkuMap(orderDTO,sellerItemSkuMap,sellerCombiItemSkuMap,orderDTONor,orderDTOComb);
        	        	
//        	log.info(" sellerItemSkuMap : "+JSONObject.toJSON(sellerItemSkuMap));
        	
        	if(sellerItemSkuMap!=null && sellerItemSkuMap.size()>0){
        		for (Map.Entry<Long, List<Long>> entry : sellerItemSkuMap.entrySet()) {
                    Long sellerId = entry.getKey();
                    List<Long> skuIdList = entry.getValue();
                    // 根据卖家ID和商品SKU ID列表来查询商品SKU信息
                    List<ItemSkuDTO> itemSkus = itemManager.queryItemSku(skuIdList, sellerId, appKey);
                    if (itemSkus == null | itemSkus.size() == 0) {
                        logger.error("itemSku is null : " + skuIdList + "," + sellerId);
                        return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "itemSku doesn't exist");
                    }

                    // 将查询到的商品SKU信息添加到SKU总列表中
                    itemSkuList.addAll(itemSkus);
                    List<Long> itemIdList = new ArrayList<Long>();
                    for (ItemSkuDTO itemSku : itemSkuList) {
                        itemIdList.add(itemSku.getItemId());
                    }

                    // 根据卖家ID和商品ID列表查询商品信息
                    List<ItemDTO> items = itemManager.queryItem(itemIdList, sellerId, appKey);
                    if (items == null | items.size() == 0) {
                        logger.error("Item is null" + itemIdList + " " + sellerId);
                        return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_NOT_EXIST, "item doesn't exist");
                    }
                    //判断商品状态
                    for(ItemDTO itemDTO:items){
                    	if (itemDTO.getItemStatus() != ItemStatus.ON_SALE.getStatus()) {
                    		return ResponseUtils.getFailResponse(ResponseCode.BZI_E_ITEM_NOT_GROUNDING, "该商品未上架");
                        }
                    }

                    // 将查询到的商品信息添加到商品总列表中
                    itemList.addAll(items);
                }
        	}
            
            
            // 组合商品数据组装
            if(CollectionUtils.isNotEmpty(orderDTOComb.getOrderItems())){
            	List<ItemSkuDTO> itemSkus = new ArrayList<ItemSkuDTO>();
            	List<ItemDTO> items = new ArrayList<ItemDTO>();
            	for(OrderItemDTO orderItemDTO :orderDTOComb.getOrderItems()){
            		List<ItemSkuDTO> skuRList = itemManager.queryItemSku(orderItemDTO.getItemSkuId(), appKey);
            		if(org.apache.commons.collections.CollectionUtils.isEmpty(skuRList)){
//            			return new TradeResponse(ResponseCode.BIZ_E_ORDER_ITEM_INVALID);
            			log.error(ResponseCode.BIZ_E_ORDER_ITEM_INVALID.getComment());
            			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_ITEM_INVALID);
            		}
//            		log.info(" === skuRList : "+JSONObject.toJSONString(skuRList));
            		
            		itemSkus.add(skuRList.get(0));
            		ItemQTO itemQTO = new ItemQTO();
            		itemQTO.setId(skuRList.get(0).getItemId());
            		itemQTO.setSellerId(orderItemDTO.getSellerId());
            		
//            		log.info(" === itemQTO : "+JSONObject.toJSONString(itemQTO));
            		
            		List<ItemDTO> itemRList = itemManager.queryItem(itemQTO, appKey);
            		
//            		log.info(" === itemRList : "+JSONObject.toJSONString(itemRList));
            		
            		items.add(itemRList.get(0));
            		//判断商品状态
                	if (itemRList.get(0).getItemStatus() != ItemStatus.ON_SALE.getStatus()) {
                		return ResponseUtils.getFailResponse(ResponseCode.BZI_E_ITEM_NOT_GROUNDING, "该商品未上架");
                    }
                    
            		// 更新item和itemsku信息到orderItemDTO
            		genOrderItemDTO(orderDTOComb.getUserId(),orderItemDTO,skuRList.get(0),itemRList.get(0),"default");
            		
            	}
            }
        } catch (TradeException e) {
            logger.error("", e);
            return ResponseUtils.getFailResponse(e.getResponseCode(), e.getMessage());
        }

        if(sellerItemSkuMap!=null && sellerItemSkuMap.size()>0){
        	// 将商品平台查询返回的商品信息封装为 Map 方便处理
            Map<Long, ItemSkuDTO> itemSkuMap = new HashMap<Long, ItemSkuDTO>();
            for (ItemSkuDTO itemSku : itemSkuList) {
                itemSkuMap.put(itemSku.getId(), itemSku);
            }

            Map<Long, ItemDTO> itemMap = new HashMap<Long, ItemDTO>();
            for (ItemDTO item : itemList) {
                itemMap.put(item.getId(), item);
            }
            
            //封装orderItemListMap和orderItemList，方便后续处理
            Map<Long, List<OrderItemDO>> orderItemListMap = Collections.EMPTY_MAP;
            List<OrderItemDO> orderItemDOList = Collections.EMPTY_LIST;
            try {
                orderItemListMap = genOrderItemListMap(orderDTONor, itemMap, itemSkuMap);
                orderItemDOList = genOrderItemList(orderDTONor, itemMap, itemSkuMap);
            } catch (TradeException e) {
                logger.error("", e);
                return ResponseUtils.getFailResponse(e.getResponseCode());
            }
    		
            // 设置商品单价
    		for(OrderItemDTO orderItemDTO:orderDTONor.getOrderItems()){
    			orderItemDTO.setUnitPrice(itemSkuMap.get(orderItemDTO.getItemSkuId()).getPromotionPrice());
    		}

            //过滤订单商品列表的相关信息
//            filterOrderItemDTOList(orderDTO, itemMap, itemSkuMap);
//            filterOrderItemDTOList(orderDTONor, itemMap, itemSkuMap);
            
            //将商品信息放入管道上下文中
            this.setAttr("orderDTONor" ,orderDTONor);
            this.setAttr("itemSkuMap" ,itemSkuMap);
            this.setAttr("itemMap" ,itemMap);
            this.setAttr("orderItemListMap", orderItemListMap);
            this.setAttr("orderItemList", orderItemDOList);
        }
                
        if(CollectionUtils.isNotEmpty(orderDTOComb.getOrderItems())){
        	
        	Map<Long,List<Long>> combineMap = this.combItemPriceMap(orderDTOComb);
        	
	        List<OrderItemDO> orderCombItemDOList = Collections.EMPTY_LIST;
	        try {
//	            orderCombItemListMap = genOrderItemListMap(orderDTOComb, combItemMap, combItemSkuMap);
	            orderCombItemDOList = genCombOrderItemList(orderDTOComb);
	        } catch (TradeException e) {
	            logger.error("", e);
	            return ResponseUtils.getFailResponse(e.getResponseCode());
	        }
	        
//	        log.info(" ----- orderDTOComb :"+JSONObject.toJSONString(orderDTOComb));
	        
	        //将商品信息放入管道上下文中
            this.setAttr("combineMap" ,combineMap);
            this.setAttr("orderDTOComb" ,orderDTOComb);
//	        this.setAttr("combItemSkuMap" ,combItemSkuMap);
//	        this.setAttr("combItemMap" ,combItemMap);
//	        this.setAttr("orderCombItemListMap", orderCombItemListMap);
	        this.setAttr("orderCombItemList", orderCombItemDOList);
        }
        
        
        
        //TODO 这里先暂时设置拆单参数为false，该参数需要根据商品平台的仓库相关逻辑来判断
//        this.setAttr("needSplitOrder", false);

        return ResponseUtils.getSuccessResponse();
    }

    /**
     * 组合商品id与单品id的对应关系
     * @param orderDTOComb
     * @return
     */
    private Map<Long,List<Long>> combItemPriceMap(OrderDTO orderDTOComb){
    	Map<Long,List<Long>> combineMap = new HashMap<Long, List<Long>>();
    	
    	for(OrderItemDTO orderItemDTO : orderDTOComb.getOrderItems()){
    		if(orderItemDTO.getCombineItemSkuId()!=null){
    			if(combineMap.get(orderItemDTO.getCombineItemSkuId()) ==null){
    				List<Long> combinelist = new ArrayList<Long>();
    				combinelist.add(orderItemDTO.getItemSkuId());
        			combineMap.put(orderItemDTO.getCombineItemSkuId(), combinelist);
        		}else{
        			combineMap.get(orderItemDTO.getCombineItemSkuId()).add(orderItemDTO.getItemSkuId());
        		}
    		}
    	}
    	
    	Iterator<Long> it = combineMap.keySet().iterator();  
    	while (it.hasNext()) { 
    		Long combSkuId = it.next();
    		long totalItemPrice = 0L;
    		long combItemPrice = 0L;
    		for(OrderItemDTO orderItemDTO : orderDTOComb.getOrderItems()){
    			if(combSkuId == orderItemDTO.getCombineItemSkuId() ){
    				totalItemPrice += orderItemDTO.getUnitPrice()*orderItemDTO.getNumber();
    				if(combItemPrice<=0){
    					combItemPrice = orderItemDTO.getCombineItemPrice();
    				}
    			}
    		}
    		long ratio = combItemPrice/totalItemPrice;
    		/*log.info(" combItemPrice : "+combItemPrice);
    		log.info(" totalItemPrice : "+totalItemPrice);*/
    		long tempPrice = 0l;
    		for(int i=0;i< orderDTOComb.getOrderItems().size();i++){
				if(combSkuId == orderDTOComb.getOrderItems().get(i).getCombineItemSkuId() ){
					if(i==orderDTOComb.getOrderItems().size()-1){
						/*log.info(" tempPrice :"+tempPrice);
						log.info(" combItemPrice-tempPrice : "+(combItemPrice-tempPrice));*/
						orderDTOComb.getOrderItems().get(i).setUnitPrice((combItemPrice-tempPrice)/orderDTOComb.getOrderItems().get(i).getNumber());
						break;
					}
					long iprice = (orderDTOComb.getOrderItems().get(i).getUnitPrice()*combItemPrice)/totalItemPrice;
		    		/*log.info(" orderDTOComb.getOrderItems().get(i).getUnitPrice() : "+orderDTOComb.getOrderItems().get(i).getUnitPrice());
		    		log.info(" iprice : "+iprice);*/
					orderDTOComb.getOrderItems().get(i).setUnitPrice(iprice);
					tempPrice+=iprice*orderDTOComb.getOrderItems().get(i).getNumber();
					
				}
    		}
    	}
    	
    	return combineMap;
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

    private OrderItemDTO getCombOrderItemSku(OrderItemDTO orderItem,List<ItemSkuDTO> itemSkuList,CompositeItemDTO compositeItemDTO,ItemDTO itemDTO) throws Exception{
    	Long skuId = compositeItemDTO.getSubSkuId();
    	
    	OrderItemDTO returnItem = new OrderItemDTO();
    	BeanUtils.copyProperties(returnItem, orderItem);
    	returnItem.setNumber(compositeItemDTO.getNum()*orderItem.getNumber());
    	returnItem.setItemSkuId(skuId);
    	returnItem.setItemType(itemDTO.getItemType());
    	returnItem.setCombineItemSkuId(orderItem.getItemSkuId());
    	returnItem.setCombineItemPrice(itemSkuList.get(0).getPromotionPrice()*orderItem.getNumber());  
    	returnItem.setCombineItemName(itemDTO.getItemName());
    	returnItem.setCombineItemNumber(orderItem.getNumber());
    	
    	return returnItem;
    }
    
    private void genSellerItemCombSkuMap(OrderDTO orderDTO,Map<Long, List<Long>> sellerItemSkuMap,Map<Long, List<Long>> sellerCombiItemSkuMap,OrderDTO orderDTONor,OrderDTO orderDTOComb)throws TradeException {
        // 按照供应商将商品分组 用于商品平台查询商品信息
    	ItemManager itemManager = (ItemManager) this.getBean("itemManager");
        String appKey = (String) this.getAttr("appKey");
        
    	List<OrderItemDTO> itemDTOsNor = new ArrayList<OrderItemDTO>();
    	List<OrderItemDTO> itemDTOsComb = new ArrayList<OrderItemDTO>();
    	
    	// 查询商品接口获取组合商品的单品 TODO
        try {
        	BeanUtils.copyProperties(orderDTONor, orderDTO);
        	BeanUtils.copyProperties(orderDTOComb, orderDTO);
        	for (OrderItemDTO orderItem : orderDTO.getOrderItems()) {
            	List<ItemSkuDTO> itemSkuList = itemManager.queryItemSku(orderItem.getItemSkuId(), appKey);
            	// 设置orderDTO单价
            	orderItem.setUnitPrice(itemSkuList.get(0).getPromotionPrice());
            	
            	List<Long> itemIdList = new ArrayList<Long>();
                for (ItemSkuDTO itemSku : itemSkuList) {
                    itemIdList.add(itemSku.getItemId());
                }
            	ItemQTO itemQTO = new ItemQTO();
    	    	itemQTO.setIdList(itemIdList);
    	    	itemQTO.setNeedComposite(true);
    	    	itemQTO.setSellerId(orderItem.getSellerId());
    	    	itemQTO.setNeedStockNum(false);

//            	log.info(" itemQTO : "+JSONObject.toJSONString(itemQTO));
        		List<ItemDTO> itemDTOs = itemManager.queryItem(itemQTO, appKey);

//            	log.info(" itemDTOs : "+JSONObject.toJSONString(itemDTOs));
        		if(CollectionUtils.isNotEmpty(itemDTOs)){
	        		for(ItemDTO itemDTO:itemDTOs){
	        			if(CollectionUtils.isNotEmpty(itemDTO.getCompositeItemList())){
	        				// 如果orderDTO包含组合商品设置组合商品skuid
	        				orderItem.setCombineItemSkuId(orderItem.getItemSkuId());
	        				for(CompositeItemDTO compositeItemDTO :itemDTO.getCompositeItemList()){	        					 
	        					 itemDTOsComb.add(getCombOrderItemSku(orderItem,itemSkuList,compositeItemDTO,itemDTO));
	        				}
	        			}else{
	        				itemDTOsNor.add(orderItem);
	        			}
	        		}
	        	}else{
	        		itemDTOsNor.add(orderItem);
	        	}
            }
//        	log.info(" itemDTOsNor : "+JSONObject.toJSONString(itemDTOsNor));
//        	log.info(" itemDTOsComb : "+JSONObject.toJSONString(itemDTOsComb));
        	
        	orderDTONor.setOrderItems(itemDTOsNor);
        	orderDTOComb.setOrderItems(itemDTOsComb);
        	
//        	log.info(" orderDTONor : "+JSONObject.toJSONString(orderDTONor));
//        	log.info(" orderDTOComb : "+JSONObject.toJSONString(orderDTOComb));
        	
	        // 将下单商品列表按照sellerid分组
        	if(CollectionUtils.isNotEmpty(itemDTOsNor)){
        		for (OrderItemDTO orderItem : itemDTOsNor) {
    	            Long skuId = orderItem.getItemSkuId();
    	            if (sellerItemSkuMap.get(orderItem.getSellerId()) == null) {
    	                List<Long> supplierItems = new ArrayList<Long>();
    	                supplierItems.add(skuId);
    	                sellerItemSkuMap.put(orderItem.getSellerId(), supplierItems);
    	            } else {
    	                sellerItemSkuMap.get(orderItem.getSellerId()).add(skuId);
    	            }
    	        }
        	}
	        
	        /*if(CollectionUtils.isNotEmpty(itemDTOsComb)){
	        	for (OrderItemDTO orderItem : itemDTOsComb) {
		            Long skuId = orderItem.getItemSkuId();
		            if (sellerCombiItemSkuMap.get(orderItem.getSellerId()) == null) {
		                List<Long> supplierItems = new ArrayList<Long>();
		                supplierItems.add(skuId);
		                sellerCombiItemSkuMap.put(orderItem.getSellerId(), supplierItems);
		            } else {
		            	sellerCombiItemSkuMap.get(orderItem.getSellerId()).add(skuId);
		            }
		        }
	        }*/
	        
		} catch (Exception e) {
			throw new TradeException(ResponseCode.SYS_E_DEFAULT_ERROR,e.getMessage()); 
		}

    }
    


    private void genOrderItemDTO(Long userId, OrderItemDTO orderItemDTO, ItemSkuDTO itemSkuDTO,
                                             ItemDTO itemDTO, String userName) {

    	orderItemDTO.setUserId(userId);
    	orderItemDTO.setUserName(userName);
        orderItemDTO.setItemName(orderItemDTO.getCombineItemName()+"("+itemDTO.getItemName()+")");
        orderItemDTO.setItemSkuDesc(itemSkuDTO.getSkuCode());
        orderItemDTO.setUnitPrice(itemSkuDTO.getPromotionPrice());
        orderItemDTO.setItemId(itemSkuDTO.getItemId());
        orderItemDTO.setItemImageUrl(itemDTO.getIconUrl());
        orderItemDTO.setDeliveryType(itemDTO.getDeliveryType());
        orderItemDTO.setCategoryId(itemDTO.getCategoryId());
        orderItemDTO.setItemBrandId(itemDTO.getItemBrandId());
        
        orderItemDTO.setHigoMark(itemDTO.getHigoMark());

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

    private List<OrderItemDO> genCombOrderItemList(OrderDTO orderDTO) throws TradeException {
        List<OrderItemDO> orderItemDOList = new ArrayList<OrderItemDO>();
        try {
	        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
	
	            OrderItemDO orderItemDO = new OrderItemDO();
	            
				BeanUtils.copyProperties(orderItemDO, orderItemDTO);
				
	            orderItemDOList.add(orderItemDO);
	        }
        } catch (Exception e) {
			e.printStackTrace();
			throw new TradeException(e);
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
