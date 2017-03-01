//package com.mockuai.tradecenter.core.manager;
//
//import java.util.List;
//
//import com.mockuai.tradecenter.common.domain.CartDTO;
//import com.mockuai.tradecenter.common.domain.CartItemDTO;
//import com.mockuai.tradecenter.common.domain.FavorableInfoDTO;
//import com.mockuai.tradecenter.core.domain.CartItemDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//
//public interface CartItemManager {
//
//	/**
//	 * 加入购物车
//	 * @param cartItemDO
//	 * @return
//	 * @throws TradeException
//	 */
//	public Long addCartItem(CartItemDO cartItemDO) throws TradeException;
//
//	/**
//	 * 根据id移除购物车项
//	 * @param id
//	 * @return
//	 * @throws TradeException
//	 */
//	public int deleteCartItem(Long id) throws TradeException;
//
//	/**
//	 * 根据sessionId清空未登入用户的购物车
//	 * @param sessionId
//	 * @return
//	 * @throws TradeException
//	 */
//	public int cleanCart(String sessionId) throws TradeException;
//
//	/**
//	 * 修改购物车商品数目
//	 * @param id
//	 * @return
//	 * @throws TradeException
//	 */
//	public int updateCartItem(CartItemDO cartItemDO)throws TradeException;
//
//	/**
//	 * 未登入用户根据sessionId列出购物车列表
//	 * @param sessionId
//	 * @return
//	 * @throws TradeException
//	 */
//	public List<CartItemDO> queryCartItems(String sessionId)throws TradeException;
//
//	/**
//	 * 移除购物车中的商品项
//	 * @param sessionId
//	 * @return
//	 * @throws TradeException
//	 */
//	public int deleteGiftItems(String sessionId)throws TradeException;
//
//	/**
//	 * 验证添加购物车商品的字段
//	 * @param cartItem
//	 * @return
//	 */
//	public String validateCartItemFields4Add(CartItemDTO cartItem);
//
//	/**
//	 * 修改购物车的商品数量
//	 * @param cartItemId
//	 * @param number
//	 * @return
//	 */
//	public int updateCartItemNumber(CartItemDO cartItem)throws TradeException;
//
//	/**
//	 * 将DO对象转为DTO对象
//	 * @param cartItem
//	 */
//	public CartItemDTO convertToDto(CartItemDO cartItem);
//
//	/**
//	 * 处理营销平台返回的优惠信息
//	 * @param favorableInfoList
//	 * @return
//	 */
//	public CartDTO handlePromotionInfo(List<FavorableInfoDTO> favorableInfoList,List<CartItemDO> cartItems);
//
//	/**
//	 * 根据sessionId和商品id查询购物车商品
//	 * @param supplierId
//	 * @param sessionId
//	 * @return
//	 */
//	public CartItemDO getCartItemBySkuId(Long itemSkuId,Long supplierId,String sessionId);
//
//	/**
//	 * 获取购物车商品条数
//	 * @param sessinId
//	 * @return
//	 */
//	public Integer getCartItemsCount(String sessionId);
//
//	/**
//	 * 根据id获取购物车项
//	 * @param id
//	 * @return
//	 */
//	public CartItemDO getCartItem(Long id);
//}
