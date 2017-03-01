//package com.mockuai.usercenter.client;
//
//import com.mockuai.usercenter.common.api.Response;
//import com.mockuai.usercenter.common.dto.SupplierExtraInfoDTO;
//import com.mockuai.usercenter.common.dto.UserExtraInfoDTO;
//
//public interface UserExtraClient {
//	/**
//	 * 添加供应商扩展信息
//	 */
//	Response<SupplierExtraInfoDTO> addSupplierExtraInfo(
//			SupplierExtraInfoDTO supplierExtraDto);
//
//	/**
//	 * 添加买家扩展信息
//	 */
//	Response<UserExtraInfoDTO> addUserExtraInfo(UserExtraInfoDTO userExtraDto);
//
//	/**
//	 * 获取供应商的扩展信息
//	 */
//	Response<SupplierExtraInfoDTO> getSupplierExtraInfoByUserId(Long userId);
//
//	/**
//	 * 获取用户的扩展信息
//	 */
//	Response<UserExtraInfoDTO> getUserExtraInfoByUserId(Long userId);
//
//	/**
//	 * 供应商审核忽略
//	 */
//	Response<Boolean> ignoreSupplierIden(Long userId);
//
//	/**
//	 * 供应商审核忽略
//	 */
//	Response<Boolean> passSupplierIden(Long userId);
//
//	/**
//	 * 供应商审核忽略
//	 */
//	Response<Boolean> refuseSupplierIden(Long userId);
//
//	/**
//	 * 修改供应商扩展信息
//	 */
//	Response<Boolean> updateSupplierExtraInfo(
//			SupplierExtraInfoDTO supplierExtraDto);
//
//	/**
//	 * 修改买家扩展信息
//	 */
//	Response<Boolean> updateUserExtraInfo(UserExtraInfoDTO userExtraDto);
//}
