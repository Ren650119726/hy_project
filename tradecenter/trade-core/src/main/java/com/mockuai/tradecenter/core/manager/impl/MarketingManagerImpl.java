package com.mockuai.tradecenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.marketingcenter.client.MarketingClient;
import com.mockuai.virtualwealthcenter.client.VirtualWealthClient;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.ActivityInfo;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketValueAddedServiceDTO;
import com.mockuai.marketingcenter.common.domain.dto.MultiSettlementInfo;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.common.domain.dto.ShopItemDTO;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderServiceDTO;
import com.mockuai.tradecenter.common.domain.UsedWealthDTO;
import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.MarketingManager;
import com.mockuai.tradecenter.core.util.DozerBeanService;

/**
 * Created by zengzhangqiang on 5/29/15.
 */
public class MarketingManagerImpl extends BaseService implements MarketingManager {
	private static final Logger log = LoggerFactory.getLogger(MarketingManagerImpl.class);

	@Resource
	private MarketingClient marketingClient;
	
	@Resource
	private VirtualWealthClient virtualWealthClient;
	
	@Resource
	private DozerBeanService dozerBeanService;

	public SettlementInfo getSettlementInfo(long userId, List<OrderItemDTO> orderItemDTOs, Long consigneeId,
			String appKey) throws TradeException {
		 printIntoService(log,"getSettlementInfo",orderItemDTOs,"");
		try {
			List<MarketItemDTO> marketingItemDTOs = new ArrayList<MarketItemDTO>();
			for (OrderItemDTO orderItemDTO : orderItemDTOs) {
				MarketItemDTO marketItemDTO = new MarketItemDTO();
				marketItemDTO.setItemSkuId(orderItemDTO.getItemSkuId());
				marketItemDTO.setUnitPrice(orderItemDTO.getUnitPrice());
				marketItemDTO.setNumber(orderItemDTO.getNumber());
				marketItemDTO.setItemType(orderItemDTO.getItemType());
				marketItemDTO.setSellerId(orderItemDTO.getSellerId());
				
				if(orderItemDTO.getActivityId()!=null){
					ActivityInfo activityInfo = new ActivityInfo();
					activityInfo.setActivityId(orderItemDTO.getActivityId());
					List<OrderItemDTO> subOrderItemList = orderItemDTO.getItemList();
					List<MarketItemDTO> subMarketItemDTOList = new ArrayList<MarketItemDTO>();
					if(null!=subOrderItemList&&subOrderItemList.size()>0){
						for(OrderItemDTO subOrderItemDTO:subOrderItemList){
							MarketItemDTO subMarketItemDTO = new MarketItemDTO();
							subMarketItemDTO.setItemSkuId(subOrderItemDTO.getItemSkuId());
							subMarketItemDTO.setUnitPrice(subOrderItemDTO.getUnitPrice());
							subMarketItemDTO.setNumber(subOrderItemDTO.getNumber());
							subMarketItemDTO.setSellerId(subOrderItemDTO.getSellerId());
							subMarketItemDTO.setItemType(subOrderItemDTO.getItemType());
							subMarketItemDTOList.add(subMarketItemDTO);
						}
						activityInfo.setItemDTOs(subMarketItemDTOList);
					}
					
					
					marketItemDTO.setActivityInfo(activityInfo);
				}
				
				
				if(orderItemDTO.getOrderServiceList()!=null&&orderItemDTO.getOrderServiceList().size()>0){
					List<MarketValueAddedServiceDTO> marketValueAddedServiceList = new ArrayList<MarketValueAddedServiceDTO>();
					for(OrderServiceDTO orderServiceDTO:orderItemDTO.getOrderServiceList()){
						MarketValueAddedServiceDTO marketValueAddedServiceDTO = new MarketValueAddedServiceDTO();
						marketValueAddedServiceDTO.setId(orderServiceDTO.getServiceId());
						marketValueAddedServiceDTO.setPrice(orderServiceDTO.getPrice());
						marketValueAddedServiceDTO.setSellerId(orderServiceDTO.getSellerId());
						marketValueAddedServiceList.add(marketValueAddedServiceDTO);
					}
					marketItemDTO.setServices(marketValueAddedServiceList);
				}
				
				
				marketingItemDTOs.add(marketItemDTO);
			}
			printIntoService(log, "getSettlementInfo", marketingItemDTOs, "");
			Response<SettlementInfo> response = null;
				response = marketingClient.getSettlementInfo(userId, marketingItemDTOs,consigneeId, appKey);
			
				printInvokeService(log,"invoke marketingcenter getSettlementInfo",response,"");
				
				
			if (response.isSuccess()) {
				return response.getModule();
			} else {
				log.error("userId:{}, errorCode:{}, errorMsg:{}", userId, response.getResCode(), response.getMessage());
				throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION,response.getMessage());
			}
		} catch (Exception e) {
			log.error("userId:{}", userId, e);
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
	}

