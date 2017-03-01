package com.mockuai.itemcenter.core.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemImageQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

@Service
public interface ItemImageManager {

	/**
	 * 添加商品图片
	 * 
	 * @param itemId
	 * @param sellerId
	 * @param itemImageDTOList
	 * @return
	 * @throws ItemException
	 */
	public List<ItemImageDTO> addItemImage(Long itemId, Long sellerId, List<ItemImageDTO> itemImageDTOList)
			throws ItemException;

	/**
	 * 添加商品图片
	 * 
	 * @param ItemImageDO
	 * @return
	 * @throws ItemException
	 */
	public boolean updateItemImage(ItemImageDTO itemImageDTO) throws ItemException;

	/**
	 * 查看商品图片
	 * 
	 * @param sellerId
	 * @param itemId
	 * @return
	 * @throws ItemException
	 */
	public ItemImageDTO getItemImage(Long id, Long sellerId) throws ItemException;

	/**
	 * 根据itemId删除商品图片
	 * 
	 * @param sellerId
	 * @param itemId
	 * @return
	 * @throws ItemException
	 */
	public int deleteItemImageListByItemId(Long itemId, Long sellerId) throws ItemException;

	/**
	 * 根据主键列表 删除 商品图片
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 */
	int deleteItemImageList(List<ItemImageDTO> itemImageList) throws ItemException;

	/**
	 * 查询商品图片列表
	 * 
	 * @param itemImageQTO
	 * @return
	 * @throws ItemException
	 */
	public List<ItemImageDTO> queryItemImage(ItemImageQTO itemImageQTO) throws ItemException;

	Long trashItemImageListByItemId(Long itemId, Long sellerId) throws ItemException;

	Long recoveryItemImageListByItemId(Long itemId, Long sellerId) throws ItemException;

	Long emptyRecycleBin(Long sellerId, String bizCode) throws ItemException;
}
