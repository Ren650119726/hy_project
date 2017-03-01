//package com.mockuai.tradecenter.core.manager;
//
//import java.util.List;
//
//import com.mockuai.tradecenter.common.domain.FavorableInfoDTO;
//import com.mockuai.tradecenter.common.domain.MarketingItemDTO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//
///**
// * 促销模块的调用接口处理类
// * @author cwr
// */
//public interface PromotionPlatformManager {
//
//	/**
//	 * 根据购物车商品列表调用促销平台查询信息
//	 * @return
//	 */
//	public List<FavorableInfoDTO> getPromotionInfo(List<MarketingItemDTO> promotionQueryList,int source)throws TradeException;
//
//	/**
//	 * 构造促销平台的查询条件
//	 * @return
//	 */
//	//public List<ItemSkuQTO> getPromotionQueryCondition(List<CartItemDO> cartItems);
//
//	/**
//	 * 下单时候根据商品列表、优惠券号计算优惠信息
//	 * @param itemSkuQTOList
//	 * @return
//	 */
//	public List<FavorableInfoDTO> getPromotionItems(List<MarketingItemDTO> itemSkuQTOList,long userId,
//													List<Long> couponId)throws TradeException;
//
//}