	public SettlementInfo getSettlementInfoMar(long userId, List<MarketItemDTO> marketingItemDTOs, Long consigneeId,
			String appKey) throws TradeException {
		try {
			Response<SettlementInfo> response = null;
			
			response = marketingClient.getSettlementInfo(userId, marketingItemDTOs,consigneeId, appKey);
			
			if (response.isSuccess()) {
				return response.getModule();
			} else {
				log.error("userId:{}, errorCode:{}, errorMsg:{}", userId, response.getResCode(), response.getMessage());
				throw new TradeException(ResponseCode.BIZ_E_LIMIT_ITEM_BUY_MAX_AMOUNT,response.getMessage());
			}
		} catch (TradeException e) {
			log.error("userId:{}", userId, e);
			throw new TradeException(e.getResponseCode(),e.getMessage());
		}
	}
	
	
	public boolean preUseUserCoupon(long userId, List<Long> userCouponIdList, long orderId, String appKey)
			throws TradeException {
		try {
			Response<Void> response = marketingClient.preUseUserCoupon(userCouponIdList, userId, orderId, appKey);
			if (response.isSuccess()) {
				return true;
			} else {
				log.error("userId:{}, orderId:{}, errorCode:{}, errorMsg:{}", userId, orderId, response.getResCode(),
						response.getMessage());
				throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
			}
		} catch (Exception e) {
			log.error("userId:{}, orderId:{}", userId, orderId, e);
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}

	}

	public boolean useUserCoupon(long userId, long orderId, String appKey) throws TradeException {
		try {
			Response<Void> response = marketingClient.useUserCoupon(userId, orderId, appKey);
			if (response.isSuccess()) {
				return true;
			} else {
				log.error("userId:{}, orderId:{}, errorCode:{}, errorMsg:{}", userId, orderId, response.getResCode(),
						response.getMessage());
				throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
			}
		} catch (Exception e) {
			log.error("userId:{}, orderId:{}", userId, orderId, e);
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}

	}

