//package com.mockuai.tradecenter.core.service.action.cart;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.mockuai.itemcenter.common.constant.DBConst;
//import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
//import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
//import com.mockuai.marketingcenter.common.domain.dto.MarketValueAddedServiceDTO;
//import com.mockuai.shopcenter.domain.dto.ShopDTO;
//import com.mockuai.tradecenter.common.api.TradeResponse;
//import com.mockuai.tradecenter.common.constant.ActionEnum;
//import com.mockuai.tradecenter.common.constant.ResponseCode;
//import com.mockuai.tradecenter.common.constant.TradeConstants;
//import com.mockuai.tradecenter.common.domain.CartDTO;
//import com.mockuai.tradecenter.common.domain.CartItemDTO;
//import com.mockuai.tradecenter.common.domain.CartItemServiceDTO;
//import com.mockuai.tradecenter.common.domain.ShopCartDTO;
//import com.mockuai.tradecenter.core.domain.CartItemDO;
//import com.mockuai.tradecenter.core.domain.CartItemServiceDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.CartItemServiceManager;
//import com.mockuai.tradecenter.core.manager.ItemManager;
//import com.mockuai.tradecenter.core.manager.MarketingManager;
//import com.mockuai.tradecenter.core.manager.ShopManager;
//import com.mockuai.tradecenter.core.manager.UserCartItemManager;
//import com.mockuai.tradecenter.core.service.RequestContext;
//import com.mockuai.tradecenter.core.service.ResponseUtils;
//import com.mockuai.tradecenter.core.service.TradeRequest;
//import com.mockuai.tradecenter.core.service.action.Action;
//import com.mockuai.tradecenter.core.util.DozerBeanService;
//import com.mockuai.tradecenter.core.util.ModelUtil;
//
///**
// * 查看用户的购物车商品列表及其优惠信息（优惠金额、运费等）
// * @author cwr
// */
//public class QueryUserCartItemsForMultipleShops implements Action{
//	
//	private static final Logger log = LoggerFactory.getLogger(QueryUserCartItemsForMultipleShops.class);
//	
//	private static final String SEPERATOR = "-";
//
//	@Resource
//	private UserCartItemManager userCartItemManager;
//	
//	@Resource
//	private ItemManager itemManager;
//	
//	@Resource
//	private ShopManager shopManager;
//	
//
//	@Autowired
//	private MarketingManager marketingManager;
//	
//	@Resource
//	private CartItemServiceManager 	cartItemServiceManager;
//	
//	@Resource
//	private DozerBeanService dozerBeanService;
//
//	public TradeResponse<List<ShopCartDTO>> execute(RequestContext context){
//		TradeRequest request = context.getRequest();
//		String appKey = (String)context.get("appKey");
//		String appDomain = (String)context.get("appDomain");
//		TradeResponse<List<ShopCartDTO>> response = null;
//		if(request.getParam("userId") == null){
//			log.error("userId is null");
//			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"userId is null");
//		}
//		
//		long userId= (Long)request.getParam("userId"); // 
//		int source = request.getParam("source")==null ? TradeConstants.OrderSource.PC : (Integer)request.getParam("source");  // 渠道来源
//		if(source != TradeConstants.OrderSource.ANDROID && source != TradeConstants.OrderSource.IOS && source!=TradeConstants.OrderSource.HTML5){
//			source = TradeConstants.OrderSource.PC ; // 默认当做pc端处理
//		}
//		// 是不是pc渠道
//		boolean isPc = source==TradeConstants.OrderSource.PC ? true : false; 
//		List<ShopCartDTO> module = new ArrayList<ShopCartDTO>();
//		List<CartItemDO> cartItems = null;
//		// 查询购物车表的商品列表
//		try {
//			cartItems = this.userCartItemManager.queryUserCartItems(userId);
//		} catch (TradeException e) {
//			log.error("db error:",e);
//			return ResponseUtils.getFailResponse(e.getResponseCode());
//		}
//		List<CartItemDTO> cartItemDTOs = new ArrayList<CartItemDTO>();
//		List<CartItemDTO> returnList = new ArrayList<CartItemDTO>();
//		if(cartItems == null || cartItems.size()== 0){
//			return ResponseUtils.getSuccessResponse(module);
//		}else{
////			for(CartItemDO cartItemDO: cartItems){
////				CartItemDTO cartItemDTO = ModelUtil.convert2CartItemDTO(cartItemDO,appDomain);
////				cartItemDTOs.add(cartItemDTO);
////			}
//			
//			
//			CartDTO cartDTO = new CartDTO();
////			List<CartItemDTO> cartItemDTOs = new ArrayList<CartItemDTO>();
//			Map<Long,CartItemDO> idCartItemMap = new HashMap<Long,CartItemDO>();
//			for(CartItemDO cartItemDO: cartItems){
//				CartItemDTO cartItemDTO = ModelUtil.convert2CartItemDTO(cartItemDO,appDomain);
//				cartItemDTOs.add(cartItemDTO);
//				idCartItemMap.put(cartItemDO.getId(), cartItemDO);
//			}
//			
//			
//			for(CartItemDTO cartItemDTO:cartItemDTOs){
//				if(cartItemDTO.getOriginalId()==null){
//					if(null!=cartItemDTO.getItemType()&&cartItemDTO.getItemType().intValue() == DBConst.SUIT_ITEM.getCode()){
//		        		List<CartItemDTO> subCartItemList = new ArrayList<CartItemDTO>();
//		        		for(CartItemDTO cartItemDTO1:cartItemDTOs){
//		        			if(cartItemDTO1.getOriginalId()!=null&&cartItemDTO1.getOriginalId().longValue()==cartItemDTO.getId()){
//		        				CartItemDTO subCartItemDTO = new CartItemDTO();
//		        				BeanUtils.copyProperties(cartItemDTO1, subCartItemDTO);
//		        				subCartItemList.add(subCartItemDTO);
//		        			}
//		        		}
//		        		cartItemDTO.setSubCartList(subCartItemList);
//		        	}
//					
//					//增加增值服务
//					List<CartItemServiceDO> cartItemServiceDOList = cartItemServiceManager.queryUserCartItemServiceList(cartItemDTO.getId());
//					if(null!=cartItemServiceDOList&&cartItemServiceDOList.size()>0){
//						cartItemDTO.setCartItemServiceList(dozerBeanService.coverList(cartItemServiceDOList, CartItemServiceDTO.class));
//					}
//		        	returnList.add(cartItemDTO);
//				}
//	        	
//	        }
//			cartDTO.setCartItems(returnList);
//			
//		}
//		Map<Long,List<DiscountInfo>> sellerDiscountsMap = new HashMap<Long,List<DiscountInfo>>();
//		Map<Long, List<MarketItemDTO>> sellerMarketItemsMap = genSellerMarketItemDTOsMap(returnList);
//		if(sellerMarketItemsMap.isEmpty()==false){
//			try {
//				sellerDiscountsMap = marketingManager.getCartDiscountInfoBatch(sellerMarketItemsMap,appKey);
//			} catch (TradeException e) {
//				log.error("getCartDiscountInfoBatch error ",e);
//				return ResponseUtils.getFailResponse(ResponseCode.SYS_E_REMOTE_CALL_ERROR,"获取结算信息出错");
//			}
//		}
//		
//		//根据sellerId 组装
//		Map<Long, List<CartItemDTO>> sellerCartsMap = genSellerCartItemMap(returnList);
//		for (Entry<Long, List<CartItemDTO>> entry : sellerCartsMap.entrySet()) {
//			ShopDTO shop = null;
//			try {
//				shop = shopManager.getShopDTO(entry.getKey(), appKey);
//			}catch (TradeException e) {
//				shop = new ShopDTO();
//				shop.setShopName("");
//				log.error("get shop error:",e);
//			}
//				ShopCartDTO shopCartDTO = new ShopCartDTO();
//				shopCartDTO.setCartItems(entry.getValue());
//				shopCartDTO.setSellerName(shop.getShopName());
//				shopCartDTO.setShopId(shop.getId());
//				
//				List<DiscountInfo> discountInfo = sellerDiscountsMap.get(entry.getKey());
//				
//				//TODO ....
//				shopCartDTO.setDiscountInfoList(discountInfo);
//				
//				module.add(shopCartDTO);
//		}
//		
//		
//		
//		
//		response = ResponseUtils.getSuccessResponse(module);
//		response.setTotalCount(module.size()); // 总记录数
//		return response;
//	}
//	
//	private Map<Long, List<CartItemDTO>> genSellerCartItemMap(List<CartItemDTO> cartItems) {
//        Map<Long, List<CartItemDTO>> sellerCartItemMap = new HashMap<Long, List<CartItemDTO>>();
//        for (CartItemDTO cartItemDTO : cartItems) {
//            Long sellerId = cartItemDTO.getSellerId();
//            if (sellerCartItemMap.get(sellerId) == null) {
//                List<CartItemDTO> carts = new ArrayList<CartItemDTO>();
//                carts.add(cartItemDTO);
//                sellerCartItemMap.put(sellerId, carts);
//            } else {
//            	sellerCartItemMap.get(sellerId).add(cartItemDTO);
//            }
//        }
//        return sellerCartItemMap;
//    }
//	
//	private  Map<Long, List<MarketItemDTO>> genSellerMarketItemDTOsMap(List<CartItemDTO> cartItems){
//		Map<Long, List<MarketItemDTO>> sellerKeyItems = new HashMap<Long, List<MarketItemDTO>>();
//		
//		for (CartItemDTO cartItemDTO : cartItems) {
//			  Long sellerId = cartItemDTO.getSellerId();
//			  	MarketItemDTO marketItemDTO = new MarketItemDTO();
//				marketItemDTO.setItemId(cartItemDTO.getItemId());
//				marketItemDTO.setItemSkuId(cartItemDTO.getItemSkuId());
//				marketItemDTO.setSellerId(cartItemDTO.getSellerId());
//				marketItemDTO.setNumber(cartItemDTO.getNumber());
//				marketItemDTO.setUnitPrice(cartItemDTO.getPromotionPrice());
//				marketItemDTO.setItemType(cartItemDTO.getItemType());
//				List<CartItemServiceDTO> cartItemServiceDTOs = cartItemDTO.getCartItemServiceList();
//				if(cartItemServiceDTOs!=null&&cartItemServiceDTOs.isEmpty()==false){
//					List<MarketValueAddedServiceDTO> marketValueAddedServiceList = new ArrayList<MarketValueAddedServiceDTO>();
//					for(CartItemServiceDTO cartItemServiceDTO:cartItemServiceDTOs){
//						MarketValueAddedServiceDTO marketValueAddedServiceDTO = new MarketValueAddedServiceDTO();
//						marketValueAddedServiceDTO.setId(cartItemServiceDTO.getServiceId());
//						marketValueAddedServiceDTO.setPrice(cartItemServiceDTO.getPrice());
//						marketValueAddedServiceDTO.setSellerId(cartItemServiceDTO.getSellerId());
//						marketValueAddedServiceList.add(marketValueAddedServiceDTO);
//					}
//					marketItemDTO.setServices(marketValueAddedServiceList);
//				}
//				
//				
//				
//			  if (sellerKeyItems.get(sellerId) == null) {
//				  List<MarketItemDTO> marketItems = new ArrayList<MarketItemDTO>();
//				  marketItems.add(marketItemDTO);
//	                sellerKeyItems.put(sellerId, marketItems);
//	            } else {
//	            	sellerKeyItems.get(sellerId).add(marketItemDTO);
//	            }
//		}
//		
//		return sellerKeyItems;
//	}
//	
//
//	@Override
//	public String getName() {
//		return ActionEnum.QUERY_USERCART_ITEMS_FOR_MULTIPLE_SHOPS.getActionName();
//	}
//
//	
//}
