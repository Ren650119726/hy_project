//package com.mockuai.usercenter.core.manager;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.dto.SupplierExtraInfoDTO;
//import com.mockuai.usercenter.core.exception.UserException;
//
//@Service
//public interface SupplierExtraInfoManager {
//
//	public SupplierExtraInfoDTO addSupplierExtraInfo(
//			SupplierExtraInfoDTO supplierExtraDto) throws UserException;
//
//	/**
//	 * 根据id查询供应商的扩展信息
//	 * */
//	public SupplierExtraInfoDTO getSupplierExtraInfoById(Long id)
//			throws UserException;
//
//	/**
//	 * 根据用户id查询供应商的扩展信息
//	 * */
//	public SupplierExtraInfoDTO getSupplierExtraInfoByUserId(Long userId)
//			throws UserException;
//
//	/**
//	 * 修改供应商的扩展信息
//	 * */
//	public int updateSupplierExtraInfo(SupplierExtraInfoDTO supplierExtraDto)
//			throws UserException;
//
//	/**
//	 * 供应商审核通过
//	 * */
//	public int passSupplierIden(Long userId) throws UserException;
//
//	/**
//	 * 供应商审核失败，退回修改
//	 * */
//	public int refuseSupplierIden(Long userId) throws UserException;
//
//	/**
//	 * 供应商审核忽略
//	 * */
//	public int ignoreSupplierIden(Long userId) throws UserException;
//
//	/**
//	 * 逻辑删除供应商扩展信息
//	 *
//	 * @param userId
//	 * @return
//	 *
//	 */
//	public int delSupplierExtraInfoByUserId(Long userId) throws UserException;
//
//	/**
//	 * 将买家的扩展信息还原
//	 * */
//	public int restoreSupplierExtraInfoByUserId(Long userId)
//			throws UserException;
//
//}
