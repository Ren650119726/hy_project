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
//import com.mockuai.usercenter.common.dto.UserDTO;
//import com.mockuai.usercenter.common.dto.UserGroupDTO;
//import com.mockuai.usercenter.core.dao.UserGroupDao;
//import com.mockuai.usercenter.core.domain.UserGroupDO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.UserGroupManager;
//import com.mockuai.usercenter.core.employee.UserManager;
//
//@Service
//public class UserGroupManagerImpl implements UserGroupManager {
//
//	private static final long MAX_INTEGRAL = 888888;
//	private static final int MAX_DISCOUNT = 0;
//	private static final int MIN_DISCOUNT = 100;
//
//	@Resource
//	private UserGroupDao userGroupDao;
//	@Resource
//	private UserManager userManager;
//
//	@Override
//	public UserGroupDTO addUserGroup(UserGroupDTO userGroupDto)
//			throws UserException {
//		// TODO Auto-generated method stub
//
//		if (null == userGroupDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"userGroupDTO is null");
//		}
//
//		// 新增加组的最大积分值
//		Long maxIntegral = userGroupDto.getMaxIntegral();
//		// 新增加组的折扣
//		Integer discount = userGroupDto.getDiscount();
//
//		if (null == userGroupDto.getName()) {
//			throw new UserException(ResponseCode.P_PARAM_NULL, "name is null");
//		}
//		// 等级号不能为空
//		if (null == userGroupDto.getGroupNo()) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"groupNo is null");
//		}
//
//		if (null == maxIntegral) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"maxIntegral is null");
//		}
//
//		if (maxIntegral > MAX_INTEGRAL) {
//			throw new UserException(ResponseCode.P_PARAM_ERROR,
//					"maxIntegral can't greater than MAX_INTEGRAL");
//		}
//
//		if (discount == null) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"discount is null");
//		}
//
//		if (discount < MAX_DISCOUNT) {
//			throw new UserException(ResponseCode.P_PARAM_ERROR,
//					"discount can't less than 0");
//		}
//		if (discount > MIN_DISCOUNT) {
//			throw new UserException(ResponseCode.P_PARAM_ERROR,
//					"discount can't greater than 100");
//		}
//
//		// 判断指定的等级号是否存在
//		UserGroupDO userGroupDo = userGroupDao.getUserGroupByNo(userGroupDto
//				.getGroupNo());
//
//		if (userGroupDo != null) {
//			throw new UserException(ResponseCode.P_PARAM_ERROR,
//					"groupNo is exist");
//		}
//
//		// 查询当前组中最大的积分值，将积分值设置给新增加的组的最小值
//		Long max = userGroupDao.getMaxIntegralValue();
//
//		if (null == max) {
//			max = 0L;
//		}
//
//		userGroupDto.setMinIntegral(max);
//
//		// 当前的最高等级组的max_integral大于传入的max_integral则错误
//		if (max > userGroupDto.getMaxIntegral()) {
//			throw new UserException(ResponseCode.P_PARAM_ERROR,
//					"the maximum current greater than the maximum incoming");
//		}
//
//		userGroupDo = new UserGroupDO();
//		BeanUtils.copyProperties(userGroupDto, userGroupDo);
//
//		Integer id = userGroupDao.addUserGroup(userGroupDo);
//		userGroupDto = getUserGroup(id);
//
//		return userGroupDto;
//	}
//
//	@Override
//	public UserGroupDTO compareMinIntegral(Integer minIntegral)
//			throws UserException {
//		// TODO Auto-generated method stub
//
//		UserGroupDO userGroupDo = userGroupDao.compareMinIntegral(minIntegral);
//		UserGroupDTO userGroupDto = null;
//		if (userGroupDo != null) {
//			userGroupDto = new UserGroupDTO();
//			BeanUtils.copyProperties(userGroupDo, userGroupDto);
//		}
//		return userGroupDto;
//	}
//
//	@Override
//	public UserGroupDTO getUserGroup(Integer id) throws UserException {
//		// TODO Auto-generated method stub
//		UserGroupDO userGroupDo = null;
//		UserGroupDTO userGroupDto = null;
//
//		if (null == id) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"minIntegral less than now maxIntegral");
//		}
//
//		userGroupDo = userGroupDao.getUserGroup(id);
//		if (userGroupDo != null) {
//			userGroupDto = new UserGroupDTO();
//			BeanUtils.copyProperties(userGroupDo, userGroupDto);
//		}
//		return userGroupDto;
//	}
//
//	@Override
//	public int delUserGroup(Integer groupId) throws UserException {
//		// TODO Auto-generated method stub
//
//		UserGroupDTO userGroupDto = null;
//
//		if (null == groupId) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"groupId is null");
//		}
//
//		// 得到当前等级最大的用户组
//		Integer max = userGroupDao.getMaxGroupNo();
//
//		userGroupDto = getUserGroup(groupId);
//		if (null == userGroupDto) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR,
//					"userGroup not exist");
//		}
//
//		if (null == max) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR,
//					"user_group table no record");
//		}
//
//		// 如果删除的组的不是等级最高组，则不能删除
//		if (userGroupDto.getGroupNo() < max) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR,
//					"delete error,not the largest group_no");
//		}
//
//		int result = userGroupDao.delUserGroup(groupId);
//		if (result != 1) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR, "delete error");
//		}
//		return result;
//	}
//
//	@Override
//	public int updateUserGroup(UserGroupDTO userGroupDto) throws UserException {
//
//		if (null == userGroupDto) {
//			throw new UserException(ResponseCode.P_PARAM_NULL,
//					"userGroupDto is null");
//		}
//
//		if (userGroupDto.getMaxIntegral() > MAX_INTEGRAL) {
//			throw new UserException(ResponseCode.P_PARAM_ERROR,
//					"maxIntegral can't greater than MAX_INTEGRAL");
//		}
//
//		Integer discount = userGroupDto.getDiscount();
//
//		// 折扣不能大于100小于0
//		if (discount != null) {
//			if (discount < 0) {
//				throw new UserException(ResponseCode.P_PARAM_ERROR,
//						"discount can't less than 0");
//			}
//			if (discount > 100) {
//				throw new UserException(ResponseCode.P_PARAM_ERROR,
//						"discount can't greater than 100");
//			}
//		}
//
//		UserGroupDO userGroupDo = new UserGroupDO();
//		BeanUtils.copyProperties(userGroupDto, userGroupDo);
//
//		int result = userGroupDao.updateUserGroup(userGroupDo);
//		if (result != 1) {
//			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
//		}
//
//		return result;
//	}
//
//	@Override
//	public List<UserGroupDTO> queryUserGroup() throws UserException {
//		// TODO Auto-generated method stub
//
//		List<UserGroupDO> groupDos = userGroupDao.queryUserGroup();
//		List<UserGroupDTO> groupDtos = new ArrayList<UserGroupDTO>();
//
//		// 将do转换为dto
//		if (groupDos != null) {
//			for (UserGroupDO groupDo : groupDos) {
//				UserGroupDTO c = new UserGroupDTO();
//				BeanUtils.copyProperties(groupDo, groupDo);
//				groupDtos.add(c);
//			}
//
//		}
//
//		return groupDtos;
//	}
//
//	@Override
//	public UserGroupDTO getNextUserGroup(Integer groupNo) throws UserException {
//
//		UserGroupDO groupDo = userGroupDao.getNextUserGroup(groupNo);
//		UserGroupDTO groupDto = null;
//		if (null != groupDo) {
//			groupDto = new UserGroupDTO();
//			BeanUtils.copyProperties(groupDo, groupDto);
//		}
//
//		return groupDto;
//	}
//
//	@Override
//	public UserGroupDTO getUpUserGroup(Integer groupNo) {
//
//		UserGroupDO groupDo = userGroupDao.getUpUserGroup(groupNo);
//		UserGroupDTO groupDto = null;
//		if (null != groupDo) {
//			groupDto = new UserGroupDTO();
//			BeanUtils.copyProperties(groupDo, groupDto);
//		}
//
//		return groupDto;
//	}
//}
