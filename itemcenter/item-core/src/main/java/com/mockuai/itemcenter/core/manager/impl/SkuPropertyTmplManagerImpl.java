package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyValueDTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;
import com.mockuai.itemcenter.core.dao.SkuPropertyTmplDAO;
import com.mockuai.itemcenter.core.domain.SkuPropertyTmplDO;
import com.mockuai.itemcenter.core.domain.SkuPropertyValueDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyTmplManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;

@Service
public class SkuPropertyTmplManagerImpl implements SkuPropertyTmplManager {
	@Resource
	private SkuPropertyTmplDAO skuPropertyTmplDAO;

	@Resource
	private ItemCategoryManager itemCategoryManager;

	@Override
	public Long addSkuPropertyTmpl(SkuPropertyTmplDTO skuPropertyTmplDTO) throws ItemException {
		try {
			// 验证skuPropertyTmplDTO内的属性
			verifyNewAddedSkuPropertyTmplDTOProperty(skuPropertyTmplDTO);
			SkuPropertyTmplDO skuPropertyTmplDO = new SkuPropertyTmplDO();
			ItemUtil.copyProperties(skuPropertyTmplDTO, skuPropertyTmplDO);// DTO转DO
			long newInsertedId = skuPropertyTmplDAO.addSkuPropertyTmpl(skuPropertyTmplDO);// 新增的记录返回的ID
			//skuPropertyTmplDTO = getSkuPropertyTmpl(newInsertedId);// 新增加的记录对应的skuPropertyTmplDO
			return newInsertedId;
		} catch (ItemException e) {
			throw e;
		}
	}

	@Override
	public boolean updateSkuPropertyTmpl(SkuPropertyTmplDTO skuPropertyTmplDTO) throws ItemException {
		verifyUpdatedSkuPropertyTmplDTOProperty(skuPropertyTmplDTO);
		SkuPropertyTmplDO skuPropertyTmplDO = new SkuPropertyTmplDO();
		ItemUtil.copyProperties(skuPropertyTmplDTO, skuPropertyTmplDO);
		int num = skuPropertyTmplDAO.updateSkuPropertyTmpl(skuPropertyTmplDO);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_UPDATE, "update skuPropertyTmpl error-->primary id:"
							+ skuPropertyTmplDTO.getId());
		}
	}

	@Override
	public SkuPropertyTmplDTO getSkuPropertyTmpl(Long id) throws ItemException {
		SkuPropertyTmplDO skuPropertyTmplDO = skuPropertyTmplDAO.getSkuPropertyTmpl(id);
		if (skuPropertyTmplDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table skuPropertyTmpl-->id:"
							+ id);
		}
		SkuPropertyTmplDTO skuPropertyTmplDTO = new SkuPropertyTmplDTO();
		ItemUtil.copyProperties(skuPropertyTmplDO, skuPropertyTmplDTO);
		return skuPropertyTmplDTO;
	}

	@Override
	public boolean deleteSkuPropertyTmpl(Long id) throws ItemException {
		SkuPropertyTmplDO skuPropertyTmplDO = new SkuPropertyTmplDO();
		skuPropertyTmplDO.setId(id);
		int num = skuPropertyTmplDAO.deleteSkuPropertyTmpl(id);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table skuPropertyTmpl-->id:"
							+ id);
		}
	}

	/**
	 * 新增验证SkuPropertyTmplDTO
	 */
	private void verifyNewAddedSkuPropertyTmplDTOProperty(SkuPropertyTmplDTO skuPropertyTmplDTO)
			throws ItemException {
		if (skuPropertyTmplDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "skuPropertyTmplDTO is null");
		}
		if (skuPropertyTmplDTO.getName() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "name is null");
		}
		// 验证CategoryId在数据库是否存在
//		Integer categoryId = skuPropertyTmplDTO.getCategoryId();
//		if (categoryId == null) {
//			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "categoryId is null");
//		}
//		itemCategoryManager.getItemCategory(categoryId);
	}

	/**
	 * 更新验证SkuPropertyTmplDTO
	 */
	private void verifyUpdatedSkuPropertyTmplDTOProperty(SkuPropertyTmplDTO skuPropertyTmplDTO)
			throws ItemException {
		if (skuPropertyTmplDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "skuPropertyTmpl id is null");
		}
//		Integer categoryId = skuPropertyTmplDTO.getCategoryId();
//		if (categoryId != null) {
//			// 验证CategoryId在数据库是否存在
//			ItemCategoryDTO itemCategoryDTO = itemCategoryManager.getItemCategory(categoryId);
//		}
	}

	public List<SkuPropertyTmplDTO> querySkuPropertyTmpl(SkuPropertyTmplQTO skuPropertyTmplQTO)
			throws ItemException {
		List<SkuPropertyTmplDO> list = skuPropertyTmplDAO.querySkuPropertyTmpl(skuPropertyTmplQTO);
		List<SkuPropertyTmplDTO> skuPropertyTmplDTOList = new ArrayList<SkuPropertyTmplDTO>();// 需要返回的DTO列表
		for (SkuPropertyTmplDO skuPropertyTmplDO : list) {
			SkuPropertyTmplDTO dto = new SkuPropertyTmplDTO();
			ItemUtil.copyProperties(skuPropertyTmplDO, dto);
			skuPropertyTmplDTOList.add(dto);
		}
		return skuPropertyTmplDTOList;
	}
	
	public List<SkuPropertyTmplDTO> querySkuPropertyTmplWithValue(SkuPropertyTmplQTO skuPropertyTmplQTO)
			throws ItemException {
		List<SkuPropertyTmplDO> list = skuPropertyTmplDAO.querySkuPropertyTmplWithValue(skuPropertyTmplQTO);
		List<SkuPropertyTmplDTO> skuPropertyTmplDTOList = new ArrayList<SkuPropertyTmplDTO>();// 需要返回的DTO列表
		for (SkuPropertyTmplDO skuPropertyTmplDO : list) {
			SkuPropertyTmplDTO dto = new SkuPropertyTmplDTO();
			ItemUtil.copyProperties(skuPropertyTmplDO, dto);
			skuPropertyTmplDTOList.add(dto);
			
			//每个属性下的具体的取值  －－ updated by cwr
			List<SkuPropertyValueDO> skuPropertyDOList = skuPropertyTmplDO.getPropertyValues();
			List<SkuPropertyValueDTO> skuPropertys = new ArrayList<SkuPropertyValueDTO>();
			if(!CollectionUtils.isEmpty(skuPropertyDOList)){
				for(int i=0,len=skuPropertyDOList.size();i<len;i++){
					SkuPropertyValueDO valueDo = skuPropertyDOList.get(i);
					SkuPropertyValueDTO valueDto = new SkuPropertyValueDTO ();
					valueDto.setId(valueDo.getId());
					valueDto.setName(valueDo.getName());
					valueDto.setSkuPropertyTmplId(valueDo.getSkuPropertyTmplId());
					valueDto.setValue(valueDo.getValue());
					skuPropertys.add(valueDto);
				}
			}
			dto.setPropertyValues(skuPropertys);
		}
		return skuPropertyTmplDTOList;
	}
}
