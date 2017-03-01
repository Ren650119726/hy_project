package com.mockuai.itemcenter.core.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.ItemImageQTO;
import com.mockuai.itemcenter.core.domain.ItemImageDO;

@Service
public interface ItemImageDAO {

	/**
	 * 增加商品图片
	 * 
	 * @param itemImageDO
	 * @return
	 */
	Long addItemImage(ItemImageDO itemImageDO);

	/**
	 * 根据id获取商品图片
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 */
	ItemImageDO getItemImage(Long id, Long sellerId);

	/**
	 * 根据主键列表 删除 商品图片
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 */
	int deleteItemImage(Long id, Long sellerId);

	/**
	 * 根据商品ID和供应商ID删除图片列表
	 * 
	 * @param sellerId
	 * @param itemId
	 * @return
	 */
	int deleteItemImageListByItemId(Long itemId, Long sellerId);

	/**
	 * 更新商品图片信息
	 * 
	 * @param itemImageDO
	 * @return
	 */
	int updateItemImage(ItemImageDO itemPropertyDO);

	/**
	 * 返回商品图片列表
	 * 
	 * @param itemImageQTO
	 * @return
	 */
	List<ItemImageDO> queryItemImage(ItemImageQTO itemImageQTO);

	Long trashItemImageListByItemId(ItemImageDO query);

	Long recoveryItemImageListByItemId(ItemImageDO query);

	Long emptyRecycleBin(ItemImageDO query);
}