	public boolean releaseUsedCoupon(long userId, long orderId, String appKey) throws TradeException {
		try {
			Response<Void> response = marketingClient.releaseUsedCoupon(userId, orderId, appKey);
			if (response.isSuccess()) {
				return true;
			} else {
				log.error("userId:{}, orderId:{}, errorCode:{}, errorMsg:{}", userId, orderId, response.getResCode(),
						response.getMessage());
				throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
			}
		} catch (Exception e) {
			log.error("userId:{}, orderId:{}", userId, orderId, e);
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
	}


	@Override
	public boolean preUseUserCouponBatch(Long userId, Map<Long, List<Long>> orderIdKeyUserCouponIdList, String appkey)
			throws TradeException {
		printIntoService(log,userId+" preUseUserCouponBatch ",orderIdKeyUserCouponIdList,"");
		try{
			Response<Void> response = marketingClient.preUseMultiUserCoupon(orderIdKeyUserCouponIdList, userId, appkey);
			printInvokeService(log,"invoke marketingcenter preUseUserCouponBatch",response,"");
			if (response.isSuccess()) {
				return true;
			} else {
				throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
			}
		}catch(Exception e){
			log.error("preUseUserCouponBatch error", e);
			throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
		}
	}

	@Override
	public boolean useUserCouponBatch(Long userId,List<Long> orderIds, String appKey) throws TradeException {
		// TODO Auto-generated method stub
		printIntoService(log,userId+" useUserCouponBatch ",orderIds,"");
		try{
			Response<Void> response = marketingClient.useMultiUserCoupon(orderIds,userId, appKey);
			printInvokeService(log,"invoke marketingcenter useUserCouponBatch",response,"");
			if (response.isSuccess()) {
				return true;
			} else {
				throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
			}
		}catch(Exception e){
			log.error("useUserCouponBatch error", e);
			throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
		}
	}

//	@Override
//	public Map<Long, List<DiscountInfo>> getCartDiscountInfoBatch(Map<Long, List<MarketItemDTO>> sellerKeyItems,
//			String appKey) throws TradeException {
//		printIntoService(log,"getCartDiscountInfoBatch",sellerKeyItems,"");
//		List<MarketItemDTO> marketItemDTOs = new ArrayList<MarketItemDTO>();
//		for (Map.Entry<Long,List<MarketItemDTO>> entry : sellerKeyItems.entrySet()) {
//			marketItemDTOs.addAll(entry.getValue());
//		}
//		try{
//			printInvokeService(log,"invoke marketcenter getCartDiscountInfoBatch request",marketItemDTOs,"");
//			
//			Response<?> response = marketingClient.getMultiCartDiscountInfo(marketItemDTOs, appKey);
//			
//			printInvokeService(log,"invoke marketcenter getCartDiscountInfoBatch response",response,"");
//			
//			if (response.isSuccess()) {
//				return (Map<Long, List<DiscountInfo>>) response.getModule();
//			} else {
//				throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
//			}
//		}catch(Exception e){
//			log.error("getCartDiscountInfoBatch error", e);
//			throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
//		}
//		
//		
//	}

//	@Override
//	public MultiSettlementInfo getMultiSettlementInfo(Long userId, Long consigneeId,
//			Map<Long,List<OrderItemDTO>> sellerIdItems,
//			Map<Long,Integer> sellerDeliveryIdMap,
//			String appKey) throws TradeException {
//		// TODO Auto-generated method stub
//		printIntoService(log,userId+"getMultiSettlementInfo",sellerIdItems,"");
//		List<ShopItemDTO> shopItemDTOs = new ArrayList<ShopItemDTO>();
//		
//		for (Map.Entry<Long,List<OrderItemDTO>> entry : sellerIdItems.entrySet()) {
//
//			ShopItemDTO shopItemDTO = new ShopItemDTO();
//			Integer deliveryId = sellerDeliveryIdMap.get(entry.getKey());
//			List<MarketItemDTO> marketingItemDTOs = new ArrayList<MarketItemDTO>();
//			for (OrderItemDTO orderItemDTO : entry.getValue()) {
//				
//				
//				
//				MarketItemDTO marketItemDTO = new MarketItemDTO();
//				marketItemDTO.setItemSkuId(orderItemDTO.getItemSkuId());
//				marketItemDTO.setUnitPrice(orderItemDTO.getUnitPrice());
//				marketItemDTO.setNumber(orderItemDTO.getNumber());
//				marketItemDTO.setSellerId(orderItemDTO.getSellerId());
//				
//				if(orderItemDTO.getActivityId()!=null){
//					ActivityInfo activityInfo = new ActivityInfo();
//					activityInfo.setActivityId(orderItemDTO.getActivityId());
//					List<OrderItemDTO> subOrderItemList = orderItemDTO.getItemList();
//					List<MarketItemDTO> subMarketItemDTOList = new ArrayList<MarketItemDTO>();
//					if(null!=subOrderItemList&&subOrderItemList.size()>0){
//						for(OrderItemDTO subOrderItemDTO:subOrderItemList){
//							MarketItemDTO subMarketItemDTO = new MarketItemDTO();
//							subMarketItemDTO.setItemSkuId(subOrderItemDTO.getItemSkuId());
//							subMarketItemDTO.setUnitPrice(subOrderItemDTO.getUnitPrice());
//							subMarketItemDTO.setNumber(subOrderItemDTO.getNumber());
//							subMarketItemDTO.setSellerId(subOrderItemDTO.getSellerId());
//							subMarketItemDTOList.add(subMarketItemDTO);
//						}
//						activityInfo.setItemDTOs(subMarketItemDTOList);
//					}
//					marketItemDTO.setActivityInfo(activityInfo);
//				}
//				
//				
//				if(orderItemDTO.getOrderServiceList()!=null&&orderItemDTO.getOrderServiceList().size()>0){
//					List<MarketValueAddedServiceDTO> marketValueAddedServiceList = new ArrayList<MarketValueAddedServiceDTO>();
//					for(OrderServiceDTO orderServiceDTO:orderItemDTO.getOrderServiceList()){
//						MarketValueAddedServiceDTO marketValueAddedServiceDTO = new MarketValueAddedServiceDTO();
//						marketValueAddedServiceDTO.setId(orderServiceDTO.getServiceId());
//						marketValueAddedServiceDTO.setPrice(orderServiceDTO.getPrice());
//						marketValueAddedServiceDTO.setSellerId(orderServiceDTO.getSellerId());
//						marketValueAddedServiceList.add(marketValueAddedServiceDTO);
//					}
//					marketItemDTO.setServices(marketValueAddedServiceList);
//				}	
//				
//				
//				marketingItemDTOs.add(marketItemDTO);
//			}
//			shopItemDTO.setItemList(marketingItemDTOs);
//			
//			shopItemDTO.setDeliverType(deliveryId);
//			shopItemDTOs.add(shopItemDTO);
//		
//		}
//		
//		try{
//			printIntoService(log,"invoke marketcenter getMultiSettlementInfo request",shopItemDTOs,"");
//			
//			Response<?> response =	marketingClient.getMultiSettlementInfo(userId, consigneeId, shopItemDTOs, appKey);
//			
//			printInvokeService(log,"invoke marketcenter getMultiSettlementInfo response",response,"");
//			
//			if (response.isSuccess()) {
//				return  (MultiSettlementInfo) response.getModule();
//			} else {
//				throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
//			}
//			
//			
//		}catch(Exception e){
//			log.error("getMultiSettlementInfo error", e);
//			throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
//		}
//	}

	@Override
	public Boolean releaseMultiUsedCoupon(Long userId,Map<Long, List<Long>> orderIdKeyUserCouponIdList,String appkey) throws TradeException {

		printIntoService(log,userId+"releaseMultiUsedCoupon",orderIdKeyUserCouponIdList,"");
		
		List<Long> orderIds = new ArrayList<Long>();
		for (Map.Entry<Long,List<Long>> entry : orderIdKeyUserCouponIdList.entrySet()) {
			orderIds.add(entry.getKey());
		}
		try{
			Response<?> response = marketingClient.releaseMultiUsedCoupon(orderIds, userId, appkey);
			if (response.isSuccess()) {
				return true;
			}else {
				throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
			}
			
		}catch(Exception e){
			log.error("releaseMultiUsedCoupon error", e);
			throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
		}
		
	}

//	@Override
//	public Map<Long, List<DiscountInfo>> getSupplierCartDiscountInfo(List<MarketItemDTO> marketItemDTOs,String appKey)
//			throws TradeException {
//		printIntoService(log,"getSupplierCartDiscountInfo",marketItemDTOs,"");
//		try{
//			Response<?> response = marketingClient.getSupplierCartDiscountInfo(marketItemDTOs, appKey);
//			
//			printInvokeService(log,"getSupplierCartDiscountInfo response",response,"");
//			
//			if (response.isSuccess()) {
//				return (Map<Long, List<DiscountInfo>>) response.getModule();
//			}else {
//				throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
//			}
//			
//		}catch(Exception e){
//			log.error("getSupplierCartDiscountInfo error", e);
//			throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
//		}
//		
//	}
    public List<DiscountInfo> getCartDiscountInfo(List<MarketItemDTO> marketItemDTOList,String appKey,Long userId ) throws TradeException {
        printIntoService(log,"getCartDiscountInfo",marketItemDTOList,"");

        try {
            Response<List<DiscountInfo>> response =  marketingClient.getCartDiscountInfo(marketItemDTOList, appKey,userId);
            printInvokeService(log,"getCartDiscountInfo response",response,"");
            if (response.isSuccess()) {
                return (List<DiscountInfo>) response.getModule();
            }else {
                throw new TradeException(ResponseCode.BIZ_E_LIMIT_ITEM_BUY_MAX_AMOUNT,response.getMessage());
            }
        }catch (TradeException e){
            log.error("marketingClient.getCartDiscountInfo error", e);
            throw new TradeException(e.getResponseCode(),e.getMessage());
        }

    }



}
