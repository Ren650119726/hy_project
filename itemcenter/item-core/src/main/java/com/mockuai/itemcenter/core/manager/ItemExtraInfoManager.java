package com.mockuai.itemcenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemExtraInfoDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemExtraInfoQTO;

@Service
public interface ItemExtraInfoManager {

	/**
	 * 添加商品扩展信息
	 * 
	 * @param itemExtraInfoDO
	 * @return
	 * @throws com.mockuai.itemcenter.core.exception.ItemException
	 */
	public ItemExtraInfoDTO addItemExtraInfo(ItemExtraInfoDTO itemExtraInfoDTO) throws ItemException;

	/**
	 * 添加商品扩展信息
	 * 
	 * @param itemExtraInfoDO
	 * @return
	 * @throws ItemException
	 */
	public boolean updateItemExtraInfo(ItemExtraInfoDTO itemExtraInfoDTO) throws ItemException;

	/**
	 * 查看商品扩展信息
	 * 
	 * @param itemExtraInfoDO
	 * @param sellerId
	 * @return
	 * @throws ItemException
	 */
	public ItemExtraInfoDTO getItemExtraInfo(Long id, Long sellerId) throws ItemException;

	/**
	 * 删除商品扩展信息
	 * 
	 * @param itemExtraInfoDO
	 * @param sellerId
	 * @return
	 * @throws ItemException
	 */
	public boolean deleteItemExtraInfo(Long id, Long sellerId) throws ItemException;


	/**
	 * 查询商品扩展信息列表
	 * 
	 * @param itemExtraInfoQTO
	 * @return
	 * @throws ItemException
	 */
	public List<ItemExtraInfoDTO> queryItemExtraInfo(ItemExtraInfoQTO itemExtraInfoQTO) throws ItemException;

}
