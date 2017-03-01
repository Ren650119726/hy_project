package com.mockuai.itemcenter.core.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.core.domain.ItemPropertyTmplDO;
import com.mockuai.itemcenter.core.exception.ItemException;

@Service
public interface ItemPropertyTmplManager {

	/**
	 * 添加商品属性模板
	 * 
	 * @param ItemPropertyTmplDO
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	public Long addItemPropertyTmpl(ItemPropertyTmplDTO itemPropertyTmplDTO) throws ItemException;

	/**
	 * 添加商品属性模板
	 * 
	 * @param ItemPropertyTmplDO
	 * @return
	 * @throws ItemException
	 */
	public boolean updateItemPropertyTmpl(ItemPropertyTmplDTO itemPropertyTmplDTO) throws ItemException;

	/**
	 * 查看商品属性模板
	 * 
	 * @param ItemPropertyTmplDO
	 * @return
	 * @throws ItemException
	 */
	public ItemPropertyTmplDTO getItemPropertyTmpl(Long id) throws ItemException;

	/**
	 * 删除商品属性模板
	 * 
	 * @param ItemPropertyTmplDO
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteItemPropertyTmpl(Long id) throws ItemException;


	/**
	 * 查询商品属性模板列表
	 * 
	 * @param itemPropertyTmplQTO
	 * @return
	 * @throws ItemException
	 */
	public List<ItemPropertyTmplDO> queryItemPropertyTmpl(ItemPropertyTmplQTO itemPropertyTmplQTO)
			throws ItemException;

	/**
	 * 根据类目查询属性列表及其基本值
	 * 
	 * @param itemPropertyTmplQTO
	 * @return
	 * @throws ItemException
	 */
	public List<ItemPropertyTmplDTO> queryItemPropertyTmplWithValue(ItemPropertyTmplQTO itemPropertyTmplQTO)
			throws ItemException;
	
}


