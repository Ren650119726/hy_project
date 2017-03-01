package com.mockuai.itemcenter.core.dao;

import java.util.List;

import com.mockuai.itemcenter.core.domain.ItemCategoryDO;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;

@Service
public interface ItemCategoryDAO {

	/**
	 * 增加商品类目(ItemCategory)
	 * 
	 * @param itemCategoryDO
	 * @return
	 */
	Long addItemCategory(ItemCategoryDO itemCategoryDO);

	/**
	 * 根据id获取商品类目(ItemCategory)
	 * 
	 * @param id
	 * @return
	 */
	ItemCategoryDO getItemCategory(Long id);

	/**
	 * 根据id删除商品类目(ItemCategory)
	 * 
	 * @param id
	 * @return
	 */
	int deleteItemCategory(Long id);

	/**
	 * 更新商品类目(ItemCategory)信息
	 * 
	 * @param itemCategoryDO
	 * @return
	 */
	int updateItemCategory(ItemCategoryDO itemCategoryDO);

	/**
	 * 返回商品类目(ItemCategory)列表
	 * 
	 * @param itemCategoryQTO
	 * @return
	 */
	List<ItemCategoryDO> queryItemCategory(ItemCategoryQTO itemCategoryQTO);

	/**
	 * 返回商品类目(ItemCategory)列表
	 * @return
	 */
	List<ItemCategoryDO> queryItemLeafCategory(String bizCode);

	void updateGmtUsed(ItemCategoryDO itemCategoryDO);
}