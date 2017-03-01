package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SpuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.SpuPropertyTmplQTO;
import com.mockuai.itemcenter.core.dao.SpuPropertyTmplDAO;
import com.mockuai.itemcenter.core.domain.SpuPropertyTmplDO;
import com.mockuai.itemcenter.core.manager.SpuPropertyTmplManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;

@Service
public class SpuPropertyTmplManagerImpl implements SpuPropertyTmplManager {
	@Resource
	private SpuPropertyTmplDAO spuPropertyTmplDAO;

	@Override
	public SpuPropertyTmplDTO addSpuPropertyTmpl(SpuPropertyTmplDTO spuPropertyTmplDTO) throws ItemException {
		try {
			// 验证spuPropertyTmplDTO内的属性
			if (!verifySpuPropertyTmplDTOProperty(spuPropertyTmplDTO)) {
				throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "spuPropertyTmplDTO property incorrect");
			}
			SpuPropertyTmplDO spuPropertyTmplDO = new SpuPropertyTmplDO();
			ItemUtil.copyProperties(spuPropertyTmplDTO, spuPropertyTmplDO);// DTO转DO
			long newInsertedId = spuPropertyTmplDAO.addSpuPropertyTmpl(spuPropertyTmplDO);// 新增的记录返回的ID
			spuPropertyTmplDTO = getSpuPropertyTmpl(newInsertedId);// 新增加的记录对应的spuPropertyTmplDO
			return spuPropertyTmplDTO;
		} catch (Exception e) {
			throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public boolean updateSpuPropertyTmpl(SpuPropertyTmplDTO spuPropertyTmplDTO) throws ItemException {
		// 验证参数
		if (spuPropertyTmplDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "spuPropertyTmplDTO is null");
		}
		if (!verifySpuPropertyTmplDTOProperty(spuPropertyTmplDTO)) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "spuPropertyTmplDTO property incorrect");
		}
		SpuPropertyTmplDO spuPropertyTmplDO = new SpuPropertyTmplDO();
		ItemUtil.copyProperties(spuPropertyTmplDTO, spuPropertyTmplDO);
		int num = spuPropertyTmplDAO.updateSpuPropertyTmpl(spuPropertyTmplDO);
		return num > 0 ? true : false;
	}

	@Override
	public SpuPropertyTmplDTO getSpuPropertyTmpl(Long id) throws ItemException {
		SpuPropertyTmplDO spuPropertyTmplDO = spuPropertyTmplDAO.getSpuPropertyTmpl(id);
		if (spuPropertyTmplDO == null) {
			throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "Cannot find requested record");
		}
		SpuPropertyTmplDTO spuPropertyTmplDTO = new SpuPropertyTmplDTO();
		ItemUtil.copyProperties(spuPropertyTmplDO, spuPropertyTmplDTO);
		return spuPropertyTmplDTO;
	}

	@Override
	public boolean deleteSpuPropertyTmpl(Long id) throws ItemException {
		// TODO 验证id

		SpuPropertyTmplDO spuPropertyTmplDO = new SpuPropertyTmplDO();
		spuPropertyTmplDO.setId(id);
		spuPropertyTmplDO.setIsDeleted(DBConst.DELETED.getCode());
		int num = spuPropertyTmplDAO.updateSpuPropertyTmpl(spuPropertyTmplDO);
		return num > 0 ? true : false;
	}

	@Override
	public boolean verifySpuPropertyTmplDTOProperty(SpuPropertyTmplDTO spuPropertyTmplDTO) throws ItemException {
		// TODO 验证SpuPropertyTmplDTO字段属性
		return true;
	}

	public List<SpuPropertyTmplDTO> querySpuPropertyTmpl(SpuPropertyTmplQTO spuPropertyTmplQTO) throws ItemException {
		List<SpuPropertyTmplDO> list = spuPropertyTmplDAO.querySpuPropertyTmpl(spuPropertyTmplQTO);
		List<SpuPropertyTmplDTO> spuPropertyTmplDTOList = new ArrayList<SpuPropertyTmplDTO>();// 需要返回的DTO列表
		for (SpuPropertyTmplDO spuPropertyTmplDO : list) {
			SpuPropertyTmplDTO dto = new SpuPropertyTmplDTO();
			ItemUtil.copyProperties(spuPropertyTmplDO, dto);
			spuPropertyTmplDTOList.add(dto);
		}
		return spuPropertyTmplDTOList;
	}
}
