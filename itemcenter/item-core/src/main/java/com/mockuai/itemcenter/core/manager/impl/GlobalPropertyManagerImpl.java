package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.GlobalPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.GlobalPropertyQTO;
import com.mockuai.itemcenter.core.dao.GlobalPropertyDAO;
import com.mockuai.itemcenter.core.dao.GlobalPropertyValueDAO;
import com.mockuai.itemcenter.core.domain.GlobalPropertyDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.GlobalPropertyManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;

@Service
public class GlobalPropertyManagerImpl implements GlobalPropertyManager {
	@Resource
	private GlobalPropertyDAO globalPropertyDAO;
	
	@Resource
	private GlobalPropertyValueDAO globalPropertyValueDAO;

	@Override
	public GlobalPropertyDTO addGlobalProperty(GlobalPropertyDTO globalPropertyDTO) throws ItemException {
		try {
			// 验证globalPropertyDTO内的属性
			verifyNewAddedGlobalPropertyDTOProperty(globalPropertyDTO);
			GlobalPropertyDO globalPropertyDO = new GlobalPropertyDO();
			ItemUtil.copyProperties(globalPropertyDTO, globalPropertyDO);// DTO转DO
			long newInsertedId = globalPropertyDAO.addGlobalProperty(globalPropertyDO);// 新增的记录返回的ID
			globalPropertyDTO = getGlobalProperty(newInsertedId);// 新增加的记录对应的globalPropertyDO
			return globalPropertyDTO;
		} catch (Exception e) {
			throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public boolean updateGlobalProperty(GlobalPropertyDTO globalPropertyDTO) throws ItemException {
		verifyUpdatedGlobalPropertyDTOProperty(globalPropertyDTO);
		GlobalPropertyDO globalPropertyDO = new GlobalPropertyDO();
		ItemUtil.copyProperties(globalPropertyDTO, globalPropertyDO);
		int num = globalPropertyDAO.updateGlobalProperty(globalPropertyDO);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_UPDATE, "update global_property error-->primary id:"
							+ globalPropertyDTO.getId());
		}
	}

	@Override
	public GlobalPropertyDTO getGlobalProperty(Long id) throws ItemException {
		GlobalPropertyDO globalPropertyDO = globalPropertyDAO.getGlobalProperty(id);
		if (globalPropertyDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table global_property-->id:"
							+ id);
		}
		GlobalPropertyDTO globalPropertyDTO = new GlobalPropertyDTO();
		ItemUtil.copyProperties(globalPropertyDO, globalPropertyDTO);
		return globalPropertyDTO;
	}

	@Override
	public boolean deleteGlobalProperty(Long id) throws ItemException {
		int num = this.globalPropertyDAO.deleteGlobalProperty(id);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_DELETE, "delete global_property error-->primary id:" + id);
		}
	}

	private void verifyNewAddedGlobalPropertyDTOProperty(GlobalPropertyDTO globalPropertyDTO) throws ItemException {
		// 验证GlobalPropertyDTO字段属性
		if (globalPropertyDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "globalPropertyDTO is null");
		}
		if (globalPropertyDTO.getPropertyName() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "property_name is null");
		}
		if (globalPropertyDTO.getPropertyType() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "property_type is null");
		}

	}

	private void verifyUpdatedGlobalPropertyDTOProperty(GlobalPropertyDTO globalPropertyDTO) throws ItemException {
		// 验证GlobalPropertyDTO字段属性
		if (globalPropertyDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "globalPropertyDTO is null");
		}
		if (globalPropertyDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "globalProperty id is null");
		}
	}

	public List<GlobalPropertyDTO> queryGlobalProperty(GlobalPropertyQTO globalPropertyQTO) throws ItemException {
		List<GlobalPropertyDO> list = globalPropertyDAO.queryGlobalProperty(globalPropertyQTO);
		List<GlobalPropertyDTO> globalPropertyDTOList = new ArrayList<GlobalPropertyDTO>();// 需要返回的DTO列表
		for (GlobalPropertyDO globalPropertyDO : list) {
			GlobalPropertyDTO dto = new GlobalPropertyDTO();
			ItemUtil.copyProperties(globalPropertyDO, dto);
			globalPropertyDTOList.add(dto);
		}
		return globalPropertyDTOList;
	}
}
