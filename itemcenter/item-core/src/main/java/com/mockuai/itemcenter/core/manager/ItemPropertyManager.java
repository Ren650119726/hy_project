package com.mockuai.itemcenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;

@Service
public interface ItemPropertyManager {

	/**
	 * 添加商品属性
	 * 
	 * @param ItemPropertyDO
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	 ItemPropertyDTO addItemProperty(ItemPropertyDTO itemPropertyDTO) throws ItemException;

    /**
     * 批量添加商品属性
      * @param itemPropertyList
     * @throws ItemException
     */
     void addItemProperty(List<ItemPropertyDTO> itemPropertyList) throws ItemException ;


        /**
         * 添加商品属性
         *
         * @param ItemPropertyDO
         * @return
         * @throws ItemException
         */
	public boolean updateItemProperty(ItemPropertyDTO itemPropertyDTO) throws ItemException;

	/**
	 * 查看商品属性
	 * 
	 * @param ItemPropertyDO
	 * @return
	 * @throws ItemException
	 */
	public ItemPropertyDTO getItemProperty(Long id,Long sellerId) throws ItemException;

	/**
	 * 删除商品属性
	 * 
	 * @param ItemPropertyDO
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteItemProperty(Long id,Long sellerId) throws ItemException;


	/**
	 * 查询商品属性列表
	 * 
	 * @param itemPropertyQTO
	 * @return
	 * @throws ItemException
	 */
	public List<ItemPropertyDTO> queryItemProperty(ItemPropertyQTO itemPropertyQTO) throws ItemException;
	
	/**
	 * 在修改商品属性时先根据itemId 和 supplierId删除 后在写入
	 * @param itemId
	 * @param supplierId
	 * @return
	 * @throws ItemException
	 */
	public int deleteByItemId(Long itemId,Long supplierId)throws ItemException;

	Long trashByItemId(Long itemId, Long sellerId);

	Long recoveryByItemId(Long itemId, Long sellerId);

	Long emptyRecycleBin(Long sellerId, String bizCode);

    Integer countBy(ItemPropertyQTO itemPropertyQTO);
}
