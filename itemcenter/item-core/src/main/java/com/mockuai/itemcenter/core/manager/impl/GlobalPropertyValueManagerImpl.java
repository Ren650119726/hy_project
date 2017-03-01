package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.dao.GlobalPropertyValueDAO;
import com.mockuai.itemcenter.core.domain.GlobalPropertyValueDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.GlobalPropertyValueManager;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.GlobalPropertyDTO;
import com.mockuai.itemcenter.common.domain.dto.GlobalPropertyValueDTO;
import com.mockuai.itemcenter.common.domain.qto.GlobalPropertyValueQTO;
import com.mockuai.itemcenter.core.manager.GlobalPropertyManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;

@Service
public class GlobalPropertyValueManagerImpl implements GlobalPropertyValueManager {
	@Resource
	private GlobalPropertyValueDAO globalPropertyValueDAO;

	@Resource
	private GlobalPropertyManager globalPropertyManager;

	@Override
	public GlobalPropertyValueDTO addGlobalPropertyValue(GlobalPropertyValueDTO globalPropertyValueDTO)
			throws ItemException {
		try {
			// 验证globalPropertyValueDTO内的属性
			verifyNewAddedGlobalPropertyValueDTOProperty(globalPropertyValueDTO);
			GlobalPropertyValueDO globalPropertyValueDO = new GlobalPropertyValueDO();
			ItemUtil.copyProperties(globalPropertyValueDTO, globalPropertyValueDO);// DTO转DO
			long newInsertedId = globalPropertyValueDAO.addGlobalPropertyValue(globalPropertyValueDO);// 新增的记录返回的ID
			globalPropertyValueDTO = getGlobalPropertyValue(newInsertedId);// 新增加的记录对应的globalPropertyValueDO
			return globalPropertyValueDTO;
		} catch (Exception e) {
			throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public boolean updateGlobalPropertyValue(GlobalPropertyValueDTO globalPropertyValueDTO) throws ItemException {
		// 验证参数
		verifyUpdatedGlobalPropertyValueDTOProperty(globalPropertyValueDTO);
		GlobalPropertyValueDO globalPropertyValueDO = new GlobalPropertyValueDO();
		ItemUtil.copyProperties(globalPropertyValueDTO, globalPropertyValueDO);
		int num = globalPropertyValueDAO.updateGlobalPropertyValue(globalPropertyValueDO);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_UPDATE, "update global_property_value error-->primary id:"
							+ globalPropertyValueDTO.getId());
		}
	}

	@Override
	public GlobalPropertyValueDTO getGlobalPropertyValue(Long id) throws ItemException {
		GlobalPropertyValueDO globalPropertyValueDO = globalPropertyValueDAO.getGlobalPropertyValue(id);
		if (globalPropertyValueDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table global_property_value-->id:"
							+ id);
		}
		GlobalPropertyValueDTO globalPropertyValueDTO = new GlobalPropertyValueDTO();
		ItemUtil.copyProperties(globalPropertyValueDO, globalPropertyValueDTO);
		return globalPropertyValueDTO;
	}

	@Override
	public boolean deleteGlobalPropertyValue(Long id) throws ItemException {
		int num = globalPropertyValueDAO.deleteGlobalPropertyValue(id);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_DELETE, "delete global_property_value error-->primary id:"
							+ id);
		}
	}

	private void verifyNewAddedGlobalPropertyValueDTOProperty(GlobalPropertyValueDTO globalPropertyValueDTO)
			throws ItemException {
		if (globalPropertyValueDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "globalPropertyValueDTO is null");
		}
		if (globalPropertyValueDTO.getGlobalPropertyId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "global_property_id  is null");
		}
		if (globalPropertyValueDTO.getPropertyValue() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "property_value  is null");
		}
		// 验证GlobalPropertyId()是否存在
		Long globalPropertyId = globalPropertyValueDTO.getGlobalPropertyId();
		GlobalPropertyDTO globalPropertyDTO = globalPropertyManager.getGlobalProperty(globalPropertyId);
		if (globalPropertyDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "global_property_id is invalid");
		}

	}

	private void verifyUpdatedGlobalPropertyValueDTOProperty(GlobalPropertyValueDTO globalPropertyValueDTO)
			throws ItemException {
		if (globalPropertyValueDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "globalPropertyValueDTO is null");
		}
		if (globalPropertyValueDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "globalPropertyValue id is null");
		}
	}

	public List<GlobalPropertyValueDTO> queryGlobalPropertyValue(GlobalPropertyValueQTO globalPropertyValueQTO)
			throws ItemException {
		List<GlobalPropertyValueDO> list = globalPropertyValueDAO.queryGlobalPropertyValue(globalPropertyValueQTO);
		List<GlobalPropertyValueDTO> globalPropertyValueDTOList = new ArrayList<GlobalPropertyValueDTO>();// 需要返回的DTO列表
		for (GlobalPropertyValueDO globalPropertyValueDO : list) {
			GlobalPropertyValueDTO dto = new GlobalPropertyValueDTO();
			ItemUtil.copyProperties(globalPropertyValueDO, dto);
			globalPropertyValueDTOList.add(dto);
		}
		return globalPropertyValueDTOList;
	}

	@Override
	public int deleteGlobalPropertyValueListByGlobalPropertyId(Long globalPropertyId) throws ItemException {
		int num = globalPropertyValueDAO.deleteGlobalPropertyValueListByGlobalPropertyId(globalPropertyId);
		return num;
	}
}
