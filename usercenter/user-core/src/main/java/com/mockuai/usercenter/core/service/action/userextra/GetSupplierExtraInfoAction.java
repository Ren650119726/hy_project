//package com.mockuai.usercenter.core.service.action.userextra;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.UserResponse;
//import com.mockuai.usercenter.common.dto.SupplierExtraInfoDTO;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.SupplierExtraInfoManager;
//import com.mockuai.usercenter.core.service.RequestContext;
//import com.mockuai.usercenter.core.service.UserRequest;
//import com.mockuai.usercenter.core.service.action.Action;
//
//@Service
//public class GetSupplierExtraInfoAction implements Action {
//
//	@Resource
//	private SupplierExtraInfoManager supplierExtraManager;
//
//	@Override
//	public UserResponse execute(RequestContext context) throws UserException {
//		UserRequest userRequest = context.getRequest();
//
//		Long userId = (Long) userRequest.getParam("userId");
//
//		SupplierExtraInfoDTO supplierExtraDto = supplierExtraManager
//				.getSupplierExtraInfoByUserId(userId);
//
//		return new UserResponse(supplierExtraDto);
//	}
//
//	@Override
//	public String getName() {
//
//		return ActionEnum.GET_SUPPLIER_EXTRA_INFO.getActionName();
//	}
//
//}
