package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.exception.DAOException;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface UserCartItemDao {

	/** 
	 * 写入用户购物车商品到db
	 * @param record
	 * @return
	 * @throws DAOException
	 */
	public Long addUserCartItem(CartItemDO cartItemDO);

	/**
	 * 删除用户购物车商品
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public int deleteUserCartItem(CartItemDO cartItemDO);
	
	public int deleteUserCartItemByOriginalId(Long originalId);

	/**
	 * 从购物车中移除指定商品SKU
	 * @param userId
	 * @param itemSkuIdList
	 * @return
	 */
	public int removeCartItem(Long userId, List<Long> itemSkuIdList);
	
	/**
	 * 清空该用户ID下所有的购物车商品
	 * @return
	 */
	public int cleanUserCart(Long userId);
	
	/**
	 * 查询用户购物车列表
	 * @param userId
	 * @return
	 */
	public List<CartItemDO> queryUserCartItems(Long userId);
	
//	/**
//	 * 更新用户购物车商品数目
//	 * @param userId
//	 * @return
//	 */
//	public int updateUserCartItem(CartItemDO cartItemDO);
	
	/**
	 * 查询已经加入用户购物车的商品ID
	 * @param user
	 * @return
	 */
	public List<Long> queryUserCartItemId(Long userId);

	/**
	 * 批量写入购物车项
	 * @param cartItemDOList
	 * @return
	 */
	public int addCartItems(List<CartItemDO> cartItemDOList)throws TradeException;
    
    /**
     * 更新用户购物车商品的数量
     * @param cartItemDO
     * @return
     */
	public int updateUserCartItemNumber(CartItemDO cartItemDO);

	/**
	 * 查询用户购物车商品是否已经存在
	 * @param cartItemDO
	 * @return
	 */
	public CartItemDO getCartItemBySkuId(CartItemDO cartItemDO);

	/**
	 * 
	 * @param cartItemDO
	 * @return
	 */
	public CartItemDO getCartItemByShareSkuId(CartItemDO cartItemDO);
	
	public List<CartItemDO> getCartItemListBySkuId(CartItemDO cartItemDO);

	/**
	 * 获取用户的购物车商品条数
	 * @param sessionId
	 * @return
	 */
	public Integer getCartItemsCount(Long userId);

	/**
	 * 获取用户购物车项
	 * @param id
	 * @param userId
	 * @return
	 */
	public CartItemDO getCartItem(CartItemDO cartItem);
	
	public int getCartItemCountByUserAndSellerId(Long itemId,Long userId,Long sellerId);
	
	public int getCartItemCountBySkuIdAndUserId(Long skuId,Long userId,Long sellerId);
	
	public List<CartItemDO> queryCartItemsByOriSkuIdList(Long userId,List<Long> oriSkuList);
	
	public List<CartItemDO> querySupplierCartItems(long userId);

	/**
	 * 更新购物车商品信息
	 * @param cartItemDO
	 * @return
	 */
	public int updateCartItem(CartItemDO cartItemDO);
	
}
