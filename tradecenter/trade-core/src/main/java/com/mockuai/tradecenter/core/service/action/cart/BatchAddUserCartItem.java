package com.mockuai.tradecenter.core.service.action.cart;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.VirtualMark;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.common.domain.ItemServiceDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.domain.CartItemServiceDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.UserCartItemManager;
import com.mockuai.tradecenter.core.manager.UserManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.TradeRequest;
import com.mockuai.tradecenter.core.service.action.Action;

@Service
public class BatchAddUserCartItem implements Action {
	private static final Logger log = LoggerFactory.getLogger(BatchAddUserCartItem.class);

	/**
	 * 购物车最大记录数
	 */
	private static final int CART_MAX_NUMBER = 50;
	
	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private UserCartItemManager userCartItemManager;
	
	@Resource
	private ItemManager itemManager;
	@Resource
    private OrderManager orderManager;
	@Resource
	private UserManager userManager;
	

	
	private void checkItemCartOutSkuStock(ItemSkuDTO itemSku,Integer cartItemCount,Integer num)throws TradeException{
		Long stockNum = itemSku.getStockNum();
		
		if(stockNum-cartItemCount-num<0){
			throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "该商品已经超过最多购买量");
		}
	}
	
	private void checkUserItemPurchase(Long itemId,Long userId,
									   Long sellerId,Integer num,Integer cartItemCount, String appKey)throws TradeException{
	   	 
	   	 OrderQTO query = new OrderQTO();
	   
	   	 query.setUserId(userId);
	   	 
	   	 if(null==itemId||null==sellerId){
	        throw new TradeException(ResponseCode.PARAM_E_PARAM_INVALID, "itemId or sellerid is null");
	   	 }

		/**
		 * 找到商品信息中的限购数量（如果有
		 */
	   	Integer itmeBuyLimitCount = itemManager.getItemBuyLimit(itemId, sellerId, appKey);
	   	
	   	if(null!=itmeBuyLimitCount&& itmeBuyLimitCount!=0){
			Integer hasBuyCount = orderManager.getHasBuyCount(userId, itemId);
			if(itmeBuyLimitCount>hasBuyCount){
		   		Integer canBuyCount = itmeBuyLimitCount-hasBuyCount-cartItemCount;
		   		
		   		if(num>canBuyCount){
			   		 log.error("item over the purchase quantity : " + itemId);
			            throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "该商品最多能购买"+canBuyCount+"件");
			   	 }
		   	}else{
		   	 throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "该商品已经超过最多购买量");
		   	}
	   	}
	   	
	   	
	   	
	   }
	
	@SuppressWarnings("unchecked")
	@Override
	public TradeResponse<Boolean> execute(RequestContext context){
		TradeRequest request = context.getRequest();
		final String appKey = (String)context.get("appKey");
		/**
		 * 没发现appDomain
		 */
//		final String appDomain = (String)context.get("appDomain");
		if(request.getParam("cartItemDTOList") == null){
			log.error("cartItemDTOList is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"cartItemDTOList is null");
		}
		final List<CartItemDTO> cartItemDTOs = (List<CartItemDTO>)request.getParam("cartItemDTOList");
		/**
		 * bizCode不保留的话好多方法都要用到
		 */
		final String bizCode = (String) context.get("bizCode");

		/**
		 * 字段验证
		 * number
		 * itemSkuId
		 * sellerId、是不是要动 若前端传来的不是sellerId
		 * userId
		 */
		String errorMsg = this.userCartItemManager.validateCartItemDTOsFields4Add(cartItemDTOs);
		if(!StringUtils.isEmpty(errorMsg)){
			log.error(errorMsg);
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,errorMsg);
		}
		
		String batchAddResult = transactionTemplate.execute(new TransactionCallback<String>() {
			
			
			@Override
			public String doInTransaction(TransactionStatus status) {
				try{
					
					for(CartItemDTO cartItemDTO:cartItemDTOs){
						// 必要的字段
						long itemSkuId = cartItemDTO.getItemSkuId();
						long sellerId = cartItemDTO.getSellerId();
						int number  = cartItemDTO.getNumber();
						long userId = cartItemDTO.getUserId();
						
						
						// 商品平台查询商品详细信息
						List<Long> queryList = new ArrayList<Long>();
						queryList.add(itemSkuId);
						List<ItemSkuDTO>  itemSkus = null;
						List<ItemDTO> items = null;
						ItemSkuDTO itemSku = null;
						try{
							itemSkus = itemManager.queryItemSku(queryList, sellerId, appKey); // 如果是返回空将抛出异常
							if(itemSkus == null || itemSkus.size() == 0){
								log.error("itemSku is null : " +itemSkuId + "," + sellerId);
								return "itemSku doesn't exist";
							}
							itemSku = itemSkus.get(0);
							
							Long itemId = itemSku.getItemId();
							queryList.set(0, itemId);
							items = itemManager.queryItem(queryList, sellerId, appKey); // 如果是返回空将抛出异常
							if(items ==null || items.size() == 0){
								log.error("item is null : " + itemId + "," + sellerId);
								return "item doesn't exist";
							}
							
							ItemDTO itemDTO = items.get(0);
							int itemType = itemDTO.getItemType();
							if(itemType==13||itemType==14||itemType==15){
								return "营销商品不能加入购物车";
							}
							
							//虚拟商品不能加入购物车
							if(null!=itemDTO.getVirtualMark()&&itemDTO.getVirtualMark()==VirtualMark.VIRTUAL.getCode()){
								return ("虚拟商品不能加入购物车");
							}
							
						}catch(TradeException e){
							log.error("getItemSku error",e);
							return ResponseCode.SYS_E_REMOTE_CALL_ERROR.getComment();
						}
						ItemDTO item = items.get(0);
						
						ItemDTO suitItemDTO = null;
						
						//TODO ...
						if (item.getItemType().intValue() == DBConst.SUIT_ITEM.getCode()) {
							try {
								suitItemDTO = itemManager.getSuitItem(sellerId, item.getId(), appKey);
							} catch (TradeException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}


						
						// 可能购物车中已经存在
						//TODO 获取分销商id的逻辑，这里先写死为0
						long distributorId = 0L;
						CartItemDO cartItem = userCartItemManager.getCartItemBySkuId(itemSkuId, distributorId, userId);
						long newId = 0L,updated = 0;
						CartItemDO newCartItem = null;
						Date now = new Date();
						Integer minLimitation = item.getSaleMinNum();
						Integer maxLimitation = item.getSaleMaxNum();
						if(minLimitation!=null && minLimitation.intValue()!=0 && number<minLimitation.intValue()){
							return "该商品最少需要购买"+minLimitation.intValue()+"件";
						}
						if(maxLimitation!=null && maxLimitation.intValue()!=0 && number>maxLimitation.intValue()){
							return 
									"该商品最多只能购买"+maxLimitation.intValue()+"件";
						}
						
						List<CartItemServiceDO> cartItemServiceDOList = null;
						if (cartItemDTO.getServiceList() != null && cartItemDTO.getServiceList().size() > 0) {
							List<Long> serviceIds = new ArrayList<Long>();
							for (ItemServiceDTO itemServiceDTO : cartItemDTO.getServiceList()) {
								serviceIds.add(itemServiceDTO.getServiceId());
							}
							List<ItemPriceDTO> serviceList = null;
							try {
								serviceList = itemManager.queryItemServiceList(itemSkuId,
										serviceIds, userId,sellerId, appKey);
							} catch (TradeException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (serviceList != null) {
								
								List<ValueAddedServiceDTO> valueAddedServiceList = serviceList.get(0).getValueAddedServiceDTOList();
								if(null!=valueAddedServiceList&&valueAddedServiceList.size()!=0){
									cartItemServiceDOList = new ArrayList<CartItemServiceDO>();
									for(ValueAddedServiceDTO valueAddedServiceDTO:valueAddedServiceList){
										CartItemServiceDO cartItemServiceDO = new CartItemServiceDO();
										cartItemServiceDO.setPrice(valueAddedServiceDTO.getServicePrice());
										cartItemServiceDO.setServiceId(valueAddedServiceDTO.getId());
										cartItemServiceDO.setServiceName(valueAddedServiceDTO.getServiceName());
										cartItemServiceDO.setServiceImageUrl(valueAddedServiceDTO.getIconUrl());
										cartItemServiceDO.setBizCode(bizCode);
										cartItemServiceDO.setSellerId(sellerId);
										cartItemServiceDOList.add(cartItemServiceDO);
									}
								}
								
								
							}

						}
						// TODO: 2016/8/30 userId,sellerId
						Integer cartItemCount = userCartItemManager.getCartItemCountByUserAndSellerId(item.getId(), userId, sellerId);
						/**
                         * userId，sellerId 跟业务
						 */
						//用户限购
						checkUserItemPurchase(item.getId(),userId,sellerId,number,cartItemCount, appKey);
						
						Integer cartSkuCount = userCartItemManager.getCartItemCountBySkuIdAndUserId(itemSku.getId(), userId, sellerId);
						
						checkItemCartOutSkuStock(itemSku,number,cartSkuCount);
						
						List<CartItemDO> subCartItemDOList = null;
						
						if (null != suitItemDTO && suitItemDTO.getSubItemList().size() > 0) {
							subCartItemDOList = new ArrayList<CartItemDO>();
							for (ItemDTO subItemDTO : suitItemDTO.getSubItemList()) {
									List<Long> subSkuIds = new ArrayList<Long>();
									for (ItemSkuDTO subItemSkuDTO : subItemDTO.getItemSkuDTOList()) {
										subSkuIds.add(subItemSkuDTO.getId());
									}
									List<ItemSkuDTO> subItemSkuDTOList = itemManager.queryItemSku(subSkuIds,
											subItemDTO.getSellerId(), appKey);
									
									if( null != subItemSkuDTOList && subItemSkuDTOList.size()>0 ){
										
										CartItemDO subCartItemDO = genSubCartItemDO(subItemSkuDTOList.get(0),subItemDTO,bizCode,userId);
										subCartItemDO.setItemType(subItemDTO.getItemType());
										subCartItemDO.setOriginalId(newId);
										
										cartItemCount = userCartItemManager.getCartItemCountByUserAndSellerId(subItemDTO.getId(), userId, sellerId);
										
										checkUserItemPurchase(subItemDTO.getId(),userId,sellerId,subCartItemDO.getNumber(),cartItemCount, appKey);
										
										cartSkuCount = userCartItemManager.getCartItemCountBySkuIdAndUserId(subCartItemDO.getItemSkuId(), userId, sellerId);
										
										checkItemCartOutSkuStock(subItemSkuDTOList.get(0),subCartItemDO.getNumber(),cartSkuCount);
										
										subCartItemDOList.add(subCartItemDO);
//										this.userCartItemManager.addUserCartItem(subCartItemDO);
									}
							}
						}
						
						
						if( null == cartItem ){// 如果是新加
							Integer cartCount = userCartItemManager.getCartItemsCount(userId);
							if(cartCount.intValue() >= CART_MAX_NUMBER){
								log.error("exceed the cart max limitation");
								return 
										"购物车最多只能添加"+CART_MAX_NUMBER+"件商品";
							}
							
							newCartItem = new CartItemDO();
							newCartItem.setItemSkuId(itemSkuId); //
							newCartItem.setItemName(item.getItemName());
							newCartItem.setSellerId(sellerId); // 供应商id
							newCartItem.setUserId(userId); //未登入时候无用户id
							newCartItem.setItemId(item.getId());
							newCartItem.setItemSkuDesc(StringUtils.isBlank(itemSku.getSkuCode())? "" : itemSku.getSkuCode());
							newCartItem.setMarketPrice(itemSku.getMarketPrice()); // 市场价
							newCartItem.setPromotionPrice(itemSku.getPromotionPrice());
							newCartItem.setWirelessPrice(itemSku.getWirelessPrice());
							//设置商品主图
							newCartItem.setItemImageUrl(item.getIconUrl());
							newCartItem.setNumber(number);
							newCartItem.setBizCode(bizCode);
							newCartItem.setDeliveryType(item.getDeliveryType());
							newCartItem.setItemType(item.getItemType());
							newId = userCartItemManager.addUserCartItem(newCartItem,cartItemServiceDOList);
							

							if(subCartItemDOList!=null&&subCartItemDOList.size()>0){
								for(  CartItemDO subCartItemDO :subCartItemDOList ){
									
									subCartItemDO.setOriginalId(newId);
									
									userCartItemManager.addUserCartItem(subCartItemDO,cartItemServiceDOList);
								}
							}
							
							
							
							cartItem = newCartItem ;
						}else{//之前已经加过该商品
							//如果多次添加购物车的总数超过商品下单上限，则提示错误信息
							int totalNumber = cartItem.getNumber() + number;
							if(maxLimitation!=null && maxLimitation!=0
									&&  totalNumber > maxLimitation.intValue()){
								return 
										"该商品最多只能购买"+maxLimitation.intValue()+"件";
							}
							cartItem.setUserId(userId);
							cartItem.setId(cartItem.getId()); // id赋值用于更新条件
							cartItem.setNumber(totalNumber);
							cartItem.setBizCode(bizCode);
							updated = userCartItemManager.updateUserCartItemNumber(cartItem,cartItemServiceDOList);
						}
						
						
						if(newId > 0 || updated > 0){
//							return ResponseUtils.getSuccessResponse(module);
						}else{
							log.error("add cartItem error : skuId" + itemSkuId + " sellerId: "+ sellerId + " userId: " + userId);
							throw new Exception("add cartItem error : skuId" + itemSkuId + " sellerId: "+ sellerId + " userId: " + userId);
						}
						
					}
					
					return "";
				}catch(Exception e){
					status.setRollbackOnly();
					return e.getMessage();
				}
				
			}
			
			

			
		});
		
		
		if(StringUtils.isNotBlank(batchAddResult)){
			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION,batchAddResult);
		}
		
		
		           return ResponseUtils.getSuccessResponse(true);

		
		
		
	}
	
	private CartItemDO genSubCartItemDO(ItemSkuDTO itemSkuDTO,ItemDTO subItemDTO,String bizCode,Long userId){
		CartItemDO newCartItem = new CartItemDO();
		newCartItem.setItemSkuId(itemSkuDTO.getId()); //
		newCartItem.setItemName(subItemDTO.getItemName());
		newCartItem.setSellerId(itemSkuDTO.getSellerId()); // 供应商id
		newCartItem.setUserId(userId); //未登入时候无用户id
		newCartItem.setItemId(subItemDTO.getId());
		newCartItem.setItemSkuDesc(StringUtils.isBlank(itemSkuDTO.getSkuCode())? "" : itemSkuDTO.getSkuCode());
		newCartItem.setMarketPrice(itemSkuDTO.getMarketPrice()); // 市场价
		newCartItem.setPromotionPrice(itemSkuDTO.getPromotionPrice());
		newCartItem.setWirelessPrice(itemSkuDTO.getWirelessPrice());
		//设置商品主图
		newCartItem.setItemImageUrl(subItemDTO.getIconUrl());
		newCartItem.setNumber(1);
		newCartItem.setBizCode(bizCode);
		newCartItem.setDeliveryType(subItemDTO.getDeliveryType());
		return newCartItem;
	}

	@Override
	public String getName() {
		return ActionEnum.BATCH_ADD_USER_CARTITEM.getActionName();
	}
}	
