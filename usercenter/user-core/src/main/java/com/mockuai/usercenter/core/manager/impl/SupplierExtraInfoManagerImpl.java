//package com.mockuai.usercenter.core.manager.impl;
//
//import javax.annotation.Resource;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.constant.ResponseCode;
//import com.mockuai.usercenter.common.dto.SupplierExtraInfoDTO;
//import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
//import com.mockuai.usercenter.common.dto.UserDTO;
//import com.mockuai.usercenter.core.dao.SupplierExtraInfoDao;
//import com.mockuai.usercenter.core.domain.SupplierExtraInfoDO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.SupplierExtraInfoManager;
//import com.mockuai.usercenter.core.employee.UserManager;
//import com.mockuai.usercenter.core.util.IdCardCheckUtil;
//import com.mockuai.usercenter.core.util.UserUtil;
//
//@Service
//public class SupplierExtraInfoManagerImpl implements SupplierExtraInfoManager {
//
//	@Resource
//	private UserManager userManager;
//	@Resource
//	private SupplierExtraInfoDao supplierExtraDao;
//
//	@Override
//	public SupplierExtraInfoDTO addSupplierExtraInfo(
//			SupplierExtraInfoDTO supplierExtraDto) throws UserException {
//
//		if (null == supplierExtraDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"supplierExtraDTO is null");
//		}
//
//		// 检查手机号是否满足格式
//		UserUtil.checkMobile(supplierExtraDto.getTel());
//
//		// 检查联系人身份证格式
//		IdCardCheckUtil.IDCardValidate(supplierExtraDto.getContactIdcard());
//
//		// 检查联系人qq
//		UserUtil.checkQQ(supplierExtraDto.getContactQq());
//
//		// 检查联系人电话
//		UserUtil.checkMobile(supplierExtraDto.getContactMobile());
//
//		// 检查联系人邮箱
//		UserUtil.checkEamil(supplierExtraDto.getContactEmail());
//
//		UserDTO userDto = userManager.getUserById(supplierExtraDto.getUserId());
//		if (null == userDto) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR,
//					"user is not exist");
//		}
//
//		// 如果用户角色不是卖家类型，这不能添加
//		if (userDto.getRoleMark() == 0) {
//			throw new UserException(ResponseCode.P_PARAM_ERROR,
//					"user is not suppliers");
//		}
//
//		// 检查是否重复添加
//		SupplierExtraInfoDTO querySupplierExtraDto = getSupplierExtraInfoByUserId(supplierExtraDto
//				.getUserId());
//		if (querySupplierExtraDto != null) {
//			throw new UserException(ResponseCode.B_ADD_ERROR,
//					"supplierExtraInfo is exist");
//		}
//
//		SupplierExtraInfoDO supplierExtraDo = new SupplierExtraInfoDO();
//
//		BeanUtils.copyProperties(supplierExtraDto, supplierExtraDo);
//
//		// 添加卖家信息
//		Long id = supplierExtraDao.addSupplierExtraInfo(supplierExtraDo);
//
//		supplierExtraDto = getSupplierExtraInfoById(id);
//
//		return supplierExtraDto;
//	}
//
//	@Override
//	public SupplierExtraInfoDTO getSupplierExtraInfoById(Long id)
//			throws UserException {
//
//		if (null == id) {
//			throw new UserException(ResponseCode.P_PARAM_NULL, "id is null");
//		}
//
//		if (id < 0) {
//			throw new UserException(ResponseCode.P_PARAM_ERROR,
//					"id must greater than zero");
//		}
//
//		SupplierExtraInfoDO supplierExtraDo = supplierExtraDao
//				.getSupplierExtraInfoById(id);
//
//		SupplierExtraInfoDTO supplierExtroDto = null;
//		if (supplierExtraDo != null) {
//			supplierExtroDto = new SupplierExtraInfoDTO();
//			BeanUtils.copyProperties(supplierExtraDo, supplierExtroDto);
//		}
//
//		return supplierExtroDto;
//	}
//
//	@Override
//	public SupplierExtraInfoDTO getSupplierExtraInfoByUserId(Long userId)
//			throws UserException {
//
//		// 查看指定的用户是否存在
//		UserDTO userDto = userManager.getUserById(userId);
//		if (null == userDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"user is not exist");
//		}
//
//		// 获得指定的供应商扩展信息
//		SupplierExtraInfoDO supplierExtraDo = supplierExtraDao
//				.getSupplierExtraInfoByUserId(userId);
//		SupplierExtraInfoDTO supplierExtroDto = null;
//		// 将do转换为dto
//		if (supplierExtraDo != null) {
//			supplierExtroDto = new SupplierExtraInfoDTO();
//			BeanUtils.copyProperties(supplierExtraDo, supplierExtroDto);
//		}
//
//		return supplierExtroDto;
//	}
//
//	@Override
//	public int updateSupplierExtraInfo(SupplierExtraInfoDTO supplierExtraDto)
//			throws UserException {
//		// TODO Auto-generated method stub
//		if (null == supplierExtraDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"supplierExtraDTO is null");
//		}
//		if (supplierExtraDto.getTel() != null) {
//			// 检查手机号是否满足格式
//			UserUtil.checkMobile(supplierExtraDto.getTel());
//		}
//
//		if (supplierExtraDto.getContactIdcard() != null) {
//			// 检查联系人身份证格式
//			IdCardCheckUtil.IDCardValidate(supplierExtraDto.getContactIdcard());
//		}
//
//		if (supplierExtraDto.getContactQq() != null) {
//			// 检查联系人qq
//			UserUtil.checkQQ(supplierExtraDto.getContactQq());
//		}
//
//		if (supplierExtraDto.getContactMobile() != null) {
//			// 检查联系人电话
//			UserUtil.checkMobile(supplierExtraDto.getContactMobile());
//		}
//
//		if (supplierExtraDto.getContactEmail() != null) {
//			// 检查联系人邮箱
//			UserUtil.checkEamil(supplierExtraDto.getContactEmail());
//		}
//
//		SupplierExtraInfoDO supplierExtraDo = new SupplierExtraInfoDO();
//		// 将dto转换为do
//		BeanUtils.copyProperties(supplierExtraDto, supplierExtraDo);
//		int result = supplierExtraDao.updateSupplierExtraInfo(supplierExtraDo);
//
//		if (result != 1) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
//		}
//
//		return result;
//	}
//
//	@Override
//	public int passSupplierIden(Long userId) throws UserException {
//
//		UserDTO userDto = userManager.getUserById(userId);
//		if (null == userDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"user is not exist");
//		}
//
//		// 通过卖家的审核
//		int result = supplierExtraDao.passSupplierIden(userId);
//		if (result != 1) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
//		}
//		return result;
//	}
//
//	@Override
//	public int refuseSupplierIden(Long userId) throws UserException {
//		// TODO Auto-generated method stub
//		UserAuthInfoDTO userAuthInfoDto = null;
//
//		UserDTO userDto = userManager.getUserById(userId);
//		if (null == userDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"user is not exist");
//		}
//
//		int result = supplierExtraDao.refuseSupplierIden(userId);
//
//		if (result != 1) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
//		}
//		return result;
//	}
//
//	@Override
//	public int ignoreSupplierIden(Long userId) throws UserException {
//
//		UserDTO userDto = userManager.getUserById(userId);
//		if (null == userDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"user is not exist");
//		}
//
//		// 忽略供应商的审核
//		int result = supplierExtraDao.ignoreSupplierIden(userId);
//
//		if (result != 1) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
//		}
//		return result;
//	}
//
//	@Override
//	public int delSupplierExtraInfoByUserId(Long userId) throws UserException {
//		// TODO Auto-generated method stub
//		UserDTO userDto = userManager.getUserById(userId);
//		if (null == userDto) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR,
//					"user is not exist");
//		}
//
//		int result = supplierExtraDao.delSupplierExtraInfoByUserId(userId);
//
//		if (result != 1) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
//		}
//		return result;
//	}
//
//	@Override
//	public int restoreSupplierExtraInfoByUserId(Long userId)
//			throws UserException {
//		// TODO Auto-generated method stub
//
//		int result = supplierExtraDao.restoreSupplierExtraInfoByUserId(userId);
//
//		return result;
//	}
//
//}
