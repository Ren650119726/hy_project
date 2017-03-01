package com.mockuai.shopcenter.core.manager;

import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.domain.dto.ShopCollectionDTO;
import com.mockuai.shopcenter.domain.qto.ShopCollectionQTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ShopCollectionManager {

	/**
	 * 添加商品收藏
	 * 
	 * @param ShopCollectionDTO
	 * @return
	 * @throws ShopException
	 * @throws Exception 
	 */
	public ShopCollectionDTO addShopCollection(ShopCollectionDTO ShopCollectionDTO) throws ShopException;

	/**
	 * 查看商品收藏
	 *
	 * @param
	 * @return
	 * @throws ShopException
	 */
	public ShopCollectionDTO getShopCollection(Long sellerId, Long userId) throws ShopException;

	/**
	 * 删除商品收藏
	 * 
	 * @param
	 * @return
	 * @throws ShopException
	 */
	public boolean deleteShopCollection(Long sellerId, Long userId) throws ShopException;

	/**
	 * 查询商品收藏列表
	 * 
	 * @param ShopCollectionQTO
	 * @return
	 * @throws ShopException
	 */
	public List<ShopCollectionDTO> queryShopCollection(ShopCollectionQTO ShopCollectionQTO) throws ShopException;

	Integer countShopCollection(ShopCollectionQTO query) throws ShopException;
}
