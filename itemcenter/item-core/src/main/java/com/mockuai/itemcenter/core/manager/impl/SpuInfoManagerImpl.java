package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SpuInfoManager;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SpuInfoDTO;
import com.mockuai.itemcenter.common.domain.qto.SpuInfoQTO;
import com.mockuai.itemcenter.core.dao.SpuInfoDAO;
import com.mockuai.itemcenter.core.domain.SpuInfoDO;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;

@Service
public class SpuInfoManagerImpl implements SpuInfoManager {
	@Resource
	private SpuInfoDAO spuInfoDAO;

	@Override
	public SpuInfoDTO addSpuInfo(SpuInfoDTO spuInfoDTO) throws ItemException {
		try {
			// 验证spuInfoDTO内的属性
			if (!verifySpuInfoDTOProperty(spuInfoDTO)) {
				throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "spuInfoDTO property incorrect");
			}
			SpuInfoDO spuInfoDO = new SpuInfoDO();
			ItemUtil.copyProperties(spuInfoDTO, spuInfoDO);// DTO转DO
			long newInsertedId = spuInfoDAO.addSpuInfo(spuInfoDO);// 新增的记录返回的ID
			spuInfoDTO = getSpuInfo(newInsertedId);// 新增加的记录对应的spuInfoDO
			return spuInfoDTO;
		} catch (Exception e) {
			throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public boolean updateSpuInfo(SpuInfoDTO spuInfoDTO) throws ItemException {
		// 验证参数
		if (spuInfoDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "spuInfoDTO is null");
		}
		if (!verifySpuInfoDTOProperty(spuInfoDTO)) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "spuInfoDTO property incorrect");
		}
		SpuInfoDO spuInfoDO = new SpuInfoDO();
		ItemUtil.copyProperties(spuInfoDTO, spuInfoDO);
		int num = spuInfoDAO.updateSpuInfo(spuInfoDO);
		return num > 0 ? true : false;
	}

	@Override
	public SpuInfoDTO getSpuInfo(Long id) throws ItemException {
		SpuInfoDO spuInfoDO = spuInfoDAO.getSpuInfo(id);
		if (spuInfoDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "Cannot find requested record");
		}
		SpuInfoDTO spuInfoDTO = new SpuInfoDTO();
		ItemUtil.copyProperties(spuInfoDO, spuInfoDTO);
		return spuInfoDTO;
	}

	@Override
	public boolean deleteSpuInfo(Long id) throws ItemException {
		// TODO 验证id

		SpuInfoDO spuInfoDO = new SpuInfoDO();
		spuInfoDO.setId(id);
		spuInfoDO.setIsDeleted(DBConst.DELETED.getCode());
		int num = spuInfoDAO.updateSpuInfo(spuInfoDO);
		return num > 0 ? true : false;
	}

	@Override
	public boolean verifySpuInfoDTOProperty(SpuInfoDTO spuInfoDTO) throws ItemException {
		// TODO 验证SpuInfoDTO字段属性
		return true;
	}

	public List<SpuInfoDTO> querySpuInfo(SpuInfoQTO spuInfoQTO) throws ItemException {
		List<SpuInfoDO> list = spuInfoDAO.querySpuInfo(spuInfoQTO);
		List<SpuInfoDTO> spuInfoDTOList = new ArrayList<SpuInfoDTO>();// 需要返回的DTO列表
		for (SpuInfoDO spuInfoDO : list) {
			SpuInfoDTO dto = new SpuInfoDTO();
			ItemUtil.copyProperties(spuInfoDO, dto);
			spuInfoDTOList.add(dto);
		}
		return spuInfoDTOList;
	}
}
