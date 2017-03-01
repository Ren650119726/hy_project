package com.mockuai.tradecenter.core.manager;

import java.util.List;

import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.domain.CartItemServiceDO;
import com.mockuai.tradecenter.core.exception.TradeException;

/**
 * 已登入用户购物车操作类 对应于表user_cart
 * @author cwr
 */
public interface UserCartItemManager {
	
	/**
	 * 已登入用户添加购物车
	 * @param userCartItem
	 * @return
	 * @throws tradeException
	 */
	public Long addUserCartItem(CartItemDO cartItemDO,List<CartItemServiceDO> cartItemServiceDOList)throws TradeException;
	
	/**
	 * 购物车中移除商品
	 * @param userCartItemDO
	 * @return
	 * @throws TradeException
	 */
	public int deleteUserCartItem(CartItemDO cartItemDO)throws TradeException;

	/**
	 * 从购物车中移除指定sku记录
	 * @param userId
	 * @param itemSkuIdList
	 * @return
	 * @throws TradeException
	 */
	public int removeCartItem(Long userId, List<Long> itemSkuIdList) throws TradeException;
	
	public int removeCartItemByOrinalSkuId(Long userId,List<Long> oriIdList)throws TradeException;
	
	/**
	 * 清空已登入用户的购物车
	 * @param userId
	 * @return
	 * @throws TradeException
	 */
	public int cleanUserCart(Long userId)throws TradeException;
	
//	/**
//	 *  修改购物车商品数量
//	 * @param number
//	 * @param id
//	 * @return
//	 * @throws TradeException
//	 */
//	public int updateUserCartItem(CartItemDO cartItemDO)throws TradeException;
	
	/**
	 * 列出用户购物车列表
	 * @param number
	 * @param id
	 * @param userId
	 * @return
	 * @throws TradeException
	 */
	public List<CartItemDO> queryUserCartItems(Long userId)throws TradeException;
	
	/**
	 * 查询用户已经加入购物车的商品ID
	 * @param userId
	 * @return
	 * @throws TradeException
	 */
	public List<Long> queryUserCartItemId(Long userId)throws TradeException;
	
	/**
	 * 批量写入购物车商品
	 * @param cartItemDOList
	 * @return
	 * @throws TradeException
	 */
	public int addBatchUserCartItem(List<CartItemDO> cartItemDOList)throws TradeException;
	
//	/**
//	 * 移除用户购物车中的赠品
//	 * @param userId
//	 * @return
//	 */
//    public int deleteGiftItems(Long userId)throws TradeException;
	
	/**
	 * 验证购物车商品字段
	 * @param cartItem
	 * @return
	 */
	public String validateCartItemFields4Add(CartItemDTO cartItem);
	
	public String validateCartItemDTOsFields4Add(List<CartItemDTO> cartItem);

	/**
	 * 更新数量
	 * @param cartItemDO
	 * @return
	 * @throws TradeException
	 */
	public int updateUserCartItemNumber(CartItemDO cartItem,List<CartItemServiceDO> cartItemServiceDOList)throws TradeException;

	/**
	 * 查询用户购物车是否已经有该商品
	 * @param itemSkuId
	 * @param distributorId
	 * @param userId
	 * @return
	 */
	public CartItemDO getCartItemBySkuId(Long itemSkuId,Long distributorId,Long userId);

    CartItemDO getCartItemBySkuId(Long itemSkuId, Long userId);

    /**
	 * 查询购物车是否存在该sku
	 * @param itemSkuId
	 * @param distributorId
	 * @param userId
	 * @return
	 */
	public CartItemDO getCartItemByShareSkuId(Long itemSkuId,Long shareUserId,Long userId);
	

	public int getCartItemCountByUserAndSellerId(Long itemId,Long userId,Long sellerId)throws TradeException;
	
	/**
	 * @param skuId
	 * @param userId
	 * @return
	 * @throws TradeException
	 */
	public int getCartItemCountBySkuIdAndUserId(Long skuId,Long userId,Long sellerId)throws TradeException;

	/**
	 * 购物车商品条数
	 * @param userId
	 * @return
	 */
	public Integer getCartItemsCount(Long userId);
	
	/**
	 * 根据id获取购物车项
	 * @param id
	 * @return
	 */
	public CartItemDO getCartItem(Long id,Long userId)throws TradeException;

	
	public List<CartItemDO> querySupplierCartItems(long userId) throws TradeException;

	/**
	 * 更新购物车商品信息
	 * @param cartItem
	 * @return
	 * @throws TradeException
	 */
	public int updateCartItem(CartItemDO cartItem) throws TradeException;
}
