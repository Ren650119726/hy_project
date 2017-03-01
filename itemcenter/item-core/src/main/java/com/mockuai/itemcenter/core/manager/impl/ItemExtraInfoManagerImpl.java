package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.dao.ItemExtraInfoDAO;
import com.mockuai.itemcenter.core.domain.ItemExtraInfoDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemExtraInfoDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemExtraInfoQTO;
import com.mockuai.itemcenter.core.manager.ItemExtraInfoManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;

@Service
public class ItemExtraInfoManagerImpl implements ItemExtraInfoManager {
	@Resource
	private ItemExtraInfoDAO itemExtraInfoDAO;

	@Resource
	private ItemManager itemManager;

	@Override
	public ItemExtraInfoDTO addItemExtraInfo(ItemExtraInfoDTO itemExtraInfoDTO) throws ItemException {
		try {
			// 验证itemExtraInfoDTO内的属性
			verifyNewAddedItemExtraInfoDTOProperty(itemExtraInfoDTO);
			ItemExtraInfoDO itemExtraInfoDO = new ItemExtraInfoDO();
			ItemUtil.copyProperties(itemExtraInfoDTO, itemExtraInfoDO);// DTO转DO
			long newInsertedId = itemExtraInfoDAO.addItemExtraInfo(itemExtraInfoDO);// 新增的记录返回的ID
			long sellerId = itemExtraInfoDTO.getSupplierId();
			itemExtraInfoDTO = getItemExtraInfo(newInsertedId, sellerId);// 新增加的记录对应的itemExtraInfoDO
			return itemExtraInfoDTO;
		} catch (Exception e) {
			throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public boolean updateItemExtraInfo(ItemExtraInfoDTO itemExtraInfoDTO) throws ItemException {
		// 验证更新的字段属性
		verifyUpdatedItemExtraInfoDTOProperty(itemExtraInfoDTO);
		ItemExtraInfoDO itemExtraInfoDO = new ItemExtraInfoDO();
		ItemUtil.copyProperties(itemExtraInfoDTO, itemExtraInfoDO);
		int num = itemExtraInfoDAO.updateItemExtraInfo(itemExtraInfoDO);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_UPDATE, "update item_extra_info error-->primary id:"
							+ itemExtraInfoDTO.getId() + " sellerId:" + itemExtraInfoDTO.getSupplierId());
		}
	}

	@Override
	public ItemExtraInfoDTO getItemExtraInfo(Long id, Long sellerId) throws ItemException {
		ItemExtraInfoDO itemDO = itemExtraInfoDAO.getItemExtraInfo(id, sellerId);
		if (itemDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "Cannot find requested record");
		}
		ItemExtraInfoDTO itemExtraInfoDTO = new ItemExtraInfoDTO();
		ItemUtil.copyProperties(itemDO, itemExtraInfoDTO);
		return itemExtraInfoDTO;
	}

	@Override
	public boolean deleteItemExtraInfo(Long id, Long sellerId) throws ItemException {
		int num = itemExtraInfoDAO.deleteItemExtraInfo(id, sellerId);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table item_extra_info-->id:"
							+ id + " sellerId:" + sellerId);
		}
	}

	private void verifyNewAddedItemExtraInfoDTOProperty(ItemExtraInfoDTO itemExtraInfoDTO) throws ItemException {
		// TODO 验证ItemExtraInfoDTO字段属性
		// 1.验证supplier_id
		// 2.验证item_id
		if (itemExtraInfoDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemExtraInfoDTO is null");
		}
		// 购物车类型
		if (itemExtraInfoDTO.getCartType() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "cart_type is null");
		}

		// TODO 验证supplier_id
		if (itemExtraInfoDTO.getSupplierId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
		}
		Long sellerId = itemExtraInfoDTO.getSupplierId();// 供应商ID

		// 验证item_id
		if (itemExtraInfoDTO.getItemId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "item_id is null");
		}


	}

	private void verifyUpdatedItemExtraInfoDTOProperty(ItemExtraInfoDTO itemExtraInfoDTO) throws ItemException {
		// TODO 验证ItemExtraInfoDTO字段属性
		if (itemExtraInfoDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemExtraInfoDTO is null");
		}
		if (itemExtraInfoDTO.getSupplierId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
		}
	}

	public List<ItemExtraInfoDTO> queryItemExtraInfo(ItemExtraInfoQTO itemExtraInfoQTO) throws ItemException {
		List<ItemExtraInfoDO> list = itemExtraInfoDAO.queryItemExtraInfo(itemExtraInfoQTO);
		List<ItemExtraInfoDTO> itemExtraInfoDTOList = new ArrayList<ItemExtraInfoDTO>();// 需要返回的DTO列表
		for (ItemExtraInfoDO itemExtraInfoDO : list) {
			ItemExtraInfoDTO dto = new ItemExtraInfoDTO();
			ItemUtil.copyProperties(itemExtraInfoDO, dto);
			itemExtraInfoDTOList.add(dto);
		}
		return itemExtraInfoDTOList;
	}
}
