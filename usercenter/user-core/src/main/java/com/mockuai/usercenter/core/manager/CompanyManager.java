//package com.mockuai.usercenter.core.manager;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.dto.SupplierCompanyDTO;
//import com.mockuai.usercenter.common.qto.SupplierCompanyQTO;
//import com.mockuai.usercenter.core.exception.UserException;
//
//@Service
//public interface CompanyManager {
//
//	/**
//	 * 添加供应商所属公司
//	 * */
//	SupplierCompanyDTO addCompany(SupplierCompanyDTO supplierCompanyDto)
//			throws UserException;
//
//	/**
//	 * 根据id查询指定的公司
//	 * */
//	SupplierCompanyDTO getCompany(Integer id) throws UserException;
//
//	int deleteCompany(Integer id) throws UserException;
//
//	int updateCompany(SupplierCompanyDTO supplierCompanyDto)
//			throws UserException;
//
//	/** 查询公司列表 */
//	List<SupplierCompanyDTO> queryCompany(SupplierCompanyQTO companyQto)
//			throws UserException;
//
//	/**
//	 * 查询公司的总数量
//	 * */
//
//	int getCompanyCount() throws UserException;
//}
