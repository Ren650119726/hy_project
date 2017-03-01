//package com.mockuai.usercenter.core.dao.impl;
//
//import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.core.dao.SupplierExtraInfoDao;
//import com.mockuai.usercenter.core.domain.SupplierExtraInfoDO;
//
//@Service
//public class SupplierExtraInfoDaoImpl extends SqlMapClientDaoSupport implements
//		SupplierExtraInfoDao {
//
//	@Override
//	public Long addSupplierExtraInfo(SupplierExtraInfoDO supplierExtraDo) {
//		// TODO Auto-generated method stub
//
//		Long id = (Long) getSqlMapClientTemplate().insert(
//				"supplier_extra_info.insert", supplierExtraDo);
//
//		return id;
//	}
//
//	@Override
//	public SupplierExtraInfoDO getSupplierExtraInfoById(Long id) {
//		// TODO Auto-generated method stub
//
//		SupplierExtraInfoDO key = new SupplierExtraInfoDO();
//		key.setId(id);
//		SupplierExtraInfoDO supplierExtraDo = (SupplierExtraInfoDO) getSqlMapClientTemplate()
//				.queryForObject("supplier_extra_info.selectById", key);
//		return supplierExtraDo;
//	}
//
//	@Override
//	public SupplierExtraInfoDO getSupplierExtraInfoByUserId(Long userId) {
//		// TODO Auto-generated method stub
//		SupplierExtraInfoDO key = new SupplierExtraInfoDO();
//		key.setUserId(userId);
//		SupplierExtraInfoDO supplierExtraDo = (SupplierExtraInfoDO) getSqlMapClientTemplate()
//				.queryForObject("supplier_extra_info.selectByUserId", key);
//		return supplierExtraDo;
//	}
//
//	@Override
//	public int updateSupplierExtraInfo(SupplierExtraInfoDO supplierExtraDo) {
//		// TODO Auto-generated method stub
//		int result = getSqlMapClientTemplate().update(
//				"supplier_extra_info.update", supplierExtraDo);
//		return result;
//	}
//
//	@Override
//	public int passSupplierIden(Long userId) {
//		// TODO Auto-generated method stub
//
//		int result = getSqlMapClientTemplate().update(
//				"supplier_extra_info.pass", userId);
//		return result;
//	}
//
//	@Override
//	public int refuseSupplierIden(Long userId) {
//		// TODO Auto-generated method stub
//
//		int result = getSqlMapClientTemplate().update(
//				"supplier_extra_info.refuse", userId);
//		return result;
//	}
//
//	@Override
//	public int ignoreSupplierIden(Long userId) {
//		// TODO Auto-generated method stub
//		int result = getSqlMapClientTemplate().update(
//				"supplier_extra_info.ignore", userId);
//		return result;
//	}
//
//	@Override
//	public int delSupplierExtraInfoByUserId(Long userId) {
//		// TODO Auto-generated method stub
//
//		int result = getSqlMapClientTemplate().update(
//				"supplier_extra_info.delete", userId);
//		return result;
//	}
//
//	@Override
//	public int restoreSupplierExtraInfoByUserId(Long userId) {
//		// TODO Auto-generated method stub
//		int result = getSqlMapClientTemplate().update(
//				"supplier_extra_info.restore", userId);
//		return result;
//	}
//
//}
