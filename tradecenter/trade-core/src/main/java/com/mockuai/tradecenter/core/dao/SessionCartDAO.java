package com.mockuai.tradecenter.core.dao;

import java.util.List;

import com.mockuai.tradecenter.core.domain.SessionCartDO;
import com.mockuai.tradecenter.core.exception.DAOException;
import com.mockuai.tradecenter.core.exception.TradeException;

public interface SessionCartDAO {

	public Long addSessionCartItem(SessionCartDO sessionCartDO);

	public int deleteSessionCartItem(SessionCartDO SessionCartDO);
	
	public int deleteSessionCartItemByOriginalId(Long originalId);

	/**
	 * 从购物车中移除指定商品SKU
	 * @param userId
	 * @param itemSkuIdList
	 * @return
	 */
	public int removeCartItem(String deviceId, List<Long> itemSkuIdList);
	
	
	public int cleanUserCart(String deviceId);
	
	/**
	 * 查询用户购物车列表
	 * @param userId
	 * @return
	 */
	public List<SessionCartDO> querySessionCartItems(String deviceId);
	
	
	
	/**
	 * 批量写入购物车项
	 * @param SessionCartDOList
	 * @return
	 */
	public int addCartItems(List<SessionCartDO> SessionCartDOList)throws TradeException;
    
    /**
     * 更新用户购物车商品的数量
     * @param SessionCartDO
     * @return
     */
	public int updateCartItemNumber(SessionCartDO SessionCartDO);

	/**
	 * 查询用户购物车商品是否已经存在
	 * @param SessionCartDO
	 * @return
	 */
	public SessionCartDO getCartItemBySkuId(SessionCartDO SessionCartDO);
	
	public List<SessionCartDO> getCartItemListBySkuId(SessionCartDO SessionCartDO);

	/**
	 * 获取用户的购物车商品条数
	 * @param sessionId
	 * @return
	 */
	public Integer getCartItemsCount(String deviceId);

	/**
	 * 获取用户购物车项
	 * @param id
	 * @param userId
	 * @return
	 */
	public SessionCartDO getCartItem(SessionCartDO cartItem);
	
	public int getCartItemCountByUserAndSellerId(Long itemId,Long userId,Long sellerId);
	
	public int getCartItemCountBySkuIdAndUserId(Long skuId,Long userId,Long sellerId);
	
	public List<SessionCartDO> queryCartItemsByOriSkuIdList(Long userId,List<Long> oriSkuList);
	
}
