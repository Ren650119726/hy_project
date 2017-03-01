package com.mockuai.itemcenter.core.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.core.domain.ItemCollectionDO;

@Service
public interface ItemCollectionDAO {

	/**
	 * 增加商品收藏
	 * 
	 * @param itemCollectionDO
	 * @return
	 */
	Long addItemCollection(ItemCollectionDO itemCollectionDO);

	/**
	 * 根据id获取收藏
	 *
	 * @param id
	 * @return
	 */
	ItemCollectionDO getItemCollection(Long id, Long userId);

	/**
	 * 根据商品id删除收藏
	 * 
	 * @param id
	 * @return
	 */
	int deleteItemCollectionByItemId(Long itemId,Long userId,Long distributorId);

	/**
	 * 返回商品收藏列表
	 * 
	 * @param itemCollectionQTO
	 * @return
	 */
	List<ItemCollectionDO> queryItemCollection(ItemCollectionQTO itemCollectionQTO);

}