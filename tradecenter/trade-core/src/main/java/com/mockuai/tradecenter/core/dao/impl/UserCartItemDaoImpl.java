package com.mockuai.tradecenter.core.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.dao.UserCartItemDao;
import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;

/**
 * 用户购物车DAO实现类
 * @author cwr
 */
public class UserCartItemDaoImpl extends SqlMapClientDaoSupport implements UserCartItemDao {
	
	@Override
	public Long addUserCartItem(CartItemDO cartItemDO){
       return (Long)this.getSqlMapClientTemplate().insert("user_cart.addUserCartItem", cartItemDO);
	}

	@Override
	public int deleteUserCartItem(CartItemDO cartItemDO){
		return this.getSqlMapClientTemplate().delete("user_cart.deleteUserCartItem",cartItemDO);
	}

	public int removeCartItem(Long userId, List<Long> itemSkuIdList) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("itemSkuIdList", itemSkuIdList);
		return this.getSqlMapClientTemplate().delete("user_cart.removeCartItem",params);
	}

	@Override
	public int cleanUserCart(Long userId){
		return this.getSqlMapClientTemplate().delete("user_cart.cleanUserCart",userId);
	}

	@Override
	public List<CartItemDO> queryUserCartItems(Long userId) {
		return this.getSqlMapClientTemplate().queryForList("user_cart.queryUserCartItems",userId);
	}

//	@Override
//	public int updateUserCartItem(CartItemDO cartItemDO) {
//		return this.getSqlMapClientTemplate().update("user_cart.updateUserCartItem",cartItemDO);
//	}

	@Override
	public List<Long> queryUserCartItemId(Long userId) {
		return this.getSqlMapClientTemplate().queryForList("user_cart.queryUserCartItemId",userId);
	}

	@Override
	public int addCartItems(List<CartItemDO> cartItemDOList)throws TradeException{
		int result = 0;
		try {
			this.getSqlMapClient().startBatch();
			for (CartItemDO item : cartItemDOList) {
				this.getSqlMapClientTemplate().insert("user_cart.addUserCartItem",item);
			}
			result = this.getSqlMapClient().executeBatch();
		} catch (SQLException e) {
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
		}
		return result;
	}

	
	@Override
	public int updateUserCartItemNumber(CartItemDO cartItemDO){
		return this.getSqlMapClientTemplate().update("user_cart.updateUserCartItemNumber",cartItemDO);
	}
 
	@Override
	public CartItemDO getCartItemBySkuId(CartItemDO cartItemDO){
		return (CartItemDO)this.getSqlMapClientTemplate().queryForObject("user_cart.getCartItemBySkuId",cartItemDO);
	}
	 
	@Override
	public CartItemDO getCartItemByShareSkuId(CartItemDO cartItemDO){
		return (CartItemDO)this.getSqlMapClientTemplate().queryForObject("user_cart.getCartItemByShareSkuId",cartItemDO);
	}
	
	@SuppressWarnings("unchecked")
	public List<CartItemDO> getCartItemListBySkuId(CartItemDO cartItemDO){
		return this.getSqlMapClientTemplate().queryForList("user_cart.getCartItemBySkuId",cartItemDO);
	}

	@Override
	public Integer getCartItemsCount(Long userId){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("user_cart.getCartItemsCount",userId);
	}
		
	@Override
	public CartItemDO getCartItem(CartItemDO cartItem){
		return (CartItemDO) this.getSqlMapClientTemplate().queryForObject("user_cart.getCartItem", cartItem);
	}

	@Override
	public int getCartItemCountByUserAndSellerId(Long itemId, Long userId, Long sellerId) {
		CartItemDO cartItemDO = new CartItemDO();
		cartItemDO.setItemId(itemId);
		cartItemDO.setUserId(userId);
		cartItemDO.setSellerId(sellerId);
		return (Integer)this.getSqlMapClientTemplate().queryForObject("user_cart.getCartItemCountByUserAndSellerId",cartItemDO);
	}

	@Override
	public int getCartItemCountBySkuIdAndUserId(Long skuId, Long userId,Long sellerId) {
		CartItemDO query = new CartItemDO();
		query.setItemSkuId(skuId);
		query.setUserId(userId);
		query.setSellerId(sellerId);
		return (Integer)this.getSqlMapClientTemplate().queryForObject("user_cart.getCartItemCountBySkuIdAndUserId",query);
	}

	@Override
	public int deleteUserCartItemByOriginalId(Long originalId) {
		CartItemDO cartItemDO = new CartItemDO();
		cartItemDO.setOriginalId(originalId);
		return this.getSqlMapClientTemplate().delete("user_cart.deleteUserCartItemByOriginalId",cartItemDO);
	}

	@Override
	public List<CartItemDO> queryCartItemsByOriSkuIdList(Long userId, List<Long> oriSkuList) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("itemSkuIdList", oriSkuList);
		return this.getSqlMapClientTemplate().queryForList("user_cart.queryCartItemsByOriSkuIdList",params);
	}

	@Override
	public List<CartItemDO> querySupplierCartItems(long userId) {
		return this.getSqlMapClientTemplate().queryForList("user_cart.querySupplierCartItems",userId);
	}

	@Override
	public int updateCartItem(CartItemDO cartItemDO) {
		return this.getSqlMapClientTemplate().update("user_cart.updateCartItem",cartItemDO);
	}
}