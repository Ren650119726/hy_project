//package com.mockuai.usercenter.core.service.action.userextra;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.UserResponse;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.SupplierExtraInfoManager;
//import com.mockuai.usercenter.core.service.RequestContext;
//import com.mockuai.usercenter.core.service.UserRequest;
//import com.mockuai.usercenter.core.service.action.Action;
//
///**
// * 供应商的审核通过
// * */
//@Service
//public class PassSupplierIdenAction implements Action {
//
//	@Resource
//	private SupplierExtraInfoManager supplierExtraManager;
//
//	@Override
//	public UserResponse execute(RequestContext context) throws UserException {
//
//		UserRequest userRequest = context.getRequest();
//		Long userId = (Long) userRequest.getParam("userId");
//
//		supplierExtraManager.passSupplierIden(userId);
//
//		return new UserResponse(true);
//	}
//
//	@Override
//	public String getName() {
//		return ActionEnum.SUPPLIER_IDENTIFIED_ACTION.getActionName();
//	}
//
//}
