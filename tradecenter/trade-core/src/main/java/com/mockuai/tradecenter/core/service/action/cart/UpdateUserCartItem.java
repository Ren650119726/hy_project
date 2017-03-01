package com.mockuai.tradecenter.core.service.action.cart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.SupplierManager;
import com.mockuai.tradecenter.core.manager.UserCartItemManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.TradeRequest;
import com.mockuai.tradecenter.core.service.action.Action;

/**
 * 更新购物车数量处理类
 * @author cwr
 */
public class UpdateUserCartItem implements Action{
	private static final Logger log = LoggerFactory.getLogger(UpdateUserCartItem.class);

	@Resource
	private UserCartItemManager UserCartItemManager;
	
	@Resource
	private ItemManager itemManager;
	
	@Resource
    private OrderManager orderManager;
	
	@Resource
	private UserCartItemManager userCartItemManager;
	
	@Resource
	private SupplierManager supplierManager;
	
	private void checkItemCartOutSkuStock(Long skuId,Long sellerId,String appkey,Integer num)throws TradeException{
		
		List<Long> queryList = new ArrayList<Long>();
		queryList.add(skuId);
		List<ItemSkuDTO>  itemSkus = null;
		ItemSkuDTO itemSku = null;
		itemSkus = this.itemManager.queryItemSku(queryList, sellerId, appkey);
		if(itemSkus == null || itemSkus.size() == 0){
			log.error("itemSku is null : " +skuId + "," + sellerId);
			throw new TradeException(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"itemSku doesn't exist");
		}
		itemSku = itemSkus.get(0);
//		Long stockNum = itemSku.getStockNum();
		Long stockNum = getSaleStockNum(skuId,appkey);
		if(stockNum-num<0){
			throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "该商品已经超过最多购买量");
		}
	}
	
	private Long getSaleStockNum(Long itemSkuId,String appKey) throws TradeException{
		StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
    	storeItemSkuQTO.setItemSkuId(itemSkuId);
    	log.info(" storeItemSkuQTO : "+JSONObject.toJSONString(storeItemSkuQTO));
    	List<StoreItemSkuDTO> storeItemSkuDTOs = supplierManager.queryStoreItemSku(storeItemSkuQTO, appKey);
    	if(storeItemSkuDTOs.get(0).getSalesNum() == null ) {
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION,"storeItemSkuDTOs.get(0).getSalesNum() id null");
        }
    	return storeItemSkuDTOs.get(0).getSalesNum(); 
	}
	
	private void checkUserItemPurchase(Long itemId,Long userId,Long sellerId,Integer num, String appKey)throws TradeException{
	   	 
	   	 OrderQTO query = new OrderQTO();
	   
	   	 query.setUserId(userId);
	   	 
	   	 if(null==itemId||null==sellerId){
	        throw new TradeException(ResponseCode.PARAM_E_PARAM_INVALID, "itemId or sellerid is null");
	   	 }
	   	 

	   	Integer itmeBuyLimitCount = itemManager.getItemBuyLimit(itemId, sellerId, appKey);
	   	
	   	if(null!=itmeBuyLimitCount&& itmeBuyLimitCount!=0){
			Integer hasBuyCount = orderManager.getHasBuyCount(userId, itemId);
			if(itmeBuyLimitCount>hasBuyCount){
		   		Integer canBuyCount = itmeBuyLimitCount-hasBuyCount;
		   		
		   		if(num>canBuyCount){
			   		 log.error("item over the purchase quantity : " + itemId);
			            throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "该商品最多能购买"+canBuyCount+"件");
			   	 }
		   	}else{
		   	 throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "该商品已经超过最多购买量");
		   	}
	   	}
	   	
	   	
	   }
	
	@Override
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
		TradeRequest request = context.getRequest();
		String appKey = (String)context.get("appKey");
		TradeResponse<Boolean> response = null;
		if(request.getParam("cartItemId") == null){
			log.error("cartItemId is null");
			return response = ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"cartItemId is null");
		}else if(request.getParam("number") == null){
			log.error("number is null");
			return response = ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"number is null");
		}else if(request.getParam("userId") == null){
			log.error("userId is null");
			return response = ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"sessionId is null");
		}
		
		Long id = (Long)request.getParam("cartItemId");
		Integer number = (Integer)request.getParam("number");
		Long userId = (Long)request.getParam("userId");
		
		CartItemDO cartItemDO = new CartItemDO();
		cartItemDO = this.UserCartItemManager.getCartItem(id, userId);
		if(cartItemDO == null){
			log.error("cartItem is null: " + id);
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_NOT_EXIST);
		}
		Long itemId = cartItemDO.getItemId();
		Long sellerId = cartItemDO.getSellerId();
		
		List<Long> itemIdList = new ArrayList<Long>();
		itemIdList.add(itemId);
		List<ItemDTO> itemList = this.itemManager.queryItem(itemIdList, sellerId, appKey);
		if(itemList == null || itemList.size()== 0){
			log.error("item is null: itemId=" +itemId+",sellerId="+sellerId+",appkey="+appKey);
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_NOT_EXIST,"item doesn't exist");
		}
		
		ItemDTO item = itemList.get(0);
		if(item.getSaleMaxNum()!=null && item.getSaleMaxNum()!=0
				&& number > item.getSaleMaxNum().intValue()){
			log.error("exceed the item max limitation : " + number + ", " + item.getSaleMaxNum());
//			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT);
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT,"大于最多购买数");
		}else if(item.getSaleMinNum()!=null && item.getSaleMinNum()!=0
				&& number < item.getSaleMinNum().intValue()){
			log.error("less than the min limitation : " + number + ", " + item.getSaleMinNum());
//			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_BUY_MIN_AMOUNT);
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_BUY_MIN_AMOUNT,"少于最少购买数");
		}
		//增加库存数量限制
		checkItemCartOutSkuStock(cartItemDO.getItemSkuId(),cartItemDO.getSellerId(),appKey,number);
		//用户限购
		checkUserItemPurchase(itemId,userId,sellerId,number, appKey);
		
		Date now = new Date();
		cartItemDO.setNumber(number);  
		cartItemDO.setId(id);
		cartItemDO.setUserId(userId);
		cartItemDO.setGmtModified(now);
		
		int result =0;
		try{
			result = this.UserCartItemManager.updateUserCartItemNumber(cartItemDO,null);
		}catch(TradeException e){
			log.error("db error : " ,e);
			return ResponseUtils.getFailResponse(e.getResponseCode());
		}
		
		/*
		List<CartItemDO> cartItems = null;
		// 获取所有的购物项目
		cartItems = this.UserCartItemManager.queryUserCartItems(userId);
		// 判断购物车是否已经有该商品
		CartDTO cartDTO = null;
		if(cartItems == null || cartItems.size()==0){
			cartDTO = new CartDTO(); // 返回属性值为空的对象
		}else{
			List<ItemSkuQTO> promotionQueryList = this.promotionManager.getPromotionQueryCondition(cartItems);
			// 根据商品列表、平台   去促销系统查询赠品  优惠金额  和是否包邮
			List<FavorableInfoDTO> activityList= null; // 促销活动列表
			activityList = this.promotionManager.getPromotionItems(promotionQueryList);
			cartDTO = this.cartItemManager.handlePromotionInfo(activityList, cartItems);
		}*/
		
		if(result > 0){
			response = ResponseUtils.getSuccessResponse(true);
		}else{
			log.error("cartItem doesn't exist");
			response = ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"cartItem doesn't exist");
		}
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.UPDATE_USER_CART_ITEM.getActionName();
	}
}
