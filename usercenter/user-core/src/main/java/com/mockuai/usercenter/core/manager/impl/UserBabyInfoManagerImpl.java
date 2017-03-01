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
//import com.mockuai.usercenter.common.dto.UserBabyInfoDTO;
//import com.mockuai.usercenter.common.dto.UserDTO;
//import com.mockuai.usercenter.core.dao.UserBabyInfoDao;
//import com.mockuai.usercenter.core.domain.UserBabyInfoDO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.UserBabyInfoManager;
//import com.mockuai.usercenter.core.employee.UserManager;
//
//@Service
//public class UserBabyInfoManagerImpl implements UserBabyInfoManager {
//	private static final int MAX_BABY = 10;
//
//	@Resource
//	private UserBabyInfoDao userBabyInfoDao;
//
//	@Resource
//	private UserManager userManager;
//
//	@Override
//	public UserBabyInfoDTO addBabyInfo(UserBabyInfoDTO babyInfoDto)
//			throws UserException {
//		UserBabyInfoDTO userBabyInfoDto = null;
//		if (null == babyInfoDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"babyInfoDto is null");
//		}
//		// 宝宝的出生状态
//		Byte babyStatus = babyInfoDto.getBabyStatus();
//		if (babyStatus != null) {
//			if (babyStatus != 0 && babyStatus != 1) {
//				throw new UserException(ResponseCode.P_PARAM_ERROR,
//						"babyStatus is error");
//			}
//		}
//
//		// 宝宝的性别
//		Byte babySex = babyInfoDto.getBabySex();
//		if (babySex != null) {
//			if (babySex != 0 && babySex != 1) {
//				throw new UserException(ResponseCode.P_PARAM_ERROR,
//						"babySex is error");
//			}
//		}
//
//		// 查询指定的用户是否存在
//		UserDTO userDto = userManager.getUserById(babyInfoDto.getUserId());
//		if (null == userDto) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR,
//					"user is not exist");
//		}
//
//		// 每个用户能添加的宝贝数最多为10个
//
//		int count = getBabyCountByUserId(babyInfoDto.getUserId());
//		if (count >= MAX_BABY) {
//			throw new UserException(ResponseCode.B_ADD_ERROR,
//					"baby count greater than 10");
//		}
//
//		UserBabyInfoDO userBabyInfoDo = new UserBabyInfoDO();
//		BeanUtils.copyProperties(babyInfoDto, userBabyInfoDo);
//
//		Long id = userBabyInfoDao.addBabyInfo(userBabyInfoDo);
//		babyInfoDto = getBabyInfoById(id);
//
//		return babyInfoDto;
//	}
//
//	@Override
//	public UserBabyInfoDTO getBabyInfoById(Long id) throws UserException {
//		// TODO Auto-generated method stub
//
//		UserBabyInfoDO userBabyInfoDo = null;
//		UserBabyInfoDTO userBabyInfoDto = null;
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
//		userBabyInfoDo = userBabyInfoDao.getBabyInfoById(id);
//		if (userBabyInfoDo != null) {
//			userBabyInfoDto = new UserBabyInfoDTO();
//			BeanUtils.copyProperties(userBabyInfoDo, userBabyInfoDto);
//		}
//
//		return userBabyInfoDto;
//	}
//
//	@Override
//	public int getBabyCountByUserId(Long userId) throws UserException {
//		UserDTO userDto = userManager.getUserById(userId);
//		if (null == userDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"user is not exist");
//		}
//
//		int result = userBabyInfoDao.getBabyCountByUserId(userId);
//
//		return result;
//	}
//
//	@Override
//	public int deleteBabyInfo(Long userId, Long babyId) throws UserException {
//
//		if (null == babyId) {
//			throw new UserException(ResponseCode.P_PARAM_NULL, "babyId is null");
//		}
//
//		if (babyId < 0) {
//			throw new UserException(ResponseCode.P_PARAM_ERROR,
//					"id must greater than zero");
//		}
//
//		int result = userBabyInfoDao.deleteBabyInfo(userId, babyId);
//
//		if (result != 1) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR, "delete error");
//		}
//
//		return result;
//	}
//
//	@Override
//	public int updateBabyInfo(UserBabyInfoDTO babyInfoDto) throws UserException {
//		// TODO Auto-generated method stub
//
//		UserBabyInfoDTO userBabyInfoDto = null;
//		UserBabyInfoDO userBabyInfoDo = null;
//
//		if (null == babyInfoDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"babyInfoDto is null");
//		}
//
//		// 宝宝的出生状态
//		Byte babyStatus = babyInfoDto.getBabyStatus();
//		if (babyStatus != null) {
//			if (babyStatus != 0 && babyStatus != 1) {
//				throw new UserException(ResponseCode.P_PARAM_ERROR,
//						"babyStatus is error");
//			}
//		}
//
//		// 宝宝的性别
//		Byte babySex = babyInfoDto.getBabySex();
//		if (babySex != null) {
//			if (babySex != 0 && babySex != 1) {
//				throw new UserException(ResponseCode.P_PARAM_ERROR,
//						"babySex is error");
//			}
//		}
//
//		// 查看指定的baby是否存在
//		userBabyInfoDto = getBabyInfoById(babyInfoDto.getId());
//		if (null == userBabyInfoDto) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR,
//					"baby is not exist");
//		}
//
//		userBabyInfoDo = new UserBabyInfoDO();
//		BeanUtils.copyProperties(babyInfoDto, userBabyInfoDo);
//
//		int result = userBabyInfoDao.updateBabyInfo(userBabyInfoDo);
//
//		if (result != 1) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
//		}
//
//		return result;
//	}
//
//	@Override
//	public List<UserBabyInfoDTO> queryBabyInfo(Long userId)
//			throws UserException {
//		// TODO Auto-generated method stub
//		UserBabyInfoDTO userBabyInfoDto = null;
//		List<UserBabyInfoDTO> babysDto = new ArrayList<UserBabyInfoDTO>();
//
//		// 查询指定的用户是否存在
//		UserDTO userDto = userManager.getUserById(userId);
//		if (null == userDto) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR,
//					"user is not exist");
//		}
//
//		// 指定用户的baby列表Do
//		List<UserBabyInfoDO> babysDo = userBabyInfoDao.queryBabyInfo(userId);
//
//		if (null == babysDo) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR, "user no baby");
//		}
//
//		// 将do转坏为dto
//		for (UserBabyInfoDO userBabyInfoDo : babysDo) {
//			userBabyInfoDto = new UserBabyInfoDTO();
//			BeanUtils.copyProperties(userBabyInfoDo, userBabyInfoDto);
//			babysDto.add(userBabyInfoDto);
//		}
//
//		return babysDto;
//	}
//
//	@Override
//	public UserBabyInfoDTO getDefBabyInfo(Long userId) throws UserException {
//		// TODO Auto-generated method stub
//
//		UserDTO usreDto = userManager.getUserById(userId);
//		UserBabyInfoDTO babyDto = null;
//		if (null == usreDto) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR,
//					"user no exist");
//		}
//
//		UserBabyInfoDO babyDo = userBabyInfoDao.getDefBabyInfo(userId);
//		if (babyDo != null) {
//			babyDto = new UserBabyInfoDTO();
//			BeanUtils.copyProperties(babyDo, babyDto);
//		}
//
//		return babyDto;
//	}
//
//	@Override
//	public int setNonDef(Long babyId) throws UserException {
//		// TODO Auto-generated method stub
//		if (null == babyId) {
//			throw new UserException(ResponseCode.P_PARAM_NULL, "babyId is null");
//		}
//
//		if (babyId < 0) {
//			throw new UserException(ResponseCode.P_PARAM_ERROR,
//					"babyId must greater than zero");
//		}
//		int result = userBabyInfoDao.setNonDef(babyId);
//
//		if (result <= 0) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
//
//		}
//
//		return result;
//	}
//
//	@Override
//	public int deleteUserAllBaby(Long userId) throws UserException {
//		// TODO Auto-generated method stub
//		UserDTO userDto = userManager.getUserById(userId);
//		if (null == userDto) {
//			throw new UserException(ResponseCode.B_SELECT_ERROR,
//					"user is not exist");
//		}
//
//		int result = userBabyInfoDao.deleteUserAllBaby(userId);
//
//		return result;
//	}
//
//	@Override
//	public int restoreUserAllBaby(Long userId) throws UserException {
//		// TODO Auto-generated method stub
//
//		int result = userBabyInfoDao.restoreUserAllBaby(userId);
//
//		return result;
//	}
//}
