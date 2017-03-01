//package com.mockuai.usercenter.core.service.action.suppliercompany;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.UserResponse;
//import com.mockuai.usercenter.core.exception.UserException;
//import com.mockuai.usercenter.core.employee.CompanyManager;
//import com.mockuai.usercenter.core.service.RequestContext;
//import com.mockuai.usercenter.core.service.UserRequest;
//import com.mockuai.usercenter.core.service.action.Action;
//
///**
// * 删除供应商所属公司，目前系统没看到有删除供应商所属公司的需求，删除的时候，要关联供应商表
// * */
//@Service
//public class DeleteCompanyAction implements Action {
//
//	@Resource
//	private CompanyManager companyManager;
//
//	@Override
//	public UserResponse execute(RequestContext context) throws UserException {
//
//		UserRequest userRequest = context.getRequest();
//		Integer id = (Integer) userRequest.getParam("id");
//		try {
//			companyManager.deleteCompany(id);
//		} catch (UserException e) {
//			log.error(e.getMessage(), e);
//			return new UserResponse(e.getResponseCode(),
//					e.getMessage());
//		}
//
//		return new UserResponse(true);
//	}
//
//	@Override
//	public String getName() {
//
//		return ActionEnum.DELETE_SUPPLIER_COMPANY.getActionName();
//	}
//
//}
