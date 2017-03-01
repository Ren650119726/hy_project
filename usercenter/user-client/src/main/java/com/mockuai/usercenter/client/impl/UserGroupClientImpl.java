//package com.mockuai.usercenter.client.impl;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import com.mockuai.usercenter.client.UserGroupClient;
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.*;
//import com.mockuai.usercenter.common.dto.UserGroupDTO;
//
//public class UserGroupClientImpl implements UserGroupClient {
//
//	@Resource
//	private UserDispatchService userDispatchService;
//
//	public Response<UserGroupDTO> addUserGroup(UserGroupDTO userGroupDTO) {
//		Request request = new BaseRequest();
//
//		request.setParam("userGroupDTO", userGroupDTO);
//		request.setCommand(ActionEnum.ADD_USER_GROUP.getActionName());
//
//		Response<UserGroupDTO> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<Boolean> delUserGroup(Integer groupId) {
//		Request request = new BaseRequest();
//
//		request.setParam("groupId", groupId);
//		request.setCommand(ActionEnum.DEL_USER_GROUP.getActionName());
//
//		Response<Boolean> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<List> queryUserGroup() {
//		Request request = new BaseRequest();
//		request.setCommand(ActionEnum.QUERY_USER_GROUP.getActionName());
//
//		Response<List> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<UserGroupDTO> getUserGroupByUserId(Long userId) {
//
//		Request request = new BaseRequest();
//		request.setParam("userId", userId);
//		request.setCommand(ActionEnum.GET_USER_GROUP.getActionName());
//
//		Response<UserGroupDTO> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<Boolean> updateUserGroup(UserGroupDTO userGroupDTO) {
//		Request request = new BaseRequest();
//		request.setParam("userGroupDTO", userGroupDTO);
//		request.setCommand(ActionEnum.UPDATE_USER_GROUP.getActionName());
//
//		Response<Boolean> response = userDispatchService.execute(request);
//		return response;
//	}
//
//}
