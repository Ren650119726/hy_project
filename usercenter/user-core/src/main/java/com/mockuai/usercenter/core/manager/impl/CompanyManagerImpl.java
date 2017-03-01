//package com.mockuai.usercenter.core.manager.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.constant.ResponseCode;
//import com.mockuai.usercenter.common.dto.SupplierCompanyDTO;
//import com.mockuai.usercenter.common.qto.SupplierCompanyQTO;
//import com.mockuai.usercenter.core.dao.CompanyDao;
//import com.mockuai.usercenter.core.domain.SupplierCompanyDO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.CompanyManager;
//
//@Service
//public class CompanyManagerImpl implements CompanyManager {
//
//	@Resource
//	private CompanyDao companyDao;
//
//	@Override
//	public SupplierCompanyDTO addCompany(SupplierCompanyDTO supplierCompanyDto)
//			throws UserException {
//
//		if (null == supplierCompanyDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"supplierCompanyDTO is null");
//		}
//
//		SupplierCompanyDO supplierCompanyDo = new SupplierCompanyDO();
//
//		BeanUtils.copyProperties(supplierCompanyDto, supplierCompanyDo);
//
//		int id = companyDao.addCompany(supplierCompanyDo);
//
//		SupplierCompanyDTO companyDto = getCompany(id);
//
//		return companyDto;
//	}
//
//	@Override
//	public SupplierCompanyDTO getCompany(Integer id) throws UserException {
//		// TODO Auto-generated method stub
//		if (null == id) {
//			throw new UserException(ResponseCode.P_PARAM_NULL, "id is null");
//		}
//		SupplierCompanyDO companyDo = companyDao.getCompany(id);
//
//		SupplierCompanyDTO companyDto = null;
//		if (companyDo != null) {
//			companyDto = new SupplierCompanyDTO();
//			BeanUtils.copyProperties(companyDo, companyDto);
//		}
//		return companyDto;
//	}
//
//	@Override
//	public int deleteCompany(Integer id) throws UserException {
//		// TODO Auto-generated method stub
//		if (null == id) {
//			throw new UserException(ResponseCode.P_PARAM_NULL, "id is null");
//		}
//
//		int result = companyDao.deleteCompany(id);
//
//		if (result != 1) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR, "delete error");
//		}
//
//		return result;
//	}
//
//	@Override
//	public int updateCompany(SupplierCompanyDTO supplierCompanyDto)
//			throws UserException {
//
//		if (null == supplierCompanyDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"supplierCompanyDto is null");
//		}
//
//		SupplierCompanyDO supplierCompanyDo = new SupplierCompanyDO();
//
//		BeanUtils.copyProperties(supplierCompanyDto, supplierCompanyDo);
//
//		int result = companyDao.updateCompany(supplierCompanyDo);
//		if (result != 1) {
//			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update fail");
//		}
//
//		return result;
//	}
//
//	@Override
//	public List<SupplierCompanyDTO> queryCompany(SupplierCompanyQTO companyQto)
//			throws UserException {
//		// TODO Auto-generated method stub
//
//		if (null == companyQto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"companyQto is null");
//		}
//
//		List<SupplierCompanyDO> companyDos = companyDao
//				.queryCompany(companyQto);
//
//		List<SupplierCompanyDTO> companyDtos = new ArrayList<SupplierCompanyDTO>();
//
//		for (SupplierCompanyDO companyDo : companyDos) {
//			SupplierCompanyDTO companyDto = new SupplierCompanyDTO();
//			BeanUtils.copyProperties(companyDo, companyDto);
//			companyDtos.add(companyDto);
//		}
//
//		return companyDtos;
//	}
//
//	@Override
//	public int getCompanyCount() throws UserException {
//		// TODO Auto-generated method stub
//		int result = companyDao.getCompanyCount();
//		return result;
//	}
//
//}
