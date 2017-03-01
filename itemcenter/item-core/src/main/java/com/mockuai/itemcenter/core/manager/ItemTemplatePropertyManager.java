package com.mockuai.itemcenter.core.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemTemplatePropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemTemplatePropertyQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

@Service
public interface ItemTemplatePropertyManager {

	/**
	 * 添加商品属性
	 * 
	 * @param ItemPropertyDO
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	public Long addItemProperty(ItemTemplatePropertyDTO itemPropertyDTO) throws ItemException;

	/**
	 * 添加商品属性
	 * 
	 * @param ItemPropertyDO
	 * @return
	 * @throws ItemException
	 */
	public boolean updateItemProperty(ItemTemplatePropertyDTO itemPropertyDTO) throws ItemException;

	/**
	 * 查看商品属性
	 * 
	 * @param ItemPropertyDO
	 * @return
	 * @throws ItemException
	 */
	public ItemTemplatePropertyDTO getItemProperty(Long id,Long sellerId) throws ItemException;

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
	public List<ItemTemplatePropertyDTO> queryItemProperty(ItemTemplatePropertyQTO itemPropertyQTO) throws ItemException;
	
	/**
	 * 在修改商品属性时先根据itemId 和 supplierId删除 后在写入
	 * @param itemId
	 * @param supplierId
	 * @return
	 * @throws ItemException
	 */
	public int deleteByItemId(Long itemId,Long supplierId)throws ItemException;

}
