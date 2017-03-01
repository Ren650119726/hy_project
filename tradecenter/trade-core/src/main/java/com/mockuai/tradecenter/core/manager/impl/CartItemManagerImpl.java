//package com.mockuai.tradecenter.core.manager.impl;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//
//import com.mockuai.tradecenter.common.constant.ResponseCode;
//import com.mockuai.tradecenter.common.domain.CartDTO;
//import com.mockuai.tradecenter.common.domain.CartItemDTO;
//import com.mockuai.tradecenter.common.domain.FavorableInfoDTO;
//import com.mockuai.tradecenter.core.dao.CartItemDao;
//import com.mockuai.tradecenter.core.domain.CartItemDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.CartItemManager;
////import com.mockuai.tradecenter.core.manager.DeliveryManager;
//
//public class CartItemManagerImpl implements CartItemManager {
//	private static final Logger log = Logger.getLogger(CartItemManagerImpl.class);
//
//	@Resource
//	private CartItemDao cartItemDao;
//
////	@Resource
////	private DeliveryManager deliveryManager;
//
//	@Override
//	public Long addCartItem(CartItemDO cartItemDO) throws TradeException{
//		if(cartItemDO == null){
//			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING,"the cartItem is null");
//		}
//		Long result =0L;
//		try{
//			result = this.cartItemDao.addCartItem(cartItemDO);
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		return result;
//	}
//
//	@Override
//	public int deleteCartItem(Long id) throws TradeException {
//		log.info("enter deleteCartItem : " + id);
//		int result=0;
//		try{
//			result = this.cartItemDao.deleteCartItem(id);
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		log.info("exit, delete num: " + result);
//		return result;
//	}
//
//	@Override
//	public int cleanCart(String sessionId) throws TradeException{
//		log.info("enter cleanCart :" + sessionId);
//		int result = 0;
//		try{
//			result = this.cartItemDao.cleanCart(sessionId);
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING,e);
//		}
//		log.info("exit cleanCart: " + result);
//		return result;
//	}
//
//	public int updateCartItem(CartItemDO cartItemDO)throws TradeException{
//		log.info("enter updateCartItem  id : " + cartItemDO.getId() + " number : " +cartItemDO.getNumber());
//		int result = 0;
//		try{
//			result = this.cartItemDao.updateCartItemNumber(cartItemDO);
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		log.info("exit updateCartItem updated: " + result);
//		return result;
//	}
//
//	@Override
//	public List<CartItemDO> queryCartItems(String sessionId)throws TradeException{
//		log.info("enter queryCartItems: " + sessionId);
//		List<CartItemDO>  result =null;
//		try{
//			result =  this.cartItemDao.querytCartItems(sessionId);
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		log.info("exit queryCartItems size: " + (result ==null ? 0 : result.size()));
//		return result;
//	}
//
//	@Override
//	public int deleteGiftItems(String sessionId)throws TradeException{
//		log.info("enter deleteGiftItems： "+sessionId);
//		int result = 0;
//		try{
//			result =  this.cartItemDao.deleteGiftItems(sessionId);
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//		log.info("exit deleteGiftItems :  " + result);
//		return result;
//	}
//
//	@Override
//	public String validateCartItemFields4Add(CartItemDTO cartItem){
//		if(cartItem.getNumber() == null){
//			return "number is null";
//		}else if(cartItem.getNumber() <= 0){
//			return "number must be greater than 0";
//		}else if(cartItem.getItemSkuId() == null){
//			return "itemSkuId is null";
//		}else if(StringUtils.isEmpty(cartItem.getSessionId())){ // 是不是null 和 空字符串
//			return "sessionId is null";
//		}else if(cartItem.getSupplierId() == null){
//			return "supplierId is null";
//		}
//		return null;
//	}
//
//	@Override
//	public int updateCartItemNumber(CartItemDO cartItem)throws TradeException{
//		log.info("enter updateCartItemNumber: " + cartItem.getId() + " number: " + cartItem.getNumber());
//		int result = 0;
//		try {
//			result = this.cartItemDao.updateCartItemNumber(cartItem);
//		} catch (Exception e) {
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
//		}
//
//		log.info("exit updateCartItemNumber : " + result);
//		return result;
//	}
//
//	@Override
//	public CartItemDTO convertToDto(CartItemDO cartItemDO){
//		if(cartItemDO == null)
//			return null;
//
//		CartItemDTO cartItem = new CartItemDTO();
//
//		cartItem.setId(cartItemDO.getId());
//		cartItem.setName(cartItemDO.getName());
//		cartItem.setItemSkuId(cartItemDO.getItemSkuId());
//		cartItem.setSupplierId(cartItemDO.getSupplierId());
//		cartItem.setUserId(cartItemDO.getUserId());
//		cartItem.setItemId(cartItemDO.getItemId());
//		cartItem.setSessionId(cartItemDO.getSessionId());
//		cartItem.setUnitPrice(cartItemDO.getUnitPrice());
//		cartItem.setTotalPrice(cartItemDO.getTotalPrice());
//		cartItem.setSavePrice(cartItemDO.getSavePrice());
//		cartItem.setTotalSave(cartItemDO.getTotalSave());
//
//		// originPrice
//		// unitPrice
//		//
//		cartItem.setNumber(cartItemDO.getNumber());
//		cartItem.setTotalPrice(cartItemDO.getTotalPrice());
//		cartItem.setTotalSave(cartItemDO.getTotalSave());
//		cartItem.setIsGift(cartItemDO.getIsGift());
//		cartItem.setGmtCreated(cartItemDO.getGmtCreated());
//
//		cartItem.setIsGift(cartItemDO.getIsGift());
////		cartItem.setMainImage(cartItemDO.getMainImage());  // 主图url
//		cartItem.setNumber(cartItemDO.getNumber());
//		cartItem.setUserId(cartItemDO.getUserId());
//		return cartItem;
//	}
//
//	@Override
//	public CartDTO handlePromotionInfo(List<FavorableInfoDTO> ActivityList,List<CartItemDO> cartItems) {
//		return null;
//
//		/*
//		long promotionSave = 0L; // 促销活动总的节省金额
//
//		// TODO 如何处理是否免邮
//		boolean isTransportFree = false;
//
//		// 生成一个商品对应的活动的映射map
//		Map<String, List<MarketingActivityDTO>> itemActivityMap = new HashMap<String, List<MarketingActivityDTO>>();
//		// 赠品列表
//		List<CartItemDTO> giftItems = new ArrayList<CartItemDTO>();
//
//		if (ActivityList == null || ActivityList.size() == 0) {
//			// TODO 返回为空如何处理
//		} else {
//			// 开始按照活动处理 计算购物车总的节省金额
//			String key;
//
//			// 遍历购物车所涉及的活动
//			for (FavorableInfoDTO favorableDTO : ActivityList) {
//				// 累加购物车商品总的优惠金额
//				promotionSave += favorableDTO.getOfferAmount();
//				System.out.println("promotionitemSize : " + favorableDTO.getItemList().size());
//				if (favorableDTO.getItemList() != null) {
//					for (PromotionItemDTO item : favorableDTO.getItemList()) {
//						key = item.getItemSkuId() + "_" + item.getSupplierId();
//						System.out.println("key: " + key);
//						//维护商品及其对应的活动关系
//						if (itemActivityMap.get(key) == null) {
//							List<MarketingActivityDTO> itemActivits = new ArrayList<MarketingActivityDTO>();
//							itemActivits.add(favorableDTO.getActivity());
//							itemActivityMap.put(key, itemActivits);
//						} else {
//							itemActivityMap.get(key).add(favorableDTO.getActivity());
//						}
//					}
//				}
//
//				// 处理该活动下的赠品
//				if (favorableDTO.getGiftList() != null) {
//					for (PromotionItemDTO item : favorableDTO.getGiftList()) {
//						CartItemDTO giftItem = new CartItemDTO();
//						giftItem.setItemSkuId(item.getItemSkuId());
//						giftItem.setSupplierId(item.getSupplierId());
//						giftItem.setMainImage(item.getMainImage());
//						giftItem.setName(item.getName());
//						giftItem.setIsGift(true); // 赠品
//						giftItem.setUnitPrice(0L);  // 赠品价格为0
//						giftItem.setNumber(item.getNumber()); // 赠品的数量
//
//						giftItems.add(giftItem);
//						// TODO 赠品是否需要跟活动挂钩
//						// giftItem.setActivities();
//
//					}
//				}
//			}
//		}
//
//		// 需要根据促销平台返回 判断是否是免邮费
//		// TODO
//		long transportFee = 0L;
//		if (!isTransportFree) {
//			// TODO 后续可能需要考虑重量累加因素
//			transportFee = this.deliveryManager.getDeliveryFee(null, null);
//		}
//
//		// 每次新加入新的商品后 购物车的优惠有可能变化 需要返回给调用方
//		CartDTO cartDTO = new CartDTO();
//
//		long cartTotalPrice = 0L;
//		List<CartItemDTO> returnCartItems = new ArrayList<CartItemDTO>();
//		for (CartItemDO item : cartItems) {
//			cartTotalPrice += item.getUnitPrice() * item.getNumber();
//			CartItemDTO cartItem = this.convertToDto(item); // 将DO转化为DTO
//
//			//填充该商品参加的活动列表
//			cartItem.setActivities(itemActivityMap.get(item.getItemSkuId() + "_" + item.getSupplierId())); // 单个商品参加的活动
//			returnCartItems.add(cartItem);
//		}
//		cartDTO.setCartItems(returnCartItems);
//		if (giftItems != null || giftItems.size() != 0) {
//			returnCartItems.addAll(giftItems); // 将赠品也加入入列表
//		}
//
//		cartDTO.setPromotionSave(promotionSave); // 总优惠节省
//		cartDTO.setTotalPrice(cartTotalPrice); // 购物车商品总的金额
//
//		cartDTO.setTransportFee(transportFee); // 运费如果是免运费为 0 反之就是网站默认的运费
//
//		return cartDTO;
//		*/
//	}
//
//	@Override
//	public CartItemDO getCartItemBySkuId(Long itemSkuId,Long supplierId,String sessionId){
//		log.info("enter getCartItemBySkuId : " + itemSkuId + "," +supplierId + " ," + sessionId);
//		CartItemDO cartItemDO = new CartItemDO();
//		cartItemDO.setSupplierId(supplierId);
//		cartItemDO.setItemSkuId(itemSkuId);
//		cartItemDO.setSessionId(sessionId);
//		cartItemDO = this.cartItemDao.getCartItemBySkuId(cartItemDO);
//		log.info("exit getCartItemBySkuId " + (cartItemDO ==null));
//		return cartItemDO ;
//	}
//
//	@Override
//	public Integer getCartItemsCount(String sessionId){
//		log.info("enter getCartItemsCount:  " + sessionId);
//		int result = this.cartItemDao.getCartItemsCount(sessionId);
//		log.info("exit getCartItemsCount: " + result);
//		return result;
//	}
//
//	@Override
//	public CartItemDO getCartItem(Long id){
//		log.info("enter getCartItem ：" + id);
//		CartItemDO result = null;
//		result = this.cartItemDao.getCartItem(id);
//		return result;
//	}
//}
