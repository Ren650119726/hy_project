package com.mockuai.itemcenter.core.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.core.domain.ItemPropertyTmplDO;

@Service
public interface ItemPropertyTmplDAO {

	/**
	 * 增加商品属性模板
	 * 
	 * @param itemPropertyTmplDO
	 * @return
	 */
	Long addItemPropertyTmpl(ItemPropertyTmplDO itemPropertyTmplDO);

	/**
	 * 根据id获取商品属性模板
	 * 
	 * @param id
	 * @return
	 */
	ItemPropertyTmplDO getItemPropertyTmpl(Long id);

	/**
	 * 根据id删除商品属性模板
	 * 
	 * @param id
	 * @return
	 */
	int deleteItemPropertyTmpl(Long id);

	/**
	 * 更新商品属性模板信息
	 * 
	 * @param itemPropertyTmplDO
	 * @return
	 */
	int updateItemPropertyTmpl(ItemPropertyTmplDO skuPropertyDO);

	/**
	 * 返回商品属性模板列表
	 * 
	 * @param itemPropertyTmplQTO
	 * @return
	 */
	List<ItemPropertyTmplDO> queryItemPropertyTmpl(ItemPropertyTmplQTO itemPropertyTmplQTO);
	
	/**
	 * 根据类目id查询属性列表及其基本值
	 * @param itemPropertyTmplQTO
	 * @return
	 */
	List<ItemPropertyTmplDO> queryItemPropertyTmplWithValue(ItemPropertyTmplQTO itemPropertyTmplQTO);


}