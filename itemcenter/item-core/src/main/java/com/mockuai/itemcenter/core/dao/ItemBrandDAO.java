package com.mockuai.itemcenter.core.dao;

import java.util.List;

import com.mockuai.itemcenter.core.domain.ItemBrandDO;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.ItemBrandQTO;

@Service
public interface ItemBrandDAO {

	/**
	 * 增加商品品牌
	 * 
	 * @param itemBrandDO
	 * @return
	 */
	Long addItemBrand(ItemBrandDO itemBrandDO);

	/**
	 * 根据id获取商品品牌
	 * 
	 * @param id
	 * @return
	 */
	ItemBrandDO getItemBrand(Long id);

	/**
	 * 根据id删除商品品牌
	 * 
	 * @param id
	 * @return
	 */
	int deleteItemBrand(Long id);

	/**
	 * 更新商品品牌信息
	 * 
	 * @param itemBrandDO
	 * @return
	 */
	int updateItemBrand(ItemBrandDO itemBrandDO);

	/**
	 * 返回商品品牌列表
	 * 
	 * @param itemBrandQTO
	 * @return
	 */
	List<ItemBrandDO> queryItemBrand(ItemBrandQTO itemBrandQTO);

}