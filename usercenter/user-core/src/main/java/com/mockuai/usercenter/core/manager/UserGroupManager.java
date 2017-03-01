//package com.mockuai.usercenter.core.manager;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.dto.UserGroupDTO;
//import com.mockuai.usercenter.core.exception.UserException;
//
//@Service
//public interface UserGroupManager {
//
//	/**
//	 * 添加用户组信息,添加时只有传入的最高值有效，最低值为上一等级的最高值
//	 * */
//	UserGroupDTO addUserGroup(UserGroupDTO userGroupDto) throws UserException;
//
//	/**
//	 * 最小值比较
//	 * */
//	UserGroupDTO compareMinIntegral(Integer minIntegral) throws UserException;
//
//	/**
//	 * 根据id，获取用户组信息
//	 * */
//	UserGroupDTO getUserGroup(Integer id) throws UserException;
//
//	/**
//	 * 根据id，删除用户组信息,删除的时候，只能删除最高等级的组，按从高到底依次删除
//	 * */
//	int delUserGroup(Integer groupId) throws UserException;
//
//	/**
//	 * 修改用户组消息
//	 * */
//	int updateUserGroup(UserGroupDTO userGroupDto) throws UserException;
//
//	/**
//	 * 查询用户组列表
//	 * */
//	List<UserGroupDTO> queryUserGroup() throws UserException;
//
//	/**
//	 * 根据用户id，获取相应的积分等级信息
//	 * */
//	UserGroupDTO getUserGroupByUserId(Long userId) throws UserException;
//
//	/**
//	 * 获得指定用户组的下一级用户组
//	 *
//	 * @return
//	 */
//	UserGroupDTO getNextUserGroup(Integer groupNo) throws UserException;
//
//	/**
//	 * 获得指定用户组的上一级用户组
//	 *
//	 * @param groupNo
//	 * @return
//	 */
//	UserGroupDTO getUpUserGroup(Integer groupNo);
//}
