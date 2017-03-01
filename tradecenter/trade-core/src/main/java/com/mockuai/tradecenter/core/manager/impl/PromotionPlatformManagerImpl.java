//package com.mockuai.tradecenter.core.manager.impl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.mockuai.tradecenter.common.domain.FavorableInfoDTO;
//import com.mockuai.tradecenter.common.domain.MarketingActivityDTO;
//import com.mockuai.tradecenter.common.domain.MarketingItemDTO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.PromotionPlatformManager;
//
//public class PromotionPlatformManagerImpl implements PromotionPlatformManager{
//
//	static Map<String,MarketingItemDTO> itemMap = new HashMap<String,MarketingItemDTO>();
//	static Map<String,MarketingItemDTO> allItemMap = new HashMap<String,MarketingItemDTO>();
//
//	{
//		MarketingItemDTO item2 = new MarketingItemDTO();
//
//		item2.setItemSkuId(1111L);
//		item2.setSupplierId(11L);
//		item2.setNumber(100);
//		itemMap.put("1111-11", item2);
//
//		MarketingItemDTO item3 = new MarketingItemDTO();
//
//		item3.setItemSkuId(1211L);
//		item3.setSupplierId(12L);
//		/*item3.setItemId(22L);
//		item3.setIsGift(false);
//		item3.setCurrentPrice(1099L);*/
//		item3.setNumber(10);
//		itemMap.put("1211-12", item3);
//
//		MarketingItemDTO item4 = new MarketingItemDTO();
//
//		item4.setItemSkuId(1311L);
//		item4.setSupplierId(13L);
//		/*item4.setItemId(22L);
//		item4.setIsGift(false);
//		item4.setCurrentPrice(1099L);*/
//		item4.setNumber(1);
//		itemMap.put("1311-13", item4);
//
//		MarketingItemDTO item5 = new MarketingItemDTO();
//
//		item5.setItemSkuId(1411L);
//		item5.setSupplierId(14L);
//		/*item5.setItemId(23L);
//		item5.setIsGift(false);
//		item5.setCurrentPrice(8888L);*/
//		item5.setNumber(2);
//		itemMap.put("1411-14", item5);
//
//		MarketingItemDTO giftItem = new MarketingItemDTO();
//		giftItem.setItemSkuId(1511L);
//		giftItem.setSupplierId(15L);
//		/*giftItem.setItemId(23L);
//		giftItem.setIsGift(true);
//		giftItem.setName("[赠品1]");
//		giftItem.setCurrentPrice(0L);*/
//		giftItem.setNumber(1);
//		itemMap.put("1511-15", giftItem);
//
//		MarketingItemDTO giftItem2 = new MarketingItemDTO();
//		giftItem2.setItemSkuId(1611L);
//		giftItem2.setSupplierId(16L);
//		/*giftItem2.setItemId(24L);
//		giftItem2.setIsGift(true);
//		giftItem2.setName("[赠品2]");
//		giftItem2.setCurrentPrice(1099L);*/
//		giftItem2.setNumber(1);
//		itemMap.put("1611-16", giftItem2);
//
//		allItemMap.put("111-21", item2);
//		allItemMap.put("121-12", item3);
//		allItemMap.put("121-12", item4);
//		allItemMap.put("131-13", item5);
//
//	}
//	/*
//	 * 测试数据
//	 */
//	/*static List<FavorableInfoDTO> list = new ArrayList<FavorableInfoDTO>();
//	{
//		FavorableInfoDTO favorableInfo = new FavorableInfoDTO();
//
//		MarketingActivityDTO activity = new MarketingActivityDTO();
//		activity.setIsCoupon(false);
//
//		// 活动 一 赠品
//		activity.setId(1001L);
//		activity.setName("促销活动一");
//		MarketingActivityDTO giftItem = new MarketingActivityDTO();
//		giftItem.setItemSkuId(21L);
//		giftItem.setSupplierId(12L);
//		giftItem.setItemId(11111L);
//		giftItem.setSupplierId(99999L);
//		giftItem.setIsGift(true);
//		giftItem.setName("[赠品1]");
//		giftItem.setCurrentPrice(1099L);
//		giftItem.setNumber(100);
//		List giftList = new ArrayList<MarketingActivityDTO>();
//		giftList.add(giftItem);
//
//		//
//		MarketingActivityDTO item2 = new MarketingActivityDTO();
//
//		item2.setItemSkuId(21L);
//		item2.setSupplierId(11L);
//		item2.setItemId(11111L);
//
//		item2.setIsGift(false);
//		item2.setCurrentPrice(1099L);
//		item2.setNumber(100);
//
//		List items = new ArrayList<MarketingActivityDTO>();
//		items.add(item2);
//
//		favorableInfo.setActivity(activity);
//		favorableInfo.setGiftList(giftList);
//		favorableInfo.setItemList(items);
//
//		favorableInfo.setOfferAmount(20);
//
//		this.list.add(favorableInfo);
//	}*/
//
//	@Override
//	public List<FavorableInfoDTO> getPromotionInfo(List<MarketingItemDTO> itemSkuQTOList,int channel)
//			throws TradeException {
//
//		List<FavorableInfoDTO> result = new ArrayList<FavorableInfoDTO>();
//		// 活动一
//		FavorableInfoDTO favorableInfo = new FavorableInfoDTO();
//		MarketingActivityDTO activity = new MarketingActivityDTO();
//
//		activity.setId(1001L);
//		activity.setName("春节大促 满200-15");
//		activity.setIsCoupon(false);
//
//		List giftList = new ArrayList<MarketingActivityDTO>();
//		List items = new ArrayList<MarketingActivityDTO>();
//
//		/*for(MarketingItemDTO item : itemSkuQTOList){
//			MarketingItemDTO dto = itemMap.get(item.getItemSkuId() + "-" + item.getSupplierId());
//			if(dto != null){
//				dto.setNumber(item.getNumber());
//				items.add(dto);
//			}
//		}*/
//
//		// 赠品
//		//giftList.add(itemMap.get("1311-13"));
//		favorableInfo.setActivity(activity);
//		favorableInfo.setGiftList(giftList);
//		favorableInfo.setItemList(items);
//		favorableInfo.setNoPostage(true);
//		// 节省金额
//		favorableInfo.setOfferAmount(15L);
//		//result.add(favorableInfo);
//
//		MarketingItemDTO i = itemMap.get("1111-11");
//		MarketingItemDTO i2 = itemMap.get("1311-13");
//
//		items.add(i);
//		items.add(i2);
//
//		favorableInfo.setItemList(items);
//
//		// 活动二
//		FavorableInfoDTO favorableInfo2 = new FavorableInfoDTO();
//		MarketingActivityDTO activity2 = new MarketingActivityDTO();
//
//		activity2.setId(1001L);
//		activity2.setName("手机品牌专场  满2000-100");
//		activity2.setIsCoupon(false);
//
//		List giftList2 = new ArrayList<MarketingActivityDTO>();
//		List items2 = new ArrayList<MarketingActivityDTO>();
//
//		MarketingItemDTO i3 = itemMap.get("1611-16");
//		MarketingItemDTO i4 = itemMap.get("1511-15");
//
//		items2.add(i3);
//		items2.add(i4);
//
//		/*for (MarketingItemDTO item2 : itemSkuQTOList) {
//			MarketingItemDTO dto2 = allItemMap.get(item2.getItemSkuId() + "-"
//					+ item2.getSupplierId());
//			if (dto2 != null) {
//				items2.add(dto2);
//			}
//		}*/
//		favorableInfo2.setActivity(activity2);
//		favorableInfo2.setGiftList(giftList2);
//		favorableInfo2.setItemList(items2);
//		favorableInfo2.setNoPostage(true);
//
//		// 节省金额
//		favorableInfo2.setOfferAmount(50L);
////		result.add(favorableInfo2);
//		return result;
//
//		//TODO
//		// 调用促销模块
//	}
//
//	/**
//	 *
//	 * @param itemList
//	 * @param userId
//	 * @param couponId
//	 * @return
//	 * @throws TradeException
//	 */
//	@Override
//	public List<FavorableInfoDTO> getPromotionItems(List<MarketingItemDTO> itemList,long userId,List<Long> couponId)
//			throws TradeException {
//		List<FavorableInfoDTO> result = new ArrayList<FavorableInfoDTO>();
//		// 活动一
//		FavorableInfoDTO favorableInfo = new FavorableInfoDTO();
//		MarketingActivityDTO activity = new MarketingActivityDTO();
//		activity.setId(1001L);
//		activity.setName("春节大促 满200-10");
//		activity.setIsCoupon(false);
//
//		List<MarketingItemDTO> giftList = new ArrayList<MarketingItemDTO>();
//		List<MarketingItemDTO> items = new ArrayList<MarketingItemDTO>();
//
//		for(MarketingItemDTO item : itemList){
//			MarketingItemDTO dto = itemMap.get(item.getItemSkuId()+ "-" + item.getSupplierId());
//			if(dto != null){
//				dto.setNumber(item.getNumber());
//				items.add(dto);
//			}
//		}
//		// 赠品
//		giftList.add(itemMap.get("1311-13"));
//
//		favorableInfo.setActivity(activity);
//		favorableInfo.setGiftList(giftList);
//		favorableInfo.setItemList(items);
//
//		// 节省金额
//		favorableInfo.setOfferAmount(10L);
////		result.add(favorableInfo);
//		// 活动一
//		FavorableInfoDTO favorableInfo2 = new FavorableInfoDTO();
//		MarketingActivityDTO activity2 = new MarketingActivityDTO();
//
//		activity2.setId(1001L);
//		activity2.setName("新春优惠券满300 - 50");
//		activity2.setIsCoupon(true);
//
//		List giftList2 = new ArrayList<MarketingActivityDTO>();
//		List items2 = new ArrayList<MarketingActivityDTO>();
//
//		for (MarketingItemDTO item2 : itemList) {
//			MarketingItemDTO dto2 = itemMap.get(item2.getItemSkuId() + "-"
//					+ item2.getSupplierId());
//			if (dto2 != null) {
//				items2.add(dto2);
//			}
//		}
//
//		favorableInfo2.setActivity(activity2);
//		favorableInfo2.setGiftList(giftList2);
//		favorableInfo2.setItemList(items2);
//
//		// 节省金额
//		favorableInfo2.setOfferAmount(15L);
////		result.add(favorableInfo2);
//
//		return result;
//
//		//TODO
//		// 调用促销模块
//	}
//
//	/*@Override
//	public List<ItemSkuQTO> getPromotionQueryCondition(List<CartItemDO> cartItems){
//		List<ItemSkuQTO> promotionQueryList = new ArrayList<ItemSkuQTO>();
//
//		for(CartItemDO item : cartItems){
//			if(!item.getIsGift()){// 将已有的是礼品的排除在外
//				MarketingItemDTO item  = new MarketingItemDTO();
//				itemSkuQTO.setNumber(item.getNumber()); //
//				itemSkuQTO.setSupplierId(item.getSupplierId()); //
//				itemSkuQTO.setId(item.getItemSkuId()); //
//				itemSkuQTO.setPrice(item.getUnitPrice()); //单价
//				promotionQueryList.add(itemSkuQTO);
//			}
//		}
//		return promotionQueryList;
//	}*/
//
//}
