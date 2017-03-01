//package com.mockuai.usercenter.core.dao;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.core.domain.UserGroupDO;
//
//@Service
//public interface UserGroupDao {
//
//	/**
//	 * 比较当前表中是否存在比该值大的值
//	 * */
//	UserGroupDO compareMinIntegral(Integer minIntegral);
//
//	/**
//	 * 添加用户组
//	 * */
//	Integer addUserGroup(UserGroupDO userGroupDo);
//
//	/**
//	 * 根据id获取用户组信息
//	 * */
//	UserGroupDO getUserGroup(Integer id);
//
//	/**
//	 * 根据id删除用户组信息
//	 * */
//	int delUserGroup(Integer groupId);
//
//	/**
//	 * 查找指定的用户组等级号是否存在
//	 * */
//	UserGroupDO getUserGroupByNo(Integer groupNo);
//
//	/**
//	 * 获取当前max_integral最大的值
//	 * */
//	Long getMaxIntegralValue();
//
//	/**
//	 * 获取当前最大的用户组等级号
//	 * */
//	Integer getMaxGroupNo();
//
//	int updateUserGroup(UserGroupDO userGroupDo);
//
//	UserGroupDO getMiniDiffer(Integer id);
//
//	List<UserGroupDO> queryUserGroup();
//
//	UserGroupDO getUserGroupByIntegral(Long integral);
//
//	// 获得指定组等级的上一等级
//	UserGroupDO getNextUserGroup(Integer groupNo);
//
//	// 获得指定组等级的下一等级
//	UserGroupDO getUpUserGroup(Integer groupNo);
//
//}
