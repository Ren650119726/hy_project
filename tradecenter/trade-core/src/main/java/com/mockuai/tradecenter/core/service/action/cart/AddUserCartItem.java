package com.mockuai.tradecenter.core.service.action.cart;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.VirtualMark;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketValueAddedServiceDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.common.domain.CartItemServiceDTO;
import com.mockuai.tradecenter.common.domain.ItemServiceDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.domain.CartItemServiceDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.*;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.TradeRequest;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.ModelUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
/**
 * 商品添加购物车
 * 1 根据商品id和供应商id查询商品平台获取最新的价格、名称等信息
 * 2 查询所有的购物车商品 ，判断是否有重复 （重复则更新为最新的价格、数目累加）,不重复则新加入
 * 3 根据所有的商品列表促销平台查询优惠信息（是否包邮、优惠金额、赠品）
 * 4 返回邮费、优惠、赠品给调用方 
 * @author cwr
 */
@Service
public class AddUserCartItem implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddUserCartItem.class);

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
	@Resource
	private DistributionManager distributionManager;
	@Resource
	private SupplierManager supplierManager; 

	@Autowired
	private MarketingManager marketingManager;
	
	

	@SuppressWarnings("unchecked")
	@Override
	public TradeResponse<CartItemDTO> execute(RequestContext context){
		TradeRequest request = context.getRequest();
		final String appKey = (String)context.get("appKey");
		final String appDomain = (String)context.get("appDomain");
		if(request.getParam("cartItemDTO") == null){
			log.error("cartItemDTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"cartItemDTO is null");
		}
		CartItemDTO cartItemDTO = (CartItemDTO)request.getParam("cartItemDTO");
		String bizCode = (String) context.get("bizCode");

		// 字段验证
		String errorMsg = this.userCartItemManager.validateCartItemFields4Add(cartItemDTO);
		if(!StringUtils.isEmpty(errorMsg)){
			log.error(errorMsg);
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,errorMsg);
		}

		// 必要的字段
		long itemSkuId = cartItemDTO.getItemSkuId();
		long sellerId = cartItemDTO.getSellerId();
		int number  = cartItemDTO.getNumber();
		long userId = cartItemDTO.getUserId();
		//long distributorId = cartItemDTO.getDistributorId();
        Long  shareUserId =  cartItemDTO.getShareUserId();
//        log.info("shareUserId:{}",shareUserId);
		// 商品平台查询商品详细信息
		List<Long> queryList = new ArrayList<Long>();
		queryList.add(itemSkuId);
		List<ItemSkuDTO>  itemSkus = null;
		List<ItemDTO> items = null;
		ItemSkuDTO itemSku = null;
		try{
			log.info(" addcart queryList : "+JSONObject.toJSONString(queryList));
//			itemSkus = this.itemManager.queryItemSku(queryList, sellerId, appKey); // 如果是返回空将抛出异常
			itemSkus = this.itemManager.queryItemSku(itemSkuId, appKey); // 如果是返回空将抛出异常
			log.info(" addcart itemSkus : "+JSONObject.toJSONString(itemSkus));
			if(itemSkus == null || itemSkus.size() == 0){
				log.error("itemSku is null : " +itemSkuId + "," + sellerId);
				return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"itemSku doesn't exist");
			}
			itemSku = itemSkus.get(0);

			Long itemId = itemSku.getItemId();
			queryList.set(0, itemId);
			items = this.itemManager.queryItem(queryList, sellerId, appKey); // 如果是返回空将抛出异常
			if(items ==null || items.size() == 0){
				log.error("item is null : " + itemId + "," + sellerId);
				return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"item doesn't exist");
			}

			ItemDTO itemDTO = items.get(0);
			int itemType = itemDTO.getItemType();
			if(itemType==13||itemType==14||itemType==15){
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"营销商品不能加入购物车");
			}

			//虚拟商品不能加入购物车
			if(null!=itemDTO.getVirtualMark()&&itemDTO.getVirtualMark()==VirtualMark.VIRTUAL.getCode()){
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"虚拟商品不能加入购物车");
			}

		}catch(TradeException e){
			log.error("getItemSku error",e);
			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_REMOTE_CALL_ERROR);
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
        //updated by jiguansheng 移除店铺概念
        // 可能购物车中已经存在
        CartItemDO cartItem = userCartItemManager.getCartItemBySkuId(itemSkuId, userId);
       // CartItemDO cartItem = this.userCartItemManager.getCartItemBySkuId(itemSkuId, distributorId, userId);
		long newId = 0L,updated = 0;
		CartItemDO newCartItem = null;
		Date now = new Date();
		Integer minLimitation = item.getSaleMinNum();
		Integer maxLimitation = item.getSaleMaxNum();
		if(minLimitation!=null && minLimitation.intValue()!=0 && number<minLimitation.intValue()){
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_BUY_MIN_AMOUNT,
					"该商品最少需要购买"+minLimitation.intValue()+"件");
		}
		if(maxLimitation!=null && maxLimitation.intValue()!=0 && number>maxLimitation.intValue()){
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT,
					"该商品最多只能购买"+maxLimitation.intValue()+"件");
		}

		Long supplierId = item.getSupplierId();



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



		try{
			Integer cartItemCount = userCartItemManager.getCartItemCountByUserAndSellerId(item.getId(), userId, sellerId);
			//用户限购
			checkUserItemPurchase(item.getId(),userId,sellerId,number,cartItemCount, appKey);

			Integer cartSkuCount = userCartItemManager.getCartItemCountBySkuIdAndUserId(itemSku.getId(), userId, sellerId);

//			checkItemCartOutSkuStock(itemSku,number,cartSkuCount);
			StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
	    	storeItemSkuQTO.setItemSkuId(itemSku.getId());
			List<StoreItemSkuDTO> storeItemSkuDTOs = supplierManager.queryStoreItemSku(storeItemSkuQTO, appKey);
			
			// 加入购物车针对限购数量做处理
			MarketItemDTO marketItemDTO = genMarketItemDTO(itemSku,item,number);
			List<MarketItemDTO> marketItemDTOs = new ArrayList<MarketItemDTO>();
			marketItemDTOs.add(marketItemDTO);
			List<DiscountInfo> discountInfoList = marketingManager.getCartDiscountInfo(marketItemDTOs,appKey,userId);
			log.info("  ------- discountInfoList   :"+JSONObject.toJSONString(discountInfoList));
			if(CollectionUtils.isNotEmpty(discountInfoList) && discountInfoList.get(0) !=null && ToolType.TIME_RANGE_DISCOUNT.getCode().equals(discountInfoList.get(0).getActivity().getToolCode())){
				Long limitNumber = discountInfoList.get(0).getItemList().get(0).getLimitNumber();
				checkLimitItemCartOutSkuStock(storeItemSkuDTOs,number,cartSkuCount,limitNumber);
			}else{
				checkItemCartOutSkuStock(storeItemSkuDTOs,number,cartSkuCount);
			}

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

						subCartItemDO.setSupplierId(supplierId); //套装的子商品和套装商品是同样的supplierid
						cartItemCount = userCartItemManager.getCartItemCountByUserAndSellerId(subItemDTO.getId(), userId, sellerId);

						checkUserItemPurchase(subItemDTO.getId(),userId,sellerId,subCartItemDO.getNumber(),cartItemCount, appKey);

						cartSkuCount = userCartItemManager.getCartItemCountBySkuIdAndUserId(subCartItemDO.getItemSkuId(), userId, sellerId);

						checkItemCartOutSkuStock(subItemSkuDTOList.get(0),subCartItemDO.getNumber(),cartSkuCount);

						subCartItemDOList.add(subCartItemDO);
//							this.userCartItemManager.addUserCartItem(subCartItemDO);
					}
				}
			}


			if( null == cartItem ){// 如果是新加
				Integer cartCount = this.userCartItemManager.getCartItemsCount(userId);
				if(cartCount.intValue() >= CART_MAX_NUMBER){
					log.error("exceed the cart max limitation");
					return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_CART_MAX_LIMITATION,
							"购物车最多只能添加"+CART_MAX_NUMBER+"件商品");
				}

				//根据分销商id获取分销商的信息，用于后面填充购物车商品的来源店铺名称
				//DistShopDTO distShopDTO = distributionManager.getDistShop(distributorId, appKey);

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
				if(itemSku.getImageUrl()==null){
					//设置商品主图
					newCartItem.setItemImageUrl(item.getIconUrl());					
				}else{
					//设置sku图片
					newCartItem.setItemImageUrl(itemSku.getImageUrl());					
				}
				newCartItem.setNumber(number);
				newCartItem.setBizCode(bizCode);
				newCartItem.setDeliveryType(item.getDeliveryType());
				newCartItem.setItemType(item.getItemType());
				newCartItem.setSupplierId(supplierId);
                newCartItem.setShareUserId(shareUserId);

				//设置分销商id和分销商店铺名称
				//newCartItem.setDistributorId(distributorId);
				//newCartItem.setDistributorName(distShopDTO.getShopName());
                log.info(" newCartItem : "+JSONObject.toJSONString(newCartItem));
				newId = this.userCartItemManager.addUserCartItem(newCartItem,cartItemServiceDOList);


				if(subCartItemDOList!=null&&subCartItemDOList.size()>0){
					for(  CartItemDO subCartItemDO :subCartItemDOList ){
						subCartItemDO.setOriginalId(newId);
						//设置分销商id和分销商店铺名称
						//subCartItemDO.setDistributorId(distributorId);
						//subCartItemDO.setDistributorName(distShopDTO.getShopName());

						this.userCartItemManager.addUserCartItem(subCartItemDO,cartItemServiceDOList);
					}
				}

				cartItem = newCartItem ;
			}else{//之前已经加过该商品
				//如果多次添加购物车的总数超过商品下单上限，则提示错误信息
				int totalNumber = cartItem.getNumber() + number;
				if(maxLimitation!=null && maxLimitation!=0
						&&  totalNumber > maxLimitation.intValue()){
					return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT,
							"该商品最多只能购买"+maxLimitation.intValue()+"件");
				}
				cartItem.setUserId(userId);
				cartItem.setId(cartItem.getId()); // id赋值用于更新条件
				cartItem.setNumber(totalNumber);
				cartItem.setBizCode(bizCode);
                if(shareUserId != null){
                    cartItem.setShareUserId(shareUserId);
                }
				updated = this.userCartItemManager.updateUserCartItemNumber(cartItem,cartItemServiceDOList);
			}
		}catch(TradeException e){
			log.error("db error: ",e);
			return ResponseUtils.getFailResponse(e.getResponseCode(),e.getMessage());
		}

