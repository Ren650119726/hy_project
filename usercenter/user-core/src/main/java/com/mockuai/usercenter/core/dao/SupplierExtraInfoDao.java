//package com.mockuai.usercenter.core.dao;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.core.domain.SupplierExtraInfoDO;
//
//@Service
//public interface SupplierExtraInfoDao {
//
//	Long addSupplierExtraInfo(SupplierExtraInfoDO supplierExtraDo);
//
//	SupplierExtraInfoDO getSupplierExtraInfoById(Long id);
//
//	SupplierExtraInfoDO getSupplierExtraInfoByUserId(Long userId);
//
//	int updateSupplierExtraInfo(SupplierExtraInfoDO supplierExtraDo);
//
//	// 供应商实名认证通过
//	int passSupplierIden(Long userId);
//
//	// 供应商实名认证不通过
//	int refuseSupplierIden(Long userId);
//
//	int ignoreSupplierIden(Long userId);
//
//	int delSupplierExtraInfoByUserId(Long userId);
//
//	int restoreSupplierExtraInfoByUserId(Long userId);
//
//}
