//package com.mockuai.usercenter.core.dao.impl;
//
//import java.util.List;
//
//import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.qto.SupplierCompanyQTO;
//import com.mockuai.usercenter.core.dao.CompanyDao;
//import com.mockuai.usercenter.core.domain.SupplierCompanyDO;
//
//@Service
//public class CompanyDaoImpl extends SqlMapClientDaoSupport implements
//		CompanyDao {
//
//	@Override
//	public int addCompany(SupplierCompanyDO supplierCompanyDo) {
//		// TODO Auto-generated method stub
//		int result = (Integer) getSqlMapClientTemplate().insert(
//				"supplier_company.insert", supplierCompanyDo);
//		return result;
//	}
//
//	@Override
//	public SupplierCompanyDO getCompany(Integer id) {
//		// TODO Auto-generated method stub
//		SupplierCompanyDO companyDo = (SupplierCompanyDO) getSqlMapClientTemplate()
//				.queryForObject("supplier_company.select", id);
//		return companyDo;
//	}
//
//	@Override
//	public int deleteCompany(Integer id) {
//		// TODO Auto-generated method stub
//		int result = getSqlMapClientTemplate().delete(
//				"supplier_company.delete", id);
//		return result;
//	}
//
//	@Override
//	public int updateCompany(SupplierCompanyDO supplierCompanyDo) {
//		// TODO Auto-generated method stub
//
//		int result = getSqlMapClientTemplate().update(
//				"supplier_company.update", supplierCompanyDo);
//		return result;
//	}
//
//	@Override
//	public int getCompanyCount() {
//		// TODO Auto-generated method stub
//		int result = (Integer) getSqlMapClientTemplate().queryForObject(
//				"supplier_company.count");
//		return result;
//	}
//
//	@Override
//	public List<SupplierCompanyDO> queryCompany(SupplierCompanyQTO companyQto) {
//		// TODO Auto-generated method stub
//		List<SupplierCompanyDO> companyDos = getSqlMapClientTemplate()
//				.queryForList("supplier_company.query", companyQto);
//		return companyDos;
//	}
//
//}
