//package com.mockuai.tradecenter.core.service.action.cart;
//
//import java.util.ArrayList;
//import java.util.Collections;
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
//import com.mockuai.higocenter.common.util.JsonUtil;
//import com.mockuai.itemcenter.client.SupplierClient;
//import com.mockuai.itemcenter.common.constant.DBConst;
//import com.mockuai.itemcenter.common.domain.dto.SupplierDTO;
//import com.mockuai.itemcenter.common.domain.qto.SupplierQTO;
//import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
//import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
//import com.mockuai.marketingcenter.common.domain.dto.MarketValueAddedServiceDTO;
//import com.mockuai.tradecenter.common.api.TradeResponse;
//import com.mockuai.tradecenter.common.constant.ActionEnum;
//import com.mockuai.tradecenter.common.constant.ResponseCode;
//import com.mockuai.tradecenter.common.domain.CartDTO;
//import com.mockuai.tradecenter.common.domain.CartItemDTO;
//import com.mockuai.tradecenter.common.domain.CartItemServiceDTO;
//import com.mockuai.tradecenter.common.domain.supplier.SupplierCartDTO;
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
//public class QueryUserCartItemsForSupplier implements Action {
//
//	private static final Logger log = LoggerFactory.getLogger(QueryUserCartItemsForSupplier.class);
//
//	private static final String SEPERATOR = "-";
//
//	@Resource
//	private UserCartItemManager userCartItemMng;
//
//	@Resource
//	private ItemManager itemManager;
//
//	@Resource
//	private ShopManager shopManager;
//
//	@Autowired
//	private MarketingManager marketingManager;
//
//	@Resource
//	private CartItemServiceManager cartItemServiceManager;
//
//	@Resource
//	private DozerBeanService dozerBeanService;
//
//	@Autowired
//	SupplierClient supplierClient;
//
//	public TradeResponse<List<SupplierCartDTO>> execute(RequestContext context) {
//		TradeRequest request = context.getRequest();
//		String appKey = (String) context.get("appKey");
//		TradeResponse<List<SupplierCartDTO>> response = null;
//		if (request.getParam("userId") == null) {
//			log.error("userId is null");
//			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
//		}
//		long userId = (Long) request.getParam("userId");
//
//		List<SupplierCartDTO> module = new ArrayList<SupplierCartDTO>();
//		List<CartItemDO> cartItems = null;
//		// 查询购物车表的商品列表
//		try {
//			cartItems = this.userCartItemMng.querySupplierCartItems(userId);
//		} catch (TradeException e) {
//			log.error("querySupplierCartItems db error:", e);
//			return ResponseUtils.getFailResponse(e.getResponseCode());
//		}
//		List<CartItemDTO> cartItemDTOs = new ArrayList<CartItemDTO>();
//		List<CartItemDTO> returnList = new ArrayList<CartItemDTO>();
//		if (cartItems == null || cartItems.size() == 0) {
//			return ResponseUtils.getSuccessResponse(module);
//		} else {
//			CartDTO cartDTO = new CartDTO();
//			Map<Long, CartItemDO> idCartItemMap = new HashMap<Long, CartItemDO>();
//			for (CartItemDO cartItemDO : cartItems) {
//				CartItemDTO cartItemDTO = ModelUtil.convert2CartItemDTO(cartItemDO, "");
//				cartItemDTOs.add(cartItemDTO);
//				idCartItemMap.put(cartItemDO.getId(), cartItemDO);
//			}
//
//			for (CartItemDTO cartItemDTO : cartItemDTOs) {
//				if (cartItemDTO.getOriginalId() == null) {
//					if (null != cartItemDTO.getItemType()
//							&& cartItemDTO.getItemType().intValue() == DBConst.SUIT_ITEM.getCode()) {
//						List<CartItemDTO> subCartItemList = new ArrayList<CartItemDTO>();
//						for (CartItemDTO cartItemDTO1 : cartItemDTOs) {
//							if (cartItemDTO1.getOriginalId() != null
//									&& cartItemDTO1.getOriginalId().longValue() == cartItemDTO.getId()) {
//								CartItemDTO subCartItemDTO = new CartItemDTO();
//								BeanUtils.copyProperties(cartItemDTO1, subCartItemDTO);
//								subCartItemList.add(subCartItemDTO);
//							}
//						}
//						cartItemDTO.setSubCartList(subCartItemList);
//					}
//
//					// 增加增值服务
//					List<CartItemServiceDO> cartItemServiceDOList = cartItemServiceManager
//							.queryUserCartItemServiceList(cartItemDTO.getId());
//					if (null != cartItemServiceDOList && cartItemServiceDOList.size() > 0) {
//						cartItemDTO.setCartItemServiceList(
//								dozerBeanService.coverList(cartItemServiceDOList, CartItemServiceDTO.class));
//					}
//					returnList.add(cartItemDTO);
//				}
//
//			}
//			cartDTO.setCartItems(returnList);
//
//		}
//
//		Map<Long, List<DiscountInfo>> supplierDiscountsMap = new HashMap<Long, List<DiscountInfo>>();
//
//		List<MarketItemDTO> marketItemDTOs = genMarketItemDTOs(returnList);
//
//		try {
//			supplierDiscountsMap = marketingManager.getSupplierCartDiscountInfo(marketItemDTOs, appKey);
//		} catch (TradeException e) {
//			log.error("getSupplierCartDiscountInfo error ", e);
//			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_REMOTE_CALL_ERROR, e.getMessage());
//		}
//
//		Map<Long, List<CartItemDTO>> supplierCartsMap = genSupplierCartItemMap(returnList);
//		for (Entry<Long, List<CartItemDTO>> entry : supplierCartsMap.entrySet()) {
//
//			SupplierCartDTO supplierCartDTO = new SupplierCartDTO();
//			supplierCartDTO.setSupplierId(entry.getKey());
//
//			List<DiscountInfo> discountInfo = supplierDiscountsMap.get(entry.getKey());
//
//			supplierCartDTO.setDiscountInfoList(discountInfo);
//
//			supplierCartDTO.setCartItems(entry.getValue());
//
//			module.add(supplierCartDTO);
//		}
//
//		try{
//			querySupplier(module,appKey);
//		}catch(Exception e){
//			log.error("invoke itemcenter querySupplier error",e);
//		}
//
//
//		response = ResponseUtils.getSuccessResponse(module);
//		response.setTotalCount(module.size()); // 总记录数
//		return response;
//	}
//
//	private Map<Long, List<CartItemDTO>> genSupplierCartItemMap(List<CartItemDTO> cartItems) {
//		Map<Long, List<CartItemDTO>> supplierCartItemMap = new HashMap<Long, List<CartItemDTO>>();
//		for (CartItemDTO cartItemDTO : cartItems) {
//			Long supplierId = cartItemDTO.getSupplierId();
//			if (supplierCartItemMap.get(supplierId) == null) {
//				List<CartItemDTO> carts = new ArrayList<CartItemDTO>();
//				carts.add(cartItemDTO);
//				supplierCartItemMap.put(supplierId, carts);
//			} else {
//				supplierCartItemMap.get(supplierId).add(cartItemDTO);
//			}
//		}
//		return supplierCartItemMap;
//	}
//
//	private void querySupplier(List<SupplierCartDTO> supplierCarts,String appKey)throws TradeException{
//		List<Long> supplierIds = new ArrayList<Long>();
//		for(SupplierCartDTO supplierCartDTO:supplierCarts){
//			supplierIds.add(supplierCartDTO.getSupplierId());
//		}
//		SupplierQTO query = new SupplierQTO();
//		query.setIdList(supplierIds);
//		Map<Long,String> supplierMap = new HashMap<Long,String>();
//		com.mockuai.itemcenter.common.api.Response response = supplierClient.querySupplier(query, appKey);
//		 if(response.getCode() != ResponseCode.RESPONSE_SUCCESS.getCode()){
//			 log.error("invoke itemcenter response"+JsonUtil.toJson(response));
//	        	throw new TradeException("querySupplier error"+response.getMessage());
//			}else{
//				List<SupplierDTO> supplierDTOs = (List<SupplierDTO>) response.getModule();
//				if(supplierDTOs!=null&&supplierDTOs.size()!=0){
//					for(SupplierDTO supplierDTO:supplierDTOs){
//						supplierMap.put(supplierDTO.getId(), supplierDTO.getName());
//					}
//				}
//			}
//
//		 for(SupplierCartDTO supplierCartDTO:supplierCarts){
//			 supplierCartDTO.setSupplierName(supplierMap.get(supplierCartDTO.getSupplierId()));
//		 }
//	}
//
//	private List<MarketItemDTO> genMarketItemDTOs(List<CartItemDTO> cartItems) {
//		List<MarketItemDTO> marketItemDTOs = Collections.EMPTY_LIST;
//		if (cartItems.isEmpty() == false) {
//			marketItemDTOs = new ArrayList<MarketItemDTO>();
//		}
//
//		for (CartItemDTO cartItemDTO : cartItems) {
//			MarketItemDTO marketItemDTO = new MarketItemDTO();
//			marketItemDTO.setItemId(cartItemDTO.getItemId());
//			marketItemDTO.setItemSkuId(cartItemDTO.getItemSkuId());
//			marketItemDTO.setSellerId(cartItemDTO.getSellerId());
//			marketItemDTO.setNumber(cartItemDTO.getNumber());
//			marketItemDTO.setUnitPrice(cartItemDTO.getPromotionPrice());
//			marketItemDTO.setItemType(cartItemDTO.getItemType());
//			List<CartItemServiceDTO> cartItemServiceDTOs = cartItemDTO.getCartItemServiceList();
//			if (cartItemServiceDTOs != null && cartItemServiceDTOs.isEmpty() == false) {
//				List<MarketValueAddedServiceDTO> marketValueAddedServiceList = new ArrayList<MarketValueAddedServiceDTO>();
//				for (CartItemServiceDTO cartItemServiceDTO : cartItemServiceDTOs) {
//					MarketValueAddedServiceDTO marketValueAddedServiceDTO = new MarketValueAddedServiceDTO();
//					marketValueAddedServiceDTO.setId(cartItemServiceDTO.getServiceId());
//					marketValueAddedServiceDTO.setPrice(cartItemServiceDTO.getPrice());
//					marketValueAddedServiceDTO.setSellerId(cartItemServiceDTO.getSellerId());
//					marketValueAddedServiceList.add(marketValueAddedServiceDTO);
//				}
//				marketItemDTO.setServices(marketValueAddedServiceList);
//			}
//			marketItemDTOs.add(marketItemDTO);
//		}
//
//		return marketItemDTOs;
//	}
//
//	@Override
//	public String getName() {
//		return ActionEnum.QUERY_USERCART_ITEMS_FOR_SUPPLIER.getActionName();
//	}
//
//}
