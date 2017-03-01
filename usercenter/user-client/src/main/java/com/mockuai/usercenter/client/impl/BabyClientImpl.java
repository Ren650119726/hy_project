//package com.mockuai.usercenter.client.impl;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import com.mockuai.usercenter.client.BabyClient;
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.*;
//import com.mockuai.usercenter.common.dto.UserBabyInfoDTO;
//
//public class BabyClientImpl implements BabyClient {
//
//	@Resource
//	private UserDispatchService userDispatchService;
//
//	public Response<UserBabyInfoDTO> addBabyInfo(UserBabyInfoDTO userBabyInfoDto) {
//		Request request = new BaseRequest();
//		request.setParam("userBabyDTO", userBabyInfoDto);
//		request.setCommand(ActionEnum.ADD_USER_BABY_INFO.getActionName());
//		Response<UserBabyInfoDTO> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<Boolean> deleteBabyInfo(Long userId, Long babyId) {
//		Request request = new BaseRequest();
//		request.setParam("userId", 30L);
//		request.setParam("babyId", babyId);
//		request.setCommand(ActionEnum.DELETE_USER_BABY_INFO.getActionName());
//		Response<Boolean> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<List> queryBabyInfo(Long userId) {
//		Request request = new BaseRequest();
//		request.setParam("userId", userId);
//		request.setCommand(ActionEnum.QUERY_USER_BABYS.getActionName());
//		Response<List> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<Boolean> updateBabyInfo(UserBabyInfoDTO babyInfoDTO) {
//		Request request = new BaseRequest();
//		request.setParam("userBabyDTO", babyInfoDTO);
//		request.setCommand(ActionEnum.UPDATE_USER_BABY_INFO.getActionName());
//		Response<Boolean> response = userDispatchService.execute(request);
//		return response;
//	}
//
//}
