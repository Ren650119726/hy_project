package com.mockuai.usercenter.client.impl;

import javax.annotation.Resource;

import com.mockuai.usercenter.client.UserAuthClient;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.*;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.common.qto.UserAuthInfoQTO;

import java.util.List;

public class UserAuthClientImpl implements UserAuthClient {
	@Resource
	private UserDispatchService userDispatchService;

	public Response<Long> addUserAuthInfo(
			UserAuthInfoDTO authInfoDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userAuthInfoDTO", authInfoDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.ADD_USER_AUTH_INFO.getActionName());
		Response<Long> response = userDispatchService.execute(request);
		return response;
	}

	public Response<UserAuthInfoDTO> getAuthInfoByUserId(Long userId, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userId", userId);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.GET_USER_AUTH_INFO.getActionName());
		Response<UserAuthInfoDTO> response = userDispatchService.execute(request);
		return response;
	}

	public Response<List<UserAuthInfoDTO>> queryUserAuthInfo(UserAuthInfoQTO userAuthInfoQTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userAuthInfoQTO", userAuthInfoQTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.QUERY_USER_AUTH_INFO.getActionName());
		Response<List<UserAuthInfoDTO>> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Void> passUserAuth(Long userAuthId, Long userId, String remark, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userAuthId", userAuthId);
		request.setParam("userId", userId);
		request.setParam("remark", remark);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.PASS_USER_AUTH_INFO.getActionName());
		Response<Void> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Void> rejectUserAuth(Long userAuthId, Long userId, String remark, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userAuthId", userAuthId);
		request.setParam("userId", userId);
		request.setParam("remark", remark);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.REJECT_USER_AUTH_INFO.getActionName());
		Response<Void> response = userDispatchService.execute(request);
		return response;
	}

	public Response<ResponseCode> updateUserAuth(UserAuthInfoDTO userAuthInfoDTO, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userAuthInfoDTO", userAuthInfoDTO);
		request.setParam("appKey", appKey);
		request.setCommand(ActionEnum.UPDATE_USER_AUTH_INFO.getActionName());
		Response<ResponseCode> response = userDispatchService.execute(request);
		return response;
	}

	public Response<Void> passWaitForGuarantee(Long userAuthId, Long userId, Long guaranteeAmount, String appKey) {
		Request request = new BaseRequest();
		request.setParam("userAuthId",userAuthId);
		request.setParam("userId",userId);
		request.setParam("guaranteeAmount", guaranteeAmount);
		request.setParam("appKey",appKey);
		request.setCommand(ActionEnum.WAIT_FOR_GUARANTEE.getActionName());
		Response<Void> response = this.userDispatchService.execute(request);
		return response;
	}
}
