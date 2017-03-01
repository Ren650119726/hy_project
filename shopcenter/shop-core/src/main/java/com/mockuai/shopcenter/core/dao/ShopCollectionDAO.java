package com.mockuai.shopcenter.core.dao;

import com.mockuai.shopcenter.core.domain.ShopCollectionDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.domain.qto.ShopCollectionQTO;

import java.util.List;

public interface ShopCollectionDAO {

	/**
	 * 增加商品收藏
	 * 
	 * @param shopCollectionDO
	 * @return
	 */
	Long addShopCollection(ShopCollectionDO shopCollectionDO);

	/**
	 * 根据id获取收藏
	 *
	 * @param sellerId
	 * @return
	 */
	ShopCollectionDO getShopCollection(Long sellerId, Long userId)  throws ShopException;

	/**
	 * 根据商品id删除收藏
	 * @param sellerId
	 * @param userId
	 * @return
	 */
	int deleteShopCollection(Long sellerId, Long userId);

	/**
	 * 返回商品收藏列表
	 * 
	 * @param shopCollectionQTO
	 * @return
	 */
	List<ShopCollectionDO> queryShopCollection(ShopCollectionQTO shopCollectionQTO);

	Integer countShopCollection(ShopCollectionQTO query);
}