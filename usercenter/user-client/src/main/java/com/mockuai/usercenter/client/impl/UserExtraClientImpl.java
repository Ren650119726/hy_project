//package com.mockuai.usercenter.client.impl;
//
//import javax.annotation.Resource;
//
//import com.mockuai.usercenter.client.UserExtraClient;
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.*;
//import com.mockuai.usercenter.common.dto.SupplierExtraInfoDTO;
//import com.mockuai.usercenter.common.dto.UserExtraInfoDTO;
//
//public class UserExtraClientImpl implements UserExtraClient {
//
//	@Resource
//	private UserDispatchService userDispatchService;
//
//	public Response<SupplierExtraInfoDTO> addSupplierExtraInfo(
//			SupplierExtraInfoDTO supplierExtraDto) {
//		Request request = new BaseRequest();
//		request.setParam("supplierExtraDTO", supplierExtraDto);
//		request.setCommand(ActionEnum.ADD_SUPPLIER_EXTRA_INFO.getActionName());
//
//		Response<SupplierExtraInfoDTO> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<UserExtraInfoDTO> addUserExtraInfo(
//			UserExtraInfoDTO userExtraDto) {
//		Request request = new BaseRequest();
//		request.setParam("userExtraDTO", userExtraDto);
//		request.setCommand(ActionEnum.ADD_USER_EXTRA_INFO.getActionName());
//
//		Response<UserExtraInfoDTO> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<SupplierExtraInfoDTO> getSupplierExtraInfoByUserId(
//			Long userId) {
//		Request request = new BaseRequest();
//		request.setParam("userId", userId);
//		request.setCommand(ActionEnum.GET_SUPPLIER_EXTRA_INFO.getActionName());
//
//		Response<SupplierExtraInfoDTO> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<UserExtraInfoDTO> getUserExtraInfoByUserId(Long userId) {
//		Request request = new BaseRequest();
//		request.setParam("userId", userId);
//		request.setCommand(ActionEnum.GET_USER_EXTRA_INFO.getActionName());
//
//		Response<UserExtraInfoDTO> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<Boolean> ignoreSupplierIden(Long userId) {
//		Request request = new BaseRequest();
//		request.setParam("userId", userId);
//		request.setCommand(ActionEnum.SUPPLIER_IGNORE_IDENTIFIED_ACTION
//				.getActionName());
//		Response<Boolean> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<Boolean> passSupplierIden(Long userId) {
//		Request request = new BaseRequest();
//		request.setParam("userId", userId);
//		request.setCommand(ActionEnum.SUPPLIER_IDENTIFIED_ACTION
//				.getActionName());
//		Response<Boolean> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<Boolean> refuseSupplierIden(Long userId) {
//		Request request = new BaseRequest();
//		request.setParam("userId", userId);
//		request.setCommand(ActionEnum.SUPPLIER_FAIL_IDENTIFIED_ACTION
//				.getActionName());
//		Response<Boolean> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<Boolean> updateSupplierExtraInfo(
//			SupplierExtraInfoDTO supplierExtraDto) {
//		Request request = new BaseRequest();
//		request.setParam("supplierExtraDTO", supplierExtraDto);
//		request.setCommand(ActionEnum.UPDATE_SUPPLIER_EXTRA_INFO
//				.getActionName());
//
//		Response<Boolean> response = userDispatchService.execute(request);
//		return response;
//	}
//
//	public Response<Boolean> updateUserExtraInfo(UserExtraInfoDTO userExtraDto) {
//		Request request = new BaseRequest();
//		request.setParam("userExtraDTO", userExtraDto);
//		request.setCommand(ActionEnum.UPDATE_USER_EXTRA_INFO.getActionName());
//
//		Response<Boolean> response = userDispatchService.execute(request);
//		return response;
//	}
//
//}
