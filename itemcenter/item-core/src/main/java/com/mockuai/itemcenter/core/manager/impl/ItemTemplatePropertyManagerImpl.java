package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemTemplatePropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemTemplatePropertyQTO;
import com.mockuai.itemcenter.core.dao.ItemTemplatePropertyDAO;
import com.mockuai.itemcenter.core.domain.ItemTemplatePropertyDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemTemplatePropertyManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;

@Service
public class ItemTemplatePropertyManagerImpl implements ItemTemplatePropertyManager {
	@Resource
	private ItemTemplatePropertyDAO itemTemplatePropertyDAO;

	@Resource
	private ItemManager itemManager;

	@Override
	public Long addItemProperty(ItemTemplatePropertyDTO itemPropertyDTO) throws ItemException {
		try {
			// 验证itemPropertyDTO内的属性
			validateFields4Add(itemPropertyDTO);
			ItemTemplatePropertyDO itemPropertyDO = new ItemTemplatePropertyDO();
			ItemUtil.copyProperties(itemPropertyDTO, itemPropertyDO);// DTO转DO
			Long newInsertedId = itemTemplatePropertyDAO.addItemProperty(itemPropertyDO);// 新增的记录返回的ID
			return newInsertedId;
		} catch (Exception e) {
			throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public boolean updateItemProperty(ItemTemplatePropertyDTO itemPropertyDTO) throws ItemException {
		// 验证参数
		vaidateFields4Update(itemPropertyDTO);
		ItemTemplatePropertyDO itemPropertyDO = new ItemTemplatePropertyDO();
		ItemUtil.copyProperties(itemPropertyDTO, itemPropertyDO);
		int num = itemTemplatePropertyDAO.updateItemProperty(itemPropertyDO);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update itemProperty error-->primary id:"
					+ itemPropertyDTO.getId() + " sellerId:" + itemPropertyDTO.getSellerId());
		}
	}

	@Override
	public ItemTemplatePropertyDTO getItemProperty(Long id, Long sellerId) throws ItemException {
		ItemTemplatePropertyDO itemPropertyDO = itemTemplatePropertyDAO.getItemProperty(id, sellerId);
		if (itemPropertyDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table itemProperty-->id:"
							+ id + " sellerId:" + sellerId);
		}
		ItemTemplatePropertyDTO itemPropertyDTO = new ItemTemplatePropertyDTO();
		ItemUtil.copyProperties(itemPropertyDO, itemPropertyDTO);
		return itemPropertyDTO;
	}

	@Override
	public boolean deleteItemProperty(Long id, Long sellerId) throws ItemException {
		int num = itemTemplatePropertyDAO.deleteItemProperty(id, sellerId);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table itemProperty-->id:"
							+ id + " sellerId:" + sellerId);
		}
	}

	private void validateFields4Add(ItemTemplatePropertyDTO itemPropertyDTO) throws ItemException {
		// 1.验证supplier_id
		// 2.验证item_id
		if (itemPropertyDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemPropertyDTO is null");
		}
		if (itemPropertyDTO.getName() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "name is null");
		}
		if (itemPropertyDTO.getValue() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "value is null");
		}
		// 验证item_id
		if (itemPropertyDTO.getItemTemplateId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemTemplateId is null");
		}
		// TODO 验证supplier_id
		if (itemPropertyDTO.getSellerId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
		}
//		Long sellerId = itemPropertyDTO.getSupplierId();// 供应商ID
//		Long itemId = itemPropertyDTO.getItemId();
//		ItemDTO itemDTO = itemManager.getItem(itemId, sellerId);
//		if (itemDTO == null) {
//			throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "itemId  is invalid");
//		}
	}

	private void vaidateFields4Update(ItemTemplatePropertyDTO itemTemplatePropertyDTO) throws ItemException {
		if (itemTemplatePropertyDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemTemplatePropertyDTO is null");
		}
		if (itemTemplatePropertyDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemTemplateProperty Id is null");
		}
		// TODO 验证supplier_id
		if (itemTemplatePropertyDTO.getSellerId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "sellerId is null");
		}
	}

	public List<ItemTemplatePropertyDTO> queryItemProperty(ItemTemplatePropertyQTO itemPropertyQTO) throws ItemException {
		List<ItemTemplatePropertyDO> list = itemTemplatePropertyDAO.queryItemProperty(itemPropertyQTO);
		List<ItemTemplatePropertyDTO> itemPropertyDTOList = new ArrayList<ItemTemplatePropertyDTO>();// 需要返回的DTO列表
		for (ItemTemplatePropertyDO itemPropertyDO : list) {
			ItemTemplatePropertyDTO dto = new ItemTemplatePropertyDTO();
			ItemUtil.copyProperties(itemPropertyDO, dto);
			itemPropertyDTOList.add(dto);
		}
		return itemPropertyDTOList;
	}
	
	public int deleteByItemId(Long itemTemplateId,Long supplierId)throws ItemException{
		ItemTemplatePropertyQTO qto = new ItemTemplatePropertyQTO();
		qto.setItemTemplateId(itemTemplateId);
		qto.setSupplierId(supplierId);
		return this.itemTemplatePropertyDAO.deleteByItemId(qto);
	}
	

}
