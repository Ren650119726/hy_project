//package com.mockuai.usercenter.core.dao;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.qto.SupplierCompanyQTO;
//import com.mockuai.usercenter.core.domain.SupplierCompanyDO;
//
//@Service
//public interface CompanyDao {
//
//	/**
//	 * 添加公司
//	 * */
//	int addCompany(SupplierCompanyDO supplierCompanyDo);
//
//	/**
//	 * 根据id获取指定的公司
//	 * */
//	SupplierCompanyDO getCompany(Integer id);
//
//	/**
//	 * 删除指定的公司
//	 * */
//	int deleteCompany(Integer id);
//
//	/**
//	 * 修改指定的公司
//	 * */
//	int updateCompany(SupplierCompanyDO supplierCompanyDo);
//
//	/**
//	 * 查询公司的总数
//	 * */
//	int getCompanyCount();
//
//	/**
//	 * 分页查询公司
//	 * */
//	List<SupplierCompanyDO> queryCompany(SupplierCompanyQTO companyQto);
//
//}