//		cartItem = this.userCartItemManager.getCartItemBySkuId(itemSkuId, sellerId, userId);
		CartItemDTO module = ModelUtil.convert2CartItemDTO(cartItem,appDomain);
		if(newId > 0 || updated > 0){
			return ResponseUtils.getSuccessResponse(module);
		}else{
			log.error("add cartItem error : skuId" + itemSkuId + " sellerId: "+ sellerId + " userId: " + userId);
			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_DEFAULT_ERROR,"add cartItem error");
		}
	}

	private MarketItemDTO genMarketItemDTO(ItemSkuDTO itemSkuDTO,ItemDTO itemDTO,int number) {
		MarketItemDTO marketItemDTO = new MarketItemDTO();
		marketItemDTO.setItemSkuId(itemSkuDTO.getId());
		marketItemDTO.setItemId(itemDTO.getId());
		marketItemDTO.setCategoryId(itemDTO.getCategoryId());
		marketItemDTO.setBrandId(itemDTO.getItemBrandId());
		marketItemDTO.setSellerId(itemDTO.getSellerId());
		marketItemDTO.setItemType(itemDTO.getItemType());
		marketItemDTO.setNumber(number);
		marketItemDTO.setUnitPrice(itemSkuDTO.getPromotionPrice());
		
		return marketItemDTO;
	}
	
	/**
     * 校验商品库存
     * @param itemSkuDTO
     * @param buyNumber
     * @throws TradeException
     */
    /*private void checkItemStockNew(Long itemSkuId, int buyNumber,String appKey) throws TradeException {
    	// 从仓库获取sku库存
        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
    	storeItemSkuQTO.setItemSkuId(itemSkuId);
    	List<StoreItemSkuDTO> storeItemSkuDTOs = supplierManager.queryStoreItemSku(storeItemSkuQTO, appKey);
    	if(storeItemSkuDTOs.get(0).getSalesNum() != null && buyNumber > storeItemSkuDTOs.get(0).getSalesNum().intValue()) {
            log.error("orderItem number : " + buyNumber + " out of stock number : "+ storeItemSkuDTOs.get(0).getSalesNum().intValue());
            throw new TradeException(ResponseCode.BIZ_E_ITEM_OUT_OF_STOCK);
        }
    }*/
	
	

	private void checkItemCartOutSkuStock(List<StoreItemSkuDTO> storeItemSkuDTOs,Integer cartItemCount,Integer num)throws TradeException{
		Long stockNum = storeItemSkuDTOs.get(0).getSalesNum();
		
		if(stockNum-cartItemCount-num<0){
			throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "该商品已经超过最多购买量");
		}
	}
	
	private void checkLimitItemCartOutSkuStock(List<StoreItemSkuDTO> storeItemSkuDTOs,Integer cartItemCount,Integer num,Long limitNumber)throws TradeException{
		Long stockNum = storeItemSkuDTOs.get(0).getSalesNum();
		if(limitNumber.longValue() > stockNum.longValue()){
			limitNumber = stockNum;
		}
		
		if(limitNumber-cartItemCount-num<0){
			throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "该商品已经超过最多购买量");
		}
	}
	
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
		return ActionEnum.ADD_USER_CART_ITEM.getActionName();
	}
}	
