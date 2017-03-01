package com.mockuai.itemcenter.core.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemCollectionDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.core.dao.ItemCollectionDAO;
import com.mockuai.itemcenter.core.exception.ItemException;


@Service
public interface ItemCollectionManager {

	/**
	 * 添加商品收藏
	 * 
	 * @param itemCollectionDTO
	 * @return
	 * @throws ItemException
	 * @throws Exception 
	 */
	public ItemCollectionDTO addItemCollection(ItemCollectionDTO itemCollectionDTO) throws ItemException;

	/**
	 * 查看商品收藏
	 *
	 * @param itemCollectionDO
	 * @return
	 * @throws ItemException
	 */
	public ItemCollectionDTO getItemCollection(Long id, Long userId) throws ItemException;

	/**
	 * 删除商品收藏
	 * 
	 * @param itemCollectionDO
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteItemCollectionByItemId(Long itemId,Long userId,Long distributorId) throws ItemException;

	/**
	 * 查询商品收藏列表
	 * 
	 * @param itemCollectionQTO
	 * @return
	 * @throws ItemException
	 */
	public List<ItemCollectionDTO> queryItemCollection(ItemCollectionQTO itemCollectionQTO) throws ItemException;

}
