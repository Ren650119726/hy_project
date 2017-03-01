package com.mockuai.itemcenter.core.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

@Service
public interface ItemCategoryManager {

	/**
	 * 添加增加商品类目(ItemCategory)
	 * 
	 * @param itemCategoryDTO
	 * @return
	 * @throws ItemException
	 */
	public ItemCategoryDTO addItemCategory(ItemCategoryDTO itemCategoryDTO) throws ItemException;

	/**
	 * 添加增加商品类目(ItemCategory)
	 * 
	 * @param itemCategoryDTO
	 * @return
	 * @throws ItemException
	 */
	public boolean updateItemCategory(ItemCategoryDTO itemCategoryDTO) throws ItemException;

	/**
	 * 查看增加商品类目(ItemCategory)
	 * 
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public ItemCategoryDTO getItemCategory(Long id) throws ItemException;





	public ItemCategoryDTO getItemCategoryByItemId(Long itemId) throws ItemException;




	/**
	 * 删除增加商品类目(ItemCategory)
	 * 
	 * @param id
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteItemCategory(Long id, String bizCode) throws ItemException;

	/**
	 * 查询增加商品类目(ItemCategory)列表
	 * 
	 * @param itemCategoryQTO
	 * @return
	 * @throws ItemException
	 */
	public List<ItemCategoryDTO> queryItemCategory(ItemCategoryQTO itemCategoryQTO) throws ItemException;

	/**
	 * 查询增加商品叶子类目(ItemCategory)列表
	 *
	 * @return
	 * @throws ItemException
	 */
	public List<ItemCategoryDTO> queryItemLeafCategory(String bizCode) throws ItemException;

	public List<ItemCategoryDTO> queryHierachyCatogaty(String bizCode) throws ItemException;

	public void updateGmtUsed(Long categoryId, String bizCode) throws ItemException;
}
