package com.mockuai.itemcenter.core.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.ItemExtraInfoQTO;
import com.mockuai.itemcenter.core.domain.ItemExtraInfoDO;

@Service
public interface ItemExtraInfoDAO {

	/**
	 * 增加商品扩展信息
	 * 
	 * @param itemExtraInfoDO
	 * @return
	 */
	Long addItemExtraInfo(ItemExtraInfoDO itemExtraInfoDO);

	/**
	 * 根据id获取商品扩展信息
	 * 
	 * @param id
	 * @return
	 */
	ItemExtraInfoDO getItemExtraInfo(Long id,Long sellerId);

	/**
	 * 根据id删除商品扩展信息
	 * 
	 * @param sellerId
	 * @param id
	 * @return
	 */
	int deleteItemExtraInfo(Long id, Long sellerId);

	/**
	 * 更新商品扩展信息信息
	 * 
	 * @param itemExtraInfoDO
	 * @return
	 */
	int updateItemExtraInfo(ItemExtraInfoDO itemExtraInfoDO);

	/**
	 * 返回商品扩展信息列表
	 * 
	 * @param itemExtraInfoQTO
	 * @return
	 */
	List<ItemExtraInfoDO> queryItemExtraInfo(ItemExtraInfoQTO itemExtraInfoQTO);

}