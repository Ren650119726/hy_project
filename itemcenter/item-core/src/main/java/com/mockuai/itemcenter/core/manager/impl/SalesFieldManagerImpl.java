package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.dao.SalesFieldDAO;
import com.mockuai.itemcenter.core.domain.SalesFieldDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SalesFieldManager;
import com.mockuai.itemcenter.core.util.DateUtil;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SalesFieldDTO;
import com.mockuai.itemcenter.common.domain.qto.SalesFieldQTO;

@Service
public class SalesFieldManagerImpl implements SalesFieldManager {
	@Resource
	private SalesFieldDAO salesFieldDAO;

	@Resource
	private SalesFieldDAO salesFieldValueDAO;

	@Override
	public SalesFieldDTO addSalesField(SalesFieldDTO salesFieldDTO) throws ItemException {
		try {
			// 验证salesFieldDTO内的属性
			verifyNewAddedSalesFieldDTOProperty(salesFieldDTO);
			SalesFieldDO salesFieldDO = new SalesFieldDO();
			ItemUtil.copyProperties(salesFieldDTO, salesFieldDO);// DTO转DO
			Integer newInsertedId = salesFieldDAO.addSalesField(salesFieldDO);// 新增的记录返回的ID
			salesFieldDTO = getSalesField(newInsertedId);// 新增加的记录对应的salesFieldDO
			return salesFieldDTO;
		} catch (Exception e) {
			throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public boolean updateSalesField(SalesFieldDTO salesFieldDTO) throws ItemException {
		verifyUpdatedSalesFieldDTOProperty(salesFieldDTO);
		SalesFieldDO salesFieldDO = new SalesFieldDO();
		ItemUtil.copyProperties(salesFieldDTO, salesFieldDO);
		int num = salesFieldDAO.updateSalesField(salesFieldDO);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update sales_field error-->primary id:"
					+ salesFieldDTO.getId());
		}
	}

	@Override
	public SalesFieldDTO getSalesField(Integer id) throws ItemException {
		SalesFieldDO salesFieldDO = salesFieldDAO.getSalesField(id);
		if (salesFieldDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table sales_field-->id:"
							+ id);
		}
		SalesFieldDTO salesFieldDTO = new SalesFieldDTO();
		ItemUtil.copyProperties(salesFieldDO, salesFieldDTO);
		return salesFieldDTO;
	}

	@Override
	public boolean deleteSalesField(Integer id) throws ItemException {
		int num = salesFieldValueDAO.deleteSalesField(id);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_DELETE, "delete sales_field error-->primary id:"
					+ id);
		}
	}

	private void verifyNewAddedSalesFieldDTOProperty(SalesFieldDTO salesFieldDTO) throws ItemException {
		// 验证SalesFieldDTO字段属性
		if (salesFieldDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "salesFieldDTO is null");
		}
		if (salesFieldDTO.getFieldName() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "field_name is null");
		}
		if (salesFieldDTO.getBeginTime() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "begin_time is null");
		}
		if (salesFieldDTO.getEndTime() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "end_time is null");
		}

		Date beginTime = salesFieldDTO.getBeginTime();
		Date endTime = salesFieldDTO.getEndTime();
		// 结束时间不能早于开始时间
		if (beginTime.after(endTime)) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "begin_time:"
					+ DateUtil.toSecondString(beginTime) + " is late than end_time:"
					+ DateUtil.toSecondString(endTime));
		}

	}

	private void verifyUpdatedSalesFieldDTOProperty(SalesFieldDTO salesFieldDTO) throws ItemException {
		// 验证SalesFieldDTO字段属性
		if (salesFieldDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "salesFieldDTO is null");
		}
		if (salesFieldDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "salesField id is null");
		}
	}

	public List<SalesFieldDTO> querySalesField(SalesFieldQTO salesFieldQTO) throws ItemException {
		List<SalesFieldDO> list = salesFieldDAO.querySalesField(salesFieldQTO);
		List<SalesFieldDTO> salesFieldDTOList = new ArrayList<SalesFieldDTO>();// 需要返回的DTO列表
		for (SalesFieldDO salesFieldDO : list) {
			SalesFieldDTO dto = new SalesFieldDTO();
			ItemUtil.copyProperties(salesFieldDO, dto);
			salesFieldDTOList.add(dto);
		}
		return salesFieldDTOList;
	}
}
