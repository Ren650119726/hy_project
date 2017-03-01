package com.mockuai.tradecenter.core.manager.impl;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.core.dao.OrderDAO;
import com.mockuai.tradecenter.core.dao.OrderItemDAO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class OrderItemManagerImpl extends BaseService implements OrderItemManager {
	private static final Logger log = LoggerFactory.getLogger(OrderItemManagerImpl.class);

	@Resource 
	private OrderItemDAO orderItemDAO;
	
	@Resource
	private OrderDAO orderDAO;
	
	 @Resource
	 private ItemManager itemManager;
	 
	@Resource
	private TransactionTemplate transactionTemplate;
	
	@Override
	public long addOrderItem(OrderItemDO orderItem) throws TradeException{
		log.info("enter addOrderItem: " + "orderId :" + orderItem.getOrderId() );
		long result = 0;
		try {
			if(orderItem.getItemSkuDesc()==null)
				orderItem.setItemSkuDesc(orderItem.getItemName());
			result = this.orderItemDAO.addOrderItem(orderItem);
		} catch (DataAccessException e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e.getMessage());
		}
		log.info("exit addOrderItem result :  " + result);
		return result;
	}
	
	@Override
	public List<OrderItemDO> queryOrderItem(OrderItemQTO orderItemQTO)throws TradeException{
		//TODO 入参校验
		List<OrderItemDO> result = null;
		try{
//			OrderQTO orderQuery = new OrderQTO();
//			orderQuery.setOriginalOrder(orderItemQTO.getOrderId());
//			List<OrderDO> subOrders = orderDAO.querySubOrdersByOriginalOrder(orderQuery);
//			
//			if(null!=subOrders&&subOrders.size()>0){
//				List<Long> orderIdList = new ArrayList<Long>();
//				for(OrderDO subOrder:subOrders){
//					orderIdList.add(subOrder.getId());
//				}
//				orderItemQTO.setOrderIdList(orderIdList);
//				orderItemQTO.setOrderId(null);
//			}
			result = this.orderItemDAO.queryOrderItem(orderItemQTO);
		}catch(Exception e){
			log.error("OrderItemManagerImpl.queryOrderItem.error",e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
		}
		/*log.info("exit queryOrderItem : " + result.size());*/
		return result ;
	}

    @Override
    public List<OrderItemDO> queryOrderItemBg(OrderItemQTO orderItemQTO)throws TradeException{
        //TODO 入参校验
        List<OrderItemDO> result = null;
        try{
//			OrderQTO orderQuery = new OrderQTO();
//			orderQuery.setOriginalOrder(orderItemQTO.getOrderId());
//			List<OrderDO> subOrders = orderDAO.querySubOrdersByOriginalOrder(orderQuery);
//
//			if(null!=subOrders&&subOrders.size()>0){
//				List<Long> orderIdList = new ArrayList<Long>();
//				for(OrderDO subOrder:subOrders){
//					orderIdList.add(subOrder.getId());
//				}
//				orderItemQTO.setOrderIdList(orderIdList);
//				orderItemQTO.setOrderId(null);
//			}
            result = this.orderItemDAO.queryOrderItemBg(orderItemQTO);
        }catch(Exception e){
            log.error("OrderItemManagerImpl.queryOrderItemBg.error",e);
            throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
        }
		/*log.info("exit queryOrderItem : " + result.size());*/
        return result ;
    }




	@Override
	public OrderItemDO getOrderItem(OrderItemQTO orderItemQTO) throws TradeException {
		OrderItemDO result = null;
		try{
			result = this.orderItemDAO.getOrderItem(orderItemQTO);
		}catch(Exception e){
			log.info("OrderItemManagerImpl.queryOrderItem.error",e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
		}
		return result;
	}

	@Override
	public void checkItemForGetPaymentUrl(List<OrderItemDO> orderList, String appkey) throws TradeException {
		printIntoService(log,"checkItemForGetPaymentUrl",orderList,"");
		
		Map<Long, Set<Long>> sellerItemIdMap = genSellerItemIdMap(orderList);
		
    	for (Map.Entry<Long,Set<Long>> entry : sellerItemIdMap.entrySet()) {
    		//判断下单商品的有效性
            List<ItemDTO> itemDTOs = itemManager.queryItem(
            		new CopyOnWriteArrayList<Long>(entry.getValue()), entry.getKey(), appkey);

            //
            printInvokeService(log,"invoke itemplatform query item",itemDTOs,"");
            
            if(itemDTOs.size() < entry.getValue().size()){
                throw new TradeException(ResponseCode.BIZ_E_ORDER_ITEM_INVALID,
                        "部分订单商品无效或已被删除");
            }
            for(ItemDTO itemDTO: itemDTOs){
                //TODO 这里的商品状态需要找商品平台将商品状态枚举常量暴露出来，然后依赖枚举常量。不能直接依赖数字常量
                if(itemDTO.getItemStatus().intValue() != 4){
                	throw new TradeException(ResponseCode.BIZ_E_ORDER_ITEM_STATUS_INVALID,
                            "部分订单商品已下架或未上架");
                }
            }
    	}
    	
    	Map<Long, Set<Long>> sellerItemSkuMap = genSellerItemSkuMap(orderList);
    	for (Map.Entry<Long,Set<Long>> entry : sellerItemSkuMap.entrySet()) {
			  
		    List<ItemSkuDTO> itemSkuDTOs = itemManager.queryItemSku(new CopyOnWriteArrayList<Long>(entry.getValue()), entry.getKey(), appkey);
                    
		    printInvokeService(log,"invoke itemplatform query itemSkuDTOs",itemSkuDTOs,"");
		    
            if(itemSkuDTOs.size() < entry.getValue().size()){
                throw new TradeException(ResponseCode.BIZ_E_ORDER_ITEM_SKU_INVALID,
                        "部分订单商品sku无效或已被删除");
            }

            Set<Long> skuIds = new HashSet<Long>();
            if(null!=itemSkuDTOs&&itemSkuDTOs.size()>0){
            	for(ItemSkuDTO itemSkuDTO: itemSkuDTOs){
                	skuIds.add(itemSkuDTO.getId());
                }
                if(sellerItemSkuMap.containsValue(skuIds) == false){
                	throw new TradeException(ResponseCode.BIZ_E_ORDER_ITEM_SKU_INVALID,
                            "部分订单商品sku无效或已被删除");
                }
            }
            
		}
	}
	
	private Map<Long, Set<Long>> genSellerItemIdMap(List<OrderItemDO> orderList){
   	 Map<Long, Set<Long>> sellerItemIdMap = new HashMap<Long, Set<Long>>();
   	 for (OrderItemDO orderItem : orderList) {
   		 Long itemId = orderItem.getItemId();
   		 if (sellerItemIdMap.get(orderItem.getSellerId()) == null) {
   			 Set<Long> itemIds = new HashSet<Long>();
   			itemIds.add(itemId);
                sellerItemIdMap.put(orderItem.getSellerId(), itemIds);
            } else {
           	 sellerItemIdMap.get(orderItem.getSellerId()).add(itemId);
            }
   	 }
   	 return sellerItemIdMap;
   }
   
   private Map<Long, Set<Long>> genSellerItemSkuMap(List<OrderItemDO> orderList) {
       // 按照供应商将商品分组 用于商品平台查询商品信息
       Map<Long, Set<Long>> sellerItemSkuMap = new HashMap<Long, Set<Long>>();
       // 将下单商品列表按照供应商id分组
       for (OrderItemDO orderItem : orderList) {
           Long skuId = orderItem.getItemSkuId();
           if (sellerItemSkuMap.get(orderItem.getSellerId()) == null) {
               Set<Long> supplierItems = new HashSet<Long>();
               supplierItems.add(skuId);
               sellerItemSkuMap.put(orderItem.getSellerId(), supplierItems);
           } else {
               sellerItemSkuMap.get(orderItem.getSellerId()).add(skuId);
           }
       }

       return sellerItemSkuMap;
   }

	@Override
	public Boolean updateOrderItemDeliveryMark(final Long orderId,final Long userId,Long deliveryInfoId,Integer deliveryMark, List<Long> orderItemIds) throws TradeException {
		printIntoService(log,"updateOrderItemDeliveryMark",orderItemIds,"");
		final OrderItemDO orderItem = new OrderItemDO();
		orderItem.setOrderId(orderId);
		orderItem.setDeliveryMark(deliveryMark);
		orderItem.setOrderItemIds(orderItemIds);
		orderItem.setDeliveryInfoId(deliveryInfoId);
		return transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				Boolean processStatus = Boolean.TRUE;
				try{
					orderItemDAO.updateOrderItemDeliveryMark(orderItem);
					
					OrderItemQTO query = new OrderItemQTO();
					query.setOrderId(orderId);
					Integer unDeliveryOrderItemCount = orderItemDAO.getUnDeliveryOrderItemCount(query);
					if(unDeliveryOrderItemCount==0){
						OrderDO order = new OrderDO();
						order.setOrderStatus(TradeConstants.Order_Status.DELIVERIED);
						order.setId(orderId);
						order.setUserId(userId);
						orderDAO.deliveryGoods(order);
					}
					
				}catch(Exception e){
					log.error("updateOrderItemDeliveryMark error",e);
					status.setRollbackOnly();
					processStatus = false;
				}
				return processStatus;
				
			}
		});
	}

	@Override
	public List<OrderItemDO> queryTimeoutUntreatedOrderItemList(OrderItemQTO orderItemQTO) throws TradeException {
		List list = (List<OrderItemDO>) orderItemDAO.queryTimeoutUntreatedOrderItemList(orderItemQTO);
		printOutService(log,"queryTimeoutUntreatedOrderItemList response",list,"");
		return list;
	}

	@Override
	public Integer getItemSalesVolumes(OrderItemQTO query) throws TradeException {
		Integer salesVolumes =  orderItemDAO.getItemSalesVolumes(query);
		printOutService(log,"getItemSalesVolumes response",salesVolumes,"");
		return salesVolumes;
	}

	@Override
	public Boolean updateOrderItemDeliveryMarkByOrderId(Long orderId) throws TradeException {
		printIntoService(log,"updateOrderItemDeliveryMarkByOrderId params",orderId,"");
		OrderItemDO query = new OrderItemDO();
		query.setOrderId(orderId);
		query.setDeliveryMark(1);
		Long result = orderItemDAO.updateOrderItemDeliveryMark(query);
		if(result>0){
			return true;
		}else{
			throw new TradeException("updateOrderItemDeliveryMarkByOrderId error");
		}
	}

	@Override
	public int deleteOrderItem(Long orderId) throws TradeException {
		if(null==orderId){
			throw new TradeException("deleteOrderItem orderId is null");
		}
		OrderItemQTO query = new OrderItemQTO();
		query.setOrderId(orderId);
		return orderItemDAO.deleteOrderItem(query);
	}

	@Override
	public int falseDeleteOrderItem(Long orderId) throws TradeException {
		if(null==orderId){
			throw new TradeException("falseDeleteOrderItem orderId is null");
		}
		OrderItemQTO query = new OrderItemQTO();
		query.setOrderId(orderId);
		return orderItemDAO.falseDeleteOrderItem(query);
	}

	@Override
	public int updateOrderItemVirtualWealthAmount(long orderItemId, long userId, long virtualWealthAmount)
			throws TradeException {
		OrderItemDO orderItemDO = new OrderItemDO();
		orderItemDO.setId(orderItemId);
		orderItemDO.setUserId(userId);
		orderItemDO.setVirtualWealthAmount(virtualWealthAmount);
		return orderItemDAO.updateOrderItemDOById(orderItemDO);
	}
}
