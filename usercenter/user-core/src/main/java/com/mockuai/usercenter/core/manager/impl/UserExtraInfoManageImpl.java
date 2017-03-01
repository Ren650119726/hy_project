//package com.mockuai.usercenter.core.manager.impl;
//
//import javax.annotation.Resource;
//
//import com.mockuai.usercenter.common.qto.UserExtraInfoQTO;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.constant.ResponseCode;
//import com.mockuai.usercenter.common.dto.UserDTO;
//import com.mockuai.usercenter.common.dto.UserExtraInfoDTO;
//import com.mockuai.usercenter.core.dao.UserExtraInfoDao;
//import com.mockuai.usercenter.core.domain.UserExtraInfoDO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.UserExtraInfoManage;
//import com.mockuai.usercenter.core.employee.UserManager;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class UserExtraInfoManageImpl implements UserExtraInfoManage {
//
//	@Resource
//	private UserManager userManager;
//
//	@Resource
//	private UserExtraInfoDao userExtraInfoDao;
//
//	@Override
//	public UserExtraInfoDTO addUserExtraInfo(UserExtraInfoDTO userExtraDto)
//			throws UserException {
//
//		UserExtraInfoDTO userExtraInfoDto = null;
//
//		if (null == userExtraDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"userExtraDTO is null");
//		}
//
//		// 查找在用户表中是否存在指定的用户
//		UserDTO userDto = userManager.getUserById(userExtraDto.getUserId());
//
//		if (null == userDto) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR,
//					"user is not exist");
//		}
//
//		// 判断用户扩展表中是否已经存在指定用户的扩展信息
//		userExtraInfoDto = getUserExtraInfoByUserId(userExtraDto.getUserId());
//
//		if (null != userExtraInfoDto) {
//			throw new UserException(ResponseCode.B_ADD_ERROR,
//					"user information is already exist");
//		}
//
//		// 判断用户的性别
//		Byte sex = userExtraDto.getSex();
//		if (sex != null) {
//			if (sex != 0 && sex != 1) {
//				throw new UserException(ResponseCode.P_PARAM_ERROR,
//						"sex is error");
//			}
//		}
//
//		UserExtraInfoDO userExtraDo = new UserExtraInfoDO();
//		BeanUtils.copyProperties(userExtraDto, userExtraDo);
//
//		// 添加买家扩展信息
//		Long id = userExtraInfoDao.addUserExtraInfo(userExtraDo);
//		return getUserExtraInfoById(id);
//	}
//
//	@Override
//	public UserExtraInfoDTO getUserExtraInfoById(Long id) throws UserException {
//
//		if (null == id) {
//			throw new UserException(ResponseCode.P_PARAM_NULL, "id is null");
//		}
//
//		if (id < 0) {
//			throw new UserException(ResponseCode.P_PARAM_ERROR,
//					"idd must greater than zero");
//		}
//
//		UserExtraInfoDO userExtraInfoDo = userExtraInfoDao
//				.getUserExtraInfoById(id);
//		UserExtraInfoDTO userExtraInfoDto = new UserExtraInfoDTO();
//		BeanUtils.copyProperties(userExtraInfoDo, userExtraInfoDto);
//		return userExtraInfoDto;
//	}
//
//	@Override
//	public UserExtraInfoDTO getUserExtraInfoByUserId(Long userId)
//			throws UserException {
//
//		UserDTO userDto = userManager.getUserById(userId);
//		if (null == userDto) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR,
//					"user is not exist");
//		}
//
//		UserExtraInfoDO userExtraInfoDo = userExtraInfoDao
//				.getUserExtraInfoByUserId(userId);
//		UserExtraInfoDTO userExtraInfoDto = null;
//		if (null != userExtraInfoDo) {
//			userExtraInfoDto = new UserExtraInfoDTO();
//			BeanUtils.copyProperties(userExtraInfoDo, userExtraInfoDto);
//		}
//
//		return userExtraInfoDto;
//	}
//
//	@Override
//	public int updateUserExtraInfo(UserExtraInfoDTO userExtraDto)
//			throws UserException {
//		if (userExtraDto == null) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"userExtraDTO is null");
//		}
//
//		// 判断用户的性别
//		Byte sex = userExtraDto.getSex();
//		if (sex != null) {
//			if (sex != 0 && sex != 1) {
//				throw new UserException(ResponseCode.P_PARAM_ERROR,
//						"sex is error");
//			}
//		}
//
//		UserExtraInfoDTO userExtraInfoDto = null;
//		// 查找在用户表中是否存在指定的用户
//		UserDTO userDto = userManager.getUserById(userExtraDto.getUserId());
//
//		if (null == userDto) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR,
//					"user is not exist");
//		}
//
//		// 判断用户扩展表中是否已经存在指定用户的扩展信息
//		userExtraInfoDto = getUserExtraInfoByUserId(userExtraDto.getUserId());
//
//		if (null == userExtraInfoDto) {
//			throw new UserException(ResponseCode.B_UPDATE_ERROR,
//					"userExtra is null");
//		}
//
//		UserExtraInfoDO userExtraDo = new UserExtraInfoDO();
//		BeanUtils.copyProperties(userExtraDto, userExtraDo);
//
//		int result = userExtraInfoDao.updateUserExtraInfo(userExtraDo);
//
//		if (result != 1) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
//		}
//		return result;
//	}
//
//	@Override
//	public int delUserExtraInfoByUserId(Long userId) throws UserException {
//		// TODO Auto-generated method stub
//
//		UserDTO userDto = userManager.getUserById(userId);
//		if (null == userDto) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR,
//					"user is not exist");
//		}
//		int result = userExtraInfoDao.delUserExtraInfoByUserId(userId);
//		return result;
//	}
//
//	@Override
//	public int restoreUserExtraInfoByUserId(Long userId) throws UserException {
//
//		int result = userExtraInfoDao.restoreUserExtraInfoByUserId(userId);
//		return result;
//	}
//
//	@Override
//	public UserExtraInfoDTO getInfoByOpenIdAndAuthType(
//			UserExtraInfoDTO userExtraInfoDto) throws UserException {
//		// TODO Auto-generated method stub
//		if (null == userExtraInfoDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"userExtraInfoDto is null");
//		}
//		String openId = userExtraInfoDto.getOpenId();
//		if (null == openId) {
//			throw new UserException(ResponseCode.P_PARAM_NULL, "openId is null");
//		}
//
//		Integer authType = userExtraInfoDto.getAuthType();
//		if (null == authType) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"authType is null");
//		}
//		return userExtraInfoDao.getInfoByOpenIdAndAuthType(userExtraInfoDto);
//	}
//
//	@Override
//	public List<UserExtraInfoDTO> queryUserExtraInfo(UserExtraInfoQTO userExtraInfoQTO) throws UserException {
//		if (userExtraInfoQTO == null) {
//			throw new UserException(ResponseCode.P_PARAM_NULL, "userExtraInfoQTO is null");
//		}
//
//		List<UserExtraInfoDO> userExtraInfoDOs = userExtraInfoDao.queryUserExtraInfo(userExtraInfoQTO);
//		List<UserExtraInfoDTO> userExtraInfoDTOs = new ArrayList<UserExtraInfoDTO>();
//		for (UserExtraInfoDO userExtraInfoDO : userExtraInfoDOs) {
//			UserExtraInfoDTO dto = new UserExtraInfoDTO();
//			BeanUtils.copyProperties(userExtraInfoDO, dto);
//			userExtraInfoDTOs.add(dto);
//		}
//
//		return userExtraInfoDTOs;
//	}
//}
