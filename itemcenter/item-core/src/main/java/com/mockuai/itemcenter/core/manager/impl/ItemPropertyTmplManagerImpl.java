package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyValueDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.core.dao.ItemPropertyTmplDAO;
import com.mockuai.itemcenter.core.domain.ItemPropertyTmplDO;
import com.mockuai.itemcenter.core.domain.ItemPropertyValueDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import com.mockuai.itemcenter.core.manager.ItemPropertyTmplManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;

@Service
public class ItemPropertyTmplManagerImpl implements ItemPropertyTmplManager {
	@Resource
	private ItemPropertyTmplDAO itemPropertyTmplDAO;

	@Resource
	private ItemCategoryManager itemCategoryManager;

	@Override
	public Long addItemPropertyTmpl(ItemPropertyTmplDTO itemPropertyTmplDTO) throws ItemException {
		//入参检查
		if(itemPropertyTmplDTO == null){
			throw new ItemException(ResponseCode.PARAM_E_MISSING, "itemPropertyTmplDTO is null");
		}

		if(StringUtils.isBlank(itemPropertyTmplDTO.getName())){
			throw new ItemException(ResponseCode.PARAM_E_MISSING, "item property name is null");
		}

		//如果未设置valueType，则设为默认值
		if(itemPropertyTmplDTO.getValueType() == null){
			itemPropertyTmplDTO.setValueType(1);//TODO valueType待重构成枚举常量
		}

		if(itemPropertyTmplDTO.getTmplName() == null){
			itemPropertyTmplDTO.setTmplName("");//TODO 后续考虑是否废弃tmpl_name字段
		}

		try {
			ItemPropertyTmplDO itemPropertyTmplDO = new ItemPropertyTmplDO();
			ItemUtil.copyProperties(itemPropertyTmplDTO, itemPropertyTmplDO);// DTO转DO
			long newInsertedId = itemPropertyTmplDAO.addItemPropertyTmpl(itemPropertyTmplDO);// 新增的记录返回的ID
			return newInsertedId;
		} catch (Exception e) {
			throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public boolean updateItemPropertyTmpl(ItemPropertyTmplDTO itemPropertyTmplDTO) throws ItemException {
		// 验证参数
		verifyUpdatedItemPropertyTmplDTOProperty(itemPropertyTmplDTO);
		ItemPropertyTmplDO itemPropertyTmplDO = new ItemPropertyTmplDO();
		ItemUtil.copyProperties(itemPropertyTmplDTO, itemPropertyTmplDO);
		int num = itemPropertyTmplDAO.updateItemPropertyTmpl(itemPropertyTmplDO);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_UPDATE, "update itemPropertyTmpl error-->primary id:"
							+ itemPropertyTmplDTO.getId());
		}
	}

	@Override
	public ItemPropertyTmplDTO getItemPropertyTmpl(Long id) throws ItemException {
		ItemPropertyTmplDO itemPropertyTmplDO = itemPropertyTmplDAO.getItemPropertyTmpl(id);
		if (itemPropertyTmplDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table itemPropertyTmpl-->id:"
							+ id);
		}
		ItemPropertyTmplDTO itemPropertyTmplDTO = new ItemPropertyTmplDTO();
		ItemUtil.copyProperties(itemPropertyTmplDO, itemPropertyTmplDTO);
		return itemPropertyTmplDTO;
	}

	@Override
	public boolean deleteItemPropertyTmpl(Long id) throws ItemException {
		int num = itemPropertyTmplDAO.deleteItemPropertyTmpl(id);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table itemPropertyTmpl-->id:"
							+ id);
		}
	}


	private void verifyUpdatedItemPropertyTmplDTOProperty(ItemPropertyTmplDTO itemPropertyTmplDTO)
			throws ItemException {
		if (itemPropertyTmplDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "primary id is null");
		}
	}

	public List<ItemPropertyTmplDO> queryItemPropertyTmpl(ItemPropertyTmplQTO itemPropertyTmplQTO)
			throws ItemException {
		List<ItemPropertyTmplDO> list = itemPropertyTmplDAO.queryItemPropertyTmpl(itemPropertyTmplQTO);
		return list ;
		/*
		List<ItemPropertyTmplDTO> itemPropertyTmplDTOList = new ArrayList<ItemPropertyTmplDTO>();// 需要返回的DTO列表
		for (ItemPropertyTmplDO itemPropertyTmplDO : list) {
			ItemPropertyTmplDTO dto = new ItemPropertyTmplDTO();
			ItemUtil.copyProperties(itemPropertyTmplDO, dto);
			itemPropertyTmplDTOList.add(dto);
		}
		return itemPropertyTmplDTOList;
		*/
	}
	
	public List<ItemPropertyTmplDTO> queryItemPropertyTmplWithValue(ItemPropertyTmplQTO itemPropertyTmplQTO)
			throws ItemException{
		List<ItemPropertyTmplDO> list = itemPropertyTmplDAO.queryItemPropertyTmplWithValue(itemPropertyTmplQTO);
		List<ItemPropertyTmplDTO> itemPropertyTmplDTOList = new ArrayList<ItemPropertyTmplDTO>();// 需要返回的DTO列表
		for (ItemPropertyTmplDO itemPropertyTmplDO : list) {
			ItemPropertyTmplDTO dto = new ItemPropertyTmplDTO();
			ItemUtil.copyProperties(itemPropertyTmplDO, dto);
			itemPropertyTmplDTOList.add(dto);
			
			//每个属性对应的值比如 颜色 ： 红色 绿色 蓝色 。。。。
			List<ItemPropertyValueDTO> propertyValues = new ArrayList<ItemPropertyValueDTO>();
			List<ItemPropertyValueDO> propertyValueList = new ArrayList<ItemPropertyValueDO>();
			propertyValueList=itemPropertyTmplDO.getPropertyValues();
			if(!CollectionUtils.isEmpty(propertyValueList)){
				for(ItemPropertyValueDO item: propertyValueList){
					ItemPropertyValueDTO valueDto = new ItemPropertyValueDTO();
					valueDto.setId(item.getId());
					valueDto.setItemPropertyTmplId(item.getItemPropertyTmplId());
					valueDto.setName(item.getName());
					valueDto.setValue(item.getValue());
					propertyValues.add(valueDto);
				}
			}
			dto.setPropertyValues(propertyValues);
		}
		return itemPropertyTmplDTOList;
	}
			
}
