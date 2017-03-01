//package com.mockuai.usercenter.client;
//
//import java.util.List;
//
//import com.mockuai.usercenter.common.api.Response;
//import com.mockuai.usercenter.common.dto.UserGroupDTO;
//
//public interface UserGroupClient {
//	/**
//	 * 增加用户组
//	 */
//	Response<UserGroupDTO> addUserGroup(UserGroupDTO userGroupDTO);
//
//	/**
//	 * 删除用户组
//	 */
//	Response<Boolean> delUserGroup(Integer groupId);
//
//	/**
//	 * 查询用户组
//	 */
//	Response<List> queryUserGroup();
//
//	/**
//	 * 根据用户id得到用户组
//	 */
//	Response<UserGroupDTO> getUserGroupByUserId(Long userId);
//
//	/**
//	 * 修改用户组
//	 */
//	Response<Boolean> updateUserGroup(UserGroupDTO userGroupDTO);
//}
