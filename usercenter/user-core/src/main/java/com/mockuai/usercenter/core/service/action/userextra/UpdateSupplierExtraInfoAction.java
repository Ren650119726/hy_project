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
///**
// * 修改供应商的扩展信息
// * */
//@Service
//public class UpdateSupplierExtraInfoAction implements Action {
//
//	@Resource
//	private SupplierExtraInfoManager supplierExtraManager;
//
//	@Override
//	public UserResponse execute(RequestContext context) throws UserException {
//
//		UserRequest userRequest = context.getRequest();
//
//		SupplierExtraInfoDTO supplierExtraDto = (SupplierExtraInfoDTO) userRequest
//				.getParam("supplierExtraDTO");
//
//		supplierExtraManager.updateSupplierExtraInfo(supplierExtraDto);
//		return new UserResponse(true);
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.UPDATE_SUPPLIER_EXTRA_INFO.getActionName();
//	}
//
//}